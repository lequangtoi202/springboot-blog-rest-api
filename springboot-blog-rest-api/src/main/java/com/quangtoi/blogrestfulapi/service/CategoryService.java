package com.quangtoi.blogrestfulapi.service;

import com.quangtoi.blogrestfulapi.dto.CategoryDto;
import org.w3c.dom.stylesheets.LinkStyle;

import java.util.List;

public interface CategoryService {
    CategoryDto addCategory(CategoryDto categoryDto);

    CategoryDto getCategory(long id);

    CategoryDto updateCategory(long id, CategoryDto categoryDto);

    List<CategoryDto> getAll();

    void deleteCategory(long id);

}
