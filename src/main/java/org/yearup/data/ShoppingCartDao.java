package org.yearup.data;

import org.yearup.models.ShoppingCart;

public interface ShoppingCartDao
{
    ShoppingCart getByUserId(int userId);
    void clearCart(int userId);
    ShoppingCart addProduct(int userId, int productId);

}
