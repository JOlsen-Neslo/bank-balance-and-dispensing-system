package za.co.neslotech.banking.components;

import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.Ordered;
import za.co.neslotech.banking.models.account.Account;

import java.math.BigDecimal;

@NoArgsConstructor
@Setter
public abstract class CardLimitProcessor implements Ordered {

    protected static final String SUBTRACT_OPERATOR = "-";
    protected static final String ADD_OPERATOR = "+";

    protected CardLimitProcessor nextProcessor;

    protected CardLimitProcessor(CardLimitProcessor nextProcessor) {
        this.nextProcessor = nextProcessor;
    }

    public abstract BigDecimal calculateNewBalance(Account account, BigDecimal withdrawalAmount);

}
