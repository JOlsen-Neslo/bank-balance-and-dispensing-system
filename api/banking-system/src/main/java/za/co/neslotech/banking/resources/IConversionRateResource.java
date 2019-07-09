package za.co.neslotech.banking.resources;

import org.springframework.data.jpa.repository.JpaRepository;
import za.co.neslotech.banking.models.account.ConversionRate;

public interface IConversionRateResource extends JpaRepository<ConversionRate, String> {
}
