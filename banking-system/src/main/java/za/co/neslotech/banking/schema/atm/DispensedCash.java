package za.co.neslotech.banking.schema.atm;

import lombok.Data;
import za.co.neslotech.banking.schema.ApiEntity;

import java.math.BigDecimal;
import java.util.Map;

@Data
public class DispensedCash extends ApiEntity {

    private Map<BigDecimal, Integer> dispensedAmounts;

}
