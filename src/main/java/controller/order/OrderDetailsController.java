package controller.order;

import model.Order;
import model.OrderDetails;
import util.CrudUtil;

import java.sql.SQLException;
import java.util.List;

public class OrderDetailsController {
    public boolean addOrderDetails(List<OrderDetails> orderDetails) {
        for (OrderDetails orderDetail:orderDetails) {
            boolean isOrderdetailsAdd = addOrderDetails(orderDetail);

            if (!isOrderdetailsAdd) {
                return false;
            }
        }
        return true;
    }

    public boolean addOrderDetails(OrderDetails orderDetails) {
        String SQL = "INSERT INTO orderdetail VALUES(?,?,?,?)";
        try {
            return CrudUtil.execute(
                    SQL,
                    orderDetails.getOrderID(),
                    orderDetails.getItemCode(),
                    orderDetails.getQty(),
                    orderDetails.getDiscount()
            );
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
