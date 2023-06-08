package daopattern;

import java.util.ArrayList;

public interface IRepository <T>{
    ArrayList<T> getAll();
    Boolean addToTable(T t);
    Boolean removeFromTable(T t);
    Double paymentCalc(float total);
    void printInvoice(String invoice);
    Boolean login();
}
