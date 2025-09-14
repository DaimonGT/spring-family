package bogdanov.spring_family.service;

import bogdanov.spring_family.entity.Pet;

import java.util.List;
import java.util.Optional;

public interface PetServiceInterface {
    // получить список всех животных
    List<Pet> getAllPets();

    // получить животное по ID
    Optional<Pet> getPetById(Long id);

    // создать новое животное и привязать его к дому по houseId
    Pet createPet(Pet pet, Long houseId);

    // обновить существующее животное (с возможностью сменить дом)
    Pet updatePet(Long id, Pet pet, Long houseId);

    // удалить животное
    void deletePet(Long id);
}
