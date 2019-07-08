package za.co.neslotech.banking.models.atm;

import lombok.Data;
import za.co.neslotech.banking.models.BankEntity;

import javax.persistence.Id;

import javax.persistence.*;

@Entity
@Table(name = "ATM_ALLOCATION")
@Data
public class Allocation extends BankEntity {

    @Id
    @GeneratedValue
    @Column(name = "ATM_ALLOCATION_ID", nullable = false)
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "ATM_ID", nullable = false)
    private ATM atm;

    @Column(name = "DENOMINATION_ID", nullable = false)
    private String denomination;

    @Column(name = "COUNT", nullable = false)
    private Integer count;

}
