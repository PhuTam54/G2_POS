package controllers;

import database.Connector;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ResourceBundle;

public class MemberController implements Initializable {
    public Label lblError;
    public TextField txtCustomerName;
    public Button btnAddMember;
    public TextField txtCustomerPhoneNumber;

    public void handleButtonAction(MouseEvent event) {
        if (event.getSource() == btnAddMember) {
            //login here
            if (checkMemberExist().equals("No Exist")) {
                try {
                    String customerName = txtCustomerName.getText();
                    String customerPhoneNumber = txtCustomerPhoneNumber.getText();
                    Connection conn = Connector.getInstance().getConn();
                    //query
                    String sql = "INSERT INTO `customer`(`customerName`, `customerPhone`) VALUES ( ?, ?)";
                    PreparedStatement pst = conn.prepareStatement(sql);
                    pst.setString(1, customerName);
                    pst.setString(2, customerPhoneNumber);
                    pst.executeUpdate();
                    //Đóng cửa sổ hiện tại
                    Stage currentStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
                    currentStage.close();
                    throw new Exception("Add member successfully!");
                } catch (Exception e) {
                    Alert alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setContentText(e.getMessage());
                    alert.show();
                }
            }
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        try {
            Connection conn = Connector.getInstance().getConn();
            if (conn == null) {
                lblError.setTextFill(Color.TOMATO);
                lblError.setText("Server Error : Check");
            } else {
                lblError.setTextFill(Color.GREEN);
                lblError.setText("Server is up : Good to go");
            }

        }catch (Exception e){
            System.err.println(e.getMessage());
        }
    }

    private String checkMemberExist() {
        String customerName = txtCustomerName.getText();
        String customerPhoneNumber = txtCustomerPhoneNumber.getText();
        String status = "No Exist";
        if(customerName.isEmpty() || customerPhoneNumber.isEmpty()) {
            setLblError(Color.TOMATO, "Empty credentials");
            status = "Exist";
        } else {
            try {
                Connection conn = Connector.getInstance().getConn();
                //query
                String sqlCheckMember = "SELECT `customerPhone` FROM `customer` WHERE `customerPhone` = ?";
                PreparedStatement psCheckMember = conn.prepareStatement(sqlCheckMember);
                ResultSet rs;
                psCheckMember.setString(1, customerPhoneNumber);
                rs = psCheckMember.executeQuery();
                if (rs.next()) {
                    setLblError(Color.TOMATO, "This phone number has been added before.");
                } else {
                    setLblError(Color.GREEN, "You can add this member now!");
                    status = "No Exist";
                }
            } catch (Exception e) {
                System.err.println(e.getMessage());
                status = "Exception";
            }
        }
        return status;
    }
    private void setLblError(Color color, String text) {
        lblError.setTextFill(color);
        lblError.setText(text);
    }
}