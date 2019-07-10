package za.co.neslotech.banking.schema.client;

import lombok.Data;
import za.co.neslotech.banking.schema.ApiEntity;
import za.co.neslotech.banking.schema.client.account.ClientAccount;

import java.util.List;

@Data
public class ApiClient extends ApiEntity {

    private String clientId;
    private List<ClientAccount> accounts;

}
