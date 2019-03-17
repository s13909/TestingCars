package TCars.domain;

public class Car
{
    private Long id;
    public String make;
    public String model;
    public String color;

    public Car(){
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

    @Override
    public boolean equals(Object o) {
        Car other = (Car) o;
        boolean ret = other.getMake().equals(this.getMake()) &&
                ((other.getId() == this.getId()) || (other.getId().longValue() == this.getId().longValue())) &&
                ((other.getModel() == this.getModel()));
        return ret;
    }

    @Override
    public String toString() {
        return "[" + id+ ", "
                + make + ", " + model + ", " + color + "]";
    }

}