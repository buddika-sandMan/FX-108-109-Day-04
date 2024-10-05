package controller.order;

import controller.item.ItemController;
import db.DBConnection;
import javafx.scene.control.Alert;
import model.Order;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class OrderController {
    private static OrderController instance;

    private OrderController(){}

    public static OrderController getInstance() { return instance==null?instance=new OrderController():instance;}

    public boolean placeOrder(Order order) throws SQLException {
        Connection connection = DBConnection.getInstance().getConnection();
        try {
            connection.setAutoCommit(false);
            String SQL = "INSERT INTO orders VALUES(?,?,?)";
            PreparedStatement preparedStatement = connection.prepareStatement(SQL);
            preparedStatement.setObject(1,order.getOrderID());
            preparedStatement.setObject(2,order.getDate());
            preparedStatement.setObject(3,order.getCustomerID());
            boolean isOrderAdded = preparedStatement.executeUpdate() > 0;

            if(isOrderAdded) {
                boolean addedOrderDetails = new OrderDetailsController().addOrderDetails(order.getOrderDetailsList());
                if (addedOrderDetails){
                    boolean isUpdatedStock = ItemController.getInstance().updateStock(order.getOrderDetailsList());
                    if (isUpdatedStock) {
                        new Alert(Alert.AlertType.INFORMATION,"Oder placed.").show();
                        return true;
                    }
                }
        }
            connection.rollback();
            return false;

        } finally {
            connection.setAutoCommit(true);
        }
    }
}
