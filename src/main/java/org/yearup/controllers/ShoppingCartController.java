package org.yearup.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import org.yearup.data.ProductDao;
import org.yearup.data.ShoppingCartDao;
import org.yearup.data.UserDao;
import org.yearup.models.ShoppingCart;
import org.yearup.models.User;

import java.security.Principal;

@RestController
@RequestMapping("cart")
@CrossOrigin(origins = "http://localhost:63342")
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
    @PreAuthorize("hasRole('ROLE_ADMIN') or hasRole('ROLE_USER')")
    @ResponseStatus(value = HttpStatus.OK)
    // each method in this controller requires a Principal object as a parameter
    public ShoppingCart getCart(Principal principal) {
        try {
            if (principal != null) {
                // get the currently logged in username
                String userName = principal.getName();
                // find database user by userId
                User user = userDao.getByUserName(userName);
                int userId = user.getId();
                // use the shoppingcartDao to get all items in the cart and return the cart
                return shoppingCartDao.getByUserId(userId);
            }
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Oops... our bad.");
        }
        return null;
    }

    // add a POST method to add a product to the cart - the url should be
    // https://localhost:8080/cart/products/15 (15 is the productId to be added
    @PostMapping("products/{productId}")
//    @PreAuthorize("hasRole('ROLE_ADMIN') or hasRole('ROLE_USER')")
    public ShoppingCart addProduct(@PathVariable int productId, Principal principal) {
        try {
            if (principal == null) {
                throw new ResponseStatusException(HttpStatus.UNAUTHORIZED);
            }
            String userName = principal.getName();
            User user = userDao.getByUserName(userName);
            if (user == null) {
                throw new ResponseStatusException(HttpStatus.NOT_FOUND);
                // get the currently logged in username
                // use the shoppingcartDao to get all items in the cart and return the cart
            }
            // find database user by userId
            int userId = user.getId();
            return shoppingCartDao.addProduct(userId, productId);
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Oops... our bad.");
        }
    }

    // add a DELETE method to clear all products from the current users cart
    // https://localhost:8080/cart
    @DeleteMapping("")
    @PreAuthorize("hasRole('ROLE_ADMIN') or hasRole('ROLE_USER')")
    public void clearCart(Principal principal) {
        if (principal != null && principal.getName() != null) {
            try {
                String userName = principal.getName();
                User user = userDao.getByUserName(userName);
                int userId = user.getId();
                shoppingCartDao.clearCart(userId);
            } catch (Exception e) {
                throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Error clearing cart");
            }
        } else {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Please make sure to login");
        }
    }
}
//    @ModelAttribute //  Will be executed before handler methods above (GET & POST)... verifies authentication first.
//    public boolean verifyUser(Principal principal) {
//        if (principal != null && principal.getName() != null) {
//            return true;
//        }
//        return false;
//    }

// BONUS Below:
// add a PUT method to update an existing product in the cart - the url should be
// https://localhost:8080/cart/products/15 (15 is the productId to be updated)
// the BODY should be a ShoppingCartItem - quantity is the only value that will be updated
