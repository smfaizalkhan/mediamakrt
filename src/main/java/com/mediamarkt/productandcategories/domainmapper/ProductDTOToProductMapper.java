package com.mediamarkt.productandcategories.domainmapper;

import com.mediamarkt.productandcategories.domain.Product;
import com.mediamarkt.productandcategories.dto.ProductDTO;
import org.springframework.stereotype.Component;

import java.util.function.Function;

@Component
public class ProductDTOToProductMapper implements Function<ProductDTO,Product> {


    @Override
    public Product apply(ProductDTO productDTO) {
        Product product = new Product();
        product.setName(productDTO.getName());
        product.setCategory(productDTO.getCategory());
        product.setOnlineStatus(productDTO.getOnlineStatus());
        product.setLongDescription(productDTO.getLongDescription());
        product.setShortDescription(productDTO.getShortDescription());
        return product;
    }
}
