package daopattern;

import model.Table;

import java.util.ArrayList;

public class TableRepository implements IRepository<Table> {
    // singleton pattern
    private static TableRepository instance;
    private TableRepository() {

    }
    public static TableRepository getInstance() {
        if (instance == null) {
            instance = new TableRepository();
        }
        return instance;
    }

    @Override
    public ArrayList<Table> getAll() {
        return null;
    }

    @Override
    public Boolean addToTable(Table table) {
        return null;
    }

    @Override
    public Boolean removeFromTable(Table table) {
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