package com.example.productserver.dto;

import java.math.BigDecimal;

public record ProductPurchaseResponseDTO(
        String productId,
        String name,
        String description,
        BigDecimal price,
        double quantity
) {
}
