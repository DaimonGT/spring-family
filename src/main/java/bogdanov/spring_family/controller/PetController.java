package bogdanov.spring_family.controller;

import bogdanov.spring_family.entity.Pet;
import bogdanov.spring_family.service.PetServiceInterface;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/pets")
@Tag(name = "Pet Management", description = "APIs for managing fruits")
public class PetController {

    private final PetServiceInterface petService;

    @Autowired
    public PetController(PetServiceInterface petService) {
        this.petService = petService;
    }

    // получить список всех животных
    @GetMapping
    @Operation(summary = "Получить всех питомцев", description = "Получение списка всех питомцев")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Успешное получение списка питомцев"),
            @ApiResponse(responseCode = "500", description = "Ошибка сервера")
    })
    public ResponseEntity<List<Pet>> getAllPets() {
        List<Pet> allPets = petService.getAllPets();
        return ResponseEntity.ok(allPets);
    }

    //получить животное по ID
    @GetMapping("/{id}")
    @Operation(summary = "Получить питомца", description = "Получить питомца по ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Успешное получение питомца по ID"),
            @ApiResponse(responseCode = "404", description = "Неверно введены данные"),
            @ApiResponse(responseCode = "500", description = "Ошибка сервера")
    })
    public ResponseEntity<Pet> getPeyById(
            @Parameter(description = "Pet ID", required = true)
            @PathVariable Long petId
    ) {
        Optional<Pet> petById = petService.getPetById(petId);
        return petById.map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build());
    }

    // создать новое животное и привязать его к дому по houseId
    @PostMapping
    @Operation(summary = "Создать нового питомца", description = "Создать нового питомца и привязать его к дому")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Успешное создание питомца по ID"),
            @ApiResponse(responseCode = "404", description = "Неверно введены данные"),
            @ApiResponse(responseCode = "500", description = "Ошибка сервера")
    })
    public ResponseEntity<Pet> createPet(
            @Parameter(description = "Pet details", required = true)
            @Valid @RequestBody Pet pet,
            @Parameter(description = "FamilyMember ID to assign the familyMember to", required = true)
            @RequestParam Long houseId) {
        Pet createPet = petService.createPet(pet, houseId);
        return ResponseEntity.status(HttpStatus.CREATED).body(createPet);
    }

    // обновить существующее животное (с возможностью сменить дом)
    @PutMapping("/{id}")
    @Operation(summary = "Обновить питомца", description = "Обновить питомца с возможностью изменить дом")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Успешное обновление питомца"),
            @ApiResponse(responseCode = "404", description = "Питомец не найден"),
            @ApiResponse(responseCode = "500", description = "Ошибка сервера")
    })
    public ResponseEntity<Pet> updatePet(
            @Parameter(description = "PetDetails", required = true)
            @PathVariable Long id, //  извлекает значения из URL пути и передает их как параметры в методы контроллера
            @Parameter(description = "Обновление деталей члена семьи", required = true)
            @Valid @RequestBody Pet pet,
            @Parameter(description = "ID Дома для присвоения питомцу", required = true)
            @RequestParam long houseId) {
        Pet updatePet = petService.updatePet(id, pet, houseId);
        return ResponseEntity.ok(updatePet);
    }

    // удалить животное
    @DeleteMapping("/{id}")
    @Operation(summary = "Удалить питомца", description = "Удалить питомца по ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Успешное удаление питомца по ID"),
            @ApiResponse(responseCode = "404", description = "Питомец по ID не найден"),
            @ApiResponse(responseCode = "500", description = "Ошибка сервера")
    })
    public ResponseEntity<Void> deletePetById(
            @Parameter(description = "Pet ID", required = true)
            @PathVariable Long id
    ) {
        petService.deletePet(id);
        return ResponseEntity.noContent().build(); //у нас же void, зачем возвращать? И что возвращаем тогда?
    }
}
