package com.store.orders_microservice.model.dtos;

import java.util.List;

public record OrderResponse(Long id , String orderNumber, List<OrderItemResponse>orderItems) {
}
