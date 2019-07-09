package za.co.neslotech.banking.resources;

import org.springframework.data.jpa.repository.JpaRepository;
import za.co.neslotech.banking.models.account.Account;

import java.util.List;
import java.util.Optional;

public interface IAccountResource extends JpaRepository<Account, String> {

    List<Account> findAllByClientIdAndTypeTransactionalOrderByBalanceDesc(Integer clientId, boolean transactional);

    List<Account> findAllByClientIdAndTypeCodeOrderByBalanceDesc(Integer clientId, String accountTypeCode);

}
