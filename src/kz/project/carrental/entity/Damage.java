package kz.project.carrental.entity;

public class Damage extends Entity {

    private Order order;
    private String description;
    private double price;
    private boolean repaired;

    public Order getOrder() {
        return order;
    }

    public void setOrder(Order order) {
        this.order = order;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public boolean isRepaired() {
        return repaired;
    }


    public boolean getRepaired() {
        return repaired;
    }

    public void setRepaired(boolean repaired) {
        this.repaired = repaired;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Damage)) return false;
        if (!super.equals(o)) return false;

        Damage damage = (Damage) o;

        if (Double.compare(damage.price, price) != 0) return false;
        if (repaired != damage.repaired) return false;
        if (description != null ? !description.equals(damage.description) : damage.description != null) return false;
        if (order != null ? !order.equals(damage.order) : damage.order != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = super.hashCode();
        long temp;
        result = 31 * result + (order != null ? order.hashCode() : 0);
        result = 31 * result + (description != null ? description.hashCode() : 0);
        temp = price != +0.0d ? Double.doubleToLongBits(price) : 0L;
        result = 31 * result + (int) (temp ^ (temp >>> 32));
        result = 31 * result + (repaired ? 1 : 0);
        return result;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder();
        sb.append("Damage");
        sb.append("{order=").append(order);
        sb.append(", description='").append(description).append('\'');
        sb.append(", price=").append(price);
        sb.append(", repaired=").append(repaired);
        sb.append('}');
        return sb.toString();
    }
}
