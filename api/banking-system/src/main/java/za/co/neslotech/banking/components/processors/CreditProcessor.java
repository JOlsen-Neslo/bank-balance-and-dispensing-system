package za.co.neslotech.banking.components.processors;

import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import za.co.neslotech.banking.components.CardLimitProcessor;
import za.co.neslotech.banking.components.OperationFacade;
import za.co.neslotech.banking.exceptions.CalculationException;
import za.co.neslotech.banking.models.account.Account;
import za.co.neslotech.banking.models.account.AccountType;
import za.co.neslotech.banking.models.account.CreditCardLimit;
import za.co.neslotech.banking.schema.client.account.ClientAccountType;

import java.math.BigDecimal;

@NoArgsConstructor
public class CreditProcessor extends CardLimitProcessor {

    @Autowired
    private OperationFacade operationFacade;

    public CreditProcessor(CardLimitProcessor nextProcessor) {
        super(nextProcessor);
    }

    @Override
    public BigDecimal calculateNewBalance(Account account, BigDecimal withdrawalAmount) {
        AccountType accountType = account.getType();
        if (accountType == null) {
            throw new CalculationException("The account does not have a linked account type.");
        }

        if (ClientAccountType.CREDIT_CARD.toString().equalsIgnoreCase(accountType.getCode())) {
            CreditCardLimit limit = account.getCardLimit();
            BigDecimal outcome = operationFacade.calculate(account.getBalance(), withdrawalAmount, SUBTRACT_OPERATOR);
            if (limit.getLimit().compareTo(outcome) < 0) {
                throw new CalculationException("Insufficient funds.");
            }

            return outcome;
        } else if (nextProcessor != null) {
            return nextProcessor.calculateNewBalance(account, withdrawalAmount);
        }

        return null;
    }

    @Override
    public int getOrder() {
        return 1;
    }

}
