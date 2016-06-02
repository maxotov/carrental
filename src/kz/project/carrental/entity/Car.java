package kz.project.carrental.entity;

public class Car extends Entity {

    private String regNumber;
    private Model model;
    private String name;
    private double price;
    private String photo;

    public Car() {
    }

    public String getRegNumber() {
        return regNumber;
    }

    public void setRegNumber(String regNumber) {
        this.regNumber = regNumber;
    }

    public Model getModel() {
        return model;
    }

    public void setModel(Model model) {
        this.model = model;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Car)) return false;
        if (!super.equals(o)) return false;

        Car car = (Car) o;

        if (Double.compare(car.price, price) != 0) return false;
        if (model != null ? !model.equals(car.model) : car.model != null) return false;
        if (name != null ? !name.equals(car.name) : car.name != null) return false;
        if (photo != null ? !photo.equals(car.photo) : car.photo != null) return false;
        if (regNumber != null ? !regNumber.equals(car.regNumber) : car.regNumber != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = super.hashCode();
        long temp;
        result = 31 * result + (regNumber != null ? regNumber.hashCode() : 0);
        result = 31 * result + (model != null ? model.hashCode() : 0);
        result = 31 * result + (name != null ? name.hashCode() : 0);
        temp = price != +0.0d ? Double.doubleToLongBits(price) : 0L;
        result = 31 * result + (int) (temp ^ (temp >>> 32));
        result = 31 * result + (photo != null ? photo.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder();
        sb.append("Car");
        sb.append("{regNumber='").append(regNumber).append('\'');
        sb.append(", model=").append(model);
        sb.append(", name='").append(name).append('\'');
        sb.append(", price=").append(price);
        sb.append(", photo='").append(photo).append('\'');
        sb.append('}');
        return sb.toString();
    }
}
