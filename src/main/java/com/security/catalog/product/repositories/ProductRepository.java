package com.security.catalog.product.repositories;

import com.security.catalog.product.entities.Product;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository  extends JpaRepository<Product, Long> {
}
