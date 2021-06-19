package com.mediamarkt.productandcategories.domainmapper;

import com.mediamarkt.productandcategories.domain.Category;
import com.mediamarkt.productandcategories.dto.CategoryDTO;
import org.springframework.stereotype.Component;

import java.util.function.Function;

@Component
public class CategoryDTOToCategoryMapper implements Function<CategoryDTO, Category> {
    @Override
    public Category apply(CategoryDTO categoryDTO) {
        Category category = new Category();
        category.setId(categoryDTO.getId());
        category.setName(categoryDTO.getName());
        category.setParentId(categoryDTO.getParentId());
        return category;
    }
}
