package org.example.services;

import org.example.exceptions.handler.ResourceNotFoundException;
import org.example.mappers.CategoryMapper;
import org.springframework.transaction.annotation.Transactional;
import lombok.RequiredArgsConstructor;
import org.example.domains.Category;
import org.example.dto.CategoryDTO;
import org.example.repository.CategoryRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class CategoryService {

private final CategoryRepository categoryRepository;
private final CategoryMapper categoryMapper;

    public Category addCategory(String name) {
        boolean existing = categoryRepository.existsByCategoryName(name);
        if (existing) {
            throw new IllegalArgumentException("Category already exists");
        } 

        return categoryRepository.save(Category.builder()
                .categoryName(name)
                .build()
        );
    }
    public Category getCategoryByName(String name) {
        return categoryRepository.findByCategoryName(name)
                .orElseThrow(() -> new ResourceNotFoundException("Category not found" + name));
    }


    public List<Category> listCategory() {
        return categoryRepository.findAll();
    }
    public Category deleteCategoryById(int id) {
        Category category = categoryRepository.findById(id).orElse(null);
        if (category == null) {
            throw new ResourceNotFoundException("Category not found: " + id);
        }
        categoryRepository.delete(category);
        return category;
    }
    public Category getCategoryById(int id) {
        return categoryRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Category not found: " + id));
    }


public CategoryDTO sendCategory(int id, CategoryDTO dto) {

    Category category = categoryRepository.findById(id)
            .orElseThrow(() -> new ResourceNotFoundException("Category not found"));

    if(dto.getCategoryName() != null){
        category.setCategoryName(dto.getCategoryName());
    }

    Category saved = categoryRepository.save(category);

    return categoryMapper.mapToDTO(saved);
}

}
