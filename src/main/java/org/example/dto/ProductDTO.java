package org.example.dto;

import lombok.Builder;
import lombok.Data;
import org.example.domains.Category;

@Builder
@Data
public class ProductDTO {
    private Category categoryName;
    private String productName;
    private int price;
    private float rating;
    private String reveiws;
    private int countProducts;
    private String description;
}

