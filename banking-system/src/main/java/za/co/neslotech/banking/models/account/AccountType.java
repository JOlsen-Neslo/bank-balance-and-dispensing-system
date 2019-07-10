package za.co.neslotech.banking.models.account;

import lombok.Data;
import za.co.neslotech.banking.models.BankEntity;

import javax.persistence.Id;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "ACCOUNT_TYPE")
@Data
public class AccountType extends BankEntity {

    @Id
    @Column(name = "ACCOUNT_TYPE_CODE", nullable = false)
    private String code;

    @Column(name = "DESCRIPTION", nullable = false)
    private String description;

    @Column(name = "TRANSACTIONAL", unique = false)
    private Boolean transactional;

}
