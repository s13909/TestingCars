package TCars.dao;

import TCars.dao.CarDao;
import TCars.domain.Car;

import java.sql.*;
import java.util.LinkedList;
import java.util.List;

public class CarDaoJdbcImpl implements CarDao {

    private Connection connection;

    private PreparedStatement addCarStmt;
    private PreparedStatement getAllCarsStmt;
    private PreparedStatement deleteCarStmt;
    private PreparedStatement getCarStmt;
    private PreparedStatement updateCarStmt;

    public CarDaoJdbcImpl(Connection connection) throws SQLException {
        this.connection = connection;
        setConnection(connection);
    }

    public CarDaoJdbcImpl() throws SQLException {
    }

    @Override
    public Connection getConnection() {
        return connection;
    }

    public void setConnection(Connection connection) throws SQLException {
        this.connection = connection;
        addCarStmt = connection.prepareStatement(
                "INSERT INTO Car (make, model, color) VALUES (?, ?, ?)",
                Statement.RETURN_GENERATED_KEYS);
        deleteCarStmt = connection.prepareStatement("DELETE FROM Car where id = ?");
        getAllCarsStmt = connection.prepareStatement("SELECT id, make, model, color FROM Car ORDER BY id");
        getCarStmt = connection.prepareStatement("SELECT id, make, model, color FROM Car WHERE id = ?");
        updateCarStmt = connection.prepareStatement("UPDATE Car SET make=?,model=?,color=? WHERE id = ?");
    }

    @Override
    public int addCar(Car car) {
        int count = 0;
        try {
            addCarStmt.setString(1, car.getMake());
            addCarStmt.setString(2, car.getModel());
            addCarStmt.setString(3, car.getColor());
            count = addCarStmt.executeUpdate();
            ResultSet generatedKeys = addCarStmt.getGeneratedKeys();
            if (generatedKeys.next()) {
                car.setId(generatedKeys.getLong(1));
            }
        } catch (SQLException e) {
            throw new IllegalStateException(e.getMessage() + "\n" + e.getStackTrace().toString());
        }
        return count;
    }

    public List<Car> getAllCars() {
        List<Car> cars = new LinkedList<>();
        try {
            ResultSet rs = getAllCarsStmt.executeQuery();

            while (rs.next()) {
                Car c = new Car();
                c.setId(rs.getInt("id"));
                c.setMake(rs.getString("make"));
                c.setModel(rs.getString("model"));
                c.setColor(rs.getString("color"));
                cars.add(c);
            }

        } catch (SQLException e) {
            throw new IllegalStateException(e.getMessage() + "\n" + e.getStackTrace().toString());
        }
        return cars;
    }


}
