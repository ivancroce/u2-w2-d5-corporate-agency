package ivancroce.u2_w2_d5_corporate_agency.exceptions.exceptions;

import lombok.Getter;

import java.util.List;

@Getter
public class ValidationException extends RuntimeException {

    private List<String> errorMessages;

    public ValidationException(List<String> errorMessages) {
        super("Various errors of validation.");
        this.errorMessages = errorMessages;
    }
}
