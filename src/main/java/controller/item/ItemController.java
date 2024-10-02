package controller.item;

import db.DBConnection;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import model.Item;
import util.CrudUtil;
import java.sql.ResultSet;
import java.sql.SQLException;

public class ItemController implements ItemService {
    private static ItemController instance;

    private ItemController(){}

    public static ItemController getInstance() { return instance==null?instance=new ItemController():instance; }

    @Override
    public boolean addItem(Item item) {
        String SQL = "INSERT INTO item VALUES(?,?,?,?)";

        try {
            Object execute = CrudUtil.execute(
                    SQL,
                    item.getCode(),
                    item.getDescription(),
                    item.getUnitPrice(),
                    item.getQtyOnHand()
            );
            return true;

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public ObservableList<Item> getAllItems() {
        ObservableList<Item> itemObservableList = FXCollections.observableArrayList();
        String SQL = "SELECT * FROM item";

        try {
            ResultSet resultSet = CrudUtil.execute(SQL);

            while (resultSet.next()) {
                itemObservableList.add(
                        new Item(
                                resultSet.getString(1),
                                resultSet.getString(2),
                                resultSet.getDouble(3),
                                resultSet.getInt(4)
                        )
                );

            }
            return itemObservableList;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public boolean updateCustomer(Item item) {
        String SQL = "UPDATE item SET description=?, unitPrice=?, qtyOnHand=? WHERE code=?";
        try {
            CrudUtil.execute(
                    SQL,
                    item.getDescription(),
                    item.getUnitPrice(),
                    item.getQtyOnHand(),
                    item.getCode()
            );
            return true;

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public boolean deleteItem(String code) {
        String SQL = "DELETE FROM item WHERE code='" + code + "'";
        try {
            Object execute = CrudUtil.execute(SQL);
            return true;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Item searchItem(String code) {
        String SQL = "SELECT * FROM item WHERE code='"+code+"'";

        try {
            ResultSet resultSet = CrudUtil.execute(SQL);

            while (resultSet.next()) {
                return new Item (
                        resultSet.getString(1),
                        resultSet.getString(2),
                        resultSet.getDouble(3),
                        resultSet.getInt(4)
                );
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return null;
    }

    public ObservableList<String> getAllItemCodes() {
        ObservableList<String> itemCodeList = FXCollections.observableArrayList();
        ObservableList<Item> allItems = getAllItems();

        allItems.forEach(obj -> itemCodeList.add(obj.getCode()));

        return itemCodeList;
    }
}