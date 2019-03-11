package TCars.dao;

import TCars.domain.Car;

import java.util.List;
import java.util.Map;
import java.util.Optional;



public class CarInMemoryDao implements Dao<Car> {
    protected Map<Long,Car> cars;



    @Override
    public Optional<Car> get(Long id) {

        return Optional.ofNullable(cars.get(id));
    }



    @Override
    public List<Car> getAll() {

        return null;
    }

    @Override
    public void save(Car o) {

    }


    @Override
    public void update(Car o) throws IllegalArgumentException {

        if (!cars.containsKey(o.getId()))
            throw new IllegalArgumentException("Key does not exist");
        cars.put(o.getId(), o);
    }



    @Override
    public void delete(Car o) {
    }

}