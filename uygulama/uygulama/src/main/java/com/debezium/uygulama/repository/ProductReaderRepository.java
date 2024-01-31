package com.debezium.uygulama.repository;

import com.debezium.uygulama.model.ProductReader;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductReaderRepository extends JpaRepository<ProductReader,Long> {
}
