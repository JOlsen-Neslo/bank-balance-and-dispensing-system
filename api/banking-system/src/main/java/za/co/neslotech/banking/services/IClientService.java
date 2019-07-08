package za.co.neslotech.banking.services;

import za.co.neslotech.banking.schema.client.ApiClient;
import za.co.neslotech.banking.schema.client.ClientAccount;

import java.util.List;

public interface IClientService {

    List<ClientAccount> retrieveAccounts(ApiClient apiClient);

}
