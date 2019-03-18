package TCars.dao;

import TCars.domain.Car;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;


public interface CarDao  {

    public Connection getConnection();
  
    public void setConnection(Connection connection) throws SQLException;
    public List<Car> getAllCars();
    public int addCar(Car car);
    public int deleteCar(Car car);
    public int updateCar(Car car) throws SQLException;
    public Car getCar(long id) throws SQLException;

}