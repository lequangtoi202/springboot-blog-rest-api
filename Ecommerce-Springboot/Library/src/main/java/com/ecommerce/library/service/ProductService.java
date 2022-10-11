package com.ecommerce.library.service;

import com.ecommerce.library.dto.ProductDto;
import com.ecommerce.library.model.Category;
import com.ecommerce.library.model.Product;
import org.springframework.data.domain.Page;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

public interface ProductService {
    /*      ADMIN       */
    List<ProductDto> findAll();
    Product save(MultipartFile imageProduct, ProductDto productDto) throws IOException;
    Product update(MultipartFile imageProduct, ProductDto productDto);
    void deleteById(Long id);
    void enableById(Long id);
    ProductDto getById(Long id);
    Page<ProductDto> pageProducts(int pageNo);
    Page<ProductDto> searchProducts(int pageNo, String keyword);
    /*      End ADMIN       */


    /*      CUSTOMER        */
    List<ProductDto> getAllProducts();
    List<ProductDto> listViewProducts();
    ProductDto getProductById(Long id);
    Product getProById(Long id);
    List<ProductDto> getRelatedProducts(Long id);
    List<ProductDto> getProductsInCategory(Long id);
    List<ProductDto> filterHighPrice();
    List<ProductDto> filterLowPrice();
}
