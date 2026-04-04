package org.example.mappers;

import org.example.domains.Category;
import org.example.dto.CategoryDTO;

public class CategoryMapper {
    public CategoryDTO mapToDTO(Category category){
        CategoryDTO dto = new CategoryDTO();
        dto.setCategoryName(category.getCategoryName());
        return dto;
    }
}
