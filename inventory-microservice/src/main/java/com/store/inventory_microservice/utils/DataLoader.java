package com.store.inventory_microservice.utils;

import com.store.inventory_microservice.model.entities.Inventory;
import com.store.inventory_microservice.repositories.InventoryRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
@Slf4j
public class DataLoader implements CommandLineRunner {
    private final InventoryRepository inventoryRepository;
    @Override
    public void run(String... args) throws Exception {

        log.info("loadiing data ...");

        if (inventoryRepository.findAll().size()==0){
            inventoryRepository.saveAll(
                    List.of(
                            Inventory.builder().sku("0000001").quantity(10L).build(),
                            Inventory.builder().sku("0000002").quantity(10L).build(),
                            Inventory.builder().sku("0000003").quantity(10L).build()
                    )
            );
        }

        log.info("data loaded ...");
    }
}
