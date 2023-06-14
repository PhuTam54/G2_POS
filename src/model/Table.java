package model;

import javafx.scene.control.Button;

public class Table {
    int id, qty;
    String name;
    Double price;
    Button delete;

    public Table(int qty, String name, Double price) {
        this.id = id;
        this.qty = qty;
        this.name = name;
        this.price = price;
    }

    public Table() {
    }
    public Table(int id, int qty, String name, Double price) {
        this.id = id;
        this.qty = qty;
        this.name = name;
        this.price = price;
        this.delete = new Button("Delete");
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

    public Button getDelete() {
        return delete;
    }
}
