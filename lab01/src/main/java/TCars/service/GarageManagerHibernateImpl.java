package TCars.service;

import java.util.List;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import TCars.domain.Client;
import TCars.domain.Car;

@Component
@Transactional
public class GarageManagerHibernateImpl implements GarageManager {

    @Autowired
    private SessionFactory sessionFactory;

    public SessionFactory getSessionFactory() { return sessionFactory; }

    public void setSessionFactory(SessionFactory sessionFactory) { this.sessionFactory = sessionFactory; }

    @Override
    public Long addClient(Client client) {
        if (client.getId() != null) throw new IllegalArgumentException("Client ID should be null when adding to DB");
        sessionFactory.getCurrentSession().persist(client);
//        for (Car car : client.getCars()){
//            car.setClient(client);
//            sessionFactory.getCurrentSession().update(car);
//        }
        sessionFactory.getCurrentSession().flush();
        return client.getId();
    }

    @Override
    public List<Client> findAllClients() {
        return sessionFactory.getCurrentSession().getNamedQuery("client.all")
                .list();
    }

    @Override
    public Client findClientById(Long id) {
        return (Client) sessionFactory.getCurrentSession().get(Client.class, id);
    }

    @Override
    public void updateClient(Client client) {
        sessionFactory.getCurrentSession().update(client);
    }

    @Override
    public Long addCar(Car car) {
        if (car.getId() != null) throw new IllegalArgumentException("Car ID should be null when adding to DB");
        sessionFactory.getCurrentSession().persist(car);
        sessionFactory.getCurrentSession().flush();
        return car.getId();

    }

    @Override
    public void updateCar(Car car) {
        sessionFactory.getCurrentSession().update(car);
    }

    @Override
    public Car findCarById(Long id) {
        return (Car) sessionFactory.getCurrentSession().get(Car.class, id);
    }

    @Override
    public void deleteCar(Car car) {
        if (car.getClient() != null) {
            car.getClient().getCars().remove(car);
            sessionFactory.getCurrentSession().update(car.getClient());
        }
        sessionFactory.getCurrentSession().delete(car);
    }

    @Override
    public List<Car> findAllCars() {
        return sessionFactory.getCurrentSession().getNamedQuery("car.all")
                .list();
    }


    @Override
    public List<Car> findCarsByMake(String makeNameFragment) {
        return (List<Car>) sessionFactory.getCurrentSession()
                .getNamedQuery("car.findAvailableCarsByMake")
                .setString("makeNameFragment", "%"+makeNameFragment+"%")
                .list();
    }

    @Override
    public void deleteClient(Client client) {
        client = (Client) sessionFactory.getCurrentSession().get(Client.class,
                client.getId());
        for (Car car : client.getCars()) {
            car.setClient(null);
            sessionFactory.getCurrentSession().update(car);
        }
        sessionFactory.getCurrentSession().delete(client);
    }


}
