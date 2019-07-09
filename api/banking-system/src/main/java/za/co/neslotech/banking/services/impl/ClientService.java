package za.co.neslotech.banking.services.impl;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import za.co.neslotech.banking.exceptions.NotFoundException;
import za.co.neslotech.banking.models.account.Account;
import za.co.neslotech.banking.models.account.AccountType;
import za.co.neslotech.banking.resources.IAccountResource;
import za.co.neslotech.banking.schema.client.ApiClient;
import za.co.neslotech.banking.schema.client.ClientAccount;
import za.co.neslotech.banking.services.IClientService;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@Service
public class ClientService implements IClientService {

    private final IAccountResource accountResource;

    @Autowired
    public ClientService(IAccountResource accountResource) {
        this.accountResource = accountResource;
    }

    @Override
    public List<ClientAccount> retrieveAccounts(ApiClient apiClient) {
        Integer id = Integer.valueOf(apiClient.getClientId());
        List<Account> accounts = accountResource.findAllByClientIdOrderByBalanceDesc(id);

        List<ClientAccount> clientAccounts = new ArrayList<>();
        accounts.forEach(account -> {
            ClientAccount clientAccount = new ClientAccount();
            AccountType accountType = account.getType();

            if (Boolean.TRUE.equals(accountType.getTransactional())) {
                clientAccount.setAccountNumber(account.getAccountNumber());
                clientAccount.setAccountType(accountType.getDescription());
                clientAccount.setCurrency(account.getCurrencyCode().getDescription());
                clientAccount.setBalance(account.getBalance());

                clientAccounts.add(clientAccount);
            }
        });

        if (clientAccounts.isEmpty()) {
            throw new NotFoundException("No accounts to display.");
        }

        return clientAccounts;
    }

}
