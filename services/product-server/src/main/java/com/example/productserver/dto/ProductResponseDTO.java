package com.example.productserver.dto;

import java.math.BigDecimal;

public record ProductResponseDTO(
        String id,
        String name,
        String description,
        double availableQuantity,
        BigDecimal price
) {
}
