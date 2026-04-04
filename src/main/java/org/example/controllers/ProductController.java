package org.example.controllers;

import org.example.domains.Product;
import org.example.dto.ProductDTO;
import org.example.dto.ProductResponseDTO;
import org.example.dto.UpdateProductRequest;
import org.example.services.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/product")
public class ProductController {
    @Autowired
    private ProductService productService;



//    {
//        "orderId": "11111111-1111-1111-1111-111111111111",
//            "userId": "bd6ca49d-1ad0-4ae1-ba9d-12d7d2c14c8f",
//            "eventType": "ORDER_CREATED",
//            "orderStatus": "PAID",
//            "totalAmount": 100.0,
//            "items": [
//        { "productId": "62ef80da-9218-4912-a4d5-bc1a13e7529f", "quantity": 2 },
//  ]
//    }

//62ef80da-9218-4912-a4d5-bc1a13e7529f
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Product addProduct(@RequestBody ProductResponseDTO  request) {
        return productService.addProduct(
                request.getCategoryId(),
                request.getProductName(),
                request.getPrice(),
                request.getDescription(),
                request.getCountProduct()
        );
    }
    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.FOUND)
    public Product getProductById(@PathVariable UUID id) {
        return productService.getProductById(id);
    }


    @GetMapping("/category/{categoryId}")
    @ResponseStatus(HttpStatus.OK)
    public List<ProductResponseDTO> getProductByCategoryId(@PathVariable int categoryId) {
        return productService.getProductsByCategory(categoryId).stream().map(p -> {
            ProductResponseDTO dto = new ProductResponseDTO();
            dto.setCategoryId(p.getCategory().getId());
            dto.setProductName(p.getProductName());
            dto.setPrice(p.getPrice());
            dto.setCountProduct(p.getCountProduct());
            dto.setDescription(p.getDescription());
            return dto;
        }).toList();
    }

    @DeleteMapping("/{id}")
    public Product deleteProductById(@PathVariable UUID id) {
        return productService.deleteProductById(id);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<ProductDTO> updateProductById(
            @PathVariable  UUID id,
            @RequestBody UpdateProductRequest request){

        boolean hasUpdates = request.getProductName() != null ||
                            request.getCountProduct() != 0 ||
                            request.getDescription() != null ||
                            request.getPrice() != 0;

        if(!hasUpdates) {
            return ResponseEntity.badRequest().build();
        }

        ProductDTO update = productService.sendProduct(id,request);
        return ResponseEntity.ok(update);

    }

}
