package org.example.services;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.example.domains.Category;
import org.example.domains.Product;
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

    private ProductDTO toDTO(Product product) {
        return ProductDTO.builder()
                .categoryName(product.getCategory())
                .countProducts(product.getCountProduct())
                .productName(product.getProductName())
                .description(product.getDescription())
                .price(product.getPrice())
                .build();
    }

    public Product getProductById(UUID id)  {
        boolean exists = productRepository.existsById(id);
        if (!exists) {
            throw new RuntimeException ("Product with this id not found");
        }
        return productRepository.findById(id).get();
    }
    public Product addProduct(int categoryId, String name, int price, String description, int countProducts) {
        boolean existing = productRepository.existsByProductName(name);
        if (existing) {
            throw new RuntimeException ("Product with this name already exists");
        }
        Category categor = categoryRepository.findById(categoryId).get();
        if (categor == null) {
            throw new RuntimeException ("Category with this id not found");
        }
        return productRepository.save(Product.builder()
                .category(categor)
                .productName(name)
                .price(price)
                .description(description)
                .countProduct(countProducts)
                .build());
    }
    public List<Product> getAllProducts() {
        return productRepository.findAll();
    }
    public Product deleteProductById(UUID id) {
        Product product = productRepository.findById(id).orElse(null);
        if (product == null) {
            throw new RuntimeException ("Product with this id not found");
        }
        productRepository.delete(product);
        return product;
    }
    public List<Product> getProductsByCategory(int categoryId) {
        Category category = categoryRepository.findById(categoryId).orElse(null);
        if (category == null) {
            throw new RuntimeException ("Category with this id not found");
        }
        return productRepository.findAllByCategoryId(categoryId);
    }

    public ProductDTO sendProduct(UUID id, UpdateProductRequest request) {
        Product product = productRepository.findById(id).get();
        product.setProductName(request.getProductName());
        product.setPrice(request.getPrice());
        product.setDescription(request.getDescription());
        product.setCountProduct(request.getCountProduct());

        product = productRepository.save(product);
        return toDTO(product);
    }


}
