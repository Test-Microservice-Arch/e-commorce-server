package com.example.productserver.util;

import com.example.productserver.dto.ProductRequestDTO;
import com.example.productserver.dto.ProductResponseDTO;
import com.example.productserver.entity.Product;
import org.springframework.stereotype.Service;

@Service
public class ProductMapper {

    public Product toProduct(ProductRequestDTO request) {
        return Product.builder()
                .id(request.id())
                .name(request.name())
                .description(request.description())
                .availableQuantity(request.availableQuantity())
                .price(request.price())
                .build();
    }

    public ProductResponseDTO toProductResponse(Product product) {
        return new ProductResponseDTO(
                product.getId(),
                product.getName(),
                product.getDescription(),
                product.getAvailableQuantity(),
                product.getPrice()
        );
    }

//    public ProductPurchaseResponse toproductPurchaseResponse(Product product, double quantity) {
//        return new ProductPurchaseResponse(
//                product.getId(),
//                product.getName(),
//                product.getDescription(),
//                product.getPrice(),
//                quantity
//        );
//    }
}
