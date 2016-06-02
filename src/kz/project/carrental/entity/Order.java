package kz.project.carrental.entity;

import kz.project.carrental.entity.enums.OrderStatus;

import java.sql.Timestamp;
import java.util.List;

public class Order extends Entity {

    private Car car;
    private User client;
    private Timestamp beginRent;
    private Timestamp endRent;
    private OrderStatus status;
    private Timestamp createTime;
    private List<Damage> damages;

    public Order() {
    }

    public Car getCar() {
        return car;
    }

    public void setCar(Car car) {
        this.car = car;
    }

    public User getClient() {
        return client;
    }

    public void setClient(User client) {
        this.client = client;
    }

    public Timestamp getBeginRent() {
        return beginRent;
    }

    public void setBeginRent(Timestamp beginRent) {
        this.beginRent = beginRent;
    }

    public Timestamp getEndRent() {
        return endRent;
    }

    public void setEndRent(Timestamp endRent) {
        this.endRent = endRent;
    }

    public OrderStatus getStatus() {
        return status;
    }

    public void setStatus(OrderStatus status) {
        this.status = status;
    }

    public double getPrice() {
        double price = 0;
        //TODO + damage
        long deltaTime = endRent.getTime() - beginRent.getTime();
        double hours = (double) deltaTime / (60 * 60 * 1000);
        price = car.getPrice() * hours;
        if (damages!=null){
            for (Damage damage:damages){
                price += damage.getPrice();
            }
        }
        return price;
    }


    public Timestamp getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Timestamp createTime) {
        this.createTime = createTime;
    }

    public List<Damage> getDamages() {
        return damages;
    }

    public void setDamages(List<Damage> damages) {
        this.damages = damages;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Order)) return false;
        if (!super.equals(o)) return false;

        Order order = (Order) o;

        if (beginRent != null ? !beginRent.equals(order.beginRent) : order.beginRent != null) return false;
        if (car != null ? !car.equals(order.car) : order.car != null) return false;
        if (client != null ? !client.equals(order.client) : order.client != null) return false;
        if (createTime != null ? !createTime.equals(order.createTime) : order.createTime != null) return false;
        if (endRent != null ? !endRent.equals(order.endRent) : order.endRent != null) return false;
        if (status != order.status) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = super.hashCode();
        result = 31 * result + (car != null ? car.hashCode() : 0);
        result = 31 * result + (client != null ? client.hashCode() : 0);
        result = 31 * result + (beginRent != null ? beginRent.hashCode() : 0);
        result = 31 * result + (endRent != null ? endRent.hashCode() : 0);
        result = 31 * result + (status != null ? status.hashCode() : 0);
        result = 31 * result + (createTime != null ? createTime.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder();
        sb.append("Order");
        sb.append("{car=").append(car);
        sb.append(", client=").append(client);
        sb.append(", beginRent=").append(beginRent);
        sb.append(", endRent=").append(endRent);
        sb.append(", status=").append(status);
        sb.append(", createTime=").append(createTime);
        sb.append('}');
        return sb.toString();
    }
}

