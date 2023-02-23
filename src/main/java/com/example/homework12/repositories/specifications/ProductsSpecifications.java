package com.example.homework12.repositories.specifications;

import com.example.homework12.entities.Product;
import org.springframework.data.jpa.domain.Specification;

public class ProductsSpecifications {
    public static Specification<Product> priceGreaterThanOrEqualTo(Integer price){
        return(Specification<Product>) (root, criteriaQuery, criteriaBuilder) -> criteriaBuilder.greaterThanOrEqualTo(root.get("price"),price);
    }

    public static Specification<Product> priceLessThanOrEqualTo(Integer price){
        return (Specification<Product>) (root, criteriaQuery, criteriaBuilder) ->criteriaBuilder.lessThanOrEqualTo(root.get("price"), price);
    }

    public static Specification<Product> nameLike(String partName){
        return (Specification<Product>) (root, criteriaQuery, criteriaBuilder) ->criteriaBuilder.like(root.get("title"), String.format("%%%s%%", partName));
    }
}
