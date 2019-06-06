package TCars.api;

import TCars.domain.Car;
import TCars.service.GarageManager;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;

@RestController
public class CarController {


    @Autowired
    GarageManager garageManager;

    @RequestMapping("/cars")
    public List<Car> getCars() {
        List<Car> cars = new LinkedList<>();
        for (Car c: garageManager.findAllCars()){
            cars.add(c.clone());
        }
        return cars;
    }

    @RequestMapping(value = "/cars",method = RequestMethod.POST)
    public Car addCar(@RequestBody Car ncar) {
        ncar.setId(garageManager.addCar(ncar));
        return ncar;
    }

    @RequestMapping(value = "/cars",method = RequestMethod.PUT)
    public Car updateClient(@RequestBody Car ncar) {
        garageManager.updateCar(ncar);
        return ncar;
    }


    @RequestMapping(value = "/cars/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public Car getCar(@PathVariable("id") Long id) throws SQLException {
        return garageManager.findCarById(id).clone();
    }

    @RequestMapping(value = "/cars", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public List<Car> getCars(@RequestParam(value = "filter", required = false) String f) throws SQLException {
        List<Car> cars = new LinkedList<Car>();
        for (Car c : garageManager.findAllCars()) {
            if (f == null) {
                cars.add(c.clone());
            } else if (c.getMake().contains(f)) {
                cars.add(c);
            }
        }
        return cars;
    }

    @RequestMapping(value = "/cars/{id}", method = RequestMethod.DELETE)
    @ResponseBody
    public String deleteCar(@PathVariable("id") Long id) throws SQLException {
        garageManager.deleteCar(garageManager.findCarById(id));
        return "Deleted";
    }



}
