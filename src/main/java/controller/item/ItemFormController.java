package controller.item;

import com.jfoenix.controls.JFXTextField;
import db.DBConnection;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import model.Customer;
import model.Item;

import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class ItemFormController implements Initializable {

    @FXML
    private TableColumn<?, ?> colCode;

    @FXML
    private TableColumn<?, ?> colDescription;

    @FXML
    private TableColumn<?, ?> colQtyOnHand;

    @FXML
    private TableColumn<?, ?> colUnitPrice;

    @FXML
    private TableView<Item> tblItem;

    @FXML
    private JFXTextField txtCode;

    @FXML
    private JFXTextField txtDescription;

    @FXML
    private JFXTextField txtQtyOnHand;

    @FXML
    private JFXTextField txtUnitPrice;

    ItemService service = ItemController.getInstance();


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        colCode.setCellValueFactory(new PropertyValueFactory<>("code"));
        colDescription.setCellValueFactory(new PropertyValueFactory<>("description"));
        colUnitPrice.setCellValueFactory(new PropertyValueFactory<>("unitPrice"));
        colQtyOnHand.setCellValueFactory(new PropertyValueFactory<>("qtyOnHand"));

        tblItem.getSelectionModel().selectedItemProperty().addListener((observableValue, oldValue, newValue) -> {
            if(newValue != null ){
                addValueToText(newValue);
            }
        });

        reloadItemTable();
    }

    private void addValueToText(Item newVal){
        txtCode.setText(newVal.getCode());
        txtDescription.setText(newVal.getDescription());
        txtUnitPrice.setText(String.valueOf(newVal.getUnitPrice()));
        txtQtyOnHand.setText(String.valueOf(newVal.getQtyOnHand()));
    }

    @FXML
    void btnAddOnAction(ActionEvent event) {
        Item item = new Item(
                txtCode.getText(),
                txtDescription.getText(),
                Double.parseDouble(txtUnitPrice.getText()),
                Integer.parseInt(txtQtyOnHand.getText())
        );

        if(service.addItem(item)){
            new Alert(Alert.AlertType.CONFIRMATION).show();
        } else {
            new Alert(Alert.AlertType.ERROR).show();
        }
        reloadItemTable();

    }

    @FXML
    void btnClearOnAction(ActionEvent event) {

    }

    @FXML
    void btnDeleteOnAction(ActionEvent event) {
        String code = txtCode.getText();

        if (service.deleteItem(code)){
            new Alert(Alert.AlertType.CONFIRMATION).show();
        } else {
            new Alert(Alert.AlertType.ERROR).show();
        }
        reloadItemTable();
    }

    @FXML
    void btnUpdateOnAction(ActionEvent event) {
        Item item = new Item(
                txtCode.getText(),
                txtDescription.getText(),
                Double.parseDouble(txtUnitPrice.getText()),
                Integer.parseInt(txtQtyOnHand.getText())
        );

        if(service.updateCustomer(item)){
            new Alert(Alert.AlertType.CONFIRMATION).show();
        } else {
            new Alert(Alert.AlertType.ERROR).show();
        }
        reloadItemTable();
    }

    public void reloadItemTable() {
        ObservableList<Item> allItems = service.getAllItems();
        tblItem.setItems(allItems);
    }

    public void btnSearchOnAction(ActionEvent actionEvent) {
        Item item = service.searchItem(txtCode.getText());

        if(item!=null) {
            txtCode.setText(item.getCode());
            txtDescription.setText(item.getDescription());
            txtUnitPrice.setText(String.valueOf(item.getUnitPrice()));
            txtQtyOnHand.setText(String.valueOf(item.getQtyOnHand()));
        }
    }
}
