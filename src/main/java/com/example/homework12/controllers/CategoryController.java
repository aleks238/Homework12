package com.example.homework12.controllers;

import com.example.homework12.converters.Converter;
import com.example.homework12.dtos.CategoryDto;
import com.example.homework12.entities.Category;
import com.example.homework12.exceptions.ResourceNotFoundException;
import com.example.homework12.services.CategoryService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController()
@RequestMapping("/api/v1/categories")
@RequiredArgsConstructor
@Slf4j
public class CategoryController {
    private final CategoryService categoryService;
    private final Converter converter;

    @GetMapping("/{id}")
    public CategoryDto findCategoryByIdWithProducts(@PathVariable Long id){
        Category category = categoryService.findCategoryByIdWithProducts(id).orElseThrow(() ->new ResourceNotFoundException("Category id " + id + " not found"));
        return converter.categoryToCategoryDto(category);
    }
}
