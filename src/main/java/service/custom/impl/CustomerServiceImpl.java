package service.custom.impl;

import javafx.collections.ObservableList;
import model.Customer;
import service.custom.CustomerService;

import java.util.List;

public class CustomerServiceImpl implements CustomerService {
    @Override
    public boolean addCustomer(Customer customer) {
        return false;
    }

    @Override
    public ObservableList<Customer> getAllCustomers() {
        return null;
    }

    @Override
    public boolean updateCustomer(Customer customer) {
        return false;
    }

    @Override
    public boolean deleteCustomer(String id) {
        return false;
    }

    @Override
    public Customer searchCustomer(String id) {
        return null;
    }

    @Override
    public List<String> getAllCustomersIds() {
        return List.of();
    }
}
