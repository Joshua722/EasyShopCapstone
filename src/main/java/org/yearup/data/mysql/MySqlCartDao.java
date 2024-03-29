package org.yearup.data.mysql;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ResponseStatusException;
import org.yearup.data.ProductDao;
import org.yearup.data.ShoppingCartDao;
import org.yearup.models.Product;
import org.yearup.models.ShoppingCart;
import org.yearup.models.ShoppingCartItem;


import javax.sql.DataSource;
import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;


@Component

public class MySqlCartDao implements ShoppingCartDao {
    @Autowired
    private DataSource dataSource;
    @Autowired
    private ProductDao productDao;

    @Override
    public ShoppingCart getByUserId(int userId) {

        Map<Integer, ShoppingCartItem> items = new HashMap<>();

        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(
                     "SELECT * FROM shopping_cart WHERE user_id = ?")) {
            statement.setInt(1, userId);

            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    int productId = resultSet.getInt("product_id");
                    int quantity = resultSet.getInt("quantity");
                    Product product = productDao.getById(productId);
                    ShoppingCartItem item = new ShoppingCartItem(product, quantity);
                    items.put(productId, item);
                }
            }
        } catch (SQLException e) {

            e.printStackTrace();
        }

        return new ShoppingCart(items);
    }

    @Override
    public void clearCart(int userId) {
        String sql = """
                DELETE FROM shopping_cart
                WHERE user_id = ?""";
        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, userId);
            int rows = statement.executeUpdate();
            if (rows == 0) {
                throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Cart is already empty");
            }
        } catch (SQLException e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Error clearing cart!");
        }
    }

    @Override
    public ShoppingCart addProduct(int userId, int productId) {
        String sql = """
                INSERT INTO shopping_cart(user_id,product_id,quantity)
                VALUES (?,?,1) ON DUPLICATE KEY UPDATE quantity = quantity + 1
                """;
        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, userId);
            statement.setInt(2, productId);
            statement.executeUpdate();

        } catch (SQLException e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return getByUserId(userId);
    }

}
