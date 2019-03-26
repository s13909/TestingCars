package TCars.dao;

import TCars.dao.CarDao;
import TCars.domain.Car;

import java.sql.*;
import java.util.LinkedList;
import java.util.List;

public class CarDaoJdbcImpl implements CarDao {

    public PreparedStatement addCarStmt;
    public PreparedStatement getAllCarsStmt;
    public PreparedStatement deleteCarStmt;
    public PreparedStatement getCarStmt;
    public PreparedStatement updateCarStmt;

    Connection connection;
    @Override
    public Connection getConnection() { return connection; }

    @Override
    public void setConnection(Connection connection) throws SQLException {
        this.connection = connection;
        addCarStmt = connection.prepareStatement("INSERT INTO Car (make, model, color) VALUES (?, ?, ?)", Statement.RETURN_GENERATED_KEYS);
        deleteCarStmt = connection.prepareStatement("DELETE FROM Car where id = ?");
        getAllCarsStmt = connection.prepareStatement("SELECT id, make, model, color FROM Car ORDER BY id");
        getCarStmt = connection.prepareStatement("SELECT id, make, model, color FROM Car WHERE id = ?");
        updateCarStmt = connection.prepareStatement("UPDATE Car SET (make,model,color) VALUES (?,?,?) WHERE id = ?",Statement.RETURN_GENERATED_KEYS);
    }

    @Override
    public int addCar(Car car) throws SQLException {
        addCarStmt.setString(1, car.getMake());
        addCarStmt.setString(2, car.getModel());
        addCarStmt.setString(3, car.getColor());
        int r = addCarStmt.executeUpdate();
        return r;
    }


    @Override
    public Car getCar(long id) throws SQLException {
        try {
            getCarStmt.setLong(1, id);
            ResultSet rs = getCarStmt.executeQuery();

            while (rs.next()) {
                Car c = new Car();
                c.setId(rs.getLong("id"));
                c.setMake(rs.getString("make"));
                c.setModel(rs.getString("model"));
                c.setColor(rs.getString("color"));
                if (c.getId() == id){
                    return c;
                }
            }

        } catch (SQLException e) {
            throw new IllegalStateException(e.getMessage() + "\n" + e.getStackTrace().toString());
        }
        throw new SQLException("Car with id " + id + " doesn't exist");
    }

    @Override
    public List<Car> getAllCars() {
        try {
            List<Car> ret = new LinkedList<>();
            ResultSet result = getAllCarsStmt.executeQuery();
            while(result.next()) {
                Car p = new Car();
                p.setId(result.getLong("id"));
                p.setMake(result.getString("make"));
                p.setModel(result.getString("model"));
                p.setColor(result.getString("color"));
                ret.add(p);
            }
            return ret;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }


    @Override
    public int deleteCar(Car car) {
        try {
            deleteCarStmt.setLong(1, car.getId());
            return deleteCarStmt.executeUpdate();
        } catch (SQLException e) {
            throw new IllegalStateException(e.getMessage() + "\n" + e.getStackTrace().toString());
        }
    }

    @Override
    public int updateCar(Car car) throws SQLException {
        int count = 0;
        try {
            updateCarStmt.setString(1, car.getMake());
            updateCarStmt.setString(2, car.getModel());
            updateCarStmt.setString(3, car.getColor());

            if (car.getId() != null) {
                updateCarStmt.setLong(4, car.getId());
            } else {
                count = 0;
                throw new SQLException("Car not found, cannot update");
            }
            count = updateCarStmt.executeUpdate();
        } catch (SQLException e) {
            throw new IllegalStateException(e.getMessage() + "\n" + e.getStackTrace().toString());
        }
        return count;
    }





}
