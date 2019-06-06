//package TCars.service;
//
//import static org.junit.Assert.assertEquals;
//import static org.junit.Assert.assertNotNull;
//import static org.junit.Assert.assertNull;
//import static org.junit.Assert.assertTrue;
//import static org.junit.Assume.assumeNoException;
//
//import java.util.ArrayList;
//import java.util.Date;
//import java.util.LinkedList;
//import java.util.List;
//
//import org.junit.Before;
//import org.junit.Test;
//import org.junit.runner.RunWith;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.test.annotation.Commit;
//import org.springframework.test.annotation.Rollback;
//import org.springframework.test.context.ContextConfiguration;
//import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
//import org.springframework.transaction.annotation.Transactional;
//
//import TCars.domain.Car;
//import TCars.domain.Client;
//
//
//
//@RunWith(SpringJUnit4ClassRunner.class)
//@ContextConfiguration(locations = { "classpath:/beans.xml" })
////@Rollback
//@Commit
//@Transactional(transactionManager = "txManager")
//public class GarageManagerTest {
//
//    @Autowired
//    GarageManager garageManager;
//    Date data = new Date();
//
//    List<Long> carIds;
//    List<Long> clientIds;
//
//
//    private Car addCarHelper(String make, String model, String color) {
//        Long carId;
//        Car car;
//        car = new Car();
//        car.setMake(make);
//        car.setModel(model);
//        car.setColor(color);
//        car.setId(null);
//        car.setClient(null);
//        carIds.add(carId = garageManager.addCar(car));
//        assertNotNull(carId);
//        return car;
//    }
//
//
//    private Client addClientHelper(String firstName, String secondName, List<Car> borowedCars) {
//        Long personId;
//        Client client;
//        client = new Client();
//        client.setFirstName(firstName);
//        client.setSecondName(secondName);
//        client.setRegistrationDate(data);
//        client.setCars(borowedCars);
//        client.setId(null);
//        clientIds.add(personId = garageManager.addClient(client));
//        assertNotNull(personId);
//        return client;
//    }
//
//    @Before
//    public void setup() {
//        carIds = new LinkedList<>();
//        clientIds = new LinkedList<>();
//
//        //cars
//        addCarHelper("Fiat", "Seicento", "Blue");
//        addCarHelper("BMW","525d", "Black");
//        Car car = addCarHelper("Volvo","V40", "Dark grey");
//
//        //clients
//        addClientHelper("Bartlomiej","Dulik",new LinkedList<Car>());
//        ArrayList<Car> cars = new ArrayList<Car>();
//        cars.add(car);
//        addClientHelper("Zbigniew","Kozłowski",cars);
//    }
//
//
//    @Test
//    public void addCarTest() {
//        assertTrue(carIds.size() > 0);
//    }
//
//    @Test
//    public void addClientTest() {
//        assertTrue(clientIds.size() > 0);
//    }
//
//    @Test
//    public void getAllCarsTest() {
//        List <Long> foundIds = new LinkedList<>();
//        for (Car car: garageManager.findAllCars()) {
//            if (carIds.contains(car.getId())) foundIds.add(car.getId());
//        }
//        assertEquals(carIds.size(), foundIds.size());
//    }
//
//    @Test
//    public void getAllClientsTest() {
//        List <Long> foundIds = new LinkedList<>();
//        for (Client client: garageManager.findAllClients()) {
//            if (clientIds.contains(client.getId())) foundIds.add(client.getId());
//        }
//        assertEquals(clientIds.size(), foundIds.size());
//    }
//
//    @Test
//    public void getCarByIdWithoutBorrowedCarsTest() {
//        Car carNotAssigned = garageManager.findCarById(carIds.get(0));
//        assertEquals("Seicento",carNotAssigned.getModel());
//        assertNull(carNotAssigned.getClient());
//    }
//
//    @Test
//    public void getCarByIdWithBorrowedCarsTest() {
//        Car carAssigned = garageManager.findCarById(carIds.get(2));
//        assertEquals("V40",carAssigned.getModel());
//        assertNotNull(carAssigned.getClient());
//    }
//
//    @Test
//    public void updateCarTest(){
//
//        Car carToUpdate = garageManager.findCarById(carIds.get(0));
//        assertEquals("Seicento",carToUpdate.getModel());
//        carToUpdate.setModel("Bravo");
//        garageManager.updateCar(carToUpdate);
//        Car carUpdated = garageManager.findCarById(carIds.get(0));
//        assertEquals("Bravo",carUpdated.getModel());
//
//    }
//
//    @Test
//    public void updateClientTest(){
//
//        Client clientToUpdate = garageManager.findClientById(clientIds.get(0));
//        assertEquals("Bartlomiej",clientToUpdate.getFirstName());
//        clientToUpdate.toString();
//        clientToUpdate.setFirstName("Bartosz");
//        garageManager.updateClient(clientToUpdate);
//        Client clientUpdated = garageManager.findClientById(clientIds.get(0));
//        assertEquals("Bartosz",clientUpdated.getFirstName());
//
//    }
//
//
//    @Test
//    public void deleteCarNotBorrowedTest() {
//        int prevSize = garageManager.findAllCars().size();
//        Car car = garageManager.findCarById(carIds.get(0));
//        assertNotNull(car);
//        garageManager.deleteCar(car);
//        assertNull(garageManager.findCarById(carIds.get(0)));
//        assertEquals(prevSize-1,garageManager.findAllCars().size());
//    }
//
//    @Test
//    public void getClientWithCarsTest() {
//        Client client = garageManager.findClientById(clientIds.get(1));
//        assertEquals(1, client.getCars().size());
//    }
//
//    @Test
//    public void deleteClientWithCarsTest() {
//        Client client = garageManager.findClientById(clientIds.get(1));
//        assertEquals(1, client.getCars().size());
//        garageManager.deleteClient(client);
//        assertNull(garageManager.findClientById(clientIds.get(1)));
//    }
//
//    @Test
//    public void deleteClientWithoutCarsTest() {
//        Client client = garageManager.findClientById(clientIds.get(0));
//        assertEquals(0, client.getCars().size());
//        garageManager.deleteClient(client);
//        assertNull(garageManager.findClientById(clientIds.get(0)));
//    }
//
//    @Test
//    public void deleteCarBorrowedTest() {
//        Client client = garageManager.findClientById(clientIds.get(1));
//        assertEquals(1, client.getCars().size());
//        Car car = garageManager.findCarById(carIds.get(2));
//        assertNotNull(car);
//        garageManager.deleteCar(car);
//        assertNull(garageManager.findCarById(carIds.get(2)));
//        assertEquals(0, client.getCars().size());
//    }
//
//    @Test
//    public void findByCarMakeTest() {
//        List<Car> cars = garageManager.findCarsByMake("Fia");
//        assertEquals("Fiat", cars.get(0).getMake());
//    }
//
//
//
//}
