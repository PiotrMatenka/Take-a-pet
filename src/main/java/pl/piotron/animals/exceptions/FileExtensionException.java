package pl.piotron.animals.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.CONFLICT, reason = "Błędne rozszerzenie lub brak pliku")
public class FileExtensionException  extends RuntimeException{
}
