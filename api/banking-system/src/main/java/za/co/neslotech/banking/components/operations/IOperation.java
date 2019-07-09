package za.co.neslotech.banking.components.operations;

import java.math.BigDecimal;

public interface IOperation {

    BigDecimal calculate(BigDecimal firstNumber, BigDecimal secondNumber);

    String getOperator();

}
