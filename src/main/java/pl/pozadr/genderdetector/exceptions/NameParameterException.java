package pl.pozadr.genderdetector.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseBody
@ResponseStatus(HttpStatus.BAD_REQUEST)
public class NameParameterException extends RuntimeException {

    public NameParameterException(String message) {
        super(message);
    }
}
