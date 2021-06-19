package com.mediamarkt.productandcategories.domainmapper;

import com.mediamarkt.productandcategories.domain.Category;
import com.mediamarkt.productandcategories.dto.CategoryDTO;
import org.springframework.stereotype.Component;

import java.util.function.Function;

@Component
public class CategoryToCategoryDTOMapper implements Function<Category, CategoryDTO> {
    @Override
    public CategoryDTO apply(Category category) {
        CategoryDTO categoryDTO = new CategoryDTO();
        categoryDTO.setId(category.getId());
        categoryDTO.setName(category.getName());
        categoryDTO.setParentId(category.getParentId());
        return categoryDTO;
    }
}
