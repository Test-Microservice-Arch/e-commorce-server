package com.example.productserver.repository;

import com.example.productserver.entity.Product;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface ProductRepository extends MongoRepository<Product, String> {

    List<Product> findAllByIdInOrderById(List<String> ids);
}
