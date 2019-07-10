package za.co.neslotech.banking.schema.client.account;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import lombok.Getter;

import java.util.Arrays;

@Getter
public enum ClientAccountType {

    CHEQUE("CHQ", "Cheque Account"),
    SAVINGS("SVGS", "Savings Account"),
    PERSONAL_LOAN("PLOAN", "Personal Loan Account"),
    HOME_LOAN("HLOAN", "Home Loan Account"),
    CREDIT_CARD("CCRD", "Credit Card"),
    FOREIGN_CURRENCY("CFCA", "Customer Foreign Currency Account");

    private final String code;
    private final String description;

    ClientAccountType(final String code, final String description) {
        this.code = code;
        this.description = description;
    }

    @JsonValue
    public String getDescription() {
        return this.description;
    }

    @Override
    public String toString() {
        return this.code;
    }

    @JsonCreator
    public static ClientAccountType fromCode(String code) throws IllegalArgumentException {
        return Arrays.stream(ClientAccountType.values())
                .filter(v -> v.getCode().equals(code))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("Unknown Account Type: " + code));
    }

}
