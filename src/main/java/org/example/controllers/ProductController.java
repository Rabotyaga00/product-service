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

import java.util.UUID;

@RestController
@RequestMapping("/product")
public class ProductController {
    @Autowired
    private ProductService productService;



//    {
//        "orderId": "11111111-1111-1111-1111-111111111111",
//            "userId": "22222222-2222-2222-2222-222222222222",
//            "eventType": "ORDER_CREATED",
//            "orderStatus": "PAID",
//            "totalAmount": 100.0,
//            "items": [
//        { "productId": "aaaaaaaa-aaaa-aaaa-aaaa-aaaaaaaaaaaa", "quantity": 2 },
//        { "productId": "bbbbbbbb-bbbb-bbbb-bbbb-bbbbbbbbbbbb", "quantity": 1 }
//  ]
//    }

//7cdafc7b-e31b-40bc-86b3-a668b220c61b
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
    public Product getProductByCategoryId(@PathVariable int categoryId) {
        return productService.getProductsByCategory(categoryId).get(0);
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
