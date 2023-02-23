package com.example.homework12.controllers;

import com.example.homework12.converters.Converter;
import com.example.homework12.dtos.ProductDto;
import com.example.homework12.entities.Category;
import com.example.homework12.entities.Product;
import com.example.homework12.exceptions.ResourceNotFoundException;
import com.example.homework12.exceptions.ValidationException;
import com.example.homework12.services.CategoryService;
import com.example.homework12.services.ProductService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.stream.Collectors;

@RestController()
@RequestMapping("/api/v1/products")
@RequiredArgsConstructor
@Slf4j
public class ProductController {
    private final ProductService productService;
    private final CategoryService categoryService;
    private final Converter converter;

    @GetMapping
    public Page<ProductDto> find(
            @RequestParam(name = "p", defaultValue = "1") Integer page,
            @RequestParam(name = "minPrice", required = false) Integer minPrice,
            @RequestParam(name = "maxPrice", required = false) Integer maxPrice,
            @RequestParam(name = "partName", required = false) String partName
    ) {
        if (page < 1) {
            page = 1;
        }
        return productService.find(page,minPrice,maxPrice,partName).map(converter::productToProductDto);
    }

    @GetMapping("/{id}")
    public ProductDto findById(@PathVariable Long id){
        Product product = productService.findById(id).orElseThrow(() ->new ResourceNotFoundException("Product id:" + id + " not found"));
        return converter.productToProductDto(product);
    }

    @PutMapping("/update")
    public void updateProduct(@RequestBody ProductDto productDto){
        productService.updateProduct(productDto);
    }

    @PostMapping("/save")
    public ProductDto save(@RequestBody @Validated ProductDto productDto, BindingResult bindingResult){
        if (bindingResult.hasErrors()){
            throw new ValidationException(bindingResult.getAllErrors().stream().map(ObjectError::getDefaultMessage).collect(Collectors.toList()));
        }
        Product product = new Product();
        product.setPrice(productDto.getPrice());
        product.setTitle(productDto.getTitle());
        Category category = categoryService.findByTitle(productDto.getCategoryTitle()).orElseThrow(()->new ResourceNotFoundException("Категория: " + productDto.getCategoryTitle() + " не найдена"));
        product.setCategory(category);
        productService.save(product);
        return converter.productToProductDto(product);
    }

    @DeleteMapping("/{id}")
    public void deleteById(@PathVariable Long id){
        productService.deleteById(id);
    }

}


