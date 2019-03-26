package TCars.dao;

import TCars.domain.Car;

import static org.junit.Assert.*;

import org.junit.*;
import org.junit.runner.RunWith;

import org.mockito.InOrder;
import org.mockito.Mock;
import org.mockito.Mockito;
import static org.mockito.Mockito.*;
import org.mockito.junit.MockitoJUnitRunner;

import static org.hamcrest.CoreMatchers.*;

import java.sql.*;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;
import java.util.logging.Logger;


@RunWith(MockitoJUnitRunner.class)
public class CarDaoTest
{
    private static final Logger LOGGER = Logger.getLogger(CarDaoTest.class.getCanonicalName());

    static List<Car> expectedDbState;
    Random random;


    abstract class AbstractResultSet implements ResultSet {
        int i; // automatycznie bedzie 0
        @Override
        public long getLong(String s) throws SQLException {
            return expectedDbState.get(i-1).getId();
        }
        @Override
        public String getString(String columnLabel) throws SQLException {

            switch (columnLabel) {
                case "make":
                    return expectedDbState.get(i - 1).getMake();

                case "model":
                    return expectedDbState.get(i - 1).getModel();

                case "color":
                    return expectedDbState.get(i - 1).getColor();
            }
          return "Bad column label";
        }
        @Override
        public boolean next() throws SQLException {
            i++;
            if (i > expectedDbState.size())
                return false;
            else
                return true;
        }
    }

    @Mock
    Connection connection;
    @Mock
    PreparedStatement selectAllStatementMock;
    @Mock
    PreparedStatement selectStatementMock;
    @Mock
    PreparedStatement insertStatementMock;
    @Mock
    PreparedStatement updateStatementMock;
    @Mock
    PreparedStatement deleteStatementMock;


    @Before

    public void setup() throws SQLException {

        random = new Random();
        expectedDbState = new LinkedList<>();
        for (int i = 0; i < 10;i++) {
            Car car = new Car();
            car.setId(i);
            car.setMake("Fiat");
            car.setModel("5" + random.nextInt(50));
            car.setColor("Black");

            expectedDbState.add(car);
        }

        Mockito.when(connection.prepareStatement("SELECT id, make, model, color FROM Car WHERE id = ?")).thenReturn(selectStatementMock);
        Mockito.when(connection.prepareStatement("SELECT id, make, model, color FROM Car ORDER BY id")).thenReturn(selectAllStatementMock);
        Mockito.when(connection.prepareStatement("INSERT INTO Car (make, model, color) VALUES (?, ?, ?)",Statement.RETURN_GENERATED_KEYS)).thenReturn(insertStatementMock);
        Mockito.when(connection.prepareStatement("DELETE FROM Car where id = ?")).thenReturn(deleteStatementMock);
        Mockito.when(connection.prepareStatement("UPDATE Car SET (make,model,color) VALUES (?,?,?) WHERE id = ?",Statement.RETURN_GENERATED_KEYS)).thenReturn(updateStatementMock);
    }



    @Test
    public void setConnectionCheck() throws SQLException {
        CarDaoJdbcImpl carManager = new CarDaoJdbcImpl();
        carManager.setConnection(connection);
        assertNotNull(carManager.getConnection());
        assertEquals(carManager.getConnection(), connection);
    }

    @Test
    public void setConnectionCreatesQueriesCheck() throws SQLException {
        // nagrajmy zachowanie mocka
        CarDaoJdbcImpl carManager = new CarDaoJdbcImpl();
        carManager.setConnection(connection);
        assertNotNull(carManager.getAllCarsStmt);
        Mockito.verify(connection).prepareStatement("SELECT id, make, model, color FROM Car ORDER BY id");
    }


    @Test
    public void checkAddingInOrder() throws Exception {


        InOrder inorder = inOrder(insertStatementMock);
        when(insertStatementMock.executeUpdate()).thenReturn(1);



        CarDaoJdbcImpl carManager = new CarDaoJdbcImpl();
        carManager.setConnection(connection);
        Car car = new Car();
        car.setMake("Fiat");
        car.setModel("Seicento");
        car.setColor("Black");

        carManager.addCar(car);


        inorder.verify(insertStatementMock, times(1)).setString(1, "Fiat");
        inorder.verify(insertStatementMock, times(1)).setString(2, "Seicento");
        inorder.verify(insertStatementMock, times(1)).setString(3, "Black");
        inorder.verify(insertStatementMock).executeUpdate();
    }


    @Test
    public void checkGetting() throws Exception {
        AbstractResultSet mockedResultSet = mock(AbstractResultSet.class);
        when(mockedResultSet.next()).thenCallRealMethod();
        when(mockedResultSet.getLong("id")).thenCallRealMethod();
        when(mockedResultSet.getString("make")).thenCallRealMethod();
        when(mockedResultSet.getString("model")).thenCallRealMethod();
        when(mockedResultSet.getString("color")).thenCallRealMethod();
        when(selectStatementMock.executeQuery()).thenReturn(mockedResultSet);

        CarDaoJdbcImpl carManager = new CarDaoJdbcImpl();
        carManager.setConnection(connection);

        Car car = expectedDbState.get(7);
       // System.out.println("car " + car );
        Car car2 = carManager.getCar(7);
       // System.out.println("car2 " + car2 );
       // Car car2 = retrievedCars.get(7);

        assertThat(car, equalTo(car2));

        verify(selectStatementMock, times(1)).executeQuery();
    }

    @Test(expected = SQLException.class)
    public void checkGettingFailure() throws Exception {
        AbstractResultSet mockedResultSet = mock(AbstractResultSet.class);
        when(mockedResultSet.next()).thenCallRealMethod();
        when(mockedResultSet.getLong("id")).thenCallRealMethod();
        when(mockedResultSet.getString("make")).thenCallRealMethod();
        when(mockedResultSet.getString("model")).thenCallRealMethod();
        when(mockedResultSet.getString("color")).thenCallRealMethod();
        when(selectStatementMock.executeQuery()).thenReturn(mockedResultSet);

        CarDaoJdbcImpl carManager = new CarDaoJdbcImpl();
        carManager.setConnection(connection);


        Car car2 = carManager.getCar(11);
        //System.out.println("car2 " + car2 );
        // Car car2 = retrievedCars.get(7);



        verify(selectStatementMock, times(1)).executeQuery();
    }

    @Test
    public void checkGettingAll() throws SQLException {


        AbstractResultSet mockedResultSet = mock(AbstractResultSet.class);
        when(mockedResultSet.next()).thenCallRealMethod();
        when(mockedResultSet.getLong("id")).thenCallRealMethod();
        when(mockedResultSet.getString("make")).thenCallRealMethod();
        when(mockedResultSet.getString("model")).thenCallRealMethod();
        when(mockedResultSet.getString("color")).thenCallRealMethod();
        when(selectAllStatementMock.executeQuery()).thenReturn(mockedResultSet);

        CarDaoJdbcImpl carManager = new CarDaoJdbcImpl();
        carManager.setConnection(connection);
        List<Car> retrievedCars = carManager.getAllCars();
        assertThat(retrievedCars, equalTo(expectedDbState));

        verify(selectAllStatementMock, times(1)).executeQuery();
        verify(mockedResultSet, times(expectedDbState.size())).getLong("id");
        verify(mockedResultSet, times(expectedDbState.size())).getString("make");
        verify(mockedResultSet, times(expectedDbState.size())).getString("model");
        verify(mockedResultSet, times(expectedDbState.size())).getString("color");
        verify(mockedResultSet, times(expectedDbState.size()+1)).next();
    }

    @Test
    public void checkDeleting() throws SQLException {

        when(deleteStatementMock.executeUpdate()).thenReturn(1);

        CarDaoJdbcImpl carManager = new CarDaoJdbcImpl();
        carManager.setConnection(connection);

        Car c = expectedDbState.get(4);

        carManager.deleteCar(c);

        verify(deleteStatementMock).executeUpdate();

    }

    @Test()
    public void checkUpdatingSuccess() throws SQLException {

        AbstractResultSet mockedResultSet = mock(AbstractResultSet.class);
        when(mockedResultSet.next()).thenCallRealMethod();
        when(mockedResultSet.getLong("id")).thenCallRealMethod();
        when(mockedResultSet.getString("make")).thenCallRealMethod();
        when(mockedResultSet.getString("model")).thenCallRealMethod();
        when(mockedResultSet.getString("color")).thenCallRealMethod();
        when(selectAllStatementMock.executeQuery()).thenReturn(mockedResultSet);
        when(updateStatementMock.executeUpdate()).thenReturn(1);


        CarDaoJdbcImpl carManager = new CarDaoJdbcImpl();
        carManager.setConnection(connection);


        Car c = expectedDbState.get(3);
        c.setMake("Porsche");
        carManager.updateCar(c);
        expectedDbState.set(3, c);
        List<Car> retrievedCars = carManager.getAllCars();
        assertThat(retrievedCars, equalTo(expectedDbState));

        verify(updateStatementMock, times(1)).executeUpdate();
    }

    @Test(expected = java.lang.IllegalStateException.class)
    public void checkUpdatingFailure() throws SQLException {



        CarDaoJdbcImpl carManager = new CarDaoJdbcImpl();
        carManager.setConnection(connection);

        Car c = new Car("Ferrari","488","Red");
        carManager.updateCar(c);

    }












   /*

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
    public void saveNewCar()
    {
        Car car3 = new Car();
        car3.setId(3L);
        car3.setMake("Ferrari");
        car3.setModel("488 Pista");
        car3.setColor("White");

        dao.save(car3);
        Optional<Car> c = dao.get(3L);
        assertThat(c.get().getModel(), is("488 Pista"));
        assertThat(c.get().getColor(), is("White"));

    }
    @Test(expected = IllegalArgumentException.class)
    public void saveNewCarOnAlreadyUsedID()
    {
        Car car3 = new Car();
        car3.setId(1L);
        car3.setMake("Ferrari");
        car3.setModel("488 Pista");
        car3.setColor("White");

        dao.save(car3);


    }

    @Test
    public void updateExistingCar(){
        Car car1 = new Car();
        car1.setId(1);

        car1.setModel("Tempra");

        Collection<Car> listExpected = dao.cars.values();
        for(Car c:listExpected) if (c.getId()==1) c.setModel("Tempra");


        dao.update(car1);

        Collection<Car> listAfter = dao.cars.values();
        assertArrayEquals(listExpected.toArray(), listAfter.toArray());

        //Optional<Car> c = dao.get(1L);

        //assertThat(c.get().getModel(), is("Tempra"));
        //assertThat(c.get().getColor(), is("Black"));
    }
    @Test(expected = java.util.NoSuchElementException.class)
    public void deleteExistingCar(){

        Car car1 = new Car();
        car1.setId(1L);
        dao.delete(car1);

        Optional<Car> c = dao.get(1L);

        assertThat(c.get().getModel(), is("Seicento"));
        //assertThat(c.get().getColor(), is("Black"));


    }


    @Test
    public void getAll(){

        assertNotNull(dao.getAll());

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