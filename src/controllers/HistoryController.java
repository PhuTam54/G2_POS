package controllers;

import com.sun.org.apache.xpath.internal.operations.Or;
import database.Connector;
import enums.RepositoryType;
import factory.RepositoryFactory;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.text.Text;
import model.HistoryTable;
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
    public TableColumn<HistoryTable, Integer> colOrderid;
    public TableColumn<HistoryTable, Integer> colCusid;
    public TableColumn<HistoryTable, String> colCusName;
    public TableColumn<HistoryTable, Integer> colAdminID;
    public TableColumn<HistoryTable, DateTimeFormatter> colOrderDate;
    public TableColumn<HistoryTable, Integer> colProductid;
    public TableColumn<HistoryTable, String> colProductName;
    public TableColumn<HistoryTable, Double> colSoldPrice;
    public TableColumn<HistoryTable, Integer> colSoldQty;
    public TableColumn<HistoryTable, Integer> colOrderStatus;
    public TableView<HistoryTable> historyView;
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
        colOrderid.setCellValueFactory(new PropertyValueFactory<>("orderID"));
        colCusid.setCellValueFactory(new PropertyValueFactory<>("customerID"));
        colCusName.setCellValueFactory(new PropertyValueFactory<>("customerName"));
        colAdminID.setCellValueFactory(new PropertyValueFactory<>("adminID"));
        colOrderDate.setCellValueFactory(new PropertyValueFactory<>("orderDate"));
        colProductid.setCellValueFactory(new PropertyValueFactory<>("productID"));
        colProductName.setCellValueFactory(new PropertyValueFactory<>("productName"));
        colSoldPrice.setCellValueFactory(new PropertyValueFactory<>("soldPrice"));
        colSoldQty.setCellValueFactory(new PropertyValueFactory<>("soldQty"));
//        colTotal.setCellValueFactory(new PropertyValueFactory<>("total"));
        colOrderStatus.setCellValueFactory(new PropertyValueFactory<>("orderStatus"));
        try{
            ObservableList<HistoryTable> list = FXCollections.observableArrayList();
            list.addAll(getAll());
            historyView.setItems(list);
        }catch (Exception e){
            System.out.println("error init:"+e.getMessage());
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
            Connection conn = new Connector().getConn();
            // query
            Statement stt = conn.createStatement();
            String sql = "SELECT o.orderID, o.customerID, a.adminID, c.customerName, o.orderDate, p.productID, p.productName, od.soldPrice, od.soldQty, o.orderStatus \n" +
                    "FROM orders AS o INNER JOIN order_detail AS od ON o.orderID = od.orderID INNER JOIN customer AS c ON o.customerID = c.customerID INNER JOIN admin AS a ON o.adminID = a.adminID INNER JOIN product AS p ON od.productID = p.productID\n" +
                    "ORDER BY o.orderID DESC";
            ResultSet rs = stt.executeQuery(sql);
            while (rs.next()) {
                int orderID = rs.getInt("orderID");
                int customerID = rs.getInt("customerID");
                String customerName = rs.getString("customerName");
                int adminID = rs.getInt("adminID");
                Date orderDate = rs.getDate("orderDate");
                int productID = rs.getInt("productID");
                String productName = rs.getString("productName");
                double soldPrice = rs.getDouble("soldPrice");
                int soldQty = rs.getInt("soldQty");
//                double total = 0;
                int orderStatus = rs.getInt("orderStatus");
                HistoryTable historyTable= new HistoryTable(orderID, customerID, customerName, adminID, orderDate, productID, productName, soldPrice, soldQty, orderStatus);
                orders.add(historyTable);
            }
        } catch (Exception e) {
            System.out.println("Error get all" + e.getMessage());
        }
        System.out.println(orders + "\n");
        return orders;
    }
}
