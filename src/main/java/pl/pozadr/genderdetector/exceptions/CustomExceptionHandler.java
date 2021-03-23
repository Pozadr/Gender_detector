package pl.pozadr.genderdetector.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;

@RestControllerAdvice
public class CustomExceptionHandler {

    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ResponseBody
    ErrorResponse handleGlobalException(final Exception exp) {
        return new ErrorResponse(LocalDateTime.now(), HttpStatus.BAD_REQUEST, exp.getMessage());
    }

    @ExceptionHandler(value = {MethodParameterNotValidException.class})
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ResponseBody
    ErrorResponse handleCarNotFoundException(final MethodParameterNotValidException ex) {
        return new ErrorResponse(LocalDateTime.now(), HttpStatus.BAD_REQUEST, ex.getMessage());
    }


}
