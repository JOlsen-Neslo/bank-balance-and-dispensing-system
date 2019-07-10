package za.co.neslotech.banking.services.impl;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.AnnotationAwareOrderComparator;
import org.springframework.stereotype.Service;
import za.co.neslotech.banking.components.CardLimitProcessor;
import za.co.neslotech.banking.components.OperationFacade;
import za.co.neslotech.banking.exceptions.NotFoundException;
import za.co.neslotech.banking.models.account.Account;
import za.co.neslotech.banking.models.account.AccountType;
import za.co.neslotech.banking.models.account.ConversionRate;
import za.co.neslotech.banking.models.account.Currency;
import za.co.neslotech.banking.resources.IAccountResource;
import za.co.neslotech.banking.schema.client.ApiClient;
import za.co.neslotech.banking.schema.client.account.ClientAccount;
import za.co.neslotech.banking.schema.client.account.ClientAccountType;
import za.co.neslotech.banking.schema.client.account.ClientCurrencyAccount;
import za.co.neslotech.banking.services.IClientService;

import javax.annotation.PostConstruct;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Slf4j
@Service
public class ClientService implements IClientService {

    private final IAccountResource accountResource;
    private final OperationFacade operationFacade;
    private final List<CardLimitProcessor> processors;

    @Autowired
    public ClientService(IAccountResource accountResource, OperationFacade operationFacade,
                         List<CardLimitProcessor> processors) {
        this.accountResource = accountResource;
        this.operationFacade = operationFacade;
        this.processors = processors;
    }

    @PostConstruct
    public void initialize() {
        processors.sort(AnnotationAwareOrderComparator.INSTANCE);
    }

    @Override
    public List<ClientAccount> retrieveAccounts(ApiClient apiClient) {
        log.debug("ClientService :: retrieveAccounts :: " + apiClient.getClientId());

        Integer id = Integer.valueOf(apiClient.getClientId());
        List<Account> accounts = accountResource.findAllByClientIdAndTypeTransactionalOrderByBalanceDesc(id, true);

        List<ClientAccount> clientAccounts = new ArrayList<>();
        accounts.forEach(account -> {
            ClientAccount clientAccount = new ClientAccount();
            AccountType accountType = account.getType();

            clientAccount.setAccountNumber(account.getAccountNumber());
            clientAccount.setAccountType(ClientAccountType.fromCode(accountType.getCode()));

            Currency currency = account.getCurrencyCode();
            clientAccount.setCurrency(currency.getCode());
            clientAccount.setBalance(account.getBalance().setScale(currency.getDecimals(), RoundingMode.HALF_UP));

            clientAccounts.add(clientAccount);
        });

        if (clientAccounts.isEmpty()) {
            throw new NotFoundException("No accounts to display.");
        }

        return clientAccounts;
    }

    @Override
    public List<ClientAccount> retrieveCurrencyAccounts(ApiClient apiClient) {
        log.debug("ClientService :: retrieveCurrencyAccounts :: " + apiClient.getClientId());

        Integer id = Integer.valueOf(apiClient.getClientId());
        List<Account> accounts = accountResource
                .findAllByClientIdAndTypeCodeOrderByBalanceDesc(id, ClientAccountType.FOREIGN_CURRENCY.toString());

        List<ClientAccount> clientAccounts = new ArrayList<>();
        accounts.forEach(account -> {
            ClientCurrencyAccount currencyAccount = new ClientCurrencyAccount();
            AccountType accountType = account.getType();

            currencyAccount.setAccountNumber(account.getAccountNumber());
            currencyAccount.setAccountType(ClientAccountType.fromCode(accountType.getCode()));

            Currency currency = account.getCurrencyCode();
            currencyAccount.setCurrency(currency.getCode());
            currencyAccount.setCurrencyBalance(account.getBalance().setScale(currency.getDecimals(), RoundingMode.HALF_UP));

            ConversionRate rate = currency.getConversionRate();
            BigDecimal rateValue = rate.getRate();
            currencyAccount.setConversionRate(rateValue);

            BigDecimal balance = operationFacade.calculate(currencyAccount.getCurrencyBalance(), rateValue,
                    rate.getIndicator());
            currencyAccount.setBalance(balance.setScale(currency.getDecimals(), RoundingMode.HALF_UP));

            clientAccounts.add(currencyAccount);
        });

        if (clientAccounts.isEmpty()) {
            throw new NotFoundException("No accounts to display.");
        }

        return clientAccounts;
    }

    @Override
    public ClientAccount checkAccountBalance(ApiClient apiClient, ClientAccount clientAccount) {
        log.debug("ClientService :: checkAccountBalance :: " + apiClient.getClientId()
                + " :: " + clientAccount.getAccountNumber());

        Integer id = Integer.valueOf(apiClient.getClientId());
        String accountNumber = clientAccount.getAccountNumber();

        Optional<Account> accountOptional = accountResource.findByAccountNumberAndClientId(accountNumber, id);
        if (!accountOptional.isPresent()) {
            throw new NotFoundException("This client does not have an account with the supplied number.");
        }

        Account account = accountOptional.get();

        CardLimitProcessor cardLimitProcessor = createChainOfResponsibility();
        BigDecimal newBalance = cardLimitProcessor.calculateNewBalance(account, clientAccount.getBalance());
        clientAccount.setBalance(newBalance);

        return clientAccount;
    }

    @Override
    public ClientAccount updateBalance(ClientAccount clientAccount) {
        log.debug("ClientService :: updateBalance :: " + clientAccount.getAccountNumber());

        String accountNumber = clientAccount.getAccountNumber();
        Optional<Account> accountOptional = accountResource.findById(accountNumber);
        if (!accountOptional.isPresent()) {
            throw new NotFoundException("This client does not have an account with the supplied number.");
        }

        Account account = accountOptional.get();
        account.setBalance(clientAccount.getBalance()
                .setScale(account.getCurrencyCode().getDecimals(), RoundingMode.HALF_UP));

        accountResource.saveAndFlush(account);
        return clientAccount;
    }

    private CardLimitProcessor createChainOfResponsibility() {
        for (int x = 0; x < processors.size(); x++) {
            CardLimitProcessor processor = processors.get(x);
            if (processors.size() <= (x + 1)) {
                break;
            }

            CardLimitProcessor secondProcessor = processors.get(x + 1);
            processor.setNextProcessor(secondProcessor);
        }

        return processors.get(0);
    }

}
