package org.example.dto;

import lombok.Builder;
import lombok.Data;
import org.example.domains.Category;

import java.util.UUID;

@Builder
@Data
public class ProductDTO {
    private UUID id;
    private Category categoryName;
    private String productName;
    private int price;
    private float rating;
    private String reveiws;
    private int countProducts;
    private String description;
}

