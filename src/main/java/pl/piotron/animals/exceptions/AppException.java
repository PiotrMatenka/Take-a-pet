package pl.piotron.animals.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.CONFLICT)
public class AppException extends RuntimeException{
    public AppException(String message)
    {
        super(message);
    }
}
