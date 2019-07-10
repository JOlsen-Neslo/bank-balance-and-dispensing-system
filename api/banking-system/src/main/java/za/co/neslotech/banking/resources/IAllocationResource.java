package za.co.neslotech.banking.resources;

import org.springframework.data.jpa.repository.JpaRepository;
import za.co.neslotech.banking.models.atm.Allocation;

import java.util.List;

public interface IAllocationResource extends JpaRepository<Allocation, Integer> {

    List<Allocation> findAllByAtmIdAndDenominationTypeCodeCode(Integer atmId, char typeCode);

}
