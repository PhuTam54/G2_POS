package controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import model.Order;

import java.io.IOException;

public class PaymentController {

    public TextField txtchange;
    public TextField txtpay;
    public Button txt10k;
    @FXML
    public TextField txttotal;
    public Button btnVis;
    public Button btnVnpay;
    public Button btnBack;
    private String previousPayValue; // Biến tạm để lưu giá trị txtpay trước khi cập nhật
    private TableView<Order> tbv;
    private String lastPayValue = "";

    public void setTotal(String total) {
        txttotal.setText(total);
    }

    public void btnMoney1(MouseEvent mouseEvent) {
        txtpay.setText("1$");
    }

    public void btnDeletes(MouseEvent mouseEvent) {
        if(txtpay!=null){
            txtpay.setText("");
        }
    }

    public void number1(MouseEvent mouseEvent) {
        txtpay.setText("1");
    }

    public void btnMoney2(MouseEvent mouseEvent) {
        txtpay.setText("2$");
    }

    public void btnMoney3(MouseEvent mouseEvent) {
        txtpay.setText("5$");
    }

    public void btnMoney4(MouseEvent mouseEvent) {
        txtpay.setText("10$");
    }

    public void btnMoney5(MouseEvent mouseEvent) {
        txtpay.setText("20$");
    }

    public void btnMoney6(MouseEvent mouseEvent) {
        txtpay.setText("50$");
    }

    public void btnMoney7(MouseEvent mouseEvent) {
        txtpay.setText("100$");
    }

    public void pay(MouseEvent mouseEvent) {
        String changeText = txtchange.getText();
        String payText = txtpay.getText();
        if (changeText.isEmpty() && payText.isEmpty()) {
            // Hiển thị thông báo khi cả `txtchange` và `txtpay` không có giá trị
            showAlert(Alert.AlertType.WARNING, "Payment Error", "Empty Payment",
                    "Please enter the payment amount.");
        } else if (changeText.isEmpty() && !payText.isEmpty()) {
            // Hiển thị thông báo khi chỉ `txtchange` không có giá trị nhưng `txtpay` có giá trị
            showAlert(Alert.AlertType.WARNING, "Payment Error", "Incomplete Payment",
                    "Let's calculate the excess.");
        } else {
            if (txtpay != null && txttotal != null) {
                String payAmountText = payText.replaceAll("[^\\d.]", "");
                String totalText = txttotal.getText().replaceAll("[^\\d.,]", "").replace(',', '.');

                try {
                    double payAmount = Double.parseDouble(payAmountText);
                    double totalAmount = Double.parseDouble(totalText);

                    if (payAmount < totalAmount) {
                        // Hiển thị cảnh báo nếu số tiền thanh toán không đủ
                        showAlert(Alert.AlertType.WARNING, "Payment Error", "Insufficient Payment",
                                "The amount paid is less than the total amount.");
                    } else {
                        double changeAmount = payAmount - totalAmount;
                        txtchange.setText(String.format("%.2f", changeAmount) + "$");

                        // Hiển thị thông báo thành công
                        showAlert(Alert.AlertType.INFORMATION, "Payment Success", "Payment completed successfully.",
                                "Change amount: $" + String.format("%.2f", changeAmount));
                        // Lưu giá trị của txtpay vào biến tạm khi hoàn tất giao dịch
                        previousPayValue = txtpay.getText();

                        // Chuyển đến trang homepos.fxml
                        try {
                            FXMLLoader loader = new FXMLLoader(getClass().getResource("../views/homepos.fxml"));
                            Parent homeParent = loader.load();
                            Scene homeScene = new Scene(homeParent);

                            Stage window = (Stage) txtpay.getScene().getWindow();
                            window.setScene(homeScene);
                            window.show();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }


                        // Lưu giá trị của txtpay vào biến tạm khi hoàn tất giao dịch
                        previousPayValue = txtpay.getText();
                    }
                } catch (NumberFormatException e) {
                    e.printStackTrace();
                }
            }
        }
    }
    private void showAlert(Alert.AlertType alertType, String title, String headerText, String contentText) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(headerText);
        alert.setContentText(contentText);
        alert.showAndWait();
    }

    public void Number(ActionEvent ae) {
        String no = ((Button) ae.getSource()).getText();
        txtpay.appendText(no);
    }


    public void complete(ActionEvent actionEvent) {
        if (txtpay != null && txttotal != null) {
            String payText = txtpay.getText().replaceAll("[^\\d.]", "");
            String totalText = txttotal.getText().replaceAll("[^\\d.,]", "").replace(',', '.');

            try {
                double payAmount = Double.parseDouble(payText);
                double totalAmount = Double.parseDouble(totalText);
                double changeAmount = payAmount - totalAmount;

                txtchange.setText(String.format("%.2f", changeAmount) + "$");

                // Lưu giá trị của txtpay vào biến lastPayValue khi hoàn tất giao dịch
                lastPayValue = txtpay.getText();

                // Cập nhật giá trị của txtpay
                txtpay.setText(lastPayValue);
                txtpay.setText(lastPayValue + "$");
            } catch (NumberFormatException e) {
                e.printStackTrace();
            }
        }
    }


    public void Vis(ActionEvent actionEvent) {

        btnVis.setStyle("-fx-background-color: #4e2a84; -fx-border-color: #D3D3D3; -fx-border-radius: 6px; -fx-text-fill: white;");
        btnVnpay.setStyle("-fx-background-color: white; -fx-border-color: #D3D3D3; -fx-border-radius: 6px;-fx-text-fill: black;");


    }

    public void Vnpay(ActionEvent actionEvent) {
        btnVnpay.setStyle("-fx-background-color: #4e2a84; -fx-border-color: #D3D3D3; -fx-border-radius: 6px; -fx-text-fill: white;");
        btnVis.setStyle("-fx-background-color:white ; -fx-border-color: #D3D3D3; -fx-border-radius: 6px; -fx-text-fill: black;");
    }


    public void btnDelete(MouseEvent mouseEvent) {
        String currentText = txtpay.getText();
        if (!currentText.isEmpty()) {
            txtpay.setText(currentText.substring(0, currentText.length() - 1));
            lastPayValue = txtpay.getText(); // Cập nhật giá trị của lastPayValue
        }
    }


}