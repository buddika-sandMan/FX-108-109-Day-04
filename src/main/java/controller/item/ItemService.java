package controller.item;

import javafx.collections.ObservableList;
import model.Item;

public interface ItemService {
    boolean addItem(Item item);
    ObservableList<Item> getAllItems();
    boolean updateCustomer(Item item);
    boolean deleteItem(String code);
    Item searchItem(String code);
}
