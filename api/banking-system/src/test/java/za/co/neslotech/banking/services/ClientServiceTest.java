package za.co.neslotech.banking.services;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import za.co.neslotech.banking.exceptions.NotFoundException;
import za.co.neslotech.banking.schema.client.ApiClient;
import za.co.neslotech.banking.schema.client.ClientAccount;

import java.util.List;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ClientServiceTest {

    @Autowired
    private IClientService clientService;

    @Test
    public void testRetrieveAccounts() {
        ApiClient apiClient = new ApiClient();
        apiClient.setClientId("1");

        List<ClientAccount> clientAccounts = clientService.retrieveAccounts(apiClient);
        assertFalse(clientAccounts.isEmpty());

        clientAccounts.forEach(clientAccount -> {
            assertNotNull(clientAccount.getAccountNumber());
            assertNotNull(clientAccount.getAccountType());
            assertNotNull(clientAccount.getBalance());
            assertNotNull(clientAccount.getCurrency());
        });
    }

    @Test(expected = NotFoundException.class)
    public void testRetrieveAccountsEmpty() {
        ApiClient apiClient = new ApiClient();
        apiClient.setClientId("45");

        clientService.retrieveAccounts(apiClient);
    }

}
