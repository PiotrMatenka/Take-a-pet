package pl.piotron.animals.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import pl.piotron.animals.model.Advertisement;

import java.util.List;
import java.util.Optional;

@Repository
public interface AdvertisementRepository extends JpaRepository<Advertisement, Long> {
    List<Advertisement> findAllByCategoryName(String category);
    Optional<Advertisement> findByTitleAndUser_Id(String title, Long id);
    @Query("select a from Advertisement a where lower(a.city)like lower(concat('%', :city, '%'))")
    List<Advertisement> findAllByCity(String city);
    @Query("select a from Advertisement a where lower(a.title) like lower(concat('%', :title, '%')) ")
    List<Advertisement> findAllByTitle(String title);




}
