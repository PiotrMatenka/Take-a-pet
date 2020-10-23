package pl.piotron.animals.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.CONFLICT, reason = "Klient z podanym emailem już istnieje")
public class DuplicateEmailException extends RuntimeException {
}
