package org.example.dto;

import lombok.Data;

@Data
public class ProductResponseDTO {
        private int categoryId;
        private String productName;
        private int price;
        private int countProduct;
        private String description;
}
