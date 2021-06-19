package com.mediamarkt.productandcategories.domainmapper;

import com.mediamarkt.productandcategories.domain.Product;
import com.mediamarkt.productandcategories.dto.ProductDTO;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.Arrays;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

@Component
public class ProductToProductDTOMapper implements Function<Product,ProductDTO> {

    @Override
    public ProductDTO apply(Product product) {
        ProductDTO productDTO = new ProductDTO();
        productDTO.setName(product.getName());
        productDTO.setCategory(product.getCategory());
        productDTO.setOnlineStatus(product.getOnlineStatus());
        productDTO.setLongDescription(product.getLongDescription());
        productDTO.setShortDescription(product.getShortDescription());
        List<URI> categoryPath = Arrays.stream(product.getCategory().split(";"))
                 .map(value -> ServletUriComponentsBuilder
                         .fromCurrentContextPath()
                           .path("/category/{productName}")
                           .build(value)).collect(Collectors.toList());
     productDTO.setCategoryPath(categoryPath);
        return productDTO;
    }
}
