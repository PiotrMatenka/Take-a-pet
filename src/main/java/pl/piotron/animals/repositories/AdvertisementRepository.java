package pl.piotron.animals.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import pl.piotron.animals.model.Advertisement;

import java.util.List;
import java.util.Optional;

@Repository
public interface AdvertisementRepository extends JpaRepository<Advertisement, Long> {
    @Query("select a from Advertisement a where lower(a.category.name)like " +
            "lower(concat('%', :category, '%') )" +
            "and a.end is null and a.isAcceptedByAdmin = true")
    List<Advertisement> findAllByCategoryName(String category);

    Optional<Advertisement> findByTitleAndUser_Id(String title, Long id);

    @Query("select a from Advertisement a where lower(a.category.name)like lower(concat('%', :category, '%'))" +
            "and lower(a.city)  like lower (concat('%', :city, '%'))" +
            "and a.isAcceptedByAdmin = true")
    List<Advertisement> findAllByCity(String category, String city);

    @Query("select a from Advertisement a where lower(a.title)like lower(concat('%', :title, '%') )" +
            "and a.end is null and a.isAcceptedByAdmin = true")
    List<Advertisement> findAllByTitle(String title);

    @Query("select a from Advertisement a where a.end is null and a.isAcceptedByUser = true " +
            "and a.isAcceptedByAdmin = false")
    List<Advertisement> findAllAcceptedByUser();




}
