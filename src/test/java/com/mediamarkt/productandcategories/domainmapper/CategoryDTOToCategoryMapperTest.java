package com.mediamarkt.productandcategories.domainmapper;

import com.mediamarkt.productandcategories.domain.Category;
import com.mediamarkt.productandcategories.domain.Product;
import com.mediamarkt.productandcategories.dto.CategoryDTO;
import com.mediamarkt.productandcategories.util.DomainFactory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

class CategoryDTOToCategoryMapperTest {

    @InjectMocks
    private CategoryDTOToCategoryMapper categoryDTOToCategoryMapper;

    @BeforeEach
    public void setUp(){
        MockitoAnnotations.openMocks(this);
    }
    @Test
    void apply() {
        CategoryDTO categoryDTO = DomainFactory.createCategoryDTO();
        assertThat(categoryDTOToCategoryMapper.apply(categoryDTO)).isInstanceOf(Category.class);
    }
}