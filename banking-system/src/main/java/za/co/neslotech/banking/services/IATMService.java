package za.co.neslotech.banking.services;

import za.co.neslotech.banking.schema.atm.DispensedCash;
import za.co.neslotech.banking.schema.atm.WithdrawRequest;

public interface IATMService {

    DispensedCash dispenseCash(WithdrawRequest withdrawRequest);

}
