package com.example.productserver.entity;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.math.BigDecimal;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
@Document
public class Product {

    @Id
    private String id;
    private String name;
    private String description;
    private double availableQuantity;
    private BigDecimal price;
}
