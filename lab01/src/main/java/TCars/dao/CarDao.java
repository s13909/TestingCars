package TCars.dao;

import TCars.domain.Car;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;



public class CarDao implements Dao<Car> {
    protected Map<Long,Car> cars;



    @Override
    public Optional<Car> get(Long id) {

        return Optional.ofNullable(cars.get(id));
    }



    @Override
    public List<Car> getAll() {

        List list = new ArrayList<Car>();
        long IDtoRead = 1;

        do{
            list.add(cars.get(IDtoRead));
            IDtoRead++;
        }
        while (cars.containsKey(IDtoRead));

        return list;
    }

    @Override
    public void save(Car o) {

        if (cars.containsKey(o.getId()))
            throw new IllegalArgumentException("Key already exists");
        cars.put(o.getId(), o);

    }


    @Override
    public void update(Car o) throws IllegalArgumentException {

        if (!cars.containsKey(o.getId()))
            throw new IllegalArgumentException("Key does not exist");
        cars.put(o.getId(), o);
    }



    @Override
    public void delete(Car o) throws IllegalArgumentException{
        if (!cars.containsKey(o.getId()))
        throw new IllegalArgumentException("Key does not exist");
        cars.remove(o.getId());
    }

}