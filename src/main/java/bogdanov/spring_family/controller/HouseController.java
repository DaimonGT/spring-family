package bogdanov.spring_family.controller;

import bogdanov.spring_family.entity.House;
import bogdanov.spring_family.service.HouseServiceInterface;
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

@RestController // данный класс является контроллером
@RequestMapping("/api/houses")
@Tag(name = "House Management", description = "APIs for managing houses")
// используется для группировки и описания API endpoints в автоматически генерируемой документации
public class HouseController {
    private final HouseServiceInterface houseService;

    // конструктор
    @Autowired
    public HouseController(HouseServiceInterface houseService) {
        this.houseService = houseService;
    }

    // получить список всех домов
    @GetMapping
    @Operation(summary = "Получить список всех домов", description = "Получение списка всех домов")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Успешное получение списка домов"),
            @ApiResponse(responseCode = "500", description = "Ошибка сервера")
    })
    public ResponseEntity<List<House>> getAllHouses() {
        List<House> houses = houseService.getAllHouse();
        return ResponseEntity.ok(houses);
    }

    // получить дом по ID
    @GetMapping("/{id}")
    @Operation(summary = "Получить дом по ID", description = "Получение дома по ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Успешное получение дома по ID"),
            @ApiResponse(responseCode = "404", description = "Дом по такому ID не найден"),
            @ApiResponse(responseCode = "500", description = "Ошибка сервера")
    })
    public ResponseEntity<House> getHouseById(
            @Parameter(description = "House ID", required = true)
            @PathVariable Long id) {
        Optional<House> house = houseService.getHouseById(id);
        return house.map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build());
    }

    // создать новый дом
    @PostMapping
    @Operation(summary = "Создание дома", description = "Создание нового дома")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Дом успешно создан"),
            @ApiResponse(responseCode = "400", description = "Неверно внесены данные"),
            @ApiResponse(responseCode = "500", description = "Ошибка сервера")
    })
    public ResponseEntity<House> createHouse(
            @Parameter(description = "House details", required = true)
            @Valid @RequestBody House house
    ) {
        House createdHouse = houseService.createHouse(house);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdHouse);
    }

    // обновить существующий дом
    @PutMapping("/{id}")
    @Operation(summary = "Обновление дома", description = "Обновление дома по ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Дом успешно обновлен"),
            @ApiResponse(responseCode = "404", description = "Неверно внесены данные"),
            @ApiResponse(responseCode = "500", description = "Ошибка сервера")
    })
    public ResponseEntity<House> updateHouse(
            @Parameter(description = "House ID", required = true)
            @PathVariable Long id,
            @Parameter(description = "House details", required = true)
            @Valid @RequestBody House houseDetails
    ) {
        House updateHouse = houseService.updateHouse(id, houseDetails);
        return ResponseEntity.ok(updateHouse);
    }

    // удалить дом (удаление дома должно каскадно удалять связанных членов семьи и животных)
    @DeleteMapping("/{id}")
    @Operation(summary = "Удаление дома", description = "Удаление дома по ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Дом успешно удалён"),
            @ApiResponse(responseCode = "404", description = "Неверно внесены данные"),
            @ApiResponse(responseCode = "500", description = "Ошибка сервера")
    })
    public ResponseEntity<Void> deleteHouse(
            @Parameter(description = "House ID", required = true)
            @PathVariable Long id
    ) {
        houseService.deleteHouse(id);
        return ResponseEntity.noContent().build();
    }
}
