package controllers;

import enums.RepositoryType;
import factory.RepositoryFactory;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.text.Text;
import model.Table;

import java.net.URL;
import java.time.LocalDateTime;
import java.util.ResourceBundle;

public class HomeController implements Initializable {
    public TableView<Table> tbv;
    public TableColumn<Table, Integer> colID;
    public TableColumn<Table, String> colName;
    public TableColumn<Table, Integer> colQty;
    public TableColumn<Table, Double> colPrice;
    public TableColumn<Table, Button> colAction;
    public Label qty1;
    public Label total;
    public Label price1;
    public Label totalproduct;
    public Text txtHours;
    public Text txtMin;
    public Text txtSecond;
    public Text txtDay;
    public Text txtMonth;
    public Text txtYear;
    ObservableList<Table> list = FXCollections.observableArrayList();
    public static Table resetTable;


    // Date
    private LocalDateTime dateTime = LocalDateTime.now();
    int day = dateTime.getDayOfMonth();
    int month = dateTime.getMonthValue();
    int year = dateTime.getYear();
    int hours = dateTime.getHour();
    int minute = dateTime.getMinute();
    int second = dateTime.getSecond();
    //

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

    public void addToTable(MouseEvent mouseEvent) {
        int count = Integer.parseInt(qty1.getText());
        count ++;
        qty1.setText(String.valueOf(count));

        Double price = Math.ceil((count * 3.99) * 100) / 100;
        addTable(1, count,"Tocotoco-$" + 3.99, price);
        total.setText("$" + price);
        totalproduct.setText(String.valueOf(count));
    }

    public void reset(ActionEvent actionEvent) {
        try {
            qty1.setText("0");
            total.setText("$0.0");
            tbv.setItems(list);
            tbv.refresh();
            resetTable = null;
            resetTable = tbv.getSelectionModel().getSelectedItem();
            if (resetTable != null) {
                for (int i = 0; i <= list.size(); i++) {
                    list.remove(list.get(i));
                }
            }
        } catch (Exception e) {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setContentText(e.getMessage());
            alert.show();
        }
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        colID.setCellValueFactory(new PropertyValueFactory<>("id"));
        colName.setCellValueFactory(new PropertyValueFactory<>("name"));
        colQty.setCellValueFactory(new PropertyValueFactory<>("qty"));
        colPrice.setCellValueFactory(new PropertyValueFactory<>("price"));
        colAction.setCellValueFactory(new PropertyValueFactory<>("delete"));
        try {
//            ObservableList<Table> list = FXCollections.observableArrayList();
            // query
            list.addAll(RepositoryFactory.createRepositoryInstance(RepositoryType.TABLE).getAll());
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }

        // Date
        new Thread(()-> {
            boolean flag = true;
            while (flag) {
                txtDay.setText(day + " /");
                txtMonth.setText(month + " /");
                txtYear.setText(String.valueOf(year));
                txtHours.setText(hours + " :");
                txtMin.setText(minute + " :");
                txtSecond.setText(String.valueOf(second));
                second ++;
                if (second > 59) {
                    second = 0;
                    minute++;
                }if(minute > 59) {
                    minute = 0;
                    hours ++;
                }if (hours > 23) {
                    hours = 0;
                    day ++;
                } switch (month) {
                    case 1:
                    case 3:
                    case 5:
                    case 7:
                    case 9:
                    case 11:
                        if (day > 31) {
                            day = 1;
                            month ++;
                        }
                    case 2:
                        if (year % 4 == 0 && year % 100 != 0) {
                            if (day > 29) {
                                day = 1;
                                month ++;
                            }
                        } else {
                            if (day > 28) {
                                day = 1;
                                month ++;
                            }
                        }
                    case 4:
                    case 6:
                    case 8:
                    case 10:
                        if (day > 30) {
                            day = 1;
                            month ++;
                        }
                    case 12:
                        if (day > 30) {
                            day = 1;
                            month = 1;
                            year ++;
                        }
                }
                try {
                    Thread.sleep(1000); // 1000 milliseconds
                } catch (Exception e) {
                    System.out.println("Error: " + e.getMessage());
                }
            }
        }).start();
        //
    }
}
