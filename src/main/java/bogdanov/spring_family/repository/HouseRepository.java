package bogdanov.spring_family.repository;

import bogdanov.spring_family.entity.House;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface HouseRepository extends JpaRepository<House, Long> {

    // Получить список всех домов
    @Query("SELECT h FROM House h")
    List<House> findAllHouses();

    // Получть дом по id
    @Query("SELECT h FROM House h WHERE h.id = :id")
    Optional<House> findHouseById(@Param("id") Long id);

    // получить дом по адресу
    Optional<House> findByAddress(String address);
}
