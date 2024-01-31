package com.debezium.uygulama.repository;

import com.debezium.uygulama.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<Product,Long> {
}
