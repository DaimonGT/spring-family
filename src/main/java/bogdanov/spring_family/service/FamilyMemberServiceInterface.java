package bogdanov.spring_family.service;

import bogdanov.spring_family.entity.FamilyMember;

import java.util.List;
import java.util.Optional;

public interface FamilyMemberServiceInterface {
    // Получить список всех членов семьи
    List<FamilyMember> getAllFamilyMembers();

    // Получить члена семьи по ID
    Optional<FamilyMember> getFamilyMemberByID(Long id);

    // Создать нового члена семьи и привязать его к дому по houseId
    FamilyMember createFamilyMember(FamilyMember familyMember, Long houseId);

    // Обновить существующего члена семьи (с возможностью сменить дом)
    // почему не void, если мы только обновляем?
    FamilyMember updateFamilyMember(Long id, FamilyMember familyDetails, Long houseId);

    // Удалить члена семьи
    void deleteFamilyMember(Long id);
}
