package TCars.domain;

import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import javax.persistence.*;

@Entity(name = "Client")
@Table(name = "client")
@NamedQueries({
        @NamedQuery(name = "client.all", query = "Select c from Client c"),
})


public class Client {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String firstName;
    private String secondName;

    @Temporal(TemporalType.DATE)
    private Date registrationDate;

    @OneToMany(cascade = CascadeType.PERSIST,
            fetch = FetchType.EAGER,
            orphanRemoval=false,
            mappedBy = "client"
    )
    private List<Car> cars = new LinkedList<>();

    public void setId(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getSecondName() {
        return secondName;
    }

    public void setSecondName(String secondName) {
        this.secondName = secondName;
    }

    public Date getRegistrationDate() {
        return registrationDate;
    }

    public void setRegistrationDate(Date registrationDate) {
        this.registrationDate = registrationDate;
    }

    public List<Car> getCars() {
        return cars;
    }

    public void setCars(List<Car> cars) {
        this.cars = cars;
    }

    public Client clone() {
        Client p = new Client();
        p.cars = null;
        p.id = id;
        p.firstName = firstName;
        p.secondName = secondName;
        p.registrationDate = registrationDate;
        return p;
    }



}
