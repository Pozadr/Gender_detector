package pl.pozadr.genderdetector.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.client.HttpServerErrorException;

import java.time.LocalDateTime;

@RestControllerAdvice
public class CustomExceptionHandler {

    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ResponseBody
    ErrorResponse handleGlobalException(final Exception exp) {
        return new ErrorResponse(LocalDateTime.now(), HttpStatus.INTERNAL_SERVER_ERROR, exp.getMessage());
    }

    @ExceptionHandler(HttpServerErrorException.InternalServerError.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ResponseBody
    ErrorResponse handleInternalServerError(final HttpServerErrorException.InternalServerError exp) {
        return new ErrorResponse(LocalDateTime.now(), HttpStatus.INTERNAL_SERVER_ERROR, exp.getMessage());
    }

    @ExceptionHandler(value = {MethodParameterNotValidException.class})
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ResponseBody
    ErrorResponse handleCarNotFoundException(final MethodParameterNotValidException ex) {
        return new ErrorResponse(LocalDateTime.now(), HttpStatus.BAD_REQUEST, ex.getMessage());
    }


}
