package com.itheima.Service;

import com.itheima.domain.ShoppingCart;

import java.util.List;

public interface ShoppingCartService {
    ShoppingCart addShoppingCart(ShoppingCart shoppingCart);

    List<ShoppingCart> list();

     ShoppingCart sub(ShoppingCart shoppingCart);

    void clean();
}
