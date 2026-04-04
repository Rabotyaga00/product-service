package org.example.controllers;

import org.example.domains.Category;
import org.example.domains.Product;
import org.example.dto.CategoryDTO;
import org.example.services.CategoryService;
import org.example.services.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/category")
public class CategoryController {

    @Autowired
    private CategoryService categoryService;
    @Autowired
    private ProductService productService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Category addCategory(@RequestBody CategoryDTO request) {
        return categoryService.addCategory(
                request.getCategoryName()
        );
    }

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.FOUND)
    public Category getCategoryById(@PathVariable int id) {
        return categoryService.getCategoryById(id);
    }

    @GetMapping("/name/{name}")
    @ResponseStatus(HttpStatus.FOUND)
    public Category getCategoryByName(@PathVariable String name) {
        return categoryService.getCategoryByName(name);
    }

    @DeleteMapping("/{id}")
    public Category deleteCategoryById(@PathVariable int id) {
        return categoryService.deleteCategoryById(id);
    }


    @PatchMapping("/{id}")
    public ResponseEntity<CategoryDTO> updateCategoryById(
            @PathVariable  int id,
            @RequestBody CategoryDTO request){

        boolean hasUpdates = request.getCategoryName() != null;

        if(!hasUpdates) {
            return ResponseEntity.badRequest().build();
        }

        CategoryDTO update = categoryService.sendCategory(id,request);
        return ResponseEntity.ok(update);

    }
    @GetMapping("/{id}/products")
    @ResponseStatus(HttpStatus.OK)
    public List<Product> getCategoryProducts(@PathVariable int id) {
        return productService.getProductsByCategory(id);
    }

}
