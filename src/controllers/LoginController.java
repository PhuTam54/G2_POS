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
import org.mindrot.jbcrypt.BCrypt;

import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ResourceBundle;

public class LoginController implements Initializable {
    public TextField txtUser;
    public PasswordField txtPass;
    public Label lblStatus;
    public Button btnSignIn;

    public void handleButtonAction(MouseEvent event) {
        if (event.getSource() == btnSignIn) {
            //login here
            if (isLoginValid().equals("Success")) {
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
            lblStatus.setTextFill(Color.TOMATO);
            lblStatus.setText("Server Error : Check");
            Connection conn = Connector.getInstance().getConn();
            lblStatus.setTextFill(Color.GREEN);
            lblStatus.setText("Server is up : Good to go");
        }catch (Exception e){
            System.err.println(e.getMessage());
        }
    }

    private String isLoginValid() {
        String status = "Success";
        String user = txtUser.getText();
        String password = txtPass.getText();

        if(user.isEmpty() || password.isEmpty()) {
            setLblStatus(Color.TOMATO, "Empty credentials");
            status = "Error";
        } else {
            //query
            String sql = "SELECT * FROM admin WHERE adminUsername = ?";
            try {
                Connection conn = Connector.getInstance().getConn();
                PreparedStatement pst = conn.prepareStatement(sql);
                ResultSet rs;
                pst.setString(1, user);
                rs = pst.executeQuery();
                if (rs.next()) {
                    String dbPassword = rs.getString("adminPassword");
                    boolean check = BCrypt.checkpw(password, dbPassword);
                    if (check) {
                        setLblStatus(Color.GREEN, "Logged In Successfully...");
                    } else {
                        status = "Error";
                    }
                } else {
                    setLblStatus(Color.TOMATO, "Enter Correct Email/Password");
                    status = "Error";
                }
            } catch (Exception e) {
                System.err.println(e.getMessage());
                status = "Exception";
            }
        }
        return status;
    }
    private void setLblStatus(Color color, String text) {
        lblStatus.setTextFill(color);
        lblStatus.setText(text);
        System.out.println(text);
    }
}