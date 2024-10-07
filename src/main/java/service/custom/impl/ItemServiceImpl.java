package service.custom.impl;

import javafx.collections.ObservableList;
import dto.Item;
import dto.OrderDetails;
import service.custom.ItemService;

import java.util.List;

public class ItemServiceImpl implements ItemService {
    @Override
    public boolean addItem(Item item) {
        return false;
    }

    @Override
    public ObservableList<Item> getAllItems() {
        return null;
    }

    @Override
    public boolean updateCustomer(Item item) {
        return false;
    }

    @Override
    public boolean deleteItem(String code) {
        return false;
    }

    @Override
    public Item searchItem(String code) {
        return null;
    }

    @Override
    public List<String> getAllItemCodes() {
        return List.of();
    }

    @Override
    public boolean updateStock(List<OrderDetails> orderDetailsList) {
        return false;
    }
}
