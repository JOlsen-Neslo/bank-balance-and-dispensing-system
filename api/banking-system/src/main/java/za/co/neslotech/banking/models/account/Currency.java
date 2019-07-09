package za.co.neslotech.banking.models.account;

import lombok.Data;
import za.co.neslotech.banking.models.BankEntity;

import javax.persistence.*;

@Entity
@Table(name = "CURRENCY")
@Data
public class Currency extends BankEntity {

    @Id
    @Column(name = "CURRENCY_CODE", nullable = false)
    private String code;

    @Column(name = "DECIMAL_PLACES", nullable = false)
    private Integer decimals;

    @Column(name = "DESCRIPTION", nullable = false)
    private String description;

}
