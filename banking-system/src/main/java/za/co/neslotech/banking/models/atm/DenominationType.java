package za.co.neslotech.banking.models.atm;

import lombok.Data;
import za.co.neslotech.banking.models.BankEntity;

import javax.persistence.Id;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Table;

@Entity
@Table(name = "DENOMINATION_TYPE")
@Data
public class DenominationType extends BankEntity {

    @Id
    @GeneratedValue
    @Column(name = "DENOMINATION_TYPE_CODE", nullable = false)
    private char code;

    @Column(name = "DESCRIPTION", nullable = false)
    private String description;

}
