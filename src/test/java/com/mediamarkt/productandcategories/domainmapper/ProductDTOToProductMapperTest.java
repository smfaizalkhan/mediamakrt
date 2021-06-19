package com.mediamarkt.productandcategories.domainmapper;

import com.mediamarkt.productandcategories.domain.Product;
import com.mediamarkt.productandcategories.dto.ProductDTO;
import com.mediamarkt.productandcategories.util.DomainFactory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

class ProductDTOToProductMapperTest {
    @InjectMocks
    private ProductDTOToProductMapper productDTOToProductMapper;

    @BeforeEach
    public void setUp(){
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void apply() {
        ProductDTO productDTO = DomainFactory.createProductDTO();
        assertThat(productDTOToProductMapper.apply(productDTO)).isInstanceOf(Product.class);
    }
}