package TCars.service;

import TCars.domain.Client;
import TCars.domain.Car;


import java.util.List;


public interface GarageManager {

    Long addClient(Client client);
    List<Client> findAllClients();
    Client findClientById(Long id);
    void deleteClient(Client client);
    void updateClient(Client client);

    Long addCar(Car car);
    void updateCar(Car car);
    void deleteCar(Car car);
    Car findCarById(Long id);
    List<Car> findAllCars();
    List<Car> findCarsByMake(String makeNameFragment);
}
