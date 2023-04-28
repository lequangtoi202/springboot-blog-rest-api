package com.quangtoi.blogrestfulapi.service.impl;

import com.quangtoi.blogrestfulapi.dto.CategoryDto;
import com.quangtoi.blogrestfulapi.exception.ResourceNotFoundException;
import com.quangtoi.blogrestfulapi.model.Category;
import com.quangtoi.blogrestfulapi.repository.CategoryRepository;
import com.quangtoi.blogrestfulapi.service.CategoryService;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class CategoryServiceImpl implements CategoryService {
    private CategoryRepository categoryRepository;
    private ModelMapper mapper;


    @Override
    public CategoryDto addCategory(CategoryDto categoryDto) {
        Category category = new Category();
        category.setName(categoryDto.getName());
        category.setDescription(categoryDto.getDescription());

        Category categorySave = categoryRepository.save(category);
        return mapper.map(categorySave, CategoryDto.class);
    }

    @Override
    public CategoryDto getCategory(long id) {
        Category category = categoryRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Category", "id", id));
        return mapper.map(category, CategoryDto.class);
    }

    @Override
    public CategoryDto updateCategory(long id, CategoryDto categoryDto) {
        Category category = categoryRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Category", "id", id));
        category.setDescription(categoryDto.getDescription());
        category.setName(categoryDto.getName());

        return mapper.map(categoryRepository.save(category), CategoryDto.class);
    }

    @Override
    public List<CategoryDto> getAll() {
        return categoryRepository.findAll()
                .stream()
                .map(c -> mapper.map(c, CategoryDto.class))
                .collect(Collectors.toList());
    }

    @Override
    public void deleteCategory(long id) {
        Category category = categoryRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Category", "id", id));

        categoryRepository.delete(category);
    }
}
