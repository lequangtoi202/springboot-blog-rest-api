package com.ecommerce.customer.controller;

import com.ecommerce.library.dto.CategoryDto;
import com.ecommerce.library.dto.ProductDto;
import com.ecommerce.library.model.Category;
import com.ecommerce.library.service.CategoryService;
import com.ecommerce.library.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@Controller
public class ProductController {
    @Autowired
    private ProductService productService;
    @Autowired
    private CategoryService categoryService;

    @GetMapping("/products")
    public String products(Model model){
        List<ProductDto> productDtos = productService.getAllProducts();
        List<CategoryDto> categories = categoryService.getCategoryAndProduct();
        List<ProductDto> viewProducts = productService.listViewProducts();
        model.addAttribute("products", productDtos);
        model.addAttribute("categories", categories);
        model.addAttribute("viewProducts", viewProducts);
        return "shop";
    }

    @GetMapping("/find-product/{id}")
    public String findProductById(@PathVariable("id") Long id,
                                  Model model){
        ProductDto productDto = productService.getProductById(id);
        List<ProductDto> productDtoList = productService.getRelatedProducts(productDto.getCategory().getId());
        model.addAttribute("product", productDto);
        model.addAttribute("relatedProducts", productDtoList);

        return "product-detail";
    }

    @GetMapping("/products-in-category/{id}")
    public String getProductsInCategory(@PathVariable("id") Long id, Model model){
        Category category = categoryService.findById(id);
        List<CategoryDto> categories = categoryService.getCategoryAndProduct();
        List<ProductDto> productDtoList = productService.getProductsInCategory(category.getId());
        model.addAttribute("products", productDtoList);
        model.addAttribute("category", category);
        model.addAttribute("categories", categories);
        return "products-in-category";
    }

    @GetMapping("/high-price")
    public String filterHighPrice(Model model){
        List<Category> categories = categoryService.findAllByActivated();
        List<ProductDto> productDtoList = productService.filterHighPrice();
        List<CategoryDto> categoryDtoList = categoryService.getCategoryAndProduct();
        model.addAttribute("products", productDtoList);
        model.addAttribute("categories", categories);
        model.addAttribute("categoryDtoList", categoryDtoList);
        return "filter-high-price";
    }

    @GetMapping("/low-price")
    public String filterLowPrice(Model model){
        List<Category> categories = categoryService.findAllByActivated();
        List<ProductDto> productDtoList = productService.filterLowPrice();
        List<CategoryDto> categoryDtoList = categoryService.getCategoryAndProduct();
        model.addAttribute("products", productDtoList);
        model.addAttribute("categories", categories);
        model.addAttribute("categoryDtoList", categoryDtoList);
        return "filter-low-price";
    }
}
