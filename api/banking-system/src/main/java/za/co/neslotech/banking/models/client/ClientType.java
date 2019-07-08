package za.co.neslotech.banking.models.client;

import lombok.Data;
import za.co.neslotech.banking.models.BankEntity;

import javax.persistence.*;

@Entity
@Table(name = "CLIENT_TYPE")
@Data
public class ClientType extends BankEntity {

    @Id
    @Column(name = "CLIENT_TYPE_CODE", nullable = false)
    private String code;

    @Column(name = "DESCRIPTION", nullable = false)
    private String description;

}
