package TCars.domain;

import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import javax.persistence.*;

@Entity(name = "Car")
@Table(name = "car")
@NamedQueries({
        @NamedQuery(name = "car.all", query = "Select c from Car c"),
        @NamedQuery(name = "car.findAvailableCarsByMake", query = "Select d from Car d where d.make like :makeNameFragment")

})
public class Car
{
    //ID
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String make;
    private String model;
    private String color;

    //if null car is availble
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "person_id")

    private Client client;



    public Car(){
        this("empty","empty","empty");
    }

    public Car(String make, String model, String color){
        this.id = null;
        this.make = make;
        this.model = model;
        this.color = color;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        id = id;
    }

    public String getMake() {
        return make;
    }

    public void setMake(String make) {
        this.make = make;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public Client getClient() {
        return client;
    }

    public void setClient(Client client) {
        this.client = client;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;

        if (!(o instanceof Car))
            return false;

        return
                id != null &&
                        id.equals(((Car) o).getId());
    }

    @Override
    public String toString() {
        return "[" + id+ ", " + make + ", " + model + ", " + color + "]";
    }

    @Override
    public int hashCode() {
        return 1337;
    }

}
