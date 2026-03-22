package org.example.dto;

import lombok.Data;

import java.util.List;
import java.util.UUID;

@Data
public class OrderEventDTO {
    private UUID orderId;
    private UUID userId;
    private List<OrderItemDTO> items;
}
