package com.ecommerce.library.service.impl;

import com.ecommerce.library.dto.ProductDto;
import com.ecommerce.library.model.Category;
import com.ecommerce.library.model.Product;
import com.ecommerce.library.repository.ProductRepository;
import com.ecommerce.library.service.CategoryService;
import com.ecommerce.library.service.ProductService;
import com.ecommerce.library.utils.ImageUpload;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;

@Service
public class ProductServiceImpl implements ProductService {
    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private ImageUpload imageUpload;

    @Autowired
    private CategoryService categoryService;

    /*ADMIN*/
    @Override
    public List<ProductDto> findAll() {

        List<Product> products = productRepository.findAll();
        List<ProductDto> productDtoList = transfer(products);
        return productDtoList;
    }

    @Override
    public Product save(MultipartFile imageProduct, ProductDto productDto) throws IOException {
        try{
            Product product = new Product();
            if (imageProduct == null){
                product.setImage(null);
            }else {
                imageUpload.uploadImage(imageProduct);
                product.setImage(Base64.getEncoder().encodeToString(imageProduct.getBytes()));
            }
            product.setName(productDto.getName());
            product.setDescription(productDto.getDescription());
            product.setCostPrice(productDto.getCostPrice());
            product.setCurrentQuantity(productDto.getCurrentQuantity());
            product.setCategory(productDto.getCategory());
            product.set_activated(true);
            product.set_deleted(false);

            return productRepository.save(product);
        }catch (Exception e){
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public Product update(MultipartFile imageProduct, ProductDto productDto) {
        try{
            Product product = productRepository.getById(productDto.getId());
            if (imageProduct == null){
                product.setImage(null);
            }else {
                if (!imageUpload.checkExisted(imageProduct)){
                    imageUpload.uploadImage(imageProduct);
                }
                product.setImage(Base64.getEncoder().encodeToString(imageProduct.getBytes()));
            }
            product.setName(productDto.getName());
            product.setDescription(productDto.getDescription());
            product.setCostPrice(productDto.getCostPrice());
            product.setCurrentQuantity(productDto.getCurrentQuantity());
            product.setCategory(productDto.getCategory());
            return productRepository.save(product);
        }catch (Exception e){
        }
        return null;
    }

    @Override
    public void deleteById(Long id) {
        Product product = productRepository.getById(id);
        product.set_deleted(true);
        product.set_activated(false);
        productRepository.save(product);
    }

    @Override
    public void enableById(Long id) {
        Product product = productRepository.getById(id);
        product.set_deleted(false);
        product.set_activated(true);
        productRepository.save(product);
    }

    @Override
    public ProductDto getById(Long id) {
        Product product = productRepository.getById(id);
        return mapToDto(product);
    }

    @Override
    public Page<ProductDto> pageProducts(int pageNo) {
        Pageable pageable = PageRequest.of(pageNo, 5);
        List<Product> products = productRepository.findAll();
        List<ProductDto> productDtoList = transfer(products);
        Page<ProductDto> productDtoPages = toPage(productDtoList, pageable);
        return productDtoPages;
    }

    private Page toPage(List<ProductDto> list, Pageable pageable){
        if (pageable.getOffset() >= list.size()){
            return Page.empty();
        }
        int startIndex = (int)pageable.getOffset();
        int endIndex = (pageable.getOffset() + pageable.getPageSize()) > list.size()
                ? list.size() : (int) (pageable.getOffset() + pageable.getPageSize());
        List subList = list.subList(startIndex, endIndex);
        return new PageImpl(subList, pageable, list.size());
    }

    @Override
    public Page<ProductDto> searchProducts(int pageNo, String keyword) {
        Pageable pageable = PageRequest.of(pageNo, 5);
        List<ProductDto> productDtoList = transfer(productRepository.searchProductList(keyword));
        Page<ProductDto> productPages = toPage(productDtoList, pageable);
        return productPages;
    }

    private List<ProductDto> transfer(List<Product> products){
        List<ProductDto> productDtoList = new ArrayList<>();
        for (Product product : products){
            ProductDto productDto = new ProductDto();
            productDto.setId(product.getId());
            productDto.setName(product.getName());
            productDto.setDescription(product.getDescription());
            productDto.setCostPrice(product.getCostPrice());
            productDto.setSalePrice(product.getSalePrice());
            productDto.setCurrentQuantity(product.getCurrentQuantity());
            productDto.setCategory(product.getCategory());
            productDto.setImage(product.getImage());
            productDto.setActivated(product.is_activated());
            productDto.setDeleted(product.is_deleted());

            productDtoList.add(productDto);
        }

        return productDtoList;
    }

    /*CUSTOMER*/

    @Override
    public List<ProductDto> getAllProducts() {
        List<Product> products = productRepository.getAllProducts();
        return transfer(products);
    }

    @Override
    public List<ProductDto> listViewProducts() {
        List<Product> products = productRepository.listViewProducts();
        return transfer(products);
    }


    @Override
    public ProductDto getProductById(Long id) {
        Product product = productRepository.getById(id);
        return mapToDto(product);
    }

    @Override
    public Product getProById(Long id) {
        return productRepository.getById(id);
    }

    @Override
    public List<ProductDto> getRelatedProducts(Long id) {
        List<Product> product = productRepository.getRelatedProducts(id);
        return transfer(product);
    }

    @Override
    public List<ProductDto> getProductsInCategory(Long id) {
        List<Product> products = productRepository.getProductsInCategory(id);
        return transfer(products);
    }

    @Override
    public List<ProductDto> filterHighPrice() {
        List<Product> products = productRepository.filterHighPrice();
        return transfer(products);
    }

    @Override
    public List<ProductDto> filterLowPrice() {
        List<Product> products = productRepository.filterLowPrice();
        return transfer(products);
    }

    public ProductDto mapToDto(Product product){
        ProductDto productDto = new ProductDto();
        productDto.setId(product.getId());
        productDto.setName(product.getName());
        productDto.setDescription(product.getDescription());
        productDto.setCostPrice(product.getCostPrice());
        productDto.setSalePrice(product.getSalePrice());
        productDto.setCurrentQuantity(product.getCurrentQuantity());
        productDto.setCategory(product.getCategory());
        productDto.setImage(product.getImage());
        productDto.setActivated(product.is_activated());
        productDto.setDeleted(product.is_deleted());
        return productDto;
    }

    public Product mapToEntity(ProductDto productDto){
        Product product = new Product();
        product.setId(productDto.getId());
        product.setName(productDto.getName());
        product.setDescription(productDto.getDescription());
        product.setCostPrice(productDto.getCostPrice());
        product.setSalePrice(productDto.getSalePrice());
        product.setCurrentQuantity(productDto.getCurrentQuantity());
        product.setCategory(productDto.getCategory());
        product.setImage(productDto.getImage());
        product.set_activated(productDto.isActivated());
        product.set_deleted(productDto.isDeleted());
        return product;
    }
}
