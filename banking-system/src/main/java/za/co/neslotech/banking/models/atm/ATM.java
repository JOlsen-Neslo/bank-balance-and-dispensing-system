package za.co.neslotech.banking.models.atm;

import lombok.Data;
import za.co.neslotech.banking.models.BankEntity;

import javax.persistence.Id;

import javax.persistence.*;

@Entity
@Table(name = "ATM")
@Data
public class ATM extends BankEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ATM_ID", nullable = false)
    private Integer id;

    @Column(name = "NAME", nullable = false)
    private String name;

    @Column(name = "LOCATION", nullable = false)
    private String location;

}
