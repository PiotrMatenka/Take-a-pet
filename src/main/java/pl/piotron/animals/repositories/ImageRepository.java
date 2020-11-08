package pl.piotron.animals.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import pl.piotron.animals.model.ImageStorage;

import java.util.List;
import java.util.Optional;

public interface ImageRepository extends JpaRepository<ImageStorage, Long> {
    List<ImageStorage> findAllByAdvertisement_Id(Long id);
    Optional<ImageStorage> findByUploadUrl (String url);

}
