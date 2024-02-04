package com.store.inventory_microservice.controllers;

import com.store.inventory_microservice.model.dtos.BaseResponse;
import com.store.inventory_microservice.model.dtos.OrderItemRequest;
import com.store.inventory_microservice.services.InventoryServices;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/inventory")
public class InventoryController {

    private final InventoryServices inventoryService;

    @GetMapping("/{sku}")
    @ResponseStatus(HttpStatus.OK)
   public boolean isInStock(@PathVariable(name = "sku", required = true) String sku) {
        return inventoryService.inStock(sku);
    }

    @PostMapping("/in-stock")
    @ResponseStatus(HttpStatus.OK)
    public BaseResponse areInStock(@RequestBody List<OrderItemRequest> orderItemsequests) {
        return inventoryService.areInStock(orderItemsequests);
    }
}
