package controller.item;

import javafx.collections.ObservableList;
import dto.Item;
import dto.OrderDetails;

import java.util.List;

public interface ItemService {
    boolean addItem(Item item);
    ObservableList<Item> getAllItems();
    boolean updateCustomer(Item item);
    boolean deleteItem(String code);
    Item searchItem(String code);
    public List<String> getAllItemCodes();

    boolean updateStock(List<OrderDetails> orderDetailsList);
}
