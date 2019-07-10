package za.co.neslotech.banking.resources;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit4.SpringRunner;
import za.co.neslotech.banking.models.atm.Allocation;
import za.co.neslotech.banking.models.atm.Denomination;

import java.util.List;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@DataJpaTest
public class AllocationResourceTest {

    @Autowired
    private IAllocationResource allocationResource;

    @Test
    public void testFindAll() {
        List<Allocation> allocations = allocationResource.findAll();
        assertFalse(allocations.isEmpty());
    }

    @Test
    public void testFindAllByAtmIdAndDenominationTypeCodeCode() {
        List<Allocation> allocations = allocationResource.findAllByAtmIdAndDenominationTypeCodeCode(1, 'N');
        assertFalse(allocations.isEmpty());

        allocations.forEach(allocation -> {
            assertNotNull(allocation.getId());
            assertNotNull(allocation.getAtm());
            assertNotNull(allocation.getCount());

            Denomination denomination = allocation.getDenomination();
            assertNotNull(denomination);

            assertEquals('N', denomination.getTypeCode().getCode());
        });
    }

}
