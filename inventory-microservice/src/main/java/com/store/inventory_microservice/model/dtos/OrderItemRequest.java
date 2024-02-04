package com.store.inventory_microservice.model.dtos;

import com.store.orders_microservice.model.entities.Order;
import com.store.orders_microservice.model.entities.OrderItems;
import lombok.*;

@Data
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class OrderItemRequest {
    private Long id;
    private String sku;
    private Double price;
    private Long quantity;

    public OrderItems itemRequestToOrderItem(OrderItemRequest orderItemItems,Order order)
    {
        return OrderItems.builder()
                .id(orderItemItems.getId())
                .sku(orderItemItems.getSku())
                .price(orderItemItems.getPrice())
                .quantity(orderItemItems.getQuantity())
                .order(order)
                .build();
    }
}
