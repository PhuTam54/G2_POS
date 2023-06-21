package model;

import javafx.scene.control.cell.PropertyValueFactory;

import java.sql.Date;

public class HistoryTable {
    int Orderid;
    int Cusid;
    String CusName;
    int Adminid;
    Date OrderDate;
    int Productid;
    String ProductName;
    double SoldPrice;
    int SoldQty ;
//    double Total;
    int OrderStatus;

    public HistoryTable(int orderid, int cusid, String cusName, int adminid,  Date orderDate, int productid, String productName, Double soldPrice, int soldQty, int orderStatus) {
        Orderid = orderid;
        Cusid = cusid;
        CusName = cusName;
        Adminid = adminid;
        OrderDate = orderDate;
        Productid = productid;
        ProductName = productName;
        SoldPrice = soldPrice;
        SoldQty = soldQty;
//        Total = total;
        OrderStatus = orderStatus;
    }

    public int getOrderid() {
        return Orderid;
    }

    public void setOrderid(int orderid) {
        Orderid = orderid;
    }

    public int getCusid() {
        return Cusid;
    }

    public void setCusid(int cusid) {
        Cusid = cusid;
    }

    public String getCusName() {
        return CusName;
    }

    public void setCusName(String cusName) {
        CusName = cusName;
    }

    public int getAdminid() {
        return Adminid;
    }

    public void setAdminid(int adminid) {
        Adminid = adminid;
    }

    public Date getOrderDate() {
        return OrderDate;
    }

    public void setOrderDate(Date orderDate) {
        OrderDate = orderDate;
    }

    public int getProductid() {
        return Productid;
    }

    public void setProductid(int productid) {
        Productid = productid;
    }

    public String getProductName() {
        return ProductName;
    }

    public void setProductName(String productName) {
        ProductName = productName;
    }

    public double getSoldPrice() {
        return SoldPrice;
    }

    public void setSoldPrice(double soldPrice) {
        SoldPrice = soldPrice;
    }

    public int getSoldQty() {
        return SoldQty;
    }

    public void setSoldQty(int soldQty) {
        SoldQty = soldQty;
    }

    public int getOrderStatus() {
        return OrderStatus;
    }

    public void setOrderStatus(int orderStatus) {
        OrderStatus = orderStatus;
    }

    @Override
    public String toString() {
        return "HistoryTable{" +
                "Orderid=" + Orderid +
                ", Cusid=" + Cusid +
                ", CusName='" + CusName + '\'' +
                ", Adminid=" + Adminid +
                ", OrderDate=" + OrderDate +
                ", Productid=" + Productid +
                ", ProductName='" + ProductName + '\'' +
                ", SoldPrice=" + SoldPrice +
                ", SoldQty=" + SoldQty +
                ", OrderStatus=" + OrderStatus +
                '}';
    }
}
