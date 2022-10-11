package com.ecommerce.library.service.impl;

import com.ecommerce.library.model.CartItem;
import com.ecommerce.library.model.Customer;
import com.ecommerce.library.model.Product;
import com.ecommerce.library.model.ShoppingCart;
import com.ecommerce.library.repository.CartItemRepository;
import com.ecommerce.library.repository.ShoppingCartRepository;
import com.ecommerce.library.service.ShoppingCartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;

@Service
public class ShoppingCartServiceImpl implements ShoppingCartService {
    @Autowired
    private ShoppingCartRepository shoppingCartRepository;

    @Autowired
    private CartItemRepository cartItemRepository;

    @Override
    public ShoppingCart addItemToCart(Product product, int quantity, Customer customer) {
        ShoppingCart shoppingCart = customer.getShoppingCart();
        if (shoppingCart == null){
            shoppingCart = new ShoppingCart();
        }
        Set<CartItem> cartItems = shoppingCart.getCartItem();
        CartItem cartItem = findCartItem(cartItems, product.getId());
        if (cartItems == null){
            cartItems = new HashSet<>();
            if (cartItem == null){
                cartItem = new CartItem();
                cartItem.setProduct(product);
                cartItem.setQuantity(quantity);
                cartItem.setTotalPrice((Math.round(quantity * product.getCostPrice()*100.0)) / 100.0);
                cartItem.setCart(shoppingCart);

                cartItems.add(cartItem);
                cartItemRepository.save(cartItem);
            }
        }else{
            if (cartItem == null){
                cartItem = new CartItem();
                cartItem.setProduct(product);
                cartItem.setQuantity(quantity);
                cartItem.setTotalPrice((Math.round(quantity * product.getCostPrice()*100.0)) / 100.0);
                cartItem.setCart(shoppingCart);

                cartItems.add(cartItem);
                cartItemRepository.save(cartItem);
            }else{
                cartItem.setQuantity(cartItem.getQuantity() + quantity);
                cartItem.setTotalPrice(cartItem.getTotalPrice() + ((Math.round(quantity * product.getCostPrice()*100.0)) / 100.0));
                cartItemRepository.save(cartItem);
            }

        }
        shoppingCart.setCartItem(cartItems);
        shoppingCart.setCustomer(customer);
        shoppingCart.setTotalItems(totalItems(cartItems));
        shoppingCart.setTotalPrices(totalPrices(cartItems));

        return shoppingCartRepository.save(shoppingCart);
    }

    @Override
    public ShoppingCart updateItemInCart(Product product, int quantity, Customer customer) {
        ShoppingCart shoppingCart = customer.getShoppingCart();

        Set<CartItem> cartItems = shoppingCart.getCartItem();

        CartItem cartItem = findCartItem(cartItems, product.getId());

        if (cartItem != null){
            cartItem.setQuantity(quantity);
            cartItem.setTotalPrice((Math.round(quantity * product.getCostPrice()*100.0)) / 100.0);
            cartItemRepository.save(cartItem);
        }

        int totalItems = totalItems(cartItems);
        double totalPrice = totalPrices(cartItems);

        shoppingCart.setTotalItems(totalItems);
        shoppingCart.setTotalPrices(totalPrice);

        return shoppingCartRepository.save(shoppingCart);
    }

    @Override
    public ShoppingCart deleteItemInCart(Product product, Customer customer) {
        ShoppingCart shoppingCart = customer.getShoppingCart();

        Set<CartItem> cartItems = shoppingCart.getCartItem();

        CartItem cartItem = findCartItem(cartItems, product.getId());

        if (cartItem != null){
            cartItems.remove(cartItem);
            cartItemRepository.delete(cartItem);
        }

        int totalItems = totalItems(cartItems);
        double totalPrice = totalPrices(cartItems);

        shoppingCart.setTotalItems(totalItems);
        shoppingCart.setTotalPrices(totalPrice);

        return shoppingCartRepository.save(shoppingCart);
    }

    private CartItem findCartItem(Set<CartItem> cartItems, Long productId){
        if (cartItems == null){
            return null;
        }
        CartItem cartItem = null;
        for (CartItem item : cartItems){
            if (item.getProduct().getId() == productId){
                cartItem = item;
            }
        }
        return cartItem;
    }

    private int totalItems(Set<CartItem> cartItems){
        int totalItems = 0;
        for (CartItem item : cartItems){
            totalItems += item.getQuantity();
        }
        return totalItems;
    }

    private double totalPrices(Set<CartItem> cartItems){
        double totalPrices = 0.0;
        for (CartItem item : cartItems){
            totalPrices += item.getTotalPrice();
        }
        return totalPrices;
    }
}
