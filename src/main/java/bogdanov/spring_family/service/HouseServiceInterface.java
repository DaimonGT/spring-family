package bogdanov.spring_family.service;

import bogdanov.spring_family.entity.House;

import java.util.List;
import java.util.Optional;

public interface HouseServiceInterface {
    // получить список всех домов
    List<House> getAllHouse();

    // получить дом по ID
    Optional<House> getHouseById(Long id);

    // создать новый дом
    House createHouse(House house);

    // обновить существующий дом
    House updateHouse (Long id, House houseDetails);

    // удалить дом
    void deleteHouse(Long id);
}
