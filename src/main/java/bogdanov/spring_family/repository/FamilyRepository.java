package bogdanov.spring_family.repository;

import bogdanov.spring_family.entity.FamilyMember;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface FamilyRepository extends JpaRepository<FamilyMember, Long> {

    // Получить все членов семьи из дома с конкретным id.
    // В чем разница между findByHouseIdWithHouse?
    List<FamilyMember> findByHouseId(Long houseId);

    //Получить члена семьи по его id
    @Query("SELECT f FROM FamilyMember f JOIN FETCH f.house WHERE f.id = :id")
    Optional<FamilyMember> findByIdWithHouse(@Param("id") Long id);

    // Получить всех членов семьи и их дома
    @Query("SELECT f FROM FamilyMember f JOIN FETCH f.house")
    List<FamilyMember> findAllWithHouse();

    // Получить всех членов семьи из конкретного дома
    @Query("SELECT f FROM FamilyMember f JOIN FETCH f.house WHERE f.house.id = :houseId")
    List<FamilyMember> findByHouseIdWithHouse(@Param("houseId") Long houseId);
}
