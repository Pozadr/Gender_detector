package pl.pozadr.genderdetector.exceptions;

import com.fasterxml.jackson.annotation.JsonFormat;
import org.springframework.http.HttpStatus;

import java.time.LocalDateTime;

public class ErrorResponse {
    private final String message;
    private final HttpStatus httpStatus;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy hh:mm:ss")
    private final LocalDateTime localDateTime;

    public ErrorResponse(LocalDateTime localDateTime, HttpStatus httpStatus, String message) {
        this.localDateTime = localDateTime;
        this.httpStatus = httpStatus;
        this.message = message;
    }

    public String getMessage() {
        return message;
    }

    public LocalDateTime getLocalDateTime() {
        return localDateTime;

    }

    public HttpStatus getHttpStatus() {
        return httpStatus;
    }
}
