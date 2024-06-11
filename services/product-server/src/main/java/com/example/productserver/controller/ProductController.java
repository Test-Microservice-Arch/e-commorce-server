package com.example.productserver.controller;

import com.example.productserver.dto.ProductRequestDTO;
import com.example.productserver.dto.ProductResponseDTO;
import com.example.productserver.entity.Product;
import com.example.productserver.service.ProductService;
import com.example.productserver.util.ProductMapper;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/v1/product")
@RequiredArgsConstructor
public class ProductController {

    @Autowired
    private ProductService productService;

    @Autowired
    private ProductMapper productMapper;

    @PostMapping
    public ResponseEntity<ProductResponseDTO> createProduct(@RequestBody @Valid ProductRequestDTO productRequestDTO) {
        Product product = productService.createProduct(productRequestDTO);
        return ResponseEntity.ok(productMapper.toProductResponse(product));
    }

    @GetMapping("/{product-id}")
    public ResponseEntity<ProductResponseDTO> findById(@PathVariable("product-id") String productId) {
        Product product = productService.findById(productId);
        return ResponseEntity.ok(productMapper.toProductResponse(product));
    }

    @GetMapping
    public ResponseEntity<List<ProductResponseDTO>> findAll() {
        List<Product> allProducts = productService.findAll();
        List<ProductResponseDTO> productResponseDTOS = allProducts.stream()
                .map(productMapper::toProductResponse)
                .collect(Collectors.toList());
        return ResponseEntity.ok(productResponseDTOS);
    }
}
