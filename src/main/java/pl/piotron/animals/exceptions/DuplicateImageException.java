package pl.piotron.animals.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.CONFLICT, reason = "Zdjęcie już istnieje")
public class DuplicateImageException extends RuntimeException{
}
