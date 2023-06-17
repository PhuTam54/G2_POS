package daopattern;

import model.Order;

import java.util.ArrayList;

public class OrderRepository implements IRepository<Order> {
    // singleton pattern
    private static OrderRepository instance;
    private OrderRepository() {

    }
    public static OrderRepository getInstance() {
        if (instance == null) {
            instance = new OrderRepository();
        }
        return instance;
    }

    @Override
    public ArrayList<Order> getAll() {
        return null;
    }

    @Override
    public Boolean addToTable(Order order) {
        return null;
    }

    @Override
    public Boolean removeFromTable(Order order) {
        return null;
    }

    @Override
    public Double paymentCalc(float total) {
        return null;
    }

    @Override
    public void printInvoice(String invoice) {

    }

    @Override
    public Boolean login() {
        return null;
    }
}
