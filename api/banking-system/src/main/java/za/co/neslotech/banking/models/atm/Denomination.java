package za.co.neslotech.banking.models.atm;

import lombok.Data;
import za.co.neslotech.banking.models.BankEntity;

import javax.persistence.Id;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Table;
import java.math.BigDecimal;

@Entity
@Table(name = "DENOMINATION")
@Data
public class Denomination extends BankEntity {

    @Id
    @GeneratedValue
    @Column(name = "DENOMINATION_ID", nullable = false)
    private Integer id;

    @Column(name = "VALUE", nullable = false)
    private BigDecimal value;

    @Column(name = "DENOMINATION_TYPE_CODE")
    private DenominationType typeCode;

}
