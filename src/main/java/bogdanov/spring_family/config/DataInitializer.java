package bogdanov.spring_family.config;

import bogdanov.spring_family.entity.FamilyMember;
import bogdanov.spring_family.entity.FamilyRole;
import bogdanov.spring_family.entity.House;
import bogdanov.spring_family.entity.Pet;
import bogdanov.spring_family.service.FamilyMemberServiceInterface;
import bogdanov.spring_family.service.HouseServiceInterface;
import bogdanov.spring_family.service.PetServiceInterface;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import static bogdanov.spring_family.entity.FamilyRole.MOTHER;

@Component
public class DataInitializer implements CommandLineRunner {

    @Autowired
    private HouseServiceInterface houseService;

    @Autowired
    private FamilyMemberServiceInterface familyMemberService;

    @Autowired
    private PetServiceInterface petService;

    @Override
    public void run(String... args) throws Exception {
        // Создание домов
        House house1 = new House("Соленская 45", "3-х комнатная квартира");
        House house2 = new House("Московский 44", "2-х комнатная квартира");
        House house3 = new House("Лазо 5", "Чатный дом");

        house1 = houseService.createHouse(house1);
        house2 = houseService.createHouse(house2);
        house3 = houseService.createHouse(house3);

        // Создание членов семьи
        FamilyMember familyMember1 = new FamilyMember("Наташа", 32, FamilyRole.MOTHER, house1);
        FamilyMember familyMember2 = new FamilyMember("Влад", 35, FamilyRole.HUSBAND, house1);
        FamilyMember familyMember3 = new FamilyMember("Влад", 14, FamilyRole.SON, house1);

        FamilyMember familyMember4 = new FamilyMember("Анатолий", 77, FamilyRole.GRANDFATHER, house2);
        FamilyMember familyMember5 = new FamilyMember("Анатолий", 70, FamilyRole.GRANDMOTHER, house2);

        FamilyMember familyMember6 = new FamilyMember("Сергей", 25, FamilyRole.FATHER, house3);
        FamilyMember familyMember7 = new FamilyMember("Настя", 23, FamilyRole.MOTHER, house3);
        FamilyMember familyMember8 = new FamilyMember("Дима", 4, FamilyRole.SON, house3);
        FamilyMember familyMember9 = new FamilyMember("Любовь", 68, FamilyRole.GRANDMOTHER, house3);
        FamilyMember familyMember10 = new FamilyMember("Иван", 74, FamilyRole.GRANDFATHER, house3);

        familyMemberService.createFamilyMember(familyMember1, house1.getId());
        familyMemberService.createFamilyMember(familyMember2, house1.getId());
        familyMemberService.createFamilyMember(familyMember3, house1.getId());

        familyMemberService.createFamilyMember(familyMember4, house2.getId());
        familyMemberService.createFamilyMember(familyMember5, house2.getId());

        familyMemberService.createFamilyMember(familyMember6, house3.getId());
        familyMemberService.createFamilyMember(familyMember7, house3.getId());
        familyMemberService.createFamilyMember(familyMember8, house3.getId());
        familyMemberService.createFamilyMember(familyMember9, house3.getId());
        familyMemberService.createFamilyMember(familyMember10, house3.getId());

        // создание питомцев
        Pet pet1 = new Pet("Марта", "Кошка", 7, house1);
        Pet pet2 = new Pet("Лиза", "Кошка", 5, house2);
        Pet pet3 = new Pet("Чак", "Собака", 8, house2);
        Pet pet4 = new Pet("Пит", "Собака", 10, house3);
        Pet pet5 = new Pet("Дашка", "Кошка", 3, house3);
        Pet pet6 = new Pet("Чик", "Попугай", 5, house3);

        petService.createPet(pet1, house1.getId());
        petService.createPet(pet2, house2.getId());
        petService.createPet(pet3, house2.getId());
        petService.createPet(pet4, house3.getId());
        petService.createPet(pet5, house3.getId());
        petService.createPet(pet6, house3.getId());

        System.out.println("Sample data initialized successfully!");
    }
}
