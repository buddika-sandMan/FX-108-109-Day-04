package controller.order;

import controller.customer.CustomerController;
import controller.item.ItemController;
import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.util.Duration;
import model.Customer;
import model.Item;
import model.Order;
import model.OrderDetails;
import util.Cart;

import java.net.URL;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.ResourceBundle;

public class OrderFormController implements Initializable {

    public TextField txtUnitPrice;
    public Label lblNetTotal;
    public TextField txtOrderId;
    @FXML
    private ComboBox<String> cmbCustomerId;

    @FXML
    private ComboBox<String> cmbItemCode;

    @FXML
    private TableColumn<?, ?> colCode;

    @FXML
    private TableColumn<?, ?> colDescription;

    @FXML
    private TableColumn<?, ?> colQty;

    @FXML
    private TableColumn<?, ?> colTotal;

    @FXML
    private TableColumn<?, ?> colUnitPrice;

    @FXML
    private Label lblDate;

    @FXML
    private Label lblOrderId;

    @FXML
    private Label lblTime;

    @FXML
    private TableView<Cart> tblOrderDetails;

    @FXML
    private TextField txtCustomerAddress;

    @FXML
    private TextField txtCustomerName;

    @FXML
    private TextField txtDescription;

    @FXML
    private TextField txtQty;

    @FXML
    private TextField txtStock;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        loadDateTime();
        loadCustomerIDs();
        loadItemCodes();

        cmbCustomerId.getSelectionModel().selectedItemProperty().addListener(((observableValue, s, t1) -> {
            if(t1!=null){
                searchCustomer(t1);
            }
        }));

        cmbItemCode.getSelectionModel().selectedItemProperty().addListener(((observableValue, s, t1) -> {
            if (t1!=null) {
                searchItem(t1);
            }
        }));

    }

    private void loadDateTime() {
        Date date = new Date();
        SimpleDateFormat sysdate = new SimpleDateFormat("yyyy-MM-dd");
        lblDate.setText(sysdate.format(date));

        Timeline timeline = new Timeline(new KeyFrame(Duration.ZERO, e -> {
            LocalTime now = LocalTime.now();
            lblTime.setText(now.getHour()+":"+ now.getMinute()+":"+now.getSecond());
        }),
            new KeyFrame(Duration.seconds(1))
        );

        timeline.setCycleCount(Animation.INDEFINITE);
        timeline.play();
    }

    private void loadCustomerIDs() {
        List<String> allCustomersIds = CustomerController.getInstance().getAllCustomersIds();
        ObservableList<String> observableCustomerIds = FXCollections.observableArrayList(allCustomersIds);

        cmbCustomerId.setItems(observableCustomerIds);
    }
    
    private void searchCustomer(String id) {
        Customer customer = CustomerController.getInstance().searchCustomer(id);

        if(customer!=null) {
            txtCustomerName.setText(customer.getName());
            txtCustomerAddress.setText(customer.getAddress());
        }
    }

    private void loadItemCodes() {
        List<String> allItemCodes = ItemController.getInstance().getAllItemCodes();
        ObservableList<String> observableItemCodes = FXCollections.observableArrayList(allItemCodes);

        cmbItemCode.setItems(observableItemCodes);
    }

    private void searchItem(String code) {
        Item item = ItemController.getInstance().searchItem(code);

        if(item!=null) {
            txtDescription.setText(item.getDescription());
            txtStock.setText(String.valueOf(item.getQtyOnHand()));
            txtUnitPrice.setText(String.valueOf(item.getUnitPrice()));
        }
    }

    ObservableList<Cart> cart = FXCollections.observableArrayList();

    public void btnAddToCartOnAction(ActionEvent actionEvent) {
        colCode.setCellValueFactory(new PropertyValueFactory<>("code"));
        colDescription.setCellValueFactory(new PropertyValueFactory<>("description"));
        colQty.setCellValueFactory(new PropertyValueFactory<>("qty"));
        colUnitPrice.setCellValueFactory(new PropertyValueFactory<>("unitPrice"));
        colTotal.setCellValueFactory(new PropertyValueFactory<>("total"));

        String code = cmbItemCode.getValue();
        String  description = txtDescription.getText();
        Integer qty = Integer.parseInt(txtQty.getText());
        Double unitPrice = Double.parseDouble(txtUnitPrice.getText());
        Double total = unitPrice * qty;

        Integer stock = Integer.parseInt(txtStock.getText());

        if(stock<qty){
            new Alert(Alert.AlertType.WARNING, "Stock have only "+qty).show();
        } else {
            cart.add(new Cart(code, description,qty, unitPrice, total));
            tblOrderDetails.setItems(cart);
            calTotal();
        }
    }

    public void calTotal() {
        Double netTotal = 0.0;
        for(Cart cart : cart) {
            netTotal += cart.getTotal();
        }
        lblNetTotal.setText(netTotal.toString());
    }

    public void btnOrderOnAction(ActionEvent actionEvent) {
        String orderID = txtOrderId.getText();
        LocalDate date = LocalDate.parse(lblDate.getText());
        String customerID = cmbCustomerId.getValue();

        ArrayList<OrderDetails> orderDetailsArrayList = new ArrayList<>();

        cart.forEach(obj -> {
            orderDetailsArrayList.add(
                    new OrderDetails(
                            txtOrderId.getText(),
                            obj.getCode(),
                            obj.getQty(),
                            0.0)
            );
        });

        Order order = new Order(orderID, date, customerID, orderDetailsArrayList);
        System.out.println(order);

    }
}
