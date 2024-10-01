package controller.customer;

import javafx.collections.ObservableList;
import model.Customer;

import java.util.List;

public interface CustomerService {
    boolean addCustomer(Customer customer);
    ObservableList<Customer> getAllCustomers();
    boolean updateCustomer(Customer customer);
    boolean deleteCustomer(String id);
    Customer searchCustomer(String id);
    public List<String> getAllCustomersIds();
}
