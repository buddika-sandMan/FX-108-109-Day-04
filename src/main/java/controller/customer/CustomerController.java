package controller.customer;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import model.Customer;
import util.CrudUtil;

import java.sql.ResultSet;
import java.sql.SQLException;

public class CustomerController implements CustomerService{
    @Override
    public boolean addCustomer(Customer customer) {
        String SQL = "INSERT INTO customer VALUES(?,?,?,?)";
        try {
        CrudUtil.execute(
                SQL,
                customer.getId(),
                customer.getName(),
                customer.getAddress(),
                customer.getSalary()
        );
        return true;

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public ObservableList<Customer> getAllCustomers() {
        ObservableList<Customer> observableList = FXCollections.observableArrayList();
        String SQL = "SELECT * FROM customer";

        try {
            ResultSet resultSet = CrudUtil.execute(SQL);

            while (resultSet.next()) {
                observableList.add(
                        new Customer(
                                resultSet.getString(1),
                                resultSet.getString(2),
                                resultSet.getString(3),
                                resultSet.getDouble(4)
                        )
                );
            }
            return observableList;

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public boolean updateCustomer(Customer customer) {
        String SQL="UPDATE customer SET name=?, address=?, salary=? WHERE id=?";
        try {
            CrudUtil.execute(
                    SQL,
                    customer.getName(),
                    customer.getAddress(),
                    customer.getSalary(),
                    customer.getId()
            );
            return true;

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public boolean deleteCustomer(String id) {
        String SQL = "DELETE FROM customer WHERE id='" +id+ "'";
        try {
            Object execute = CrudUtil.execute(SQL);
            return true;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Customer searchCustomer(String id) {
        return null;
    }
}
