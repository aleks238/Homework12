package com.example.homework12.dtos;

import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.List;

@Data
@NoArgsConstructor
public class CategoryDto {
    private Long id;
    private String title;
    private List<ProductDto> products;

    public CategoryDto(Long id, String title, List<ProductDto> products) {
        this.id = id;
        this.title = title;
        this.products = products;
    }
}
