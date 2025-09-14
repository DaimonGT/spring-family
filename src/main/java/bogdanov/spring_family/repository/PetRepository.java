package bogdanov.spring_family.repository;

import bogdanov.spring_family.entity.Pet;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface PetRepository extends JpaRepository<Pet, Long> {

    // получить всех питомцев
    @Query("SELECT p FROM Pet JOIN FETCH p.house")
    List<Pet> findAllPetWithHouse();

    // получить питомца по ID
    @Query("SELECT p FROM Pet p JOIN FETCH p.house WHERE p.id = :id")
    Optional<Pet> findPetById(@Param("id") Long id);

    // Получить питомца по имени
    List<Pet> findPetByName(String name);

}
