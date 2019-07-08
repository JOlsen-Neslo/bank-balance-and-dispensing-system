package za.co.neslotech.banking.models.account;

import lombok.Data;
import javax.persistence.Id;

import za.co.neslotech.banking.models.BankEntity;
import za.co.neslotech.banking.models.client.Client;

import javax.persistence.*;
import java.math.BigDecimal;

@Entity
@Table(name = "CLIENT_ACCOUNT")
@Data
public class Account extends BankEntity {

    @Id
    @Column(name = "CLIENT_ACCOUNT_NUMBER", nullable = false)
    private String accountNumber;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "CLIENT_ID", nullable = false)
    private Client client;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ACCOUNT_TYPE_CODE", nullable = false)
    private AccountType type;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "CURRENCY_CODE", nullable = false)
    private Currency currencyCode;

    @Column(name = "DISPLAY_BALANCE")
    private BigDecimal balance;

}
