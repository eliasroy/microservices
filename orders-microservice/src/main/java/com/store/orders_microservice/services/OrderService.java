package com.store.orders_microservice.services;

import com.store.orders_microservice.model.dtos.BaseResponse;
import com.store.orders_microservice.model.dtos.OrderRequest;
import com.store.orders_microservice.model.entities.Order;
import com.store.orders_microservice.repositories.OrderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

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
}
