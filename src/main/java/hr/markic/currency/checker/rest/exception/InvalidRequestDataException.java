package hr.markic.currency.checker.rest.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Getter
public class InvalidRequestDataException extends Exception {

    String customMsg;
}
