package com.store.orders_microservice.services;

import com.store.orders_microservice.model.dtos.OrderRequest;
import com.store.orders_microservice.model.entities.Order;
import com.store.orders_microservice.repositories.OrderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class OrderService {
    private final OrderRepository orderRepository;

    public void placeOrder(OrderRequest orderRequest) {

        //check for inventory

        Order order= new Order();
        order.setOrderNumber(UUID.randomUUID().toString());
        order.setOrderItems(orderRequest.getOrderItemsRequest().stream()
                .map(orderItem -> orderItem.itemRequestToOrderItem(orderItem,order))
                .toList());
        this.orderRepository.save(order);

    }
}
