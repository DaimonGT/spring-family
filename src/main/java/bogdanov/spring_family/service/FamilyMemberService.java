package bogdanov.spring_family.service;

import bogdanov.spring_family.entity.FamilyMember;
import bogdanov.spring_family.entity.House;
import bogdanov.spring_family.exception.ResourceNotFoundException;
import bogdanov.spring_family.repository.FamilyRepository;
import bogdanov.spring_family.repository.HouseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
@Service //помечает класс как компонент бизнес-логики
@Transactional
public class FamilyMemberService implements FamilyMemberServiceInterface {

    // подключение репозитория
    private final FamilyRepository familyRepository;
    private final HouseRepository houseRepository;

    // конструктор (обязателен)
    @Autowired // для автоматического внедрения зависимостей
    public FamilyMemberService(FamilyRepository familyRepository, HouseRepository houseRepository) {
        this.familyRepository = familyRepository;
        this.houseRepository = houseRepository;
    }

    // получить список всех членов семьи
    @Transactional
    public List<FamilyMember> getAllFamilyMembers() {
        return familyRepository.findAllWithHouse();
    }

    // получить члена семьи по ID
    @Transactional
    public Optional<FamilyMember> getFamilyMemberByID(Long id) {
        return familyRepository.findByIdWithHouse(id);
    }

    // создать нового члена семьи и привязать его к дому по houseId
    @Transactional
    public FamilyMember createFamilyMember(FamilyMember familyMember, Long houseId) {
        House house = houseRepository.findHouseById(houseId).orElseThrow(() -> new ResourceNotFoundException("Дом не найдет с таким ID " + houseId));
        familyMember.setHouse(house);
        return familyRepository.save(familyMember);
    }

    // обновить существующего члена семьи (с возможностью сменить дом)
    @Transactional
    public FamilyMember updateFamilyMember(Long id, FamilyMember familyDetails, Long houseId) {
        FamilyMember familyMember = familyRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Член семьи не найден с таким ID " + id));
        House house = houseRepository.findById(houseId).orElseThrow(() -> new ResourceNotFoundException("Дом не найдет с таким ID " + houseId));

        // Обновление члена семьи
        familyMember.setName(familyDetails.getName());
        familyMember.setAge(familyDetails.getAge());
        familyMember.setRole(familyDetails.getRole());

        // Обновление дома
        if (!familyMember.getHouse().getId().equals(houseId)){
            House oldHouse = familyMember.getHouse();
            oldHouse.removeFamilyMember(familyMember);
            familyMember.setHouse(house);
            house.addFamilyMember(familyMember);
        }
        return familyRepository.save(familyMember);
    }

    // удалить члена семьи
    @Transactional
    public void deleteFamilyMember(Long id) {
        FamilyMember familyMember = familyRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Член семьи не найден с таким ID " + id));
        House house = familyMember.getHouse();
        house.removeFamilyMember(familyMember);
    }
}
