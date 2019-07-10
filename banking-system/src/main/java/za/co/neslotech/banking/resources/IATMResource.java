package za.co.neslotech.banking.resources;

import org.springframework.data.jpa.repository.JpaRepository;
import za.co.neslotech.banking.models.atm.ATM;

public interface IATMResource extends JpaRepository<ATM, Integer> {
}
