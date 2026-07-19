package org.example.services;

import org.example.exceptions.handler.InsufficientStockException;
import org.example.exceptions.handler.ResourceNotFoundException;
import org.example.mappers.ProductMapper;
import org.springframework.transaction.annotation.Transactional;
import lombok.RequiredArgsConstructor;
import org.example.domains.Category;
import org.example.domains.Product;
import org.example.dto.OrderEventDTO;
import org.example.dto.OrderItemDTO;
import org.example.dto.ProductDTO;
import org.example.dto.UpdateProductRequest;
import org.example.repository.CategoryRepository;
import org.example.repository.ProductRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Transactional
public class ProductService {

private final ProductRepository productRepository;
private final CategoryRepository categoryRepository;

    private final ProductMapper productMapper;

    public Product getProductById(UUID id)  {
        return productRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Product with this id not found: " + id));
    }
    public Product addProduct(int categoryId, String name, int price, String description, int countProduct) {
        boolean existing = productRepository.existsByProductName(name);
        if (existing) {
            throw new RuntimeException ("Product with this name already exists");
        }

        //Resource not found exception
        Category categor = categoryRepository.findById(categoryId).orElseThrow(() -> new ResourceNotFoundException("Category not found: " + categoryId));
        return productRepository.save(Product.builder()
                .category(categor)
                .productName(name)
                .price(price)
                .description(description)
                .countProduct(countProduct)
                .build());
    }
    public List<Product> getAllProducts() {
        return productRepository.findAll();
    }
    public Product deleteProductById(UUID id) {
        Product product = productRepository.findById(id).orElse(null);
        if (product == null) {
            throw new ResourceNotFoundException ("Product with this id not found: " + id);
        }
        productRepository.delete(product);
        return product;
    }
    public List<Product> getProductsByCategory(int categoryId) {
        Category category = categoryRepository.findById(categoryId).orElse(null);
        if (category == null) {
            throw new ResourceNotFoundException ("Category with this id not found: " + categoryId);
        }
        return productRepository.findAllByCategoryId(categoryId);
    }

    public ProductDTO sendProduct(UUID id, UpdateProductRequest request) {
        Product product = productRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Product with this id not found: " + id));
        if (request.getProductName() != null) {
            product.setProductName(request.getProductName());
        }
        if (request.getPrice() != null){
            product.setPrice(request.getPrice());
        }
        if (request.getDescription() != null){
            product.setDescription(request.getDescription());
        }
        if (request.getCountProduct() != null){
            product.setCountProduct(request.getCountProduct());
        }
        product = productRepository.save(product);
        return productMapper.toDTO(product);
    }

    public void applyOrderEvent(OrderEventDTO orderEvent) {
        if (orderEvent == null || orderEvent.getItems() == null) {
            return;
        }
        for (OrderItemDTO item : orderEvent.getItems()) {
            if (item.getProductId() == null) {
                continue;
            }
            Product product = productRepository.findById(item.getProductId()).orElse(null);
            if (product == null) {
                throw new ResourceNotFoundException("Product not found: " + item.getProductId());
            }
            int newCount = product.getCountProduct() - item.getQuantity();
            if (newCount < 0) {
                throw new InsufficientStockException("Insufficient stock for product: " + item.getProductId());
            }
            product.setCountProduct(newCount);
            productRepository.save(product);
        }
    }


}
