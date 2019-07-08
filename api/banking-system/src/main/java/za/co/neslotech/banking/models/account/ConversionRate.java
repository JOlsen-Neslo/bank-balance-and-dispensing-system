package za.co.neslotech.banking.models.account;

import lombok.Data;
import za.co.neslotech.banking.models.BankEntity;

import javax.persistence.Id;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.math.BigDecimal;

@Entity
@Table(name = "CURRENCY_CONVERSION_RATE")
@Data
public class ConversionRate extends BankEntity {

    @Id
    @Column(name = "CURRENCY_CODE", nullable = false)
    private String currencyCode;

    @Column(name = "CONVERSION_INDICATOR", nullable = false)
    private String indicator;

    @Column(name = "RATE", nullable = false)
    private BigDecimal rate;

}
