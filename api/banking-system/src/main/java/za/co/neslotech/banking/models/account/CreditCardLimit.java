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
    @Column(name = "CLIENT_ACCOUNT_NUMBER", nullable = false)
    private String accountNumber;

    @OneToOne
    @MapsId
    @JoinColumn(name = "CLIENT_ACCOUNT_NUMBER", nullable = false)
    private Account account;

    @Column(name = "ACCOUNT_LIMIT", nullable = false)
    private BigDecimal limit;

}
