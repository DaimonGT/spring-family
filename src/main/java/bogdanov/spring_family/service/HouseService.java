package bogdanov.spring_family.service;

import bogdanov.spring_family.entity.House;
import bogdanov.spring_family.exception.ResourceNotFoundException;
import bogdanov.spring_family.repository.HouseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class HouseService implements HouseServiceInterface {

    // подключение репозитория
    private final HouseRepository houseRepository;

    // конструктор (обязателен)
    @Autowired // для автоматического внедрения зависимостей
    public HouseService(HouseRepository houseRepository) {
        this.houseRepository = houseRepository;
    }

    // получить список всех домов
    @Transactional
    public List<House> getAllHouse() {
       return houseRepository.findAllHouses();
    }

    // получить дом по ID
    @Transactional
    public Optional<House> getHouseById(Long id) {
       return houseRepository.findHouseById(id);
    }

    // создать новый дом
    @Transactional
    public House createHouse(House house) {
       return houseRepository.save(house);
    }

    // обновить существующий дом
    @Transactional
    public House updateHouse(Long id, House houseDetails) {
        House house = houseRepository.findHouseById(id).orElseThrow(() -> new ResourceNotFoundException("Дом не найдет с таким ID " + id));
        house.setAddress(houseDetails.getAddress());
        house.setDescription(houseDetails.getDescription());
        return houseRepository.save(house);
    }

    // удалить дом
    @Transactional
    public void deleteHouse(Long id) {
        House house = houseRepository.findHouseById(id).orElseThrow(() -> new ResourceNotFoundException("Дом не найдет с таким ID " + id));
        houseRepository.delete(house);
    }
}
