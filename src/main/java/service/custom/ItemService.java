package service.custom;

import javafx.collections.ObservableList;
import model.Item;
import model.OrderDetails;
import service.SuperService;

import java.util.List;

public interface ItemService extends SuperService {
    boolean addItem(Item item);
    ObservableList<Item> getAllItems();
    boolean updateCustomer(Item item);
    boolean deleteItem(String code);
    Item searchItem(String code);
    public List<String> getAllItemCodes();

    boolean updateStock(List<OrderDetails> orderDetailsList);
}
