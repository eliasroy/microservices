package com.store.products_microservice.services;

import com.store.products_microservice.model.dtos.ProductRequest;
import com.store.products_microservice.model.dtos.ProductResponse;
import com.store.products_microservice.model.entities.Product;
import com.store.products_microservice.repositories.ProductRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class ProductService {
    private final ProductRepository productRepository;

    public void addProduct(ProductRequest  productRequest) {
        log.info("Adding product");
        var product= Product.builder()
                        .sku(productRequest.getSku())
                        .name(productRequest.getName())
                        .description(productRequest.getDescription())
                        .price(productRequest.getPrice())
                        .status(productRequest.getStatus())
                        .build();
        productRepository.save(product);
        log.info("Product added successfully");
    }

    public List<ProductResponse> getAllProducts() {
        return productRepository.findAll()
                .stream()
                .map(ProductResponse::fromProduct)
                .toList();
    }




}
