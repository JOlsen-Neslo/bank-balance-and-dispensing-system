package za.co.neslotech.banking.services.impl;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import za.co.neslotech.banking.components.OperationFacade;
import za.co.neslotech.banking.exceptions.NotFoundException;
import za.co.neslotech.banking.models.account.Account;
import za.co.neslotech.banking.models.account.AccountType;
import za.co.neslotech.banking.models.account.ConversionRate;
import za.co.neslotech.banking.models.account.Currency;
import za.co.neslotech.banking.resources.IAccountResource;
import za.co.neslotech.banking.resources.IConversionRateResource;
import za.co.neslotech.banking.schema.client.ApiClient;
import za.co.neslotech.banking.schema.client.account.ClientAccount;
import za.co.neslotech.banking.schema.client.account.ClientAccountType;
import za.co.neslotech.banking.schema.client.account.ClientCurrencyAccount;
import za.co.neslotech.banking.services.IClientService;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Slf4j
@Service
public class ClientService implements IClientService {

    private final IAccountResource accountResource;
    private final IConversionRateResource rateResource;
    private final OperationFacade operationFacade;

    @Autowired
    public ClientService(IAccountResource accountResource, IConversionRateResource rateResource,
                         OperationFacade operationFacade) {
        this.accountResource = accountResource;
        this.rateResource = rateResource;
        this.operationFacade = operationFacade;
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

            Optional<ConversionRate> rateOptional = rateResource.findById(currency.getCode());
            rateOptional.ifPresent(rate -> {
                BigDecimal rateValue = rate.getRate();
                currencyAccount.setConversionRate(rateValue);

                BigDecimal balance = operationFacade.calculate(currencyAccount.getCurrencyBalance(), rateValue,
                        rate.getIndicator());
                currencyAccount.setBalance(balance.setScale(currency.getDecimals(), RoundingMode.HALF_UP));
            });

            clientAccounts.add(currencyAccount);
        });

        if (clientAccounts.isEmpty()) {
            throw new NotFoundException("No accounts to display.");
        }

        return clientAccounts;
    }

}
