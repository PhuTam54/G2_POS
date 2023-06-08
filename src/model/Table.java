package model;

public class Table {
    int id, qty;
    String name;
    Double price;

    public Table(int id, int qty, String name, Double price) {
        this.id = id;
        this.qty = qty;
        this.name = name;
        this.price = price;
    }

    public Table() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getQty() {
        return qty;
    }

    public void setQty(int qty) {
        this.qty = qty;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }
}
