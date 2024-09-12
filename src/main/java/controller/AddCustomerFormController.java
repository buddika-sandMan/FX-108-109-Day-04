package controller;

import com.jfoenix.controls.JFXTextField;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import model.Customer;

import java.net.URL;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class AddCustomerFormController implements Initializable {

    @FXML
    public DatePicker dpBirthday;

    @FXML
    public ComboBox<String> cmbTitle;

    @FXML
    private TableColumn<?, ?> colAddress;

    @FXML
    private TableColumn<?, ?> colBirthday;

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

    List<Customer> customerList = new ArrayList<>();

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        colId.setCellValueFactory(new PropertyValueFactory<>("id"));
        colName.setCellValueFactory(new PropertyValueFactory<>("name"));
        colAddress.setCellValueFactory(new PropertyValueFactory<>("address"));
        colBirthday.setCellValueFactory(new PropertyValueFactory<>("birthday"));
        colSalary.setCellValueFactory(new PropertyValueFactory<>("salary"));

        ObservableList<String> titleList = FXCollections.observableArrayList();
        titleList.add("MR.");
        titleList.add("MRS.");
        titleList.add("MS.");

        cmbTitle.setItems(titleList);
    }

    @FXML
    void btnAddCustomerOnAction(ActionEvent event) {

        customerList.add(
                new Customer(
                    txtId.getText(),
                    cmbTitle.getValue()+" "+txtName.getText(),
                    txtAddress.getText(),
                    dpBirthday.getValue(),
                    Double.parseDouble(txtSalary.getText())
                )
        );

        reloadCustomerTable();

    }

    @FXML
    void btnClearOnAction(ActionEvent event) {

    }

    @FXML
    void btnDeleteOnAction(ActionEvent event) {

    }

    @FXML
    void btnUpdateOnAction(ActionEvent event) {

    }

    public void reloadCustomerTable(){
        ObservableList<Customer> customerObservableList = FXCollections.observableArrayList();
        customerList.forEach(customer->{
            customerObservableList.add(customer);
        });

        tblCustomerDetails.setItems(customerObservableList);
    }


}
