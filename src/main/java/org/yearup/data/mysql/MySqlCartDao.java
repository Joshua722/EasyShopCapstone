package org.yearup.data.mysql;

import org.yearup.data.ShoppingCartDao;
import org.yearup.models.ShoppingCart;
import org.yearup.models.ShoppingCartItem;

import javax.sql.DataSource;
import java.util.List;

public class MySqlCartDao extends MySqlDaoBase implements ShoppingCartDao {

    public MySqlCartDao(DataSource dataSource) {
        super(dataSource);
    }

    @Override
    public ShoppingCart getByUserId(int userId) {
        return null;
    }

    @Override
    public void clearCart(int userId) {
        String sql = """
                DELETE FROM shopping_cart
                WHERE user_id = ?""";

    }

    @Override
    public void addProduct(int userId, int productId, int quantity) {
        productId = ShoppingCartItem.getProductId();
        String sql = """
                SELECT *
                FROM shopping_cart
                WHERE
                    user_id AND product_id = ?""";
    }

}
