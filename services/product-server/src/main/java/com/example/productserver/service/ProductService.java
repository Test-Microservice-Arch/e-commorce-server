package com.example.productserver.service;

import com.example.productserver.dto.ProductPurchaseRequestDTO;
import com.example.productserver.dto.ProductPurchaseResponseDTO;
import com.example.productserver.dto.ProductRequestDTO;
import com.example.productserver.entity.Product;
import com.example.productserver.exception.ProductNotFoundException;
import com.example.productserver.exception.ProductPurchaseException;
import com.example.productserver.repository.ProductRepository;
import com.example.productserver.util.ProductMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Comparator;
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
        log.info("find product by id = {}", id);
        Optional<Product> product = this.productRepository.findById(id);
        if (!product.isPresent()) {
            throw new ProductNotFoundException(String.format("Product not found with ID: %s", id));
        }
        return product.get();
    }

    public List<Product> findAll() {
        log.info("find all products");
        return productRepository.findAll()
                .stream()
                .collect(Collectors.toList());
    }

    @Transactional(rollbackFor = ProductPurchaseException.class)
    public List<ProductPurchaseResponseDTO> purchaseProducts(List<ProductPurchaseRequestDTO> request) {
        log.info("purchase products");
        List<String> productIds = request
                .stream()
                .map(ProductPurchaseRequestDTO::productId)
                .toList();
        List<Product> storedProducts = productRepository.findAllByIdInOrderById(productIds);
        if (productIds.size() != storedProducts.size()) {
            throw new ProductPurchaseException("One or more products does not exist");
        }
        List<ProductPurchaseRequestDTO> sortedRequest = request
                .stream()
                .sorted(Comparator.comparing(ProductPurchaseRequestDTO::productId))
                .toList();
        List<ProductPurchaseResponseDTO> purchasedProducts = new ArrayList<>();
        for (int i = 0; i < storedProducts.size(); i++) {
            Product product = storedProducts.get(i);
            ProductPurchaseRequestDTO productRequest = sortedRequest.get(i);
            if (product.getAvailableQuantity() < productRequest.quantity()) {
                throw new ProductPurchaseException("Insufficient stock quantity for product with ID:: " + productRequest.productId());
            }
            double newAvailableQuantity = product.getAvailableQuantity() - productRequest.quantity();
            product.setAvailableQuantity(newAvailableQuantity);
            productRepository.save(product);
            purchasedProducts.add(mapper.toProductPurchaseResponse(product, productRequest.quantity()));
        }
        return purchasedProducts;
    }
}
