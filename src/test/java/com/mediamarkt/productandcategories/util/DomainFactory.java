package com.mediamarkt.productandcategories.util;

import com.mediamarkt.productandcategories.domain.Category;
import com.mediamarkt.productandcategories.domain.Product;
import com.mediamarkt.productandcategories.dto.CategoryDTO;
import com.mediamarkt.productandcategories.dto.ProductDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;

import java.util.Arrays;

public class DomainFactory {

    public static ProductDTO createProductDTO() {
        ProductDTO productDTO = new ProductDTO();
        productDTO.setName("2130776 CD 58 UNION JACK");
        productDTO.setCategory("1165;307;2586");
        productDTO.setOnlineStatus("ACTIVE");
        productDTO.setLongDescription("Spezifikationen  - COMPATIBLE CD-R / RW / MP3 - LCD-Anzeige - USB 2.0 - AUX IN - Repeat-Funktion - zufällig - Programm - FM-Stereoradio - Teleskopantenne");
        productDTO.setShortDescription("CD 58 UNION JACK");
        return productDTO;
    }

    public static Product createProduct() {
        Product product = new Product();
        product.setName("2130776 CD 58 UNION JACK");
        product.setCategory("1165;307;2586");
        product.setOnlineStatus("ACTIVE");
        product.setLongDescription("Spezifikationen  - COMPATIBLE CD-R / RW / MP3 - LCD-Anzeige - USB 2.0 - AUX IN - Repeat-Funktion - zufällig - Programm - FM-Stereoradio - Teleskopantenne");
        product.setShortDescription("CD 58 UNION JACK");
        return product;
    }

    public static CategoryDTO createCategoryDTO() {
        CategoryDTO categoryDTO = new CategoryDTO();
        categoryDTO.setId("202");
        categoryDTO.setName("TV & Audio");
        categoryDTO.setParentId("203");
        return categoryDTO;
    }

    public static Category createCategory() {
        Category category = new Category();
        category.setId("202");
        category.setName("TV & Audio");
        category.setParentId("203");
        return category;
    }


    public static Page<Category> pageOfCategory() {
        final Page<Category> page = new PageImpl<Category>(Arrays.asList(createCategory()));
        return page;
    }

    public static Page<Product> pageOfProduct() {
        final Page<Product> page = new PageImpl<Product>(Arrays.asList(createProduct()));
        return page;
    }

    public static Page<ProductDTO> pageOfProductDTO() {
        final Page<ProductDTO> page = new PageImpl<ProductDTO>(Arrays.asList(createProductDTO()));
        return page;
    }

    public static Page<CategoryDTO> pageOfCategoryDTO() {
        final Page<CategoryDTO> page = new PageImpl<CategoryDTO>(Arrays.asList(createCategoryDTO()));
        return page;
    }
}
