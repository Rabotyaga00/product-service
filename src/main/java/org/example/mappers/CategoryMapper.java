package org.example.mappers;

import org.example.domains.Category;
import org.example.dto.CategoryDTO;
import org.mapstruct.Mapper;
import org.springframework.stereotype.Component;

@Component
@Mapper(componentModel = "spring")
public class CategoryMapper {
    public CategoryDTO mapToDTO(Category category){
        CategoryDTO dto = new CategoryDTO();
        dto.setCategoryName(category.getCategoryName());
        return dto;
    }
}
