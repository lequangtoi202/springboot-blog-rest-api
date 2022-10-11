package com.ecommerce.customer.controller;

import com.ecommerce.library.dto.ProductDto;
import com.ecommerce.library.model.Category;
import com.ecommerce.library.model.Customer;
import com.ecommerce.library.model.ShoppingCart;
import com.ecommerce.library.service.CategoryService;
import com.ecommerce.library.service.CustomerService;
import com.ecommerce.library.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import javax.servlet.http.HttpSession;
import java.security.Principal;
import java.util.List;

@Controller
public class HomeController {
    @Autowired
    private ProductService productService;

    @Autowired
    private CustomerService customerService;

    @Autowired
    private CategoryService categoryService;

    @GetMapping({"/index", "/"})
    public String index(Model model, Principal principal, HttpSession session){

        if (principal != null)
        {
            session.setAttribute("username", principal.getName());
            Customer customer = customerService.findByUsername(principal.getName());
            ShoppingCart shoppingCart = customer.getShoppingCart();
            session.setAttribute("totalItems", shoppingCart.getTotalItems());
        }

        return "home";
    }

    @GetMapping("/home")
    public String home(Model model){
        List<Category> categories = categoryService.findAll();
        List<ProductDto> productDtoList = productService.findAll();
        model.addAttribute("categories", categories);
        model.addAttribute("products", productDtoList);
        model.addAttribute("title", "Menu");
        return "index";
    }
}
