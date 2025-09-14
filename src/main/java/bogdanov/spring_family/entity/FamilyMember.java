package bogdanov.spring_family.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;

@Entity
@Table(name = "familyMembers")
public class FamilyMember {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Требуется ввести имя") //аннотация для валидации, что строка не является пустой
    @Size(min = 2, max = 50, message = "Имя должно быть от 2 до 50 символов")
    @Column(nullable = false, length = 50)
    private String name;

    @Positive(message = "Возраст должен быть от 0")
    @Column(nullable = false, length = 120)
    private Integer age;

    @Enumerated(EnumType.STRING) // аннотация, которая указывает, как хранить enum в БД
    @Column(nullable = false)
    private FamilyRole role;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "house_id", nullable = false)
    @JsonBackReference //предотвращает цикличность
    private House house;

    // конструкторы
    public FamilyMember() {
    }

    public FamilyMember(String name, Integer age, FamilyRole role, House house) {
        this.name = name;
        this.age = age;
        this.role = role;
        this.house = house;
    }

    // геттеры и сеттеры

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public FamilyRole getRole() {
        return role;
    }

    public void setRole(FamilyRole role) {
        this.role = role;
    }

    public House getHouse() {
        return house;
    }

    public void setHouse(House house) {
        this.house = house;
    }

    @Override
    public String toString() {
        return "FamilyMember" +
                "id=" + id +
                ", name='" + name +
                ", age=" + age +
                ", FamilyRole=" + role +
                ", house=" + house;
    }
}
