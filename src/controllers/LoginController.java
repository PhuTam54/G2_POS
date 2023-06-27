package controllers;

import database.Connector;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ResourceBundle;

public class LoginController implements Initializable {
    public TextField txtUser;
    public PasswordField txtPass;
    public Label lblError;
    public Button btnSignIn;

    public void handleButtonAction(MouseEvent event) {
        if (event.getSource() == btnSignIn) {
            //login here
            if (logIn().equals("Success")) {
                try {
                    FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/views/home_pos.fxml"));
                    Parent root = fxmlLoader.load();

                    try {
                        String adminName = txtUser.getText();
                        HomeController hc = fxmlLoader.getController();
                        hc.setAdminName(adminName);
                    } catch (Exception e) {
                        System.out.println("Set admin error: " + e.getMessage());
                    }

                    Stage stage = new Stage();
                    stage.setTitle("POS | Dashboard");
                    stage.setScene(new Scene(root, 1315, 810));
                    stage.show();
                } catch (Exception e) {
                    System.err.println(e.getMessage());
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

    private String logIn() {
        String status = "Success";
        String user = txtUser.getText();
        String password = txtPass.getText();
        if(user.isEmpty() || password.isEmpty()) {
            setLblError(Color.TOMATO, "Empty credentials");
            status = "Error";
        } else {
            //query
            String sql = "SELECT * FROM admin WHERE adminUsername = ? AND adminPassword = ?";
            try {
                Connection conn = Connector.getInstance().getConn();
                PreparedStatement pst = conn.prepareStatement(sql);
                ResultSet rs;
                pst.setString(1, user);
                pst.setString(2, password);
                rs = pst.executeQuery();
                if (!rs.next()) {
                    setLblError(Color.TOMATO, "Enter Correct Email/Password");
                    status = "Error";
                } else {

                    setLblError(Color.GREEN, "Logged In Successfully...");
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
        System.out.println(text);
    }
}