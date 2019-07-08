package za.co.neslotech.banking.models.account;

import lombok.Data;
import za.co.neslotech.banking.models.BankEntity;

import javax.persistence.*;
import java.math.BigDecimal;

@Entity
@Table(name = "CREDIT_CARD_LIMIT")
@Data
public class CreditCardLimit extends BankEntity {

    @Id
    @OneToOne
    @JoinColumn(name = "CLIENT_ACCOUNT_NUMBER", nullable = false)
    private Account accountNumber;

    @Column(name = "ACCOUNT_LIMIT", nullable = false)
    private BigDecimal limit;

}
