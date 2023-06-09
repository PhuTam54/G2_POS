package controllers;

import enums.RepositoryType;
import factory.RepositoryFactory;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import model.Table;

import java.net.URL;
import java.util.ResourceBundle;

public class HomeController implements Initializable {
    public TableView<Table> tbv;
    public TableColumn<Table, Integer> colID;
    public TableColumn<Table, String> colName;
    public TableColumn<Table, Integer> colQty;
    public TableColumn<Table, Double> colPrice;
    public Label qty1;
    public Label total;

    ObservableList<Table> list = FXCollections.observableArrayList();
    public static Table resetTable;
    public void addTable(int id, int qty ,String name,  Double price) {
        // product already add
        for (Table t: list) {
            if (list.contains(name)) {
                list.remove(t);
            }
        }
        Table tb = new Table(id, qty, name, price);
        list.add(tb);
        tbv.setItems(list);
        tbv.refresh();
    }

    public void addToTable(MouseEvent mouseEvent) throws Exception {
      try {
          int count = Integer.parseInt(qty1.getText());
          count ++;
          qty1.setText(String.valueOf(count));

          Double price = Math.ceil((count * 3.99) * 100) / 100;
          addTable(1, count,"Tocotoco-$" + 3.99, price);
              total.setText("$" + price);
      }catch (Exception e) {
          System.out.println("Error: " + e.getMessage());
      }
    }



    @Override
    public void initialize(URL location, ResourceBundle resources) {
        colID.setCellValueFactory(new PropertyValueFactory<>("id"));
        colName.setCellValueFactory(new PropertyValueFactory<>("name"));
        colQty.setCellValueFactory(new PropertyValueFactory<>("qty"));
        colPrice.setCellValueFactory(new PropertyValueFactory<>("price"));
        try {
//            ObservableList<Table> list = FXCollections.observableArrayList();
            // query
            list.addAll(RepositoryFactory.createRepositoryInstance(RepositoryType.TABLE).getAll());
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }
}