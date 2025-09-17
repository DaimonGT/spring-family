package bogdanov.spring_family.controller;

import bogdanov.spring_family.entity.FamilyMember;
import bogdanov.spring_family.service.FamilyMemberServiceInterface;
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
@RequestMapping("/api/familyMembers")
@Tag(name = "FamilyMember менеджер", description = "APIs for managing FamilyMember") // используется для группировки и описания API endpoints в автоматически генерируемой документации
public class FamilyMemberController {

    private final FamilyMemberServiceInterface familyService;

    // конструктор (обязателен)
    @Autowired
    public FamilyMemberController(FamilyMemberServiceInterface familyService) {
        this.familyService = familyService;
    }

    // получить список всех членов семьи
    @GetMapping // аннотация для обработки get запросов
    @Operation(summary = "Получить всех членов семьи", description = "Получите список всех членов семьи")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Успешное получение списка всех членов семьи"),
            @ApiResponse(responseCode = "404", description = "Члены семьи не найдены"),
            @ApiResponse(responseCode = "500", description = "Ошибка сервера")
    })
    public ResponseEntity<List<FamilyMember>> getAllFamilyMembers() {
        List<FamilyMember> familyMembers = familyService.getAllFamilyMembers();
        return ResponseEntity.ok(familyMembers);
    }

    // получить члена семьи по ID
    @GetMapping("/{id}")
    @Operation(summary = "Получить члена семьи по ID", description = "Получить члена семьи по ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Успешное получение члена семьи по ID"),
            @ApiResponse(responseCode = "404", description = "Член семьи не найде"),
            @ApiResponse(responseCode = "500", description = "Ошибка сервера")
    })
    public ResponseEntity<FamilyMember> getFamilyMemberById(@Parameter(description = "FamilyMember ID", required = true)
                                                            @PathVariable Long id) {
        Optional<FamilyMember> familyMemberByID = familyService.getFamilyMemberByID(id);
        return familyMemberByID.map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build());
    }

    //создать нового члена семьи и привязать его к дому по houseId
    @PostMapping
    @Operation(summary = "Создать нового члена семьи", description = "Создать члена семьи по ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Успешное создание члена семьи по ID"),
            @ApiResponse(responseCode = "404", description = "Ошибка на стороне пользователя"),
            @ApiResponse(responseCode = "500", description = "Ошибка сервера")
    })
    public ResponseEntity<FamilyMember> createFamilyMemberById(
            @Parameter(description = "FamilyMember details", required = true)
            @Valid @RequestBody FamilyMember familyMember,
            @Parameter(description = "FamilyMember ID to assign the familyMember to", required = true)
            @RequestParam Long houseId) {
        FamilyMember createFamilyMember = familyService.createFamilyMember(familyMember, houseId);
        return ResponseEntity.status(HttpStatus.CREATED).body(createFamilyMember);
    }

    // обновить существующего члена семьи
    @PutMapping("/{id}")
    @Operation(summary = "Обновить члена семьи", description = "Обновить члена семьи с возможностью изменить дом")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Успешное обновление члена семьи"),
            @ApiResponse(responseCode = "404", description = "Член семьи не найден"),
            @ApiResponse(responseCode = "500", description = "Ошибка сервера")
    })
    public ResponseEntity<FamilyMember> updateFamilyMember(
            @Parameter(description = "FamilyMemberDetails", required = true)
            @PathVariable Long id, //  извлекает значения из URL пути и передает их как параметры в методы контроллера
            @Parameter(description = "Обновление деталей члена семьи", required = true)
            @Valid @RequestBody FamilyMember familyMember,
            @Parameter(description = "ID Дома для присвоения члену семьи", required = true)
            @RequestParam long houseId) {
        FamilyMember updateFamilyMember = familyService.updateFamilyMember(id, familyMember, houseId);
        return ResponseEntity.ok(updateFamilyMember);
    }

    // удалить члена семьи
    @DeleteMapping("/{id}")
    @Operation(summary = "Удалить члена семьи", description = "Удалить члена семьи по ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Успешное удаление члена семьи по ID"),
            @ApiResponse(responseCode = "404", description = "Член семьи по ID не найден"),
            @ApiResponse(responseCode = "500", description = "Ошибка сервера")
    })
    public ResponseEntity<Void> deleteFamilyMemberById(
            @Parameter(description = "FamilyMember ID", required = true)
            @PathVariable Long id
            ){
        familyService.deleteFamilyMember(id);
        return ResponseEntity.noContent().build(); //у нас же void, зачем возвращать? И что возвращаем тогда?
    }
}
