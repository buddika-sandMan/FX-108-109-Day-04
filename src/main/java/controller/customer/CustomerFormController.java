package controller.customer;

import com.jfoenix.controls.JFXTextField;
import db.DBConnection;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import model.Customer;

import java.net.URL;
import java.sql.*;
import java.util.ResourceBundle;

public class CustomerFormController implements Initializable {

    @FXML
    public ComboBox<String> cmbTitle;

    @FXML
    private TableColumn<?, ?> colAddress;

    @FXML
    private TableColumn<?, ?> colId;

    @FXML
    private TableColumn<?, ?> colName;

    @FXML
    private TableColumn<?, ?> colSalary;

    @FXML
    private TableColumn<?, ?> colTitle;

    @FXML
    private TableView<Customer> tblCustomerDetails;

    @FXML
    private JFXTextField txtAddress;

    @FXML
    private JFXTextField txtId;

    @FXML
    private JFXTextField txtName;

    @FXML
    private JFXTextField txtSalary;

    CustomerService customerService = CustomerController.getInstance();

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        colId.setCellValueFactory(new PropertyValueFactory<>("id"));
        colName.setCellValueFactory(new PropertyValueFactory<>("name"));
        colAddress.setCellValueFactory(new PropertyValueFactory<>("address"));
        colSalary.setCellValueFactory(new PropertyValueFactory<>("salary"));

        ObservableList<String> titleList = FXCollections.observableArrayList();
        titleList.add("MR.");
        titleList.add("MRS.");
        titleList.add("MS.");

        cmbTitle.setItems(titleList);

        reloadCustomerTable();

        tblCustomerDetails.getSelectionModel().selectedItemProperty().addListener((observableValue, oldVal, newVal) -> {
            System.out.println("1 : "+observableValue);
            System.out.println("2 : "+oldVal);
            System.out.println("3 : "+newVal);

            if(newVal != null) {
                addValueToText(newVal);
            }
        });


    }

    private void addValueToText(Customer newVal){
        txtId.setText(newVal.getId());
        txtName.setText(newVal.getName());
        txtAddress.setText(newVal.getAddress());
        txtSalary.setText(String.valueOf(newVal.getSalary()));
    }

    @FXML
    void btnAddCustomerOnAction(ActionEvent event) {

        Customer customer = new Customer(
                txtId.getText(),
                cmbTitle.getValue()+" "+txtName.getText(),
                txtAddress.getText(),
                Double.parseDouble(txtSalary.getText())
        );

        if(customerService.addCustomer(customer)){
            new Alert(Alert.AlertType.INFORMATION).show();
        } else {
            new Alert(Alert.AlertType.CONFIRMATION).show();
        }

        reloadCustomerTable();
    }

    @FXML
    void btnClearOnAction(ActionEvent event) {

    }

    @FXML
    void btnDeleteOnAction(ActionEvent event) {
        String id = txtId.getId();
        if(customerService.deleteCustomer(id)){
            new Alert(Alert.AlertType.INFORMATION).show();
        } else {
            new Alert(Alert.AlertType.CONFIRMATION).show();
        }

        reloadCustomerTable();
    }

    @FXML
    void btnUpdateOnAction(ActionEvent event) {
        Customer customer = new Customer(
                txtId.getText(),
                cmbTitle.getValue()+" "+txtName.getText(),
                txtAddress.getText(),
                Double.parseDouble(txtSalary.getText())
        );

        if(customerService.updateCustomer(customer)){
            new Alert(Alert.AlertType.INFORMATION).show();
        } else {
            new Alert(Alert.AlertType.CONFIRMATION).show();
        }

        reloadCustomerTable();
    }

    public void reloadCustomerTable() {
        try {
            ObservableList<Customer> allCustomers = customerService.getAllCustomers();
            tblCustomerDetails.setItems(allCustomers);
        } catch (Exception ex) {
            throw new RuntimeException(ex);
        }
    }

    public void btnSearchOnAction(ActionEvent actionEvent) {
        Customer customer = customerService.searchCustomer(txtId.getText());

        if(customer!=null) {
            txtId.setText(customer.getId());
            txtName.setText(customer.getName());
            txtAddress.setText(customer.getAddress());
            txtSalary.setText(String.valueOf(customer.getSalary()));
        }
    }
}
