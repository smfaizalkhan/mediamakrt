package com.mediamarkt.productandcategories.domainmapper;

import com.mediamarkt.productandcategories.domain.Category;
import com.mediamarkt.productandcategories.dto.CategoryDTO;
import com.mediamarkt.productandcategories.util.DomainFactory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;

import static org.assertj.core.api.Assertions.assertThat;

class CategoryToCategoryDTOMapperTest {

    @InjectMocks
    private CategoryToCategoryDTOMapper categoryToCategoryDTOMapper;

    @BeforeEach
    public void setUp(){
        MockitoAnnotations.openMocks(this);
    }
    @Test
    void apply() {
        Category category = DomainFactory.createCategory();
        assertThat(categoryToCategoryDTOMapper.apply(category)).isInstanceOf(CategoryDTO.class);
    }
}