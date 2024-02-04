package com.store.inventory_microservice.services;

import com.store.inventory_microservice.model.dtos.BaseResponse;
import com.store.inventory_microservice.model.dtos.OrderItemRequest;
import com.store.inventory_microservice.model.entities.Inventory;
import com.store.inventory_microservice.repositories.InventoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class InventoryServices {

    private final InventoryRepository inventoryRepository;


    public boolean inStock(String sku) {
        var inventory = inventoryRepository.findBySku(sku);
        return inventory.filter(v->v.getQuantity()>0).isPresent();
    }

    public BaseResponse areInStock(List<OrderItemRequest> orderItemsequests) {

        var errors=new ArrayList<String>();
        List<String> skus=orderItemsequests.stream()
                .map(OrderItemRequest::getSku)
                .toList();
        List<Inventory> inventoryList=inventoryRepository.findBySkuIn(skus);

        orderItemsequests.forEach(orderItem->{
            var inventory=inventoryList.stream()
                    .filter(v->v.getSku().equals(orderItem.getSku()))
                    .findFirst();
            if (inventory.isEmpty()){
                errors.add(String.format("Product with sku %s not found",orderItem.getSku()));
            }else if (inventory.get().getQuantity()<orderItem.getQuantity()){
                errors.add(String.format("Product with sku %s is out of stock", orderItem.getSku()));
            }
        });
        return errors.size()>0?new BaseResponse(errors.toArray(new String[0])):new BaseResponse(null);
    }
}
