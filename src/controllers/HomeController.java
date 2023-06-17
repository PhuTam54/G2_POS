package controllers;

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

import java.io.IOException;
import java.net.URL;
import java.time.LocalDateTime;
import java.util.Optional;
import java.util.ResourceBundle;

public class HomeController implements Initializable {
    public TableView<Order> tbv;
    public TableColumn<Order, Integer> colID;
    public TableColumn<Order, String> colName;
    public TableColumn<Order, Integer> colQty;
    public TableColumn<Order, Double> colPrice;
    public TableColumn<Order, Button> colAction;
    public Label price1, price2, price3, price4, price5, price6, price7, price8, price9, total, totalproduct;
    ObservableList<Order> list = FXCollections.observableArrayList();
    public static Order resetOrder;
//    public int countProduct;

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

    // test
    public ImageView imageView1, imageView2, imageView3, imageView4, imageView5, imageView6, imageView7, imageView8, imageView9;
    public Label nameImg1, nameImg2, nameImg3, nameImg4, nameImg5, nameImg6, nameImg7, nameImg8, nameImg9;
    public Button btnBobatea, btnFruits, btnMilktea, btnYogurt, btnPastry;
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
                calculateTotalPrice();
            }
        });

        colPrice.setCellFactory(column -> new TableCell<Order, Double>() {
            @Override
            protected void updateItem(Double item, boolean empty) {
                super.updateItem(item, empty);
                if (item == null || empty) {
                    setText(null);
                } else {
                    setText(String.format("$%.2f", item));
                }
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
//            System.out.println("Error: " + e.getMessage());
        }

        // img
        Image image1 = new Image("img/Tra-sua-pho-mai-tuoi.png");
        imageView1.setImage(image1);
        nameImg1.setText("Strawberry Whip Juice");
        price1.setText("$3.99");
        Image image2 = new Image("img/Tra-Sua-Tran-Chau-Hoang-Gia-1-copy.jpg");
        imageView2.setImage(image2);
        nameImg2.setText("Grapefruit Juice");
        price2.setText("$4");
        Image image3 = new Image("img/Tra-Sua-Panda-1-copy.jpg");
        imageView3.setImage(image3);
        nameImg3.setText("Whip Juice");
        price3.setText("$2");
        Image image4 = new Image("img/Tra-Sua-Ba-Anh-Em-1-copy.jpg");
        imageView4.setImage(image4);
        nameImg4.setText("Pineapple Orange Juice");
        price4.setText("$4");
        Image image5 = new Image("img/O-Long-Man-Kem-Pho-Mai.jpg");
        imageView5.setImage(image5);
        nameImg5.setText("Watermelon juice");
        price5.setText("$5");
        Image image6 = new Image("img/Royal-Pearl-Milk-Coffee.png");
        imageView6.setImage(image6);
        nameImg6.setText("Orange juice");
        price6.setText("$2");
        Image image7 = new Image("img/trà-xoài-bưởi-hồng.png");
        imageView7.setImage(image7);
        nameImg7.setText("Toad Guava Juice");
        price7.setText("$4");
        Image image8 = new Image("img/trà-xoài-bưởi-hồng-kem-phô-mai.png");
        imageView8.setImage(image8);
        nameImg8.setText("Pineapple Juice");
        price8.setText("$6");
        Image image9 = new Image("img/O-Long-Man-Chanh-Leo.jpg");
        imageView9.setImage(image9);
        nameImg9.setText("Toad juice");
        price9.setText("$3");

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
            System.out.println("Error: " + e.getMessage());
        }
    }


    public void updateTotalProduct() {
        int totalProduct = 0;
        double totalPrice = 0;
        for (Order order : list) {
            totalProduct += order.getQty();
            totalPrice += order.getPrice();
        }

        total.setText(String.format("$%.2f", totalPrice));
        calculateTotalPrice();
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
                dialog.setTitle("Sửa hàng sản phẩm");
                dialog.setHeaderText(null);
                dialog.setContentText("Số lượng hàng sản phẩm:");
                Optional<String> result = dialog.showAndWait();

                result.ifPresent(qty -> {
                    try {
                        int newQty = Integer.parseInt(qty);
                        selectedOrder.setQty(newQty);
                        selectedOrder.setPrice(selectedOrder.getPrice() * newQty);
                        tbv.refresh();

                    } catch (NumberFormatException e) {
                        System.out.println("Error: " + e.getMessage());
                    }
                    calculateTotalPrice();
                });
            }
        }
    }
    public void payment(ActionEvent actionEvent) {
        String totalPrice = total.getText();
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("../views/paymentpos.fxml"));
            Parent root = loader.load();
            PaymentController pc = loader.getController(); // Lấy tham chiếu đến PayController đã tạo từ FXML
            pc.setTotal(totalPrice);

            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.show();

            // Đóng cửa sổ hiện tại (nếu cần)
            Stage currentStage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
            currentStage.close();
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    public void exit(ActionEvent actionEvent) {
        // Đóng cửa sổ hiện tại
        Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        stage.close();

        // Mở trang loginpos.fxml
        try {
            Parent root = FXMLLoader.load(getClass().getResource("../views/loginpos.fxml"));
            Stage loginStage = new Stage();
            loginStage.setScene(new Scene(root));
            loginStage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void cancelOrder(ActionEvent actionEvent) {
        Alert confirmationAlert = new Alert(Alert.AlertType.CONFIRMATION);
        confirmationAlert.setTitle("Xác nhận hủy đơn hàng");
        confirmationAlert.setHeaderText(null);
        confirmationAlert.setContentText("Bạn có chắc chắn muốn hủy đơn hàng và xóa tất cả các sản phẩm đã thêm?");

        Optional<ButtonType> result = confirmationAlert.showAndWait();
        if (result.isPresent() && result.get() == ButtonType.OK) {
            list.clear();
            tbv.refresh();
            updateTotalProduct();
            calculateTotalPrice();
        }
    }


    public void btnBobatea(ActionEvent actionEvent) {
        Image image1 = new Image("img/Tra-sua-pho-mai-tuoi.png");
        imageView1.setImage(image1);
        nameImg1.setText("Strawberry Whip Juice");
        price1.setText("$3.99");
        Image image2 = new Image("img/Tra-Sua-Tran-Chau-Hoang-Gia-1-copy.jpg");
        imageView2.setImage(image2);
        nameImg2.setText("Grapefruit Juice");
        price2.setText("$4");
        Image image3 = new Image("img/Tra-Sua-Panda-1-copy.jpg");
        imageView3.setImage(image3);
        nameImg3.setText("Whip Juice");
        price3.setText("$2");
        Image image4 = new Image("img/Tra-Sua-Ba-Anh-Em-1-copy.jpg");
        imageView4.setImage(image4);
        nameImg4.setText("Pineapple Orange Juice");
        price4.setText("$4");
        Image image5 = new Image("img/O-Long-Man-Kem-Pho-Mai.jpg");
        imageView5.setImage(image5);
        nameImg5.setText("Watermelon juice");
        price5.setText("$5");
        Image image6 = new Image("img/Royal-Pearl-Milk-Coffee.png");
        imageView6.setImage(image6);
        nameImg6.setText("Orange juice");
        price6.setText("$2");
        Image image7 = new Image("img/trà-xoài-bưởi-hồng.png");
        imageView7.setImage(image7);
        nameImg7.setText("Toad Guava Juice");
        price7.setText("$4");
        Image image8 = new Image("img/trà-xoài-bưởi-hồng-kem-phô-mai.png");
        imageView8.setImage(image8);
        nameImg8.setText("Pineapple Juice");
        price8.setText("$6");
        Image image9 = new Image("img/O-Long-Man-Chanh-Leo.jpg");
        imageView9.setImage(image9);
        nameImg9.setText("Toad juice");
        price9.setText("$3");

//        btnBobatea.setStyle("-fx-background-color: #4e2a84; -fx-text-fill: white;-fx-background-radius:10;");
//        btnFruits.setStyle("-fx-background-color: white; -fx-text-fill: black;-fx-background-radius:10;");
//        btnMilktea.setStyle("-fx-background-color: white; -fx-text-fill: black;-fx-background-radius:10;");
//        btnYogurt.setStyle("-fx-background-color: white; -fx-text-fill: black;-fx-background-radius:10;");
//        btnPastry.setStyle("-fx-background-color: white; -fx-text-fill: black;-fx-background-radius:10;");

    }

    public void btnFruits(ActionEvent actionEvent) {

//        btnBobatea.setStyle("-fx-background-color: white; -fx-text-fill: black;-fx-background-radius:10;");
//        btnFruits.setStyle("-fx-background-color:  #4e2a84; -fx-text-fill: white;-fx-background-radius:10;");
//        btnMilktea.setStyle("-fx-background-color: white; -fx-text-fill: black;-fx-background-radius:10;");
//        btnYogurt.setStyle("-fx-background-color: white; -fx-text-fill: black;-fx-background-radius:10;");
//        btnPastry.setStyle("-fx-background-color: white; -fx-text-fill: black;-fx-background-radius:10;");

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

    }

    public void btnMilktea(ActionEvent actionEvent) {

//        btnBobatea.setStyle("-fx-background-color: white; -fx-text-fill: black;-fx-background-radius:10;");
//        btnFruits.setStyle("-fx-background-color: white; -fx-text-fill: black;-fx-background-radius:10;");
//        btnMilktea.setStyle("-fx-background-color:#4e2a84; -fx-text-fill: white;-fx-background-radius:10;");
//        btnYogurt.setStyle("-fx-background-color: white; -fx-text-fill: black;-fx-background-radius:10;");
//        btnPastry.setStyle("-fx-background-color: white; -fx-text-fill: black;-fx-background-radius:10;");

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

    }

    public void btnYogurt(ActionEvent actionEvent) {
//        btnBobatea.setStyle("-fx-background-color: white; -fx-text-fill: black;-fx-background-radius:10;");
//        btnFruits.setStyle("-fx-background-color: white; -fx-text-fill: black;-fx-background-radius:10;");
//        btnMilktea.setStyle("-fx-background-color:white; -fx-text-fill: black;-fx-background-radius:10;");
//        btnYogurt.setStyle("-fx-background-color: #4e2a84; -fx-text-fill: white;-fx-background-radius:10;");
//        btnPastry.setStyle("-fx-background-color: white; -fx-text-fill: black;-fx-background-radius:10;");

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
    }

    public void btnPastry(ActionEvent actionEvent) {
//        btnBobatea.setStyle("-fx-background-color: white; -fx-text-fill: black;-fx-background-radius:10;");
//        btnFruits.setStyle("-fx-background-color: white; -fx-text-fill: black;-fx-background-radius:10;");
//        btnMilktea.setStyle("-fx-background-color:white; -fx-text-fill: black;-fx-background-radius:10;");
//        btnYogurt.setStyle("-fx-background-color: white; -fx-text-fill: black;-fx-background-radius:10;");
//        btnPastry.setStyle("-fx-background-color: #4e2a84; -fx-text-fill: white;-fx-background-radius:10;");

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
    }

    public void btnCombo(ActionEvent actionEvent) {
    }
}
    // PT cmt
//    @Override
//    public void initialize(URL location, ResourceBundle resources) {
//        colID.setCellValueFactory(new PropertyValueFactory<>("id"));
//        colName.setCellValueFactory(new PropertyValueFactory<>("name"));
//        colQty.setCellValueFactory(new PropertyValueFactory<>("qty"));
//        colPrice.setCellValueFactory(new PropertyValueFactory<>("price"));
//        colAction.setCellValueFactory(new PropertyValueFactory<>("delete"));
//        try {
////            ObservableList<Table> list = FXCollections.observableArrayList();
//            // query
//            RepositoryFactory.createRepositoryInstance(RepositoryType.TABLE).getAll();
//        } catch (Exception e) {
//            System.out.println("Initialize Error: " + e.getMessage());
//        }
//
////        try {
////            countProduct = 0;
////            int tableLength = list.size();
////            for (int i = 0; i < tableLength; i++) {
////            countProduct += colQty.getCellData(countProduct);
////            }
////            for (Table t: list) {
////                countProduct += t.getQty();
////            }
////        }catch (Exception e) {
////            System.out.println(e.getMessage());
////        }
//
//
////        total.setText($"" + price);
////        totalproduct.setText(String.valueOf(countProduct));
//
//        // Date
//        new Thread(()-> {
//            boolean flag = true;
//            while (flag) {
//                txtDay.setText(day + "/");
//                txtMonth.setText(month + "/");
//                txtYear.setText(String.valueOf(year));
//                txtHours.setText(hours + ":");
//                txtMin.setText(minute + ":");
//                txtSecond.setText(String.valueOf(second));
//                second ++;
//                if (second > 59) {
//                    second = 0;
//                    minute++;
//                }if(minute > 59) {
//                    minute = 0;
//                    hours ++;
//                }if (hours > 23) {
//                    hours = 0;
//                    day ++;
//                } switch (month) {
//                    case 1:
//                    case 3:
//                    case 5:
//                    case 7:
//                    case 9:
//                    case 11:
//                        if (day > 31) {
//                            day = 1;
//                            month ++;
//                        }
//                    case 2:
//                        if (year % 4 == 0 && year % 100 != 0) {
//                            if (day > 29) {
//                                day = 1;
//                                month ++;
//                            }
//                        } else {
//                            if (day > 28) {
//                                day = 1;
//                                month ++;
//                            }
//                        }
//                    case 4:
//                    case 6:
//                    case 8:
//                    case 10:
//                        if (day > 30) {
//                            day = 1;
//                            month ++;
//                        }
//                    case 12:
//                        if (day > 30) {
//                            day = 1;
//                            month = 1;
//                            year ++;
//                        }
//                    default:
//                }
//                try {
//                    Thread.sleep(1000); // 1000 milliseconds
//                } catch (Exception e) {
//                    System.out.println("Thread Error: " + e.getMessage());
//                }
//            }
//        }).start();
//        //
//    }
//
    // add to table
//    public void addToTable(Table table) {
//        list.add(table);
//        tbv.setItems(list);
//        tbv.refresh();
//    }

//    public void addToTable1(MouseEvent mouseEvent) {
//        // test add
//        int id = 1;
//        int count = Integer.parseInt(price1.getText());
//        count ++;
//        String name = "Tocotoco Bobatea";
//        Double price = Math.ceil((count * 3.99) * 100) / 100;
//        Table tb = new Table(id, count, name, price);
//        // product already add
//        try {
//            for (Table t: list) {
//                if (t.getName().equals(tb.getName())) {
//                    list.remove(t);
//                }
//            }
//        } catch (Exception e) {
////            System.out.println("Product already add Error: " + e.getMessage());
//        }
//        addToTable(tb);
//
//        price1.setText(String.valueOf(count));
//    }

//    public void addToTable2(ActionEvent actionEvent) {
//        // test add
//        int id = 2;
//        int count = Integer.parseInt(price2.getText());
//        count ++;
//        String name = "Trà xoài bưởi hồng";
//        Double price = Math.ceil((count * 2.99) * 100) / 100;
//        Table tb = new Table(id, count, name, price);
//        // product already add
//        try {
//            for (Table t: list) {
//                if (t.getName().equals(tb.getName())) {
//                    list.remove(t);
//                }
//            }
//        } catch (Exception e) {
////            System.out.println("Product already add Error: " + e.getMessage());
//        }
//        addToTable(tb);
//
//        price2.setText(String.valueOf(count));
//    }

//    public void reset(ActionEvent actionEvent) {
//        try {
//            price1.setText("0");
//            price2.setText("0");
//            total.setText($"0.0");
//            totalproduct.setText("0");
//            resetTable = null;
//            resetTable = tbv.getSelectionModel().getSelectedItem();
//            if (resetTable != null) {
////                for (int i = 0; i <= list.size(); i++) {
////                    list.remove(list.get(i));
////                }
//                for (Table tb: list) {
//                    list.remove(tb);
//                }
//                throw new Exception("Canceled");
//            }
////            if (resetTable != null) {
////                list.forEach();
////            }
//            tbv.setItems(list);
//            tbv.refresh();
//        } catch (Exception e) {
//            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
//            alert.setContentText(e.getMessage());
//            alert.show();
//        }
//    }

//    public void exit(MouseEvent mouseEvent) {
//        System.exit(0);
//    }

//    public void printBill(MouseEvent mouseEvent) {
//        String billText = "                              POS Market \n";
//        billText += "                    \tSố 18 Tôn Thất Thuyết \n";
//        billText += "                       \t+84 123456789 \n";
//        billText += "----------------------------------------------------------------\n";
//        billText += " Name                      \tQty                 \tPrice \n";
//        billText += "----------------------------------------------------------------\n";
//
//        ObservableList<Table> data = tbv.getItems();
//        for (int i = 0; i < data.size(); i++) {
//            billText += data.get(i).getName() + "\t\t"+ data.get(i).getQty()+ "\t\t\t"+ data.get(i).getPrice() + " \n";
//        }
//
//        billText += "----------------------------------------------------------------\n";
//        billText += "Total :\t" + total.getText() + "\n";
//        billText += "Cash :\t" + total.getText() + "\n";
//        billText += "Ballance :\t" + totalproduct.getText() + "\n";
//        billText += "======================================\n";
//        billText += "                    Thanks For Your Business...!" + "\n";
//
//
//        TextArea bill = new TextArea(billText); // hiển thị hóa đơn trong TextArea
//        bill.setEditable(false);
//        Stage stage = new Stage();
//        Text billtext = new Text(); //khởi tạo giá trị của billtext
//        billtext.setText(billText);
//        Scene scene = new Scene(bill ,400,600); //sử dụng biến bill thay cho billtext
//        stage.setScene(scene);
//        stage.show();
//
//        // In hóa đơn
//        PrinterJob printerJob = PrinterJob.createPrinterJob();
//        if (printerJob != null) {
//            boolean success = printerJob.printPage(billtext);
//            if (success) {
//                printerJob.endJob();
//            }
//        }
//    }
//}
