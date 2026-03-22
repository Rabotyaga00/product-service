package org.example.dto;

import lombok.Data;

import java.math.BigDecimal;
import java.util.UUID;

@Data
public class OrderItemDTO {
    private UUID productId;
    private int quantity;
//    private float price;
}
