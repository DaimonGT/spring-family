package bogdanov.spring_family.service;

import bogdanov.spring_family.entity.House;
import bogdanov.spring_family.entity.Pet;
import bogdanov.spring_family.exception.ResourceNotFoundException;
import bogdanov.spring_family.repository.HouseRepository;
import bogdanov.spring_family.repository.PetRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class PetService implements PetServiceInterface {

    // подключаем репозитории
    private final PetRepository petRepository;
    private final HouseRepository houseRepository;

    @Autowired
    public PetService(PetRepository petRepository, HouseRepository houseRepository) {
        this.petRepository = petRepository;
        this.houseRepository = houseRepository;
    }

    // получить список всех животных
    @Transactional
    public List<Pet> getAllPets() {
        return petRepository.findAllPetWithHouse();
    }

    // получить животное по ID
    @Transactional
    public Optional<Pet> getPetById(Long id) {
        return petRepository.findPetById(id);
    }

    // создать новое животное и привязать его к дому по houseId
    @Transactional
    public Pet createPet(Pet pet, Long houseId) {
        House house = houseRepository.findById(houseId).orElseThrow(() -> new ResourceNotFoundException("Дом с таким ID не найден " + houseId));
        pet.setHouse(house);
        return petRepository.save(pet);
    }

    @Transactional
    public Pet updatePet(Long id, Pet petDetails, Long houseId) {
        Pet pet = petRepository.findPetById(id).orElseThrow(() -> new ResourceNotFoundException("Питомца с таким ID нет " + id));
        House house = houseRepository.findHouseById(houseId).orElseThrow(() -> new ResourceNotFoundException("Дом с таким ID не найден " + houseId));

        // Обновляем питомца:
        pet.setName(petDetails.getName());
        pet.setAge(petDetails.getAge());
        pet.setSpecies(petDetails.getSpecies());

        // меняем дом
        if (!pet.getHouse().getId().equals(houseId)) {
            House oldHouse = pet.getHouse();
            oldHouse.removePet(pet);
            pet.setHouse(house);
            house.addPet(pet);
        }
        return petRepository.save(pet);
    }

    @Transactional
    public void deletePet(Long id) {
        Pet pet = petRepository.findPetById(id).orElseThrow(() -> new ResourceNotFoundException("Питомца с таким ID нет " + id));
        House house = pet.getHouse();
        house.removePet(pet);
        petRepository.delete(pet);
    }
}
