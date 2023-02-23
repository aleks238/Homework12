package com.example.homework12.dtos;

import lombok.Data;
import lombok.NoArgsConstructor;
@Data
@NoArgsConstructor
public class ProductDto {
    private Long id;
    private String title;
    private Integer price;
    private String categoryTitle;

    public ProductDto(Long id, String title, Integer price, String categoryTitle) {
        this.id = id;
        this.title = title;
        this.price = price;
        this.categoryTitle = categoryTitle;
    }
}
