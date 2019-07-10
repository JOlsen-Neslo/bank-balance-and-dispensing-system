package za.co.neslotech.banking.models.client;

import lombok.Data;
import za.co.neslotech.banking.models.BankEntity;

import javax.persistence.*;
import java.sql.Date;

@Entity
@Table(name = "CLIENT")
@Data
public class Client extends BankEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "CLIENT_ID")
    private Integer id;

    @Column(name = "TITLE")
    private String title;

    @Column(name = "NAME", nullable = false)
    private String name;

    @Column(name = "SURNAME")
    private String surname;

    @Column(name = "DOB", nullable = false)
    private Date dateOfBirth;

    @OneToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinColumn(name = "CLIENT_SUB_TYPE_CODE", nullable = false)
    private ClientSubType typeCode;

}
