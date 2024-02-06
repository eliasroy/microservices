package com.store.orders_microservice.services;

import com.store.orders_microservice.model.dtos.*;
import com.store.orders_microservice.model.entities.Order;
import com.store.orders_microservice.model.entities.OrderItems;
import com.store.orders_microservice.repositories.OrderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class OrderService {
    private final OrderRepository orderRepository;

    private final WebClient.Builder webClientBuilder;
    public void placeOrder(OrderRequest orderRequest) {

        //check for inventory
       BaseResponse result= this.webClientBuilder.build()
                .post()
                .uri("http://localhost:8083/api/inventory/in-stock")
                .bodyValue(orderRequest.getOrderItemsRequest())
                .retrieve()
                .bodyToMono(BaseResponse.class)
                .block();

       if (result.hasErrors() || !result.hasErrors() ){
           Order order= new Order();
           order.setOrderNumber(UUID.randomUUID().toString());
           order.setOrderItems(orderRequest.getOrderItemsRequest().stream()
                   .map(orderItem -> orderItem.itemRequestToOrderItem(orderItem,order))
                   .toList());
           this.orderRepository.save(order);
       }else{
           throw new IllegalArgumentException("Soem of the products are not in stock");
       }



    }

    public List<OrderResponse> getAllOrders() {
        List<Order> orders= this.orderRepository.findAll();

        return orders.stream().map(this::mapToOrderResponse).toList();
    }

    private OrderResponse mapToOrderResponse(Order order) {
    return new OrderResponse(order.getId(),order.getOrderNumber(),
            order.getOrderItems().stream().map(this::mapToOrderItemResquest).toList());
    }

    private OrderItemResponse mapToOrderItemResquest(OrderItems orderItems) {
    return new OrderItemResponse(orderItems.getId(),orderItems.getSku(),orderItems.getPrice(),orderItems.getQuantity());
    }
}
