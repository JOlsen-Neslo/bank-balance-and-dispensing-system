package za.co.neslotech.banking.components.operations.impl;

import org.springframework.stereotype.Component;
import za.co.neslotech.banking.components.operations.IOperation;

import java.math.BigDecimal;
import java.math.RoundingMode;

@Component
public class DivideOperation implements IOperation {

    @Override
    public BigDecimal calculate(BigDecimal firstNumber, BigDecimal secondNumber) {
        return firstNumber.divide(secondNumber, RoundingMode.HALF_UP);
    }

    @Override
    public String getOperator() {
        return "/";
    }

}
