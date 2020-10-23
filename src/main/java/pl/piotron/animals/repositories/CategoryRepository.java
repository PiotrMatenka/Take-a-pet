package pl.piotron.animals.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.piotron.animals.model.Category;

import java.util.Optional;

public interface CategoryRepository extends JpaRepository<Category, Long> {
    Optional<Category> findByName (String name);
}
