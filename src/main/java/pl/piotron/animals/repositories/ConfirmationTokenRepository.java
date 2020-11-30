package pl.piotron.animals.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.piotron.animals.model.ConfirmationToken;

public interface ConfirmationTokenRepository extends JpaRepository<ConfirmationToken, Long> {

    ConfirmationToken findByConfirmationToken(String token);
}
