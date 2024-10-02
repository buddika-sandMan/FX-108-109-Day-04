package controller.order;

import controller.customer.CustomerController;
import controller.item.ItemController;
import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.util.Duration;
import model.Customer;
import model.Item;

import java.net.URL;
import java.text.SimpleDateFormat;
import java.time.LocalTime;
import java.util.Date;
import java.util.List;
import java.util.ResourceBundle;

public class OrderFormController implements Initializable {

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
    private TableView<?> tblOrderDetails;

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
        }
    }

}
