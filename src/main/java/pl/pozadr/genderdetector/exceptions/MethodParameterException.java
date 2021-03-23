package pl.pozadr.genderdetector.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseBody
@ResponseStatus(HttpStatus.BAD_REQUEST)
public class MethodParameterException extends RuntimeException {

    public MethodParameterException(String message) {
        super(message);
    }
}
