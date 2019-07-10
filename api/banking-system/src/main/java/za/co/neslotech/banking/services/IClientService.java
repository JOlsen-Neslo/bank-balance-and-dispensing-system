package za.co.neslotech.banking.services;

import za.co.neslotech.banking.schema.client.ApiClient;
import za.co.neslotech.banking.schema.client.account.ClientAccount;

import java.util.List;

public interface IClientService {

    List<ClientAccount> retrieveAccounts(ApiClient apiClient);

    List<ClientAccount> retrieveCurrencyAccounts(ApiClient apiClient);

    ClientAccount checkAccountBalance(ApiClient apiClient, ClientAccount clientAccount);

    ClientAccount updateBalance(ClientAccount account);

}
