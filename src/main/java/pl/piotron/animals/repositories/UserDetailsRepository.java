package pl.piotron.animals.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.piotron.animals.model.UserDetails;

import java.util.Optional;

public interface UserDetailsRepository extends JpaRepository<UserDetails, Long> {
    Optional<UserDetails> findByUser_Id(Long id);
}
