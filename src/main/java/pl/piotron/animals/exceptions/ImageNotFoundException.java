package pl.piotron.animals.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.NOT_FOUND, reason = "Nie znaleziono podanego zdjęcia")
public class ImageNotFoundException extends RuntimeException {
}
