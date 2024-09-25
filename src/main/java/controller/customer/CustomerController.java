package controller.customer;

import db.DBConnection;
import javafx.collections.ObservableList;
import model.Customer;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class CustomerController implements CustomerService{
    @Override
    public boolean addCustomer(Customer customer) {
        boolean isAdded;
        try {
            String SQL = "INSERT INTO customer VALUES(?,?,?,?)";
            Connection connection = DBConnection.getInstance().getConnection();

            PreparedStatement preparedStatement = connection.prepareStatement(SQL);
            preparedStatement.setObject(1, customer.getId());
            preparedStatement.setObject(2, customer.getName());
            preparedStatement.setObject(3, customer.getAddress());
            preparedStatement.setObject(4, customer.getSalary());

            isAdded = preparedStatement.executeUpdate() > 0;

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        if(isAdded) {
            return true;
        }

        return false;
    }

    @Override
    public ObservableList<Customer> getAllCustomers() {
        return null;
    }

    @Override
    public boolean updateCustomer(Customer customer) {
        String SQL="UPDATE customer SET name=?, address=?, salary=? WHERE id=?";
        boolean isUpdated;
        try {
            Connection connection = DBConnection.getInstance().getConnection();

            PreparedStatement preparedStatement = connection.prepareStatement(SQL);
            preparedStatement.setObject(1,customer.getName());
            preparedStatement.setObject(2,customer.getAddress());
            preparedStatement.setObject(3,customer.getSalary());
            preparedStatement.setObject(4,customer.getId());

            isUpdated = preparedStatement.executeUpdate() > 0;

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        if(isUpdated){
            return true;
        }

        return false;
    }

    @Override
    public boolean deleteCustomer(String id) {
        boolean isDeleted;
        try {
            isDeleted = DBConnection.getInstance().getConnection().createStatement()
                    .executeUpdate("DELETE FROM customer WHERE id='" +id+ "'") > 0;

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        if(isDeleted){
            return true;
        }
        return false;
    }

    @Override
    public Customer searchCustomer(String id) {
        return null;
    }
}
