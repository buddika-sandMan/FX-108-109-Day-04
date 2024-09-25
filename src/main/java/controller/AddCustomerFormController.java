package controller;

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
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class AddCustomerFormController implements Initializable {

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

        try {
            String SQL = "INSERT INTO customer VALUES(?,?,?,?)";
            Connection connection = DBConnection.getInstance().getConnection();

            PreparedStatement preparedStatement = connection.prepareStatement(SQL);
            preparedStatement.setObject(1, customer.getId());
            preparedStatement.setObject(2, customer.getName());
            preparedStatement.setObject(3, customer.getAddress());
            preparedStatement.setObject(4, customer.getSalary());

            boolean isAdded = preparedStatement.executeUpdate() > 0;

            if(isAdded) {
                new Alert(Alert.AlertType.CONFIRMATION,"Customer Added").show();
                reloadCustomerTable();
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @FXML
    void btnClearOnAction(ActionEvent event) {

    }

    @FXML
    void btnDeleteOnAction(ActionEvent event) {
        try {
            boolean isDeleted = DBConnection.getInstance().getConnection().createStatement()
                    .executeUpdate("DELETE FROM customer WHERE id='" + txtId.getText() + "'") > 0;

            if(isDeleted) {
                new Alert(Alert.AlertType.INFORMATION,"Successfully deleted").show();
                reloadCustomerTable();
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }

    @FXML
    void btnUpdateOnAction(ActionEvent event) {
        Customer customer = new Customer(
                txtId.getText(),
                cmbTitle.getValue()+" "+txtName.getText(),
                txtAddress.getText(),
                Double.parseDouble(txtSalary.getText())
        );

        String SQL="UPDATE customer SET name=?, address=?, salary=? WHERE id=?";

        try {
            Connection connection = DBConnection.getInstance().getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(SQL);
            preparedStatement.setObject(1,customer.getName());
            preparedStatement.setObject(2,customer.getAddress());
            preparedStatement.setObject(3,customer.getSalary());
            preparedStatement.setObject(4,customer.getId());

            boolean isUpdated = preparedStatement.executeUpdate() > 0;

            if(isUpdated) {
                new Alert(Alert.AlertType.CONFIRMATION,"Customer Updated").show();
                reloadCustomerTable();
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }

    public void reloadCustomerTable(){

        ObservableList<Customer> customerObservableList = FXCollections.observableArrayList();

        try {
            Connection connection = DBConnection.getInstance().getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM customer");
            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()){
                Customer customer = new Customer(
                        resultSet.getString("id"),
                        resultSet.getString("name"),
                        resultSet.getString("address"),
                        resultSet.getDouble("salary")
                );
                customerObservableList.add(customer);
                tblCustomerDetails.setItems(customerObservableList);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

}
