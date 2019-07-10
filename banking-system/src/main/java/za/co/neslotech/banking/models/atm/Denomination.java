package za.co.neslotech.banking.models.atm;

import lombok.Data;
import za.co.neslotech.banking.models.BankEntity;

import javax.persistence.*;

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

    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "DENOMINATION_TYPE_CODE", nullable = false)
    private DenominationType typeCode;

}
