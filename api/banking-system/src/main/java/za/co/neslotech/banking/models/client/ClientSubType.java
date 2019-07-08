package za.co.neslotech.banking.models.client;

import lombok.Data;
import za.co.neslotech.banking.models.BankEntity;

import javax.persistence.*;

@Entity
@Table(name = "CLIENT_SUB_TYPE")
@Data
public class ClientSubType extends BankEntity {

    @Id
    @Column(name = "CLIENT_SUB_TYPE_CODE", nullable = false)
    private String subTypeCode;

    @Column(name = "DESCRIPTION", nullable = false)
    private String description;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "CLIENT_TYPE_CODE", nullable = false)
    private ClientType clientType;

}
