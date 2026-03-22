package org.example.repository;

import jakarta.annotation.PostConstruct;
import org.example.domains.Category;
import org.example.domains.Product;
import org.example.dto.ProductResponseDTO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

@Repository
public interface ProductRepository extends JpaRepository<Product, UUID> {

    boolean existsByProductName(String name);

    List<Product> findAllByCategoryId(int categoryId);


}
