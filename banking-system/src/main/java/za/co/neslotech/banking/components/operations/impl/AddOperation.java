package za.co.neslotech.banking.components.operations.impl;

import org.springframework.stereotype.Component;
import za.co.neslotech.banking.components.operations.IOperation;

import java.math.BigDecimal;

@Component
public class AddOperation implements IOperation {

    @Override
    public BigDecimal calculate(BigDecimal firstNumber, BigDecimal secondNumber) {
        return firstNumber.add(secondNumber);
    }

    @Override
    public String getOperator() {
        return "+";
    }

}
