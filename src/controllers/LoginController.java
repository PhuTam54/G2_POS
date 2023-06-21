package controllers;

import database.Connector;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
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
    public Button btnSignin;

    public void handleButtonAction(MouseEvent event) {
        if (event.getSource() == btnSignin) {
            //login here
            if (logIn().equals("Success")) {
                try {

                    FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/views/homepos.fxml"));
                    Parent root = fxmlLoader.load();

                    Stage stage = new Stage();
                    stage.setTitle("POS | Dashboard");
                    stage.setScene(new Scene(root, 1315, 810));

                    stage.show();
                    ((Node)(event.getSource())).getScene().getWindow().hide();
                } catch (Exception e) {
                    System.err.println(e.getMessage());
                }
            }
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        try {
            Connection conn = new Connector().getConn();
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
//            Label lbLogin2 = new text(user).lbLogin2;
            setLblError(Color.TOMATO, "Empty credentials");
            status = "Error";
        } else {
            //query
            String sql = "SELECT * FROM admin WHERE adminUsername = ? AND adminPassword = ?";
            try {
                Connection conn = new Connector().getConn();
                PreparedStatement pst = conn.prepareStatement(sql);
                ResultSet rs;
                pst.setString(1, user);
                pst.setString(2, password);
                rs = pst.executeQuery();
                if (!rs.next()) {
//                    new CRUD1(user).showBooks();
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