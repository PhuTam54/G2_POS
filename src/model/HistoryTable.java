package model;

import java.sql.Date;

public class HistoryTable {
    int orderID;
    int customerID;
    String customerName;
    int adminID;
    Date orderDate;
    int productID;
    String productName;
    double soldPrice;
    int soldQty;
    int orderStatus;
    double orderCash;
    String orderNotes;


    public HistoryTable(int orderID, int customerID, int adminID, Date orderDate, int orderStatus, double orderCash, String orderNotes) {
        this.orderID = orderID;
        this.customerID = customerID;
        this.adminID = adminID;
        this.orderDate = orderDate;
        this.orderStatus = orderStatus;
        this.orderCash = orderCash;
        this.orderNotes = orderNotes;
    }

    public HistoryTable(int orderID, int customerID, int adminID, Date orderDate, int productID, String productName, double soldPrice, int soldQty, int orderStatus, double orderCash, String orderNotes) {
        this.orderID = orderID;
        this.customerID = customerID;
        this.adminID = adminID;
        this.orderDate = orderDate;
        this.productID = productID;
        this.productName = productName;
        this.soldPrice = soldPrice;
        this.soldQty = soldQty;
        this.orderStatus = orderStatus;
        this.orderCash = orderCash;
        this.orderNotes = orderNotes;
    }

    public HistoryTable(int orderID, int customerID, int adminID, Date orderDate, double orderCash, String orderNotes) {
        this.orderID = orderID;
        this.customerID = customerID;
        this.adminID = adminID;
        this.orderDate = orderDate;
        this.orderCash = orderCash;
        this.orderNotes = orderNotes;
    }

    public int getOrderID() {
        return orderID;
    }

    public void setOrderID(int orderID) {
        this.orderID = orderID;
    }

    public int getCustomerID() {
        return customerID;
    }

    public void setCustomerID(int customerID) {
        this.customerID = customerID;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public int getAdminID() {
        return adminID;
    }

    public void setAdminID(int adminID) {
        this.adminID = adminID;
    }

    public Date getOrderDate() {
        return orderDate;
    }

    public void setOrderDate(Date orderDate) {
        this.orderDate = orderDate;
    }

    public int getProductID() {
        return productID;
    }

    public void setProductID(int productID) {
        this.productID = productID;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public double getSoldPrice() {
        return soldPrice;
    }

    public void setSoldPrice(double soldPrice) {
        this.soldPrice = soldPrice;
    }

    public int getSoldQty() {
        return soldQty;
    }

    public void setSoldQty(int soldQty) {
        this.soldQty = soldQty;
    }

    public int getOrderStatus() {
        return orderStatus;
    }

    public void setOrderStatus(int orderStatus) {
        this.orderStatus = orderStatus;
    }

    public double getOrderCash() {
        return orderCash;
    }

    public void setOrderCash(double orderCash) {
        this.orderCash = orderCash;
    }

    public String getOrderNotes() {
        return orderNotes;
    }

    public void setOrderNotes(String orderNotes) {
        this.orderNotes = orderNotes;
    }

    @Override
    public String toString() {
        return "HistoryTable{" +
                "orderID=" + orderID +
                ", customerID=" + customerID +
                ", adminID=" + adminID +
                ", orderDate=" + orderDate +
                ", productID=" + productID +
                ", productName='" + productName + '\'' +
                ", soldPrice=" + soldPrice +
                ", soldQty=" + soldQty +
                ", orderStatus=" + orderStatus +
                ", orderCash=" + orderCash +
                ", orderNotes='" + orderNotes + '\'' +
                '}';
    }
}

//package model;
//
//import javafx.scene.control.cell.PropertyValueFactory;
//
//import java.sql.Date;
//
//public class HistoryTable {
//    int orderID;
//    int customerID;
////    String customerName;
//    int adminID;
//    Date orderDate;
////    int productID;
////    String productName;
////    double soldPrice;
////    int soldQty;
////    int orderStatus;
//    double orderCash;
//    String orderNotes;
//
//
//    public HistoryTable(int orderID, int customerID, int adminID, Date orderDate, double orderCash, String orderNotes) {
//        this.orderID = orderID;
//        this.customerID = customerID;
//        this.adminID = adminID;
//        this.orderDate = orderDate;
////        this.orderStatus = orderStatus;
//        this.orderCash = orderCash;
//        this.orderNotes = orderNotes;
//    }
//
//    public int getOrderID() {
//        return orderID;
//    }
//
//    public void setOrderID(int orderID) {
//        this.orderID = orderID;
//    }
//
//    public int getCustomerID() {
//        return customerID;
//    }
//
//    public void setCustomerID(int customerID) {
//        this.customerID = customerID;
//    }
//
//    public int getAdminID() {
//        return adminID;
//    }
//
//    public void setAdminID(int adminID) {
//        this.adminID = adminID;
//    }
//
//    public Date getOrderDate() {
//        return orderDate;
//    }
//
//    public void setOrderDate(Date orderDate) {
//        this.orderDate = orderDate;
//    }
//
//
//    public double getOrderCash() {
//        return orderCash;
//    }
//
//    public void setOrderCash(double orderCash) {
//        this.orderCash = orderCash;
//    }
//
//    public String getOrderNotes() {
//        return orderNotes;
//    }
//
//    public void setOrderNotes(String orderNotes) {
//        this.orderNotes = orderNotes;
//    }
//
//    @Override
//    public String toString() {
//        return "HistoryTable{" +
//                "orderID=" + orderID +
//                ", customerID=" + customerID +
//                ", adminID=" + adminID +
//                ", orderDate=" + orderDate +
//                ", orderCash=" + orderCash +
//                ", orderNotes='" + orderNotes + '\'' +
//                '}';
//    }
//}
