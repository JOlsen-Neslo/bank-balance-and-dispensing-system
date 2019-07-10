package za.co.neslotech.banking.services.impl;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import za.co.neslotech.banking.exceptions.CalculationException;
import za.co.neslotech.banking.exceptions.NotFoundException;
import za.co.neslotech.banking.models.atm.ATM;
import za.co.neslotech.banking.models.atm.Allocation;
import za.co.neslotech.banking.models.atm.Denomination;
import za.co.neslotech.banking.resources.IATMResource;
import za.co.neslotech.banking.resources.IAllocationResource;
import za.co.neslotech.banking.schema.atm.DispensedCash;
import za.co.neslotech.banking.schema.atm.WithdrawRequest;
import za.co.neslotech.banking.services.IATMService;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.*;

@Service
@Slf4j
public class ATMService implements IATMService {

    private final IATMResource atmResource;
    private final IAllocationResource allocationResource;

    @Autowired
    public ATMService(IATMResource atmResource, IAllocationResource allocationResource) {
        this.atmResource = atmResource;
        this.allocationResource = allocationResource;
    }

    @Override
    public DispensedCash dispenseCash(WithdrawRequest withdrawRequest) {
        log.debug("ATMService :: dispenseCash :: " + withdrawRequest.getAtmId());

        Integer atmId = Integer.valueOf(withdrawRequest.getAtmId());
        Optional<ATM> atmOptional = atmResource.findById(atmId);
        if (!atmOptional.isPresent()) {
            throw new NotFoundException("ATM not registered or unfunded.");
        }

        List<Allocation> allocations = allocationResource
                .findAllByAtmIdAndDenominationTypeCodeCode(atmId, 'N');
        if (allocations.isEmpty()) {
            throw new NotFoundException("ATM not registered or unfunded.");
        }

        Map<BigDecimal, Integer> amountsForCount = new TreeMap<>();
        amountsForCount = ((TreeMap<BigDecimal, Integer>) amountsForCount).descendingMap();
        for (Allocation allocation : allocations) {
            int numberOfDenominations = allocation.getCount();
            Denomination denomination = allocation.getDenomination();

            BigDecimal value = denomination.getValue();
            amountsForCount.put(value, numberOfDenominations);
        }

        BigDecimal amountToDispense = withdrawRequest.getAmount();

        Map<BigDecimal, Integer> dispensedAmounts = new LinkedHashMap<>();
        BigDecimal zero = BigDecimal.ZERO.setScale(2, RoundingMode.DOWN);

        while (!zero.equals(amountToDispense)) {
            int counter = 1;
            for (Map.Entry<BigDecimal, Integer> entry : amountsForCount.entrySet()) {
                if (zero.equals(amountToDispense)) {
                    break;
                }

                BigDecimal denomination = entry.getKey();
                Integer count = entry.getValue();

                Integer dispensedNotes = dispensedAmounts.get(denomination);
                if (dispensedNotes != null && dispensedNotes == 40) {
                    counter++;
                    continue;
                }

                int numberNeeded = amountToDispense.divide(denomination, RoundingMode.DOWN).intValue();
                if (BigDecimal.ZERO.intValue() == numberNeeded) {
                    counter++;
                    continue;
                }

                numberNeeded = applyRules(numberNeeded, count, counter, amountsForCount);

                dispensedNotes = dispensedAmounts.get(denomination);
                if (dispensedNotes == null) {
                    dispensedNotes = numberNeeded;
                } else {
                    dispensedNotes += numberNeeded;
                }

                if (dispensedNotes > 40) {
                    int valueToChange = dispensedNotes - 40;
                    dispensedNotes = 40;
                    numberNeeded -= valueToChange;
                }

                int amountToSubtract = numberNeeded * denomination.intValue();
                amountToDispense = amountToDispense.subtract(BigDecimal.valueOf(amountToSubtract));

                dispensedAmounts.put(denomination, dispensedNotes);
                counter++;
            }

            checkIfMaxed(dispensedAmounts, amountToDispense);
        }

        DispensedCash dispensedCash = new DispensedCash();
        dispensedCash.setDispensedAmounts(dispensedAmounts);

        return dispensedCash;
    }

    private void checkIfMaxed(Map<BigDecimal, Integer> dispensedAmounts, BigDecimal amountToDispense) {
        boolean allMaxed = true;
        BigDecimal totalValue = BigDecimal.ZERO;
        for (Map.Entry<BigDecimal, Integer> count : dispensedAmounts.entrySet()) {
            if (count.getValue() != 40) {
                allMaxed = false;
                break;
            }

            totalValue = totalValue.add(BigDecimal.valueOf(count.getKey().intValue() * count.getValue()));
        }

        if (allMaxed && BigDecimal.ZERO.intValue() < amountToDispense.intValue()) {
            throw new CalculationException("Amount not available, would you like to draw: " + totalValue.intValue());
        }
    }

    private int applyRules(int numberNeeded, int count, int counter, Map<BigDecimal, Integer> amountsForCount) {
        // Each denomination slot should not dispense more than 40 notes on a single transaction.
        int maxNoteAllocationAllowed = 40;

        // Each denomination should not dispense its last 5 notes until all denomination counts are below
        // this amount
        int noteRemainder = 5;

        // Value to subtract to allow other denominations to fill this void.
        int denominationVoid = 5;

        if (maxNoteAllocationAllowed < numberNeeded) {
            numberNeeded = maxNoteAllocationAllowed;
        }

        if (count < numberNeeded) {
            numberNeeded -= noteRemainder;
        }

        if (counter < amountsForCount.size() && numberNeeded > denominationVoid) {
            numberNeeded -= denominationVoid;
        }

        return numberNeeded;
    }

}
