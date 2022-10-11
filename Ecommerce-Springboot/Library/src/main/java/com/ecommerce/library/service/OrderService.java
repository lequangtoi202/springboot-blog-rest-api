package com.ecommerce.library.service;

import com.ecommerce.library.model.ShoppingCart;

public interface OrderService {
    void saveOrder(ShoppingCart cart);
    void cancelOrder(Long id);
}
