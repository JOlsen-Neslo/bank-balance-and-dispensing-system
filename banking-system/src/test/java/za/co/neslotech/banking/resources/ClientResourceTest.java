package za.co.neslotech.banking.resources;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit4.SpringRunner;
import za.co.neslotech.banking.models.client.Client;
import za.co.neslotech.banking.models.client.ClientSubType;
import za.co.neslotech.banking.models.client.ClientType;

import java.util.List;
import java.util.Optional;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@DataJpaTest
public class ClientResourceTest {

    @Autowired
    private IClientResource clientResource;

    @Test
    public void testFindAll() {
        List<Client> clients = clientResource.findAll();
        assertFalse(clients.isEmpty());
    }

    @Test
    public void testRetrieveClientById() {
        Optional<Client> clientOptional = clientResource.findById(1);
        if (!clientOptional.isPresent()) {
            fail("A client could not be found with the ID supplied.");
        }

        Client client = clientOptional.get();
        Integer id = client.getId();
        if (id == null) {
            fail("The client found does not have and ID set.");
        }

        assertEquals(1L, id.longValue());

        ClientSubType clientSubType = client.getTypeCode();
        assertNotNull(clientSubType);

        ClientType clientType = clientSubType.getClientType();
        assertNotNull(clientType);
    }

}
