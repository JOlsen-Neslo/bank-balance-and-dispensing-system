package za.co.neslotech.banking.components;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import za.co.neslotech.banking.components.operations.IOperation;
import za.co.neslotech.banking.exceptions.CalculationException;

import java.math.BigDecimal;
import java.util.List;

@Component
public class OperationFacade {

    private final List<IOperation> operations;

    @Autowired
    public OperationFacade(List<IOperation> operations) {
        this.operations = operations;
    }

    public BigDecimal calculate(BigDecimal firstNumber, BigDecimal secondNumber, String operationType) {
        for (IOperation operation : operations) {
            if (operation.getOperator().equals(operationType)) {
                return operation.calculate(firstNumber, secondNumber);
            }
        }

        throw new CalculationException("Operation not found");
    }
}
