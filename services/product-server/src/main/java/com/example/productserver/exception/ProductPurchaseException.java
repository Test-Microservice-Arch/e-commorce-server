package com.example.productserver.exception;

public class ProductPurchaseException extends RuntimeException{
    public ProductPurchaseException(String s) {
        super(s);
    }
}
