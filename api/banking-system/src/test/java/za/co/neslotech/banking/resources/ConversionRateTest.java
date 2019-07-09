package za.co.neslotech.banking.resources;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit4.SpringRunner;
import za.co.neslotech.banking.models.account.Account;
import za.co.neslotech.banking.models.account.ConversionRate;

import java.util.List;
import java.util.Optional;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.fail;

@RunWith(SpringRunner.class)
@DataJpaTest
public class ConversionRateTest {

    @Autowired
    private IConversionRateResource rateResource;

    @Test
    public void testFindAll() {
        List<ConversionRate> rates = rateResource.findAll();
        assertFalse(rates.isEmpty());
    }

    @Test
    public void testFindById() {
        Optional<ConversionRate> rateOptional = rateResource.findById("ZAR");
        if (!rateOptional.isPresent()) {
            fail("A conversion rate could not be found for the specified ID.");
        }

        ConversionRate rate = rateOptional.get();
        assertEquals("ZAR", rate.getCurrencyCode());
    }

}
