package controller.item;

import db.DBConnection;
import javafx.collections.ObservableList;
import model.Item;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class ItemController implements ItemService{
    @Override
    public boolean addItem(Item item) {
        boolean isAdded;
        String SQL = "INSERT INTO item VALUES(?,?,?,?)";

        try {
            Connection connection = DBConnection.getInstance().getConnection();

            PreparedStatement preparedStatement = connection.prepareStatement(SQL);
            preparedStatement.setObject(1, item.getCode());
            preparedStatement.setObject(2, item.getDescription());
            preparedStatement.setObject(3, item.getUnitPrice());
            preparedStatement.setObject(4, item.getQtyOnHand());

            isAdded = preparedStatement.executeUpdate() > 0;

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        if(isAdded){
            return true;
        }
        return false;
    }

    @Override
    public ObservableList<Item> getAllItems() {
        return null;
    }

    @Override
    public boolean updateCustomer(Item item) {
        boolean isUpdated;
        String SQL = "UPDATE item SET description=?, unitPrice=?, qtyOnHand=? WHERE code=?";

        try {
            Connection connection = DBConnection.getInstance().getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(SQL);

            preparedStatement.setObject(1, item.getDescription());
            preparedStatement.setObject(2, item.getUnitPrice());
            preparedStatement.setObject(3, item.getQtyOnHand());
            preparedStatement.setObject(4, item.getCode());

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
    public boolean deleteItem(String code) {
        boolean isDeleted;

        try {
            isDeleted = DBConnection.getInstance().getConnection().createStatement()
                    .executeUpdate("DELETE FROM item WHERE code='" +code+ "'") > 0;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        if(isDeleted){
            return true;
        }
        return false;
    }

    @Override
    public Item searchItem(String code) {
        String SQL = "SELECT * FROM item WHERE code='" +code+ "'";

        try {
            Connection connection = DBConnection.getInstance().getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(SQL);

            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()){
                return new Item(
                        resultSet.getString(1),
                        resultSet.getString(2),
                        resultSet.getDouble(3),
                        resultSet.getInt(4)
                );
            }

            return null;

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return null;
    }
}
