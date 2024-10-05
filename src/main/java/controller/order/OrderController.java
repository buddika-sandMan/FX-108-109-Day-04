package controller.order;

import db.DBConnection;
import model.Order;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class OrderController {
    public boolean placeOrder(Order order) throws SQLException {
        Connection connection = DBConnection.getInstance().getConnection();
        String SQL = "INSERT INTO orders VALUES(?,?,?)";
        PreparedStatement preparedStatement = connection.prepareStatement(SQL);
        preparedStatement.setObject(1,order.getOrderID());
        preparedStatement.setObject(2,order.getDate());
        preparedStatement.setObject(3,order.getCustomerID());
        boolean isOrderAdded = preparedStatement.executeUpdate() > 0;

        if(isOrderAdded) {
            boolean addedOrderDetails = new OrderDetailsController().addOrderDetails(order.getOrderDetailsList());
        }
         }
    }
