package za.co.neslotech.banking.resources;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit4.SpringRunner;
import za.co.neslotech.banking.models.account.Account;
import za.co.neslotech.banking.models.account.AccountType;
import za.co.neslotech.banking.models.client.Client;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@DataJpaTest
public class AccountResourceTest {

    @Autowired
    private IAccountResource accountResource;

    @Test
    public void testRetrieveAll() {
        List<Account> accounts = accountResource.findAll();
        assertFalse(accounts.isEmpty());
    }

    @Test
    public void testRetrieveAccountByAccountNumber() {
        Optional<Account> accountOptional = accountResource.findByAccountNumber("1053664521");
        if (!accountOptional.isPresent()) {
            fail("A account could not be found with the ID supplied.");
        }

        Account account = accountOptional.get();
        assertEquals("1053664521", account.getAccountNumber());

        AccountType accountType = account.getType();
        assertNotNull(accountType);

        Client client = account.getClient();
        assertNotNull(client);
    }

    @Test
    public void testRetrieveAccountsByClientIdDesc() {
        List<Account> accounts = accountResource.findAllByClientIdOrderByBalanceDesc(1);
        assertFalse(accounts.isEmpty());

        BigDecimal amount = null;
        for (Account account : accounts) {
            if (amount == null) {
                amount = account.getBalance();
                continue;
            }

            if (amount.compareTo(account.getBalance()) < 0) {
                fail("The list of accounts are not sorted correctly.");
            }
        }
    }

}
