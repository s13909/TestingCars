package TCars.dao;

import TCars.domain.Car;
import TCars.dao.*;

import static org.junit.Assert.*;
import static org.hamcrest.CoreMatchers.*;

import org.junit.Before;
import org.junit.Test;
import org.junit.matchers.JUnitMatchers;
import org.junit.runner.RunWith;
import org.junit.runners.BlockJUnit4ClassRunner;

import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;



@RunWith(BlockJUnit4ClassRunner.class)
public class CarInMemoryDaoTest
{
    CarInMemoryDao dao;

    @Before

    public void setup() {

        Car car1 = new Car();
        Car car2 = new Car();

        car1.setId(1L);
        car1.setMake("Fiat");
        car1.setModel("Seicento");
        car1.setColor("Blue");

        car2.setId(2L);
        car2.setMake("Volvo");
        car2.setModel("V40");
        car2.setColor("Black");

        dao = new CarInMemoryDao();
        dao.cars = new HashMap<Long, Car>();
        dao.cars.put(1L,car1);
        dao.cars.put(2L,car2);
    }

    @Test
    public void createDaoObjectTest() {
        assertNotNull(dao);
    }

    @Test
    public void createCarTest() {
        assertNotNull(1L);
    }

    @Test
    public void getCarThatExistsTest()
    {
        Optional<Car> c = dao.get(2L);
        assertThat(c.get().getModel(), is("V40"));

    }

    @Test
    public void getById(){
        long idToFind = 1L;
        assertNotNull(dao.get(idToFind));
    }

    @Test
    public void updateExistingCar(){
        Car car1 = new Car();
        car1.setId(1);

        car1.setModel("Tempra");

        Collection<Car> listExpected = dao.cars.values();
        System.out.println(listExpected);
        for(Car c:listExpected) if (c.getId()==1) c.setModel("Tempra");


        dao.update(car1);

        Collection<Car> listAfter = dao.cars.values();
        assertArrayEquals(listExpected.toArray(), listAfter.toArray());

        //Optional<Car> c = dao.get(1L);

        //assertThat(c.get().getModel(), is("Tempra"));
        //assertThat(c.get().getColor(), is("Black"));
    }


    /*
    @Test
    public void getAll(){
        assertNotNull(CarRepository.getAll());
    }

    @Test
    public void addCar() {
        Car car = new Car();
        car.setId(1);
        car.setMake("Fiat");
        car.setModel("Seicento");
        CarRepository.add(car);
        assertNotNull(CarRepository.getById(car.getId()));
    }

    @Test
    public void deleteCar() {
        Car car = CarRepository.getById(1);
        Car carToTest = CarRepository.getById(2);
        CarRepository.delete(car);
        assertNull(CarRepository.getById(car.getId()));
    }

*/
}