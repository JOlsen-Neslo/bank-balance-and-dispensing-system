package za.co.neslotech.banking.schema;

import lombok.Data;
import org.springframework.http.HttpStatus;

@Data
public class ApiError {

    private HttpStatus status;
    private String message;
    private String timestamp;

    public ApiError(HttpStatus status) {
        this.status = status;
    }

}
