package org.yearup.data.mysql;

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

public class MySqlCartDao extends MySqlDaoBase implements ShoppingCartDao {
    private ProductDao productDao;

    public MySqlCartDao(DataSource dataSource, ProductDao productDao) {
        super(dataSource);
        this.productDao = productDao;
    }

      public ShoppingCart getByUserId(int userId) {
        Map<Integer, ShoppingCartItem> items = new HashMap<>();
        String sql = """
                SELECT *
                FROM shopping_cart
                WHERE user_id = ?""";
        try (Connection connection = getConnection();
             PreparedStatement statement = connection.prepareStatement(sql);
             ResultSet rs = statement.executeQuery()) {
            statement.setInt(1, userId);
            while (rs.next()) {
                int productId = rs.getInt("product_id");
                int quantity = rs.getInt("quantity");
                Product product = productDao.getById(productId);
                ShoppingCartItem temp = new ShoppingCartItem(product,quantity);
                items.put(productId, temp);

            }

        } catch (SQLException e) {
            throw new RuntimeException("Error retrieving shopping cart items.", e);
        }
        return new ShoppingCart(items);
    }

    @Override
    public void clearCart(int userId) {
        String sql = """
                DELETE FROM shopping_cart
                WHERE user_id = ?""";
        try (Connection connection = getConnection();
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
        try (Connection connection = getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, userId);
            statement.setInt(2, productId);
            int rowEquals = statement.executeUpdate();
            if (rowEquals > 0) {

            }

        } catch (SQLException e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return getByUserId(userId);
    }

}
