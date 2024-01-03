package org.yearup.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.HttpStatusCodeException;
import org.springframework.web.server.ResponseStatusException;
import org.yearup.data.ProductDao;
import org.yearup.data.ShoppingCartDao;
import org.yearup.data.UserDao;
import org.yearup.models.ShoppingCart;
import org.yearup.models.ShoppingCartItem;
import org.yearup.models.User;
import org.yearup.models.Product;

import java.security.Principal;
import java.sql.ResultSet;
import java.util.Map;

@RestController
@RequestMapping("cart")
@CrossOrigin
// only logged in users should have access to these actions
public class ShoppingCartController {
    // a shopping cart requires
    private ShoppingCartDao shoppingCartDao;
    private UserDao userDao;
    private ProductDao productDao;

    @Autowired
    public ShoppingCartController(ShoppingCartDao shoppingCartDao, UserDao userDao, ProductDao productDao) {
        this.shoppingCartDao = shoppingCartDao;
        this.userDao = userDao;
        this.productDao = productDao;
    }

    @GetMapping("")
    // each method in this controller requires a Principal object as a parameter
    public Map<Integer, ShoppingCartItem> getCart(Principal principal) {
        try {
            // get the currently logged in username
            String userName = principal.getName();
            // find database user by userId
            User user = userDao.getByUserName(userName);
            int userId = user.getId();
            if (userId > 0) {
                // use the shoppingcartDao to get all items in the cart and return the cart

                return shoppingCartDao.getByUserId(userId);
            } else {
                throw new ResponseStatusException(HttpStatus.NO_CONTENT);
            }

        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Oops... our bad.");
        }
    }

    // add a POST method to add a product to the cart - the url should be
    // https://localhost:8080/cart/products/15 (15 is the productId to be added

    @PostMapping("/add/{productId}/{quantity}")
    public void addProduct(Principal principal, @PathVariable int productId, @PathVariable int quantity) {
        if (verifyUser(principal)) {
            try {
                String userName = principal.getName();
                User user = userDao.getByUserName(userName);
                int userId = user.getId();

                Product product = productDao.getById(productId);
                if (product != null) {
                    shoppingCartDao.addProduct(userId, productId, quantity);
                } else {
                    throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Product does not exist.");
                }
            } catch (Exception e) {
                throw new ResponseStatusException(HttpStatus.NO_CONTENT, "Please make sure to login");
            }
        }
    }

    // add a PUT method to update an existing product in the cart - the url should be
    // https://localhost:8080/cart/products/15 (15 is the productId to be updated)
    // the BODY should be a ShoppingCartItem - quantity is the only value that will be updated


    // add a DELETE method to clear all products from the current users cart
    // https://localhost:8080/cart
    @DeleteMapping("clearCart")
    public void clearCart(Principal principal) {
        try {
            String userName = principal.getName();
            User user = userDao.getByUserName(userName);
            int userId = user.getId();
            shoppingCartDao.clearCart(userId);
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Error clearing cart");
        }
    }

    @ModelAttribute //  Will be executed before handler methods above (GET & POST)... verifies authentication first.
    public boolean verifyUser(Principal principal) {
        String userName = principal.getName();
        if (userName == null) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "User not authenticated");
        }
        return true;
    }

}
