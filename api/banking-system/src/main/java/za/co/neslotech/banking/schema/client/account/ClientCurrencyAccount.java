package za.co.neslotech.banking.schema.client.account;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class ClientCurrencyAccount extends ClientAccount {

    private BigDecimal conversionRate;
    private BigDecimal currencyBalance;

}
