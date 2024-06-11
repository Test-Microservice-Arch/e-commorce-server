package com.example.productserver.service;

import com.example.productserver.dto.ProductRequestDTO;
import com.example.productserver.entity.Product;
import com.example.productserver.exception.ProductNotFoundException;
import com.example.productserver.repository.ProductRepository;
import com.example.productserver.util.ProductMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class ProductService {

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private ProductMapper mapper;

    public Product createProduct(ProductRequestDTO request) {
        log.info("create product");
        Product product = productRepository.save(mapper.toProduct(request));
        return product;
    }

    public Product findById(String id) {
        log.info("find customer by id = {}", id);
        Optional<Product> product = this.productRepository.findById(id);
        if (!product.isPresent()) {
            throw new ProductNotFoundException(String.format("Product not found with ID: %s", id));
        }
        return product.get();
    }

    public List<Product> findAll() {
        return productRepository.findAll()
                .stream()
                .collect(Collectors.toList());
    }
}
