package bogdanov.spring_family.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;

@Entity
@Table(name = "pets")
public class Pet {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Требуется ввести имя питомца")
    @Size(min = 2, max = 50, message = "Имя питомца должно быть от 2 до 50 символов")
    @Column(nullable = false, length = 50)
    private String name;

    @NotBlank(message = "Требуется ввести вид питомца")
    @Size(min = 2, max = 50, message = "Вид питомца должен быть от 2 до 50 символов")
    @Column(nullable = false, length = 50)
    private String species;

    @Positive(message = "Возраст должен быть положительным")
    @Column(length = 30)
    private Integer age;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "house_id", nullable = false)
    @JsonBackReference
    private House house;

    // конструкторы

    public Pet() {
    }

    public Pet(String name, String species, Integer age, House house) {
        this.name = name;
        this.species = species;
        this.age = age;
        this.house = house;
    }

    //геттеры и сеттеры

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

    public String getSpecies() {
        return species;
    }

    public void setSpecies(String species) {
        this.species = species;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public House getHouse() {
        return house;
    }

    public void setHouse(House house) {
        this.house = house;
    }


    @Override
    public String toString() {
        return "Pet " +
                "id=" + id +
                ", name='" + name +
                ", species='" + species +
                ", age=" + age +
                ", house=" + house;
    }
}
