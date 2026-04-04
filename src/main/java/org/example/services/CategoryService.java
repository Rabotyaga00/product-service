package org.example.services;

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
CategoryMapper categoryMapper;

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
                .orElseThrow(() -> new RuntimeException("Category not found"));
    }


    public List<Category> listCategory() {
        return categoryRepository.findAll();
    }
    public Category deleteCategoryById(int id) {
        Category category = categoryRepository.findById(id).orElse(null);
        if (category == null) {
            throw new IllegalArgumentException("Category not found");
        }
        categoryRepository.delete(category);
        return category;
    }
    public Category getCategoryById(int id) {
        return categoryRepository.findById(id).orElse(null);
    }


public CategoryDTO sendCategory(int id, CategoryDTO dto) {

    Category category = categoryRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Category not found"));

    if(dto.getCategoryName() != null){
        category.setCategoryName(dto.getCategoryName());
    }

    Category saved = categoryRepository.save(category);

    return categoryMapper.mapToDTO(saved);
}

}
