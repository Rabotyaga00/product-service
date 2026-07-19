package org.example.mappers;

import org.example.domains.Product;
import org.example.dto.ProductDTO;
import org.mapstruct.Mapper;
import org.springframework.stereotype.Component;

@Component
@Mapper(componentModel = "spring")
public class ProductMapper {
    public ProductDTO toDTO(Product product) {
        return ProductDTO.builder()
                .categoryName(product.getCategory())
                .countProducts(product.getCountProduct())
                .productName(product.getProductName())
                .description(product.getDescription())
                .price(product.getPrice())
                .build();
    }
}
