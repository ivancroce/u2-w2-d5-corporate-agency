package ivancroce.u2_w2_d5_corporate_agency.exceptions;

import ivancroce.u2_w2_d5_corporate_agency.payloads.ErrorRespDTO;
import ivancroce.u2_w2_d5_corporate_agency.payloads.ValidationErrorRespDTO;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;

@RestControllerAdvice
public class ExceptionsHandler {
    @ExceptionHandler(ValidationException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ValidationErrorRespDTO handleValidationErrors(ValidationException ex) {
        return new ValidationErrorRespDTO(ex.getMessage(), LocalDateTime.now(), ex.getErrorMessages());
    }

    @ExceptionHandler(BadRequestException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorRespDTO handleBadRequest(BadRequestException ex) {
        return new ErrorRespDTO(ex.getMessage(), LocalDateTime.now());
    }

    @ExceptionHandler(NotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ErrorRespDTO handleNotFound(NotFoundException ex) {
        return new ErrorRespDTO(ex.getMessage(), LocalDateTime.now());
    }

    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ErrorRespDTO handleServerError(Exception ex) {
        ex.printStackTrace();
        return new ErrorRespDTO("There was a generic error! We promise we'll fix it soon!", LocalDateTime.now());
    }
}
