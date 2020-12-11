package pl.piotron.animals.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import pl.piotron.animals.model.User;

import java.util.List;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    User findByEmail(String email);
    @Query("select u from User u where lower(u.userDetails.lastName) like lower(concat('%', :text, '%') )")
    List<User> findAllByLastName (String text);
}
