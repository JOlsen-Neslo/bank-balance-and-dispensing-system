package za.co.neslotech.banking.schema.client.account;

import lombok.Data;
import za.co.neslotech.banking.schema.ApiEntity;

import java.math.BigDecimal;

@Data
public class ClientAccount extends ApiEntity {

    private String accountNumber;
    private ClientAccountType accountType;
    private String currency;
    private BigDecimal balance;

}
