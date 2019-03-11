package TCars.domain;
public class Car
{
    private long id;
    public String make;
    public String model;
    public String color;

    public long getId() {
        return id;
    }

    public void setId(long i) {
        id = i;
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


}