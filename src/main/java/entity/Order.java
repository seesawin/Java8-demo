package entity;

public class Order {
    String name;
    Double totalQty;

    public Order(String name, Double totalQty) {
        this.name = name;
        this.totalQty = totalQty;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "Order{" +
                "name='" + name + '\'' +
                ", totalQty=" + totalQty +
                '}';
    }

    public Double getTotalQty() {
        return totalQty;
    }

    public void setTotalQty(Double totalQty) {
        this.totalQty = totalQty;
    }
}
