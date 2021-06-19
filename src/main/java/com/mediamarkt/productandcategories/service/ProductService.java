package com.mediamarkt.productandcategories.service;

import com.mediamarkt.productandcategories.domain.Category;
import com.mediamarkt.productandcategories.domain.Product;
import com.mediamarkt.productandcategories.domainmapper.ProductDTOToProductMapper;
import com.mediamarkt.productandcategories.domainmapper.ProductToProductDTOMapper;
import com.mediamarkt.productandcategories.dto.CategoryDTO;
import com.mediamarkt.productandcategories.dto.ProductDTO;
import com.mediamarkt.productandcategories.repo.ProductRepo;
import com.univocity.parsers.tsv.TsvParser;
import com.univocity.parsers.tsv.TsvParserSettings;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.ClassPathResource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import javax.annotation.PostConstruct;
import java.io.FileReader;
import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;


@Service
@RequiredArgsConstructor
public class ProductService {

    private final ProductRepo productRepo;
    private final ProductDTOToProductMapper productDTOToProductMapper;
    private final ProductToProductDTOMapper productToProductDTOMapper;

    public Page<ProductDTO> getProducts(Pageable pageable) {
        Page<Product> productList= productRepo.findAll(pageable);
        List<ProductDTO> productDTOList = productList.stream()
                .map(product -> productToProductDTOMapper.apply(product)).collect(Collectors.toList());
        return new PageImpl<ProductDTO>(productDTOList);
    }

    public ProductDTO getProduct(String name) {
        Product product= productRepo.findById(name).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
        return productToProductDTOMapper.apply(product);
    }

    public ProductDTO save(ProductDTO productDTO) {
        Product product =productDTOToProductMapper.apply(productDTO);
        product = productRepo.save(product);
        return productToProductDTOMapper.apply(product);
    }

    public ProductDTO update(ProductDTO productDTO, String name) {
        if(!productRepo.existsById(name))
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        Product product = productDTOToProductMapper.apply(productDTO);
        product = productRepo.save(product);
        return productToProductDTOMapper.apply(product);
    }

    public void delete(String name) {
        if (productRepo.existsById(name)) {
            productRepo.deleteById(name);
        } else {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
    }

    /**
     *   Load init Data from Excel Sheet and populate Database
     */
    @PostConstruct
    public void initData(){
        TsvParserSettings settings = new TsvParserSettings();
        settings.setMaxCharsPerColumn(10000);
        try {

            TsvParser parser = new TsvParser(settings);
            ClassPathResource classPathResource = new ClassPathResource("TV & Audio products Data Set.tsv");
            List<String[]> allRows = parser.parseAll(new FileReader(classPathResource.getFile()));
       //allRows.size()
            for(int i=1;i<30;i++){
                String[] values = allRows.get(i);
                Product product = new Product(values[0],values[1],values[2],values[3],values[4]);
                productRepo.save(product);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}

