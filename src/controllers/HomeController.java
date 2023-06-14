package controllers;

import enums.RepositoryType;
import factory.RepositoryFactory;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.print.PrinterJob;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.text.Text;
import javafx.stage.Stage;
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
    public Label qty2;
    ObservableList<Table> list = FXCollections.observableArrayList();
    public static Table resetTable;

    // Date
    private final LocalDateTime dateTime = LocalDateTime.now();
    int day = dateTime.getDayOfMonth();
    int month = dateTime.getMonthValue();
    int year = dateTime.getYear();
    int hours = dateTime.getHour();
    int minute = dateTime.getMinute();
    int second = dateTime.getSecond();
    //

    public void addToTable(Table table) {
        list.add(table);
        tbv.setItems(list);
        tbv.refresh();
    }

    public void addToTable1(MouseEvent mouseEvent) {
        // test add
        int id = 1;
        int count = Integer.parseInt(qty1.getText());
        count ++;
        String name = "Tocotoco Bobatea";
        Double price = Math.ceil((count * 3.99) * 100) / 100;
        Table tb = new Table(id, count, name, price);
        // product already add
        try {
            for (Table t: list) {
                if (t.getName().equals(tb.getName())) {
                    list.remove(t);
                }
            }
        } catch (Exception e) {
            System.out.println("Product already add Error: " + e.getMessage());
        }
        addToTable(tb);


        qty1.setText(String.valueOf(count));
        total.setText("$" + price);
        totalproduct.setText(String.valueOf(count));
    }
    public void addToTable2(ActionEvent actionEvent) {
        // test add
        int id = 2;
        int count = Integer.parseInt(qty2.getText());
        count ++;
        String name = "Trà xoài bưởi hồng";
        Double price = Math.ceil((count * 3.99) * 100) / 100;
        Table tb = new Table(id, count, name, price);
        // product already add
        try {
            for (Table t: list) {
                if (t.getName().equals(tb.getName())) {
                    list.remove(t);
                }
            }
        } catch (Exception e) {
            System.out.println("Product already add Error: " + e.getMessage());
        }
        addToTable(tb);

        qty2.setText(String.valueOf(count));
    }

    public void reset(ActionEvent actionEvent) {
        try {
            qty1.setText("0");
            qty2.setText("0");
            total.setText("$0.0");
            totalproduct.setText("0");
            resetTable = null;
            resetTable = tbv.getSelectionModel().getSelectedItem();
            if (resetTable != null) {
//                for (int i = 0; i <= list.size(); i++) {
//                    list.remove(list.get(i));
//                }
                for (Table tb: list) {
                    list.remove(tb);
                }
                throw new Exception("Canceled");
            }
//            if (resetTable != null) {
//                list.forEach();
//            }
            tbv.setItems(list);
            tbv.refresh();
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
            RepositoryFactory.createRepositoryInstance(RepositoryType.TABLE).getAll();
        } catch (Exception e) {
            System.out.println("Initialize Error: " + e.getMessage());
        }

        // Date
        new Thread(()-> {
            boolean flag = true;
            while (flag) {
                txtDay.setText(day + "/");
                txtMonth.setText(month + "/");
                txtYear.setText(String.valueOf(year));
                txtHours.setText(hours + ":");
                txtMin.setText(minute + ":");
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
                    System.out.println("Thread Error: " + e.getMessage());
                }
            }
        }).start();
        //
    }

    public void printBill(MouseEvent mouseEvent) {
        String billText = "                              POS Market \n";
        billText += "                    \tSố 18 Tôn Thất Thuyết \n";
        billText += "                       \t+84 123456789 \n";
        billText += "----------------------------------------------------------------\n";
        billText += " Name                      \tQty                 \tPrice \n";
        billText += "----------------------------------------------------------------\n";

        ObservableList<Table> data = tbv.getItems();
        for (int i = 0; i < data.size(); i++) {
            billText += data.get(i).getName() + "\t\t"+ data.get(i).getQty()+ "\t\t\t"+ data.get(i).getPrice() + " \n";
        }

        billText += "----------------------------------------------------------------\n";
        billText += "Total :\t" + total.getText() + "\n";
        billText += "Cash :\t" + total.getText() + "\n";
        billText += "Ballance :\t" + totalproduct.getText() + "\n";
        billText += "======================================\n";
        billText += "                    Thanks For Your Business...!" + "\n";


        TextArea bill = new TextArea(billText); // hiển thị hóa đơn trong TextArea
        bill.setEditable(false);
        Stage stage = new Stage();
        Text billtext = new Text(); //khởi tạo giá trị của billtext
        billtext.setText(billText);
        Scene scene = new Scene(bill ,400,600); //sử dụng biến bill thay cho billtext
        stage.setScene(scene);
        stage.show();

        // In hóa đơn
        PrinterJob printerJob = PrinterJob.createPrinterJob();
        if (printerJob != null) {
            boolean success = printerJob.printPage(billtext);
            if (success) {
                printerJob.endJob();
            }
        }

    }
}
