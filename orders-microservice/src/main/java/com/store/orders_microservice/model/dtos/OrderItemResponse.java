package com.store.orders_microservice.model.dtos;

public record OrderItemResponse(Long id , String sku,Double price,Long quantity) {
}
