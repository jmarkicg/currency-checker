package hr.markic.currency.checker.rest.error;

import hr.markic.currency.checker.constants.ErrorCodes;
import hr.markic.currency.checker.rest.exception.InvalidRequestDataException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class CommonErrorHandler {

    private final Logger log = LoggerFactory.getLogger(CommonErrorHandler.class);

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiErrorResponse> handleApiException(Exception ex) {
        ApiErrorResponse response = new ApiErrorResponse(ErrorCodes.ERR_INTERNAL_SERVER_ERROR,
                ex.toString());
        log.error("Failed to handle request.", ex);
        return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(InvalidRequestDataException.class)
    public ResponseEntity<ApiErrorResponse> handleInvalidRequestData(InvalidRequestDataException ex) {
        String msg = (ex.getCustomMsg() != null)? ex.getCustomMsg(): "Request body not readable / not fully populated.";
        ApiErrorResponse response = new ApiErrorResponse(ErrorCodes.ERR_BAD_REQUEST, msg);
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }
}
