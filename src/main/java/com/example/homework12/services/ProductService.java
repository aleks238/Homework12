package com.example.homework12.services;

import com.example.homework12.dtos.ProductDto;
import com.example.homework12.entities.Category;
import com.example.homework12.entities.Product;
import com.example.homework12.exceptions.ResourceNotFoundException;
import com.example.homework12.repositories.ProductRepository;
import com.example.homework12.repositories.specifications.ProductsSpecifications;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ProductService {
    private final ProductRepository productRepository;
    private final CategoryService categoryService;

    public Page<Product> find(Integer page, Integer minPrice, Integer maxPrice, String partName) {
        Specification<Product> specification = Specification.where(null);
        if (minPrice != null) {
            specification = specification.and(ProductsSpecifications.priceGreaterThanOrEqualTo(minPrice));
        }
        if (maxPrice != null) {
            specification = specification.and(ProductsSpecifications.priceLessThanOrEqualTo(maxPrice));
        }
        if (partName != null) {
            specification = specification.and(ProductsSpecifications.nameLike(partName));
        }
        return productRepository.findAll(specification, PageRequest.of(page - 1, 5));
    }

    public Optional<Product> findById(Long id) {
        return productRepository.findById(id);
    }

    public Product save(Product product) {
        return productRepository.save(product);
    }

    @Transactional
    public void updateProduct(ProductDto productDto) {
        Product product = findById(productDto.getId()).orElseThrow(() -> new ResourceNotFoundException("Продукт с id:" + productDto.getId() + " не найден"));
        product.setPrice(productDto.getPrice());
        product.setTitle(productDto.getTitle());
        if (!product.getCategory().getTitle().equals(productDto.getCategoryTitle())) {
            Category category = categoryService.findByTitle(productDto.getCategoryTitle()).orElseThrow(() -> new ResourceNotFoundException("Категория:" + productDto.getCategoryTitle() + " не найдена"));
            product.setCategory(category);
        }
    }

    @Transactional
    public void deleteById(Long id) {
        Optional<Product> product = productRepository.findById(id);
        if (product.isPresent()) {
            productRepository.deleteById(id);
        } else {
            throw new ResourceNotFoundException("Product id:" + id + " not found");
        }
    }
}
