package org.yearup.data;

import org.yearup.models.ShoppingCart;
import org.yearup.models.ShoppingCartItem;

import java.util.HashMap;
import java.util.Map;

public interface ShoppingCartDao
{
    HashMap<Integer, ShoppingCartItem> getByUserId(int userId);
    void clearCart(int userId);
    void addProduct(int userId, int productId, int quantity);

}
