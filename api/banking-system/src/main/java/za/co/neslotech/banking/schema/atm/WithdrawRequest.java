package za.co.neslotech.banking.schema.atm;

import lombok.Data;
import za.co.neslotech.banking.schema.ApiRequest;

import java.math.BigDecimal;

@Data
public class WithdrawRequest extends ApiRequest {

    private String atmId;
    private BigDecimal amount;

}
