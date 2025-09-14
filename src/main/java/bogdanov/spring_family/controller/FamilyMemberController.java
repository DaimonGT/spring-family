package bogdanov.spring_family.controller;

import bogdanov.spring_family.entity.FamilyMember;
import bogdanov.spring_family.service.FamilyMemberServiceInterface;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Optional;

@RestController // данный класс является контроллером
@RequestMapping("/api/familyMembers")
public class FamilyMemberController {

    private final FamilyMemberServiceInterface familyService;

    // конструкто (обязателен)
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
                                                            @PathVariable Long id){
        Optional<FamilyMember> familyMemberByID = familyService.getFamilyMemberByID(id);
        return familyMemberByID.map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build());
    }

    //создать нового члена семьи и привязать его к дому по houseId



}
