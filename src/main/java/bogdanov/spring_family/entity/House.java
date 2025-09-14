package bogdanov.spring_family.entity;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "houses")
public class House {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Требуется ввести адрес")
    @Size(min = 5, max = 100, message = "Адрес должен быть от 5 до 100 символов")
    @Column(nullable = false, length = 100)
    private String address;

    @Column(length = 200)
    private String description;

    @OneToMany(mappedBy = "house", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    @JsonManagedReference //предотвращает цикличность
    private List<FamilyMember> familyMembers = new ArrayList<>();

    @OneToMany(mappedBy = "house", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    @JsonManagedReference //предотвращает цикличность
    private List<Pet> pets = new ArrayList<>();

    // конструкторы

    public House() {
    }

    public House(String address, String description, List<FamilyMember> familyMembers, List<Pet> pets) {
        this.address = address;
        this.description = description;
        this.familyMembers = familyMembers;
        this.pets = pets;
    }

    //геттеры и сеттеры

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public List<FamilyMember> getFamilyMembers() {
        return familyMembers;
    }

    public void setFamilyMembers(List<FamilyMember> familyMembers) {
        this.familyMembers = familyMembers;
    }

    public List<Pet> getPets() {
        return pets;
    }

    public void setPets(List<Pet> pets) {
        this.pets = pets;
    }

    // метод для удаления члена семьи из дома (для обновления дома, метод updateFamilyMember())
    public void removeFamilyMember(FamilyMember familyMember){
        familyMembers.remove(familyMember);
        familyMember.setHouse(null);
    }

    // метод для добавления члена семьи в новый дом (для обновления дома, метод updateFamilyMember())
    public void addFamilyMember(FamilyMember familyMember){
        familyMembers.add(familyMember);
        familyMember.setHouse(this); // зачем this, что делает эта строчка?
    }

    // метод для удаления питомуа из дома (для обновления дома, метод updatePet())
    public void removePet(Pet pet){
        pets.remove(pet);
        pet.setHouse(null);
    }

    // метод для добавления члена питомца в новый дом (для обновления дома, метод updatePet())
    public void addPet(Pet pet){
        pets.add(pet);
        pet.setHouse(this);
    }

    @Override
    public String toString() {
        return "House " +
                "id=" + id +
                ", address='" + address +
                ", description='" + description +
                ", familyMembers=" + familyMembers +
                ", pets=" + pets;
    }
}
