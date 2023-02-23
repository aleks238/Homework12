package com.example.homework12.converters;

import com.example.homework12.dtos.CategoryDto;
import com.example.homework12.dtos.ProductDto;
import com.example.homework12.dtos.UserDto;
import com.example.homework12.entities.Category;
import com.example.homework12.entities.Product;
import com.example.homework12.entities.User;
import com.example.homework12.exceptions.ResourceNotFoundException;
import com.example.homework12.services.CategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class Converter {
    private final CategoryService categoryService;


  public ProductDto productToProductDto(Product product){
      return new ProductDto(product.getId(),product.getTitle(), product.getPrice(),product.getCategory().getTitle());
  }

    public Product productDtoToProduct(ProductDto productDto){
        return new Product(productDto.getId(),productDto.getTitle(), productDto.getPrice(), categoryService.findByTitle(productDto.getCategoryTitle()).orElseThrow(()->new ResourceNotFoundException("Категория: " + productDto.getCategoryTitle() + " не найдена")));
    }


    public User userDtoToUser(UserDto userDto){
        return new User(userDto.getUsername(), userDto.getPassword(), userDto.getEmail());
    }

    public UserDto userToUserDto(User user){
        return new UserDto(user.getUsername(), user.getPassword(), user.getEmail());
    }

    public CategoryDto categoryToCategoryDto(Category category){
      return new CategoryDto(
              category.getId(), category.getTitle(),category.getProducts().stream().map(this::productToProductDto).collect(Collectors.toList())
      );
    }

    public Category categoryDtoToCategory(CategoryDto categoryDto){
      return new Category(
              categoryDto.getId(), categoryDto.getTitle(), categoryDto.getProducts().stream().map(this::productDtoToProduct).collect(Collectors.toList())
      );
    }
}
