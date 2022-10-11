package com.ecommerce.customer.controller;

import com.ecommerce.library.model.Customer;
import com.ecommerce.library.model.Product;
import com.ecommerce.library.model.ShoppingCart;
import com.ecommerce.library.service.CustomerService;
import com.ecommerce.library.service.ProductService;
import com.ecommerce.library.service.ShoppingCartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.security.Principal;

@Controller
public class CartController {

    @Autowired
    private CustomerService customerService;

    @Autowired
    private ProductService productService;

    @Autowired
    private ShoppingCartService shoppingCartService;

    @GetMapping("/cart")
    public String cart(Model model, Principal principal, HttpSession session){
        if (principal == null){
            return "redirect:/login";
        }
        String username = principal.getName();
        Customer customer = customerService.findByUsername(username);
        ShoppingCart shoppingCart = customer.getShoppingCart();
        if (shoppingCart == null){
            model.addAttribute("check", "No item in your cart");
        }
        session.setAttribute("totalItems", shoppingCart.getTotalItems());
        model.addAttribute("subTotal", shoppingCart.getTotalPrices());
        model.addAttribute("shoppingCart", shoppingCart);
        return "cart";
    }

    @PostMapping("/add-to-cart")
    public String addItemToCart(
            @RequestParam("id") Long id,
            @RequestParam(value = "quantity", required = false, defaultValue = "1") int quantity,
            Principal principal,
            Model model,
            HttpServletRequest request,
            HttpSession session){

        if (principal == null){
            return "redirect:/login";
        }
        Product product = productService.getProById(id);
        String username = principal.getName();
        Customer customer = customerService.findByUsername(username);

        ShoppingCart shoppingCart = shoppingCartService.addItemToCart(product, quantity, customer);
        session.setAttribute("totalItems", shoppingCart.getTotalItems());
        return "redirect:" + request.getHeader("Referer");
    }
    @GetMapping("/add-to-cart")
    public String addItem(
            @RequestParam("id") Long id,
            @RequestParam(value = "quantity", required = false, defaultValue = "1") int quantity,
            Principal principal,
            Model model,
            HttpServletRequest request,
            HttpSession session){

        if (principal == null){
            return "redirect:/login";
        }
        Product product = productService.getProById(id);
        String username = principal.getName();
        Customer customer = customerService.findByUsername(username);

        ShoppingCart shoppingCart = shoppingCartService.addItemToCart(product, quantity, customer);
        session.setAttribute("totalItems", shoppingCart.getTotalItems());
        return "redirect:" + request.getHeader("Referer");
    }

    @RequestMapping(value = "/update-cart", params = "action=update", method = RequestMethod.POST)
    public String updateCart(@RequestParam("quantity") int quantity,
                             @RequestParam("id") Long productId,
                             Model model,
                             Principal principal){

        if (principal == null) {
            return "redirect:/login";
        }
        else{
            String username = principal.getName();
            Customer customer = customerService.findByUsername(username);
            Product product = productService.getProById(productId);

            ShoppingCart shoppingCart = shoppingCartService.updateItemInCart(product, quantity, customer);

            model.addAttribute("shoppingCart", shoppingCart);

            return "redirect:/cart";
        }
    }

    @RequestMapping(value = "/update-cart", params = "action=delete", method = RequestMethod.POST)
    public String deleteItemFromCart(@RequestParam("id")Long productId, Model model, Principal principal){
        if (principal == null) {
            return "redirect:/login";
        }
        else {
            String username = principal.getName();
            Customer customer = customerService.findByUsername(username);
            Product product = productService.getProById(productId);

            ShoppingCart shoppingCart = shoppingCartService.deleteItemInCart(product, customer);
            model.addAttribute("shoppingCart", shoppingCart);
            return "redirect:/cart";
        }
    }
}
