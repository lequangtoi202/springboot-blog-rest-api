package com.ecommerce.customer.controller;

import com.ecommerce.library.model.Customer;
import com.ecommerce.library.model.Order;
import com.ecommerce.library.model.ShoppingCart;
import com.ecommerce.library.service.CustomerService;
import com.ecommerce.library.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import javax.servlet.http.HttpSession;
import java.security.Principal;
import java.util.List;

@Controller
public class OrderController {
    @Autowired
    private CustomerService customerService;

    @Autowired
    private OrderService orderService;

    @GetMapping("/check-out")
    public String checkout(Model model, Principal principal){
        if (principal == null){
            return "redirect:/login";
        }
        String username = principal.getName();
        Customer customer = customerService.findByUsername(username);

        if (customer.getPhoneNumber().trim().isEmpty() || customer.getAddress().trim().isEmpty()
                || customer.getCity().trim().isEmpty() || customer.getCountry().trim().isEmpty()){
            model.addAttribute("customer", customer);
            model.addAttribute("error", "You must fill the information before checkout");
            return "account";
        }else{
            model.addAttribute("customer", customer);
            ShoppingCart shoppingCart = customer.getShoppingCart();
            model.addAttribute("shoppingCart", shoppingCart);
        }
        return "checkout";
    }

    @GetMapping("/order")
    public String order(Model model, Principal principal){
        if (principal == null){
            return "redirect:/login";
        }
        String username = principal.getName();
        Customer customer = customerService.findByUsername(username);
        /*Tạo pagination cho order*/
        List<Order> orderList = customer.getOrders();

        model.addAttribute("orders", orderList);
        return "order";
    }

    @GetMapping("/save-order")
    public String saveOrder(Model model, Principal principal, HttpSession session){
        if (principal == null){
            return "redirect:/login";
        }
        String username = principal.getName();
        Customer customer = customerService.findByUsername(username);
        ShoppingCart shoppingCart = customer.getShoppingCart();
        orderService.saveOrder(shoppingCart);
        session.removeAttribute("totalItems");

        return "redirect:/order";
    }

    @GetMapping("/cancel/{id}")
    public String cancelOrder(@PathVariable("id")Long id, Model model, Principal principal, HttpSession session){
        if (principal == null){
            return "redirect:/login";
        }
        orderService.cancelOrder(id);

        return "redirect:/order";
    }
}
