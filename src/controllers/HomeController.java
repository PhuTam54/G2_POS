package controllers;

import database.Connector;
import enums.RepositoryType;
import factory.RepositoryFactory;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import model.Order;

import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Optional;
import java.util.ResourceBundle;

public class HomeController implements Initializable {
    public TableView<Order> tbv;
    public TableColumn<Order, String> colName;
    public TableColumn<Order, Integer> colQty;
    public TableColumn<Order, Double> colPrice;
    public Label price1, price2, price3, price4, price5, price6, price7, price8, price9, total, totalProductQty;
    public TextField txtCusPhoneNumber;
    public TextField txtNote;
    public Label adminName;
    ObservableList<Order> list = FXCollections.observableArrayList();

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

    // team
    public ImageView imageView1, imageView2, imageView3, imageView4, imageView5, imageView6, imageView7, imageView8, imageView9;
    public Label nameImg1, nameImg2, nameImg3, nameImg4, nameImg5, nameImg6, nameImg7, nameImg8, nameImg9;
    public Button btnTocoToco, btnFruits, btnMilktea, btnYogurt, btnPastry, btnCombo;
    public TableColumn colDelete;
    private static Order selectedOrder;
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        colName.setCellValueFactory(new PropertyValueFactory<>("name"));
        colQty.setCellValueFactory(new PropertyValueFactory<>("qty"));
        colPrice.setCellValueFactory(new PropertyValueFactory<>("price"));
        tbv.setOnMouseClicked(this::editTableRow);
        total.setText("$0.00");

        colPrice.setCellValueFactory(cellData -> new SimpleDoubleProperty(cellData.getValue().getPrice()).asObject());
        colPrice.setCellFactory(column -> new TableCell<Order, Double>() {
            @Override
            protected void updateItem(Double item, boolean empty) {
                super.updateItem(item, empty);
                if (item == null || empty) {
                    setText(null);
                    calculateTotalPrice();
                } else {
                    setText(String.format("$%.2f", item));
                }
                updateTotalProduct();
                calculateTotalPrice();
            }
        });

        // Thêm chức năng xóa vào cột

        colDelete.setCellFactory(column -> {
            TableCell<Order, String> cell = new TableCell<Order, String>() {
                private final Button deleteButton = new Button("Delete");
                {
                    deleteButton.setOnAction(event -> {
                        Order data = getTableView().getItems().get(getIndex());
                        list.remove(data);
                        updateTotalProduct();
                        calculateTotalPrice();
                    });
                }

                @Override
                protected void updateItem(String item, boolean empty) {
                    super.updateItem(item, empty);
                    if (empty) {
                        setGraphic(null);
                    } else {
                        setGraphic(deleteButton);
                    }
                }
            };
            return cell;
        });

        try {
            list.addAll(RepositoryFactory.createRepositoryInstance(RepositoryType.TABLE).getAll());
            updateTotalProduct();
        } catch (Exception e) {
        }

        // img
        Image image1 = new Image("img/Tra-sua-pho-mai-tuoi.png");
        imageView1.setImage(image1);
        nameImg1.setText("Fresh cheese milk tea");
        price1.setText("$3.99");
        Image image2 = new Image("img/Tra-Sua-Tran-Chau-Hoang-Gia-1-copy.jpg");
        imageView2.setImage(image2);
        nameImg2.setText("Royal Pearl Milk Tea");
        price2.setText("$4");
        Image image3 = new Image("img/Tra-Sua-Panda-1-copy.jpg");
        imageView3.setImage(image3);
        nameImg3.setText("Panda milk tea");
        price3.setText("$2.99");
        Image image4 = new Image("img/Tra-Sua-Ba-Anh-Em-1-copy.jpg");
        imageView4.setImage(image4);
        nameImg4.setText("Three Brother milk tea");
        price4.setText("$4.25");
        Image image5 = new Image("img/O-Long-Man-Kem-Pho-Mai.jpg");
        imageView5.setImage(image5);
        nameImg5.setText("O Long salted cream cheese");
        price5.setText("$5");
        Image image6 = new Image("img/Royal-Pearl-Milk-Coffee.png");
        imageView6.setImage(image6);
        nameImg6.setText("Royal Pearl Milk Coffee");
        price6.setText("$2.99");
        Image image7 = new Image("img/trà-xoài-bưởi-hồng.png");
        imageView7.setImage(image7);
        nameImg7.setText("Pink grapefruit mango tea");
        price7.setText("$4");
        Image image8 = new Image("img/trà-xoài-bưởi-hồng-kem-phô-mai.png");
        imageView8.setImage(image8);
        nameImg8.setText("Pink grapefruit mango tea creame cheese");
        price8.setText("$4.25");
        Image image9 = new Image("img/O-Long-Man-Chanh-Leo.jpg");
        imageView9.setImage(image9);
        nameImg9.setText("O Long salty passion fruit");
        price9.setText("$3.45");

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

    public void addTable(String name, Double price, int qty) {
        // Check if the product is already in the table
        for (Order order : list) {
            if (order.getName().equals(name)) {
                // Update the quantity and total price of the existing product
                int currentQty = order.getQty();
                int newQty = currentQty + qty;
                order.setQty(newQty);
                order.setPrice(price * newQty);
                tbv.refresh();
                updateTotalProduct();
                calculateTotalPrice();
                return;
            }
        }

        // Add the new product to the table
        Order tb = new Order(qty, name, price);
        list.add(tb);
        tbv.setItems(list);
        updateTotalProduct();
        calculateTotalPrice(); // Cập nhật giá trị tổng sau khi thêm sản phẩm mới
    }

    public void addToTable(MouseEvent mouseEvent, String productName, Label quantityLabel, int quantityMultiplier) {
        try {
            String text = quantityLabel.getText().replace("$", "");
            Double price = Double.parseDouble(text);
            boolean productExists = false;

            // Check if the product is already in the table
            for (Order order : list) {
                if (order.getName().equals(productName)) {
                    // Update the quantity and total price of the existing product
                    int currentQty = order.getQty();
                    int newQty = currentQty + quantityMultiplier;
                    order.setQty(newQty);
                    order.setPrice(price * newQty);
                    tbv.refresh();
                    updateTotalProduct();
                    calculateTotalPrice();
                    productExists = true;
                    break;
                }
            }

            if (!productExists) {
                addTable(productName, price, quantityMultiplier);
            }
        } catch (Exception e) {
            System.out.println("addToTable Error: " + e.getMessage());
        }
    }

    public void addToTable1(MouseEvent mouseEvent) {
        addToTable(mouseEvent, nameImg1.getText(), price1, 1);

    }

    public void addToTable2(MouseEvent mouseEvent) {
        addToTable(mouseEvent, nameImg2.getText(), price2, 1);

    }

    public void addToTable3(MouseEvent mouseEvent) {
        addToTable(mouseEvent, nameImg3.getText(), price3, 1);

    }

    public void addToTable4(MouseEvent mouseEvent) {
        addToTable(mouseEvent, nameImg4.getText(), price4, 1);
    }

    public void addToTable5(MouseEvent mouseEvent) {
        addToTable(mouseEvent, nameImg5.getText(), price5, 1);
    }

    public void addToTable6(MouseEvent mouseEvent) {
        addToTable(mouseEvent, nameImg6.getText(), price6, 1);
    }

    public void addToTable7(MouseEvent mouseEvent) {
        addToTable(mouseEvent, nameImg7.getText(), price7, 1);
    }

    public void addToTable8(MouseEvent mouseEvent) {
        addToTable(mouseEvent, nameImg8.getText(), price8, 1);
    }

    public void addToTable9(MouseEvent mouseEvent) {
        addToTable(mouseEvent, nameImg9.getText(), price9, 1);
    }

    public void updateTotalProduct() {
        int totalProduct = 0;
        for (Order order : list) {
            totalProduct += order.getQty();
        }
        totalProductQty.setText(String.valueOf(totalProduct));
    }

    private void calculateTotalPrice() {
        double totalPrice = 0;
        for (Order order : list) {
            totalPrice += order.getPrice();
        }
        total.setText(String.format("$%.2f", totalPrice));
    }

    public void editTableRow(MouseEvent event) {
        if (event.getClickCount() == 2) { // Kiểm tra xem đã nhấp đúp chuột vào hàng sản phẩm chưa
            selectedOrder = tbv.getSelectionModel().getSelectedItem(); // Lấy hàng sản phẩm đang được chọn
            if (selectedOrder != null) {
//                 Hiển thị hộp thoại để sửa thông tin
                TextInputDialog dialog = new TextInputDialog();
                dialog.setTitle("Product editing");
                dialog.setHeaderText(null);
                dialog.setContentText("Number of products:");
                Optional<String> result = dialog.showAndWait();

                result.ifPresent(qty -> {
                    try {
                        int newQty = Integer.parseInt(qty);
                        selectedOrder.setQty(newQty);
                        selectedOrder.setPrice(selectedOrder.getPrice() * newQty);
                        tbv.refresh();

                    } catch (NumberFormatException e) {
                        System.out.println("editTableRow Error: " + e.getMessage());
                    }
                    calculateTotalPrice();
                    updateTotalProduct();
                });
            }
        }
    }
    public void payment(ActionEvent actionEvent) {
        String totalPrice = total.getText();
        String totalProduct = totalProductQty.getText();
        double customerPoint = 0;
        int customerID = 0;
        // add to orders
        int sqlYear = year;
        int sqlMonth = month;
        int sqlDay = day;
        int sqlHours = hours;
        int sqlMinute = minute;
        int sqlSecond = second;
        // get product ID
        ArrayList<String> productNameInOrder = new ArrayList<>();
        ArrayList<Integer> productIDInOrder = new ArrayList<>();
        ObservableList<Order> data = tbv.getItems();
        // get soldPrice and soldQty
        ArrayList<Double> soldPrice = new ArrayList<>();
        ArrayList<Integer> soldQty = new ArrayList<>();
        int orderID = 0;
        try {
            try {
                Connection conn = Connector.getInstance().getConn();
                // query

                // get admin ID
                String getAdminIDSql = "SELECT `adminID` FROM `admin` WHERE `adminUserName` = '" + adminName.getText() + "'";
                PreparedStatement pst = conn.prepareStatement(getAdminIDSql);
                ResultSet rs = pst.executeQuery(getAdminIDSql);
                int adminID = 0;
                while (rs.next()) {
                    adminID = rs.getInt("adminID");
                }
                // get customer ID + point
                String getCustomerIDSql = "SELECT customerID, customerPoint FROM customer WHERE customerPhone LIKE '" + txtCusPhoneNumber.getText() + "'";
                PreparedStatement pst2 = conn.prepareStatement(getCustomerIDSql);
                ResultSet resultSet = pst2.executeQuery(getCustomerIDSql);
                if (resultSet.next()) {
                    customerID = resultSet.getInt("customerID");
                    customerPoint = resultSet.getInt("customerPoint");
                } else {
                    customerID = 1;
                }

                // add new orders
                try {
                    String insertOrderSql = "INSERT INTO `orders`(`customerID`, `orderDate`, `orderStatus`, `adminID`, `orderNotes`) VALUES (?, ?, ?, ?, ?)";
                    PreparedStatement insertOrderStatement = conn.prepareStatement(insertOrderSql);
                    insertOrderStatement.setString(1, String.valueOf(customerID));
                    insertOrderStatement.setString(2, sqlYear + "-" + sqlMonth + "-" + sqlDay + " " + sqlHours + ":" + sqlMinute + ":" + sqlSecond);
                    insertOrderStatement.setString(3, "1");
                    insertOrderStatement.setString(4, String.valueOf(adminID));
                    insertOrderStatement.setString(5, txtNote.getText());
                    insertOrderStatement.executeUpdate();
                    System.out.println(insertOrderStatement);
                } catch (Exception e) {
                    System.out.println("Insert error: " + e.getMessage());
                }

                // get orders ID
                String getOrdersIDSql = "SELECT `orderID` FROM `orders` WHERE `orderDate` = '" + sqlYear + "-" + sqlMonth + "-" + sqlDay + " " + sqlHours + ":" + sqlMinute + ":" + sqlSecond + "'";
                PreparedStatement psOrders = conn.prepareStatement(getOrdersIDSql);
                ResultSet rsOrders = psOrders.executeQuery(getOrdersIDSql);
                while (rsOrders.next()) {
                    orderID = rsOrders.getInt("orderID");
                }

                // get Product ID and soldPrice
                for (int i = 0; i < data.size(); i++) {
                    productNameInOrder.add(data.get(i).getName());
                }
                for (int i = 0; i < data.size(); i++) {
                    String getProductIDSql = "SELECT `productID`, `productPrice` FROM `product` WHERE `productName` = '" + productNameInOrder.get(i) + "'";
                    PreparedStatement psProduct = conn.prepareStatement(getProductIDSql);
                    ResultSet rsProduct = psProduct.executeQuery(getProductIDSql);
                    while (rsProduct.next()) {
                        productIDInOrder.add(rsProduct.getInt("productID"));
                        soldPrice.add(rsProduct.getDouble("productPrice"));
                    }
                }

                // get soldQty
                for (int i = 0; i < data.size(); i++) {
                    soldQty.add(data.get(i).getQty());
                }

//                add to order_detail
                try {
                    for (int i = 0; i < data.size(); i++) {
                        String insertOrderSql = "INSERT INTO `order_detail`(`orderID`, `productID`, `soldPrice`, `soldQty`) VALUES ( ?,  ? , ? ,? )";
                        PreparedStatement insertOrderStatement = conn.prepareStatement(insertOrderSql);
                        insertOrderStatement.setString(1, String.valueOf(orderID));
                        insertOrderStatement.setString(2, String.valueOf(productIDInOrder.get(i)));
                        insertOrderStatement.setString(3, String.valueOf(soldPrice.get(i)));
                        insertOrderStatement.setString(4, String.valueOf(soldQty.get(i)));
                        insertOrderStatement.executeUpdate();
                    }
                } catch (Exception e) {
                    System.out.println("Insert error: " + e.getMessage());
                }

            } catch (Exception e) {
                System.out.println("get ID sql error: " + e.getMessage());
            }

            FXMLLoader loader = new FXMLLoader(getClass().getResource("/views/payment_pos.fxml"));
            Parent root = loader.load();
            PaymentController pc = loader.getController(); // Lấy tham chiếu đến PayController đã tạo từ FXML
            pc.setTotal(totalPrice, totalProduct);
            String billText = "\n                \t\t\tPOS Market \n";
            billText += "                \t\tSố 18 Tôn Thất Thuyết \n";
            billText += "                   \t\t+84 123456789 \n\n";
            // Thêm thông tin ngày giờ
            LocalDateTime now = LocalDateTime.now();
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");
            String formattedDateTime = now.format(formatter);
            billText += "                                           \tDate: " + formattedDateTime + "\n";
            // Thêm thông tin tên và ghi chú
            if (!txtCusPhoneNumber.getText().equals("")){
                billText += "Customer's phone number: " + txtCusPhoneNumber.getText() + "\n";
            }
            if (!txtNote.getText().equals("")) {
                billText += "Notes: " + txtNote.getText() + "\n";
            }
            billText += "----------------------------------------------------------------\n";
            billText += "Product\t\tPrice      \t\tQuantity                \tTotal \n";
            billText += "----------------------------------------------------------------\n";
            // Nhập dữ liệu từ db
            for (int i = 0; i < data.size(); i++) {
                billText += data.get(i).getName() + "\n" + productIDInOrder.get(i) + "\t\t       $"+ soldPrice.get(i) + "\t\t\t     x"+ data.get(i).getQty()+ "\t\t\t$"+ Math.ceil(data.get(i).getPrice()*100)/100 + " \n";
            }

            billText += "----------------------------------------------------------------\n";
            billText += "Total:                                  \t\t\t\t\t" + total.getText() + "\n";

            pc.setInvoice(billText);
            pc.setOrderID(orderID);
            pc.setCustomerPoint(customerPoint, customerID);

            Stage stage = new Stage();
            stage.setScene(new Scene(root, 1315, 805));
            stage.setTitle("POS Market | Payment");
            stage.show();
        } catch (Exception e) {
            System.out.println("Payment Error: " + e.getMessage());
        }
    }

    public void findCustomerByPhone(MouseEvent mouseEvent) {
        try {
            Connection conn = Connector.getInstance().getConn();
            // query
            // get customer name
            String getCusNameSql = "SELECT customerName FROM customer WHERE customerPhone LIKE '" + txtCusPhoneNumber.getText() + "'";
            PreparedStatement pst = conn.prepareStatement(getCusNameSql);
            ResultSet rs = pst.executeQuery(getCusNameSql);
            String customerName = "";
            if (rs.next()) {
                String name = rs.getString("customerName");
                customerName = name;
            } else {
                customerName = "Not found";
            }
            Alert confirmationAlert = new Alert(Alert.AlertType.CONFIRMATION);
            confirmationAlert.setTitle("Customer found");
            confirmationAlert.setHeaderText(null);
            confirmationAlert.setContentText("Hello customer: " + customerName);
            confirmationAlert.show();
        }catch (Exception e) {
            System.out.println("findCustomerByPhone error: " + e.getMessage());
        }
    }

    public void cancelOrder(ActionEvent actionEvent) {
        Alert confirmationAlert = new Alert(Alert.AlertType.CONFIRMATION);
        confirmationAlert.setTitle("Order Cancellation Confirmation");
        confirmationAlert.setHeaderText(null);
        confirmationAlert.setContentText("Are you sure you want to cancel the order and delete all added products?");

        Optional<ButtonType> result = confirmationAlert.showAndWait();
        if (result.isPresent() && result.get() == ButtonType.OK) {
            list.clear();
            tbv.refresh();
            updateTotalProduct();
            calculateTotalPrice();
        }
    }

    public void exit(ActionEvent actionEvent) {
        System.exit(0);
    }

    public void goToHistory(MouseEvent mouseEvent) {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("/views/history_pos.fxml"));
            Stage historyStage = new Stage();
            historyStage.setScene(new Scene(root, 1315, 810));
            historyStage.setTitle("POS | Sale report");
            historyStage.show();

        } catch (Exception e) {
            System.out.println("Go to history error: " + e.getMessage());
        }
    }

    public void goToAddMember(MouseEvent mouseEvent) {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("/views/member_pos.fxml"));
            Stage historyStage = new Stage();
            historyStage.setScene(new Scene(root, 930, 525));
            historyStage.setTitle("POS | Add membership");
            historyStage.show();

        } catch (Exception e) {
            System.out.println("Member error: " + e.getMessage());
        }
    }


    public void setAdminName(String admin) {
        adminName.setText(admin);
    }

    public void goBackLogin(MouseEvent mouseEvent) {
        Stage currentStage = (Stage) ((Node) mouseEvent.getSource()).getScene().getWindow();
        currentStage.close();
    }

    public void btnTocoToco(ActionEvent actionEvent) {
        Image image1 = new Image("img/Tra-sua-pho-mai-tuoi.png");
        imageView1.setImage(image1);
        nameImg1.setText("Fresh cheese milk tea");
        price1.setText("$3.99");
        Image image2 = new Image("img/Tra-Sua-Tran-Chau-Hoang-Gia-1-copy.jpg");
        imageView2.setImage(image2);
        nameImg2.setText("Royal Pearl Milk Tea");
        price2.setText("$4");
        Image image3 = new Image("img/Tra-Sua-Panda-1-copy.jpg");
        imageView3.setImage(image3);
        nameImg3.setText("Panda milk tea");
        price3.setText("$2.99");
        Image image4 = new Image("img/Tra-Sua-Ba-Anh-Em-1-copy.jpg");
        imageView4.setImage(image4);
        nameImg4.setText("Three Brother milk tea");
        price4.setText("$4.25");
        Image image5 = new Image("img/O-Long-Man-Kem-Pho-Mai.jpg");
        imageView5.setImage(image5);
        nameImg5.setText("O Long salted cream cheese");
        price5.setText("$5");
        Image image6 = new Image("img/Royal-Pearl-Milk-Coffee.png");
        imageView6.setImage(image6);
        nameImg6.setText("Royal Pearl Milk Coffee");
        price6.setText("$2.99");
        Image image7 = new Image("img/trà-xoài-bưởi-hồng.png");
        imageView7.setImage(image7);
        nameImg7.setText("Pink grapefruit mango tea");
        price7.setText("$4");
        Image image8 = new Image("img/trà-xoài-bưởi-hồng-kem-phô-mai.png");
        imageView8.setImage(image8);
        nameImg8.setText("Pink grapefruit mango tea creame cheese");
        price8.setText("$4.25");
        Image image9 = new Image("img/O-Long-Man-Chanh-Leo.jpg");
        imageView9.setImage(image9);
        nameImg9.setText("O Long salty passion fruit");
        price9.setText("$3.45");

        btnTocoToco.setStyle("-fx-background-color: #4e2a84; -fx-text-fill: white;-fx-background-radius:10;");
        btnFruits.setStyle("-fx-background-color: white; -fx-text-fill: black;-fx-background-radius:10;");
        btnMilktea.setStyle("-fx-background-color: white; -fx-text-fill: black;-fx-background-radius:10;");
        btnYogurt.setStyle("-fx-background-color: white; -fx-text-fill: black;-fx-background-radius:10;");
        btnPastry.setStyle("-fx-background-color: white; -fx-text-fill: black;-fx-background-radius:10;");
        btnCombo.setStyle("-fx-background-color: white; -fx-text-fill: black;-fx-background-radius:10;");
    }

    public void btnFruits(ActionEvent actionEvent) {
        Image image1 = new Image("img/l25.jpeg");
        imageView1.setImage(image1);
        nameImg1.setText("Four Quarter Mangoes");
        price1.setText("$2");

        Image image2 = new Image("img/l26.jpeg");
        imageView2.setImage(image2);
        nameImg2.setText("Green grapefruit");
        price2.setText("$1");

        Image image3 = new Image("img/l27.jpeg");
        imageView3.setImage(image3);
        nameImg3.setText("Cucumber");
        price3.setText("$2");

        Image image4 = new Image("img/l28.jpeg");
        imageView4.setImage(image4);
        nameImg4.setText("Stomach Mango");
        price4.setText("$3");

        Image image5 = new Image("img/l29.jpeg");
        imageView5.setImage(image5);
        nameImg5.setText("Toad Stomach");
        price5.setText("$1");

        Image image6 = new Image("img/l30.jpeg");
        imageView6.setImage(image6);
        nameImg6.setText("Rod");
        price6.setText("$2");

        Image image7 = new Image("img/l31.jpeg");
        imageView7.setImage(image7);
        nameImg7.setText("Pickled plums");
        price7.setText("$1");

        Image image8 = new Image("img");
        imageView8.setImage(image8);
        nameImg8.setText("");
        price8.setText("");

        Image image9 = new Image("img");
        imageView9.setImage(image9);
        nameImg9.setText("");
        price9.setText("");

        btnTocoToco.setStyle("-fx-background-color: white; -fx-text-fill: black;-fx-background-radius:10;");
        btnFruits.setStyle("-fx-background-color:  #4e2a84; -fx-text-fill: white;-fx-background-radius:10;");
        btnMilktea.setStyle("-fx-background-color: white; -fx-text-fill: black;-fx-background-radius:10;");
        btnYogurt.setStyle("-fx-background-color: white; -fx-text-fill: black;-fx-background-radius:10;");
        btnPastry.setStyle("-fx-background-color: white; -fx-text-fill: black;-fx-background-radius:10;");
        btnCombo.setStyle("-fx-background-color: white; -fx-text-fill: black;-fx-background-radius:10;");
    }

    public void btnMilktea(ActionEvent actionEvent) {
        Image image1 = new Image("img/l17.jpg");
        imageView1.setImage(image1);
        nameImg1.setText("Black Pearl Milk Tea");
        price1.setText("$3");

        Image image2 = new Image("img/l18.jpg");
        imageView2.setImage(image2);
        nameImg2.setText("Oolong Milk Tea");
        price2.setText("$1");

        Image image3 = new Image("img/l19.jpg");
        imageView3.setImage(image3);
        nameImg3.setText("Green Tea Milk Tea");
        price3.setText("$1");

        Image image4 = new Image("img/l20.jpg");
        imageView4.setImage(image4);
        nameImg4.setText("Hokkaido Milk Tea");
        price4.setText("$4");

        Image image5 = new Image("img/l21.jpg");
        imageView5.setImage(image5);
        nameImg5.setText("Okinawa Milk Tea");
        price5.setText("$2");

        Image image6 = new Image("img/l22.jpg");
        imageView6.setImage(image6);
        nameImg6.setText("Black Tea Milk Tea");
        price6.setText("$4");

        Image image7 = new Image("img/l23.jpg");
        imageView7.setImage(image7);
        nameImg7.setText("Chocolate Milk Tea");
        price7.setText("$1");

        Image image8 = new Image("img/l24.jpg");
        imageView8.setImage(image8);
        nameImg8.setText("Taro Milk Tea");
        price8.setText("$4");

        Image image9 = new Image("img");
        imageView9.setImage(image9);
        nameImg9.setText("");
        price9.setText("");

        btnTocoToco.setStyle("-fx-background-color: white; -fx-text-fill: black;-fx-background-radius:10;");
        btnFruits.setStyle("-fx-background-color: white; -fx-text-fill: black;-fx-background-radius:10;");
        btnMilktea.setStyle("-fx-background-color:#4e2a84; -fx-text-fill: white;-fx-background-radius:10;");
        btnYogurt.setStyle("-fx-background-color: white; -fx-text-fill: black;-fx-background-radius:10;");
        btnPastry.setStyle("-fx-background-color: white; -fx-text-fill: black;-fx-background-radius:10;");
        btnCombo.setStyle("-fx-background-color: white; -fx-text-fill: black;-fx-background-radius:10;");
    }

    public void btnYogurt(ActionEvent actionEvent) {
        Image image1 = new Image("img/l8.jpg");
        imageView1.setImage(image1);
        nameImg1.setText("Sticky Jackfruit Yogurt Tea");
        price1.setText("$2");

        Image image2 = new Image("img/l9.jpg");
        imageView2.setImage(image2);
        nameImg2.setText("Jackfruit Yogurt Tea");
        price2.setText("$1");

        Image image3 = new Image("img/l10.jpg");
        imageView3.setImage(image3);
        nameImg3.setText("Mixed Yogurt Tea");
        price3.setText("$3");

        Image image4 = new Image("img/l11.jpg");
        imageView4.setImage(image4);
        nameImg4.setText("Sticky Cam Yogurt Tea");
        price4.setText("$4");

        Image image5 = new Image("img/l12.jpg");
        imageView5.setImage(image5);
        nameImg5.setText("Leo Lemon Yogurt");
        price5.setText("$2");

        Image image6 = new Image("img/l13.jpg");
        imageView6.setImage(image6);
        nameImg6.setText("Pearl Coconut Milk");
        price6.setText("$3");

        Image image7 = new Image("img/l14.jpg");
        imageView7.setImage(image7);
        nameImg7.setText("Thai Yogurt Tea");
        price7.setText("$1");

        Image image8 = new Image("img/l15.jpg");
        imageView8.setImage(image8);
        nameImg8.setText("Coffee Yogurt");
        price8.setText("$5");

        Image image9 = new Image("img/l16.jpg");
        imageView9.setImage(image9);
        nameImg9.setText("Strawberry Yogurt");
        price9.setText("$4");

        btnTocoToco.setStyle("-fx-background-color: white; -fx-text-fill: black;-fx-background-radius:10;");
        btnFruits.setStyle("-fx-background-color: white; -fx-text-fill: black;-fx-background-radius:10;");
        btnMilktea.setStyle("-fx-background-color:white; -fx-text-fill: black;-fx-background-radius:10;");
        btnYogurt.setStyle("-fx-background-color: #4e2a84; -fx-text-fill: white;-fx-background-radius:10;");
        btnPastry.setStyle("-fx-background-color: white; -fx-text-fill: black;-fx-background-radius:10;");
        btnCombo.setStyle("-fx-background-color: white; -fx-text-fill: black;-fx-background-radius:10;");
    }

    public void btnPastry(ActionEvent actionEvent) {
        Image image1 = new Image("img/l1.jpeg");
        imageView1.setImage(image1);
        nameImg1.setText("Almond Tile Cake");
        price1.setText("$2");

        Image image2 = new Image("img/l2.jpeg");
        imageView2.setImage(image2);
        nameImg2.setText("Chocolate biscuits");
        price2.setText("$4");

        Image image3 = new Image("img/l3.jpeg");
        imageView3.setImage(image3);
        nameImg3.setText("Tiramisu Cacao");
        price3.setText("$3");

        Image image4 = new Image("img/l4.jpeg");
        imageView4.setImage(image4);
        nameImg4.setText("Leo Lemon Mousse");
        price4.setText("$1");

        Image image5 = new Image("img/l5.jpeg");
        imageView5.setImage(image5);
        nameImg5.setText("Oreo cake");
        price5.setText("$3");

        Image image6 = new Image("img/l6.jpeg");
        imageView6.setImage(image6);
        nameImg6.setText("Milk Tea Cake");
        price6.setText("$2");

        Image image7 = new Image("img/l7.jpeg");
        imageView7.setImage(image7);
        nameImg7.setText("Tiramisu Matcha");
        price7.setText("$1");

        Image image8 = new Image("img");
        imageView8.setImage(image8);
        nameImg8.setText("");
        price8.setText("");

        Image image9 = new Image("img");
        imageView9.setImage(image9);
        nameImg9.setText("");
        price9.setText("");

        btnTocoToco.setStyle("-fx-background-color: white; -fx-text-fill: black;-fx-background-radius:10;");
        btnFruits.setStyle("-fx-background-color: white; -fx-text-fill: black;-fx-background-radius:10;");
        btnMilktea.setStyle("-fx-background-color:white; -fx-text-fill: black;-fx-background-radius:10;");
        btnYogurt.setStyle("-fx-background-color: white; -fx-text-fill: black;-fx-background-radius:10;");
        btnPastry.setStyle("-fx-background-color: #4e2a84; -fx-text-fill: white;-fx-background-radius:10;");
        btnCombo.setStyle("-fx-background-color: white; -fx-text-fill: black;-fx-background-radius:10;");
    }

    public void btnCombo(ActionEvent actionEvent) {
        Image image1 = new Image("img/Stawberry-Milk-Tea.jpg");
        imageView1.setImage(image1);
        nameImg1.setText("Stawberry Milk Tea");
        price1.setText("$2.59");

        Image image2 = new Image("img/Original-Milk-Tea.jpg");
        imageView2.setImage(image2);
        nameImg2.setText("Original Milk Tea");
        price2.setText("$3");

        Image image3 = new Image("img/6ly_hong.png");
        imageView3.setImage(image3);
        nameImg3.setText("6 pink packs");
        price3.setText("$15");

        Image image4 = new Image("img/6ly_vang.png");
        imageView4.setImage(image4);
        nameImg4.setText("Leo Lemon Mousse");
        price4.setText("$18");

        Image image5 = new Image("img");
        imageView5.setImage(image5);
        nameImg5.setText("");
        price5.setText("");

        Image image6 = new Image("img");
        imageView6.setImage(image6);
        nameImg6.setText("");
        price6.setText("");

        Image image7 = new Image("img");
        imageView7.setImage(image7);
        nameImg7.setText("");
        price7.setText("");

        Image image8 = new Image("img");
        imageView8.setImage(image8);
        nameImg8.setText("");
        price8.setText("");

        Image image9 = new Image("img");
        imageView9.setImage(image9);
        nameImg9.setText("");
        price9.setText("");

        btnTocoToco.setStyle("-fx-background-color: white; -fx-text-fill: black;-fx-background-radius:10;");
        btnFruits.setStyle("-fx-background-color: white; -fx-text-fill: black;-fx-background-radius:10;");
        btnMilktea.setStyle("-fx-background-color:white; -fx-text-fill: black;-fx-background-radius:10;");
        btnYogurt.setStyle("-fx-background-color: white; -fx-text-fill: black;-fx-background-radius:10;");
        btnPastry.setStyle("-fx-background-color: white; -fx-text-fill: black;-fx-background-radius:10;");
        btnCombo.setStyle("-fx-background-color: #4e2a84; -fx-text-fill: white;-fx-background-radius:10;");
    }
}