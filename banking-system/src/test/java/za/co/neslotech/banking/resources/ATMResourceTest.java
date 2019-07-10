package za.co.neslotech.banking.resources;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit4.SpringRunner;
import za.co.neslotech.banking.models.atm.ATM;

import java.util.List;
import java.util.Optional;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@DataJpaTest
public class ATMResourceTest {

    @Autowired
    private IATMResource atmResource;

    @Test
    public void testFindAll() {
        List<ATM> atms = atmResource.findAll();
        assertFalse(atms.isEmpty());
    }

    @Test
    public void testRetrieveAccountByAccountNumber() {
        Optional<ATM> atmOptional = atmResource.findById(1);
        if (!atmOptional.isPresent()) {
            fail("A atm could not be found with the ID supplied.");
        }

        ATM atm = atmOptional.get();
        Integer id = atm.getId();
        if (id == null) {
            fail("The atm found does not have and ID set.");
        }

        assertEquals(1L, id.longValue());
    }

}
