package za.co.neslotech.banking.components.operations.impl;

import org.springframework.stereotype.Component;
import za.co.neslotech.banking.components.operations.IOperation;

import java.math.BigDecimal;

@Component
public class MultiplyOperation implements IOperation {

    @Override
    public BigDecimal calculate(BigDecimal firstNumber, BigDecimal secondNumber) {
        return firstNumber.multiply(secondNumber);
    }

    @Override
    public String getOperator() {
        return "*";
    }

}
