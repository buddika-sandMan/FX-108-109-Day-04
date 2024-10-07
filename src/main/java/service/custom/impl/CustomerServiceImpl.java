package service.custom.impl;

import entity.CustomerEntity;
import javafx.collections.ObservableList;
import dto.Customer;
import org.modelmapper.ModelMapper;
import repository.DaoFactory;
import repository.cusotm.CustomerDao;
import service.custom.CustomerService;
import util.DaoType;

import java.util.List;

public class CustomerServiceImpl implements CustomerService {
    @Override
    public boolean addCustomer(Customer customer) {
        System.out.println("service  "+customer);

        CustomerEntity entity = new ModelMapper().map(customer, CustomerEntity.class);
        CustomerDao repository = DaoFactory.getInstance().getDaoType(DaoType.CUSTOMER);

        return repository.save(entity);
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
