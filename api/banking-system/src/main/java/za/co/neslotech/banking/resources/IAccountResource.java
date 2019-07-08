package za.co.neslotech.banking.resources;

import org.springframework.data.jpa.repository.JpaRepository;
import za.co.neslotech.banking.models.account.Account;

import java.util.List;
import java.util.Optional;

public interface IAccountResource extends JpaRepository<Account, Integer> {

    Optional<Account> findByAccountNumber(String accountNumber);

    List<Account> findAllByClientIdOrderByBalanceDesc(Integer clientId);

}
