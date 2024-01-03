package org.yearup.data;

import org.yearup.models.ShoppingCart;

public interface ShoppingCartDao
{
    ShoppingCart getByUserId(int userId);
    void clearCart(int userId);
    void addProduct(int userId, int productId, int quantity);

}
