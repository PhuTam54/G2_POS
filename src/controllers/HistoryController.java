package controllers;

import database.Connector;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import model.HistoryTable;

import java.net.URL;
import java.sql.Connection;
import java.sql.Date;
import java.sql.ResultSet;
import java.sql.Statement;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class HistoryController implements Initializable {
    public TableColumn<HistoryTable, Integer> colOrderID;
    public TableColumn<HistoryTable, Integer> colCusID;
    public TableColumn<HistoryTable, Integer> colAdminID;
    public TableColumn<HistoryTable, DateTimeFormatter> colOrderDate;
//    public TableColumn<HistoryTable, Integer> colProductID;
    public TableColumn<HistoryTable, String> colProductName;
    public TableColumn<HistoryTable, Double> colOrderCash;
    public TableColumn<HistoryTable, String> colOrderNote;
    public TableView<HistoryTable> historyView;

    // edit
    public TableColumn<HistoryTable, Double> colSoldPrice;
    public TableColumn<HistoryTable, Integer> colSoldQty;
    public TableColumn<HistoryTable, Double> colTotal;
    public Label txtOrderID;
    public Label txtCusName;
    public Label txtCusPhone;
    public Label txtOrderDate;
    public Label txtTotalPrice;
    public TableView orderDetailView;

    // Date
    public Text txtHours, txtMin, txtSecond, txtDay, txtMonth, txtYear;
    public final LocalDateTime dateTime = LocalDateTime.now();
    int day = dateTime.getDayOfMonth();
    int month = dateTime.getMonthValue();
    int year = dateTime.getYear();
    int hours = dateTime.getHour();
    int minute = dateTime.getMinute();
    int second = dateTime.getSecond();
    //

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        colOrderID.setCellValueFactory(new PropertyValueFactory<>("orderID"));
        colCusID.setCellValueFactory(new PropertyValueFactory<>("customerID"));
        colAdminID.setCellValueFactory(new PropertyValueFactory<>("adminID"));
        colOrderDate.setCellValueFactory(new PropertyValueFactory<>("orderDate"));
//        colProductID.setCellValueFactory(new PropertyValueFactory<>("productID"));
//        colProductName.setCellValueFactory(new PropertyValueFactory<>("productName"));
//        colSoldPrice.setCellValueFactory(new PropertyValueFactory<>("soldPrice"));
//        colSoldQty.setCellValueFactory(new PropertyValueFactory<>("soldQty"));
//        colOrderStatus.setCellValueFactory(new PropertyValueFactory<>("orderStatus"));
        colOrderCash.setCellValueFactory(new PropertyValueFactory<>("orderCash"));
        colOrderNote.setCellValueFactory(new PropertyValueFactory<>("orderNotes"));

        try{
            ObservableList<HistoryTable> list = FXCollections.observableArrayList();
            list.addAll(getAll());
            historyView.setItems(list);
        }catch (Exception e){
            System.out.println("Init error: "+e.getMessage());
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
                    default:
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
    public ArrayList<HistoryTable> getAll() {
        ArrayList<HistoryTable> orders = new ArrayList<>();

        try {
            Connection conn = Connector.getInstance().getConn();
            // query
            Statement stt = conn.createStatement();
            String sql = "SELECT * \n" +
                    "FROM orders AS o \n" +
                    "WHERE o.orderStatus = 2 ORDER BY o.orderID DESC";

//            String sql ="SELECT o.orderID, c.customerID, c.customerName, a.adminID, o.orderDate, p.productID, p.productName, od.soldPrice, od.soldQty, o.orderStatus, o.orderCash, o.orderNotes \n" +
//                    "FROM orders AS o INNER JOIN order_detail AS od ON o.orderID = od.orderID INNER JOIN customer AS c ON o.customerID = c.customerID INNER JOIN `admin` AS a ON o.adminID = a.adminID INNER JOIN product AS p ON od.productID = p.productID\n" +
//                    "WHERE o.orderStatus = 2 ORDER BY o.orderID DESC";
            ResultSet rs = stt.executeQuery(sql);
            while (rs.next()) {
                int orderID = rs.getInt("orderID");
                int customerID = rs.getInt("customerID");
                int adminID = rs.getInt("adminID");
                Date orderDate = rs.getDate("orderDate");
//                int productID = rs.getInt("productID");
//                String productName = rs.getString("productName");
//                double soldPrice = rs.getDouble("soldPrice");
//                int soldQty = rs.getInt("soldQty");
//                int orderStatus = rs.getInt("orderStatus");
                double orderCash = rs.getDouble("orderCash");
                String orderNotes = rs.getString("orderNotes");
//                HistoryTable historyTable= new HistoryTable(orderID, customerID, adminID, orderDate, productID, productName, soldPrice, soldQty, orderStatus, orderCash, orderNotes);
                HistoryTable historyTable= new HistoryTable(orderID, customerID, adminID, orderDate, orderCash, orderNotes);
                orders.add(historyTable);
            }
        } catch (Exception e) {
            System.out.println("Error get all" + e.getMessage());
        }
        System.out.println(orders + "\n");
        return orders;
    }

    public void goBackHome(MouseEvent mouseEvent) {
        Stage currentStage = (Stage) ((Node) mouseEvent.getSource()).getScene().getWindow();
        currentStage.close();
    }
}
