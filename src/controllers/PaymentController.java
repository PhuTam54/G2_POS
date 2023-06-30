package controllers;

import database.Connector;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.print.PrinterJob;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.time.LocalDateTime;
import java.util.ResourceBundle;

public class PaymentController implements Initializable {
    public TextField txtChange;
    public TextField txtPay;
    public Button txt10k;
    @FXML
    public TextField txtTotal;
    public Button btnVis;
    public Button btnVNPay;
    public Button btnBack;
    public TextField txtSubTotal;
    public TextArea billTextArea;
    public TextField txtTotalProduct;
    private String lastPayValue = "";
    private  String billText = "";
    private boolean isPayment = false;
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

    // db
    int orderIDInPayment;
    int customerID;
    double orderTotal, customerPoint;

    public void setTotal(String total, String totalProduct) {
        txtTotal.setText(total);
        orderTotal = Double.parseDouble(total.replaceAll("[^\\d.]", ""));
        txtSubTotal.setText(total);
        txtTotalProduct.setText(totalProduct);
    }

    public void setInvoice(String invoiceText) {
        billText += invoiceText;
        billTextArea.setText(invoiceText);
    }

    public void setOrderID(int orderID) {
        orderIDInPayment = orderID;
    }

    public void btnClear(MouseEvent mouseEvent) {
        if(txtPay !=null){
            txtPay.setText("");
        }
    }

    public void btnDelete(MouseEvent mouseEvent) {
        String currentText = txtPay.getText();
        if (!currentText.isEmpty()) {
            txtPay.setText(currentText.substring(0, currentText.length() - 1));
            lastPayValue = txtPay.getText(); // Cập nhật giá trị của lastPayValue
        }
    }

    public void btnMoney1(MouseEvent mouseEvent) {
        txtPay.setText("$1");
    }

    public void btnMoney2(MouseEvent mouseEvent) {
        txtPay.setText("$2");
    }

    public void btnMoney3(MouseEvent mouseEvent) {
        txtPay.setText("$5");
    }

    public void btnMoney4(MouseEvent mouseEvent) {
        txtPay.setText("$10");
    }

    public void btnMoney5(MouseEvent mouseEvent) {
        txtPay.setText("$20");
    }

    public void btnMoney6(MouseEvent mouseEvent) {
        txtPay.setText("$50");
    }

    public void btnMoney7(MouseEvent mouseEvent) {
        txtPay.setText("$100");
    }

    public void pay(MouseEvent mouseEvent) {
        if (txtPay != null && txtTotal != null) {
            String payText = txtPay.getText().replaceAll("[^\\d.]", "");
            String totalText = txtTotal.getText().replaceAll("[^\\d.,]", "").replace(',', '.');
            try {
                double payAmount = Double.parseDouble(payText);
                double totalAmount = Double.parseDouble(totalText);
                double changeAmount = payAmount - totalAmount;

                txtChange.setText("$" + String.format("%.2f", changeAmount));

                // Lưu giá trị của txtPay vào biến lastPayValue khi hoàn tất giao dịch
                lastPayValue = txtPay.getText();

                // Cập nhật giá trị của txtPay
                txtPay.setText(lastPayValue);
                txtPay.setText(lastPayValue + "$");
            } catch (NumberFormatException e) {
                e.printStackTrace();
            }
        }
        String changeText = txtChange.getText();
        String payText = txtPay.getText();
        if (changeText.isEmpty() && payText.isEmpty()) {
            // Hiển thị thông báo khi cả `txtChange` và `txtPay` không có giá trị
            showAlert(Alert.AlertType.WARNING, "Payment Error", "Empty Payment",
                    "Please enter the payment amount.");
        } else if (changeText.isEmpty() && !payText.isEmpty()) {
            // Hiển thị thông báo khi chỉ `txtChange` không có giá trị nhưng `txtPay` có giá trị
            showAlert(Alert.AlertType.WARNING, "Payment Error", "Incomplete Payment",
                    "Let's calculate the excess.");
        } else {
            if (txtPay != null && txtTotal != null) {
                String payAmountText = payText.replaceAll("[^\\d.]", "");
                String totalText = txtTotal.getText().replaceAll("[^\\d.,]", "").replace(',', '.');

                try {
                    double payAmount = Double.parseDouble(payAmountText);
                    double totalAmount = Double.parseDouble(totalText);

                    if (payAmount < totalAmount) {
                        // Hiển thị cảnh báo nếu số tiền thanh toán không đủ
                        showAlert(Alert.AlertType.WARNING, "Payment Error", "Insufficient Payment",
                                "The amount paid is less than the total amount.");
                    } else {
                        double changeAmount = payAmount - totalAmount;
                        txtChange.setText("$" + String.format("%.2f", changeAmount));

                        // Hiển thị thông báo thành công
                        showAlert(Alert.AlertType.INFORMATION, "Payment Success", "Payment completed successfully.",
                                "Change amount: $" + String.format("%.2f", changeAmount));
                        // Lưu giá trị của txtPay vào biến tạm khi hoàn tất giao dịch
                        // Biến tạm để lưu giá trị txtPay trước khi cập nhật
                        String previousPayValue = txtPay.getText();

                        // Hiển thị hóa đơn trong TextArea
                        billText += "Cash:                                  \t\t\t\t\t" + txtPay.getText() + "\n";
                        billText += "Balance:                               \t\t\t\t\t" + txtChange.getText() + "\n";
                        billText += "----------------------------------------------------------------\n";
                        billText +=  "            Tax Invoice will be issued within the same day" + "\n";
                        billText +=  "=====================================\n";
                        billText +=  "                        Thanks For Your Business...!" + "\n";
                        billTextArea.setText(billText);
                    }
                } catch (NumberFormatException e) {
                    e.printStackTrace();
                }
            }
        }
        // update orderStatus
        try {
            String cash = txtPay.getText().replaceAll("[^\\d.]", "");
            Double.parseDouble(cash);
            Connection conn = Connector.getInstance().getConn();
            // query
            String updateOrderSql = "UPDATE `orders` SET `orderStatus`= 2,`orderCash`='" + cash +"' WHERE orderID = '" + orderIDInPayment + "'";
            PreparedStatement updateOrderStatement = conn.prepareStatement(updateOrderSql);
            updateOrderStatement.executeUpdate();
        } catch (Exception e) {
            System.out.println("Update error: " + e.getMessage());
        }

        // update customer POINT
        if (customerID != 1) {
            try {
                double point = (orderTotal / 10);
                customerPoint += point;
                //voucher
                if (customerPoint >= 10) {
                    // Hiển thị thông báo voucher
                    showAlert(Alert.AlertType.INFORMATION, "Voucher", "Congratulation!!!",
                            "You got " + customerPoint + " point so this is your voucher $10 <3");
                    customerPoint -= 10;
                }
                // update point
                try {
                    Connection conn = Connector.getInstance().getConn();
                    // query
                    String updatePointSql = "UPDATE `customer` SET `customerPoint`= '" + customerPoint + "' WHERE customerID = '" + customerID + "'";
                    PreparedStatement updatePointStatement = conn.prepareStatement(updatePointSql);
                    updatePointStatement.executeUpdate();
                } catch (Exception e) {
                    System.out.println("updatePointSql error: " + e.getMessage());
                }
            } catch (Exception e) {
                System.out.println("Update error: " + e.getMessage());
            }
        }
        isPayment = true;
    }

    public void setCustomerPoint(double point, int cusID) {
        customerPoint = point;
        customerID = cusID;
    }
    public void printBill(MouseEvent mouseEvent) {
        if (isPayment) {
            // In hóa đơn
            PrinterJob printerJob = PrinterJob.createPrinterJob();
            if (printerJob != null) {
                boolean success = printerJob.printPage(billTextArea);
                if (success) {
                    printerJob.endJob();
                }
            }
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/views/home_pos.fxml"));
                Parent root = loader.load();
                Stage stage = new Stage();
                stage.setScene(new Scene(root, 1315, 805));
                stage.setTitle("POS Market | Dashboard");
                stage.show();
                //Đóng cửa sổ hiện tại
                Stage currentStage = (Stage) ((Node) mouseEvent.getSource()).getScene().getWindow();
                currentStage.close();

            } catch (Exception e) {
                System.out.println("From print bill to home error: " + e.getMessage());
            }
        }
    }

    public void Number(ActionEvent ae) {
        String no = ((Button) ae.getSource()).getText();
        txtPay.appendText(no);
    }

    public void payByVisa(ActionEvent actionEvent) {
        btnVis.setStyle("-fx-background-color: #4e2a84; -fx-border-color: #D3D3D3; -fx-border-radius: 6px; -fx-text-fill: white;");
        btnVNPay.setStyle("-fx-background-color: white; -fx-border-color: #D3D3D3; -fx-border-radius: 6px;-fx-text-fill: black;");
//        // Lấy thông tin thẻ Visa từ người dùng
//        String cardNumber = txtCardNumber.getText();
//        String expMonth = txtExpMonth.getText();
//        String expYear = txtExpYear.getText();
//        String cvc = txtCvc.getText();
//
//        // Tạo một đối tượng Stripe để tạo giao dịch thanh toán
//        Stripe.apiKey = "your_stripe_api_key";
//        Map<String, Object> params = new HashMap<>();
//        params.put("amount", 1000);
//        params.put("currency", "usd");
//        params.put("description", "Example payment");
//        params.put("source", createToken(cardNumber, expMonth, expYear, cvc));
//
//        try {
//            Charge charge = Charge.create(params);
//            System.out.println(charge);
//        } catch (StripeException e) {
//            e.printStackTrace();
//        }
    }

//    private String createToken(String cardNumber, String expMonth, String expYear, String cvc) {
//        Map<String, Object> cardParams = new HashMap<>();
//        cardParams.put("number", cardNumber);
//        cardParams.put("exp_month", expMonth);
//        cardParams.put("exp_year", expYear);
//        cardParams.put("cvc", cvc);
//
//        Map<String, Object> tokenParams = new HashMap<>();
//        tokenParams.put("card", cardParams);
//
//        try {
//            Token token = Token.create(tokenParams);
//            return token.getId();
//        } catch (Exception e) {
//            e.printStackTrace();
//            return null;
//        }
//    }

    public void payByVNpay(ActionEvent actionEvent) {
        btnVNPay.setStyle("-fx-background-color: #4e2a84; -fx-border-color: #D3D3D3; -fx-border-radius: 6px; -fx-text-fill: white;");
        btnVis.setStyle("-fx-background-color:white ; -fx-border-color: #D3D3D3; -fx-border-radius: 6px; -fx-text-fill: black;");
    }

    private void showAlert(Alert.AlertType alertType, String title, String headerText, String contentText) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(headerText);
        alert.setContentText(contentText);
        alert.showAndWait();
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
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

    public void backToHome(MouseEvent mouseEvent) {
        Stage currentStage = (Stage) ((Node) mouseEvent.getSource()).getScene().getWindow();
        currentStage.close();
    }
}