package za.co.neslotech.banking.resources;

import org.springframework.data.jpa.repository.JpaRepository;
import za.co.neslotech.banking.models.client.Client;

public interface IClientResource extends JpaRepository<Client, Integer> {
}
