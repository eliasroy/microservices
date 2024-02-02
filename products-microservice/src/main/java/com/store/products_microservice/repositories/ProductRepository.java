package com.store.products_microservice.repositories;

import com.store.products_microservice.model.entities.Product;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<Product,Long> {
}
