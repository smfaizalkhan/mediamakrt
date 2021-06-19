package com.mediamarkt.productandcategories.integration;

import com.google.gson.Gson;
import com.mediamarkt.productandcategories.ProductAndCategories;
import com.mediamarkt.productandcategories.domain.Product;
import com.mediamarkt.productandcategories.dto.ProductDTO;
import com.mediamarkt.productandcategories.repo.ProductRepo;
import net.minidev.json.JSONObject;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.*;

import java.util.Collections;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(classes = ProductAndCategories.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class ProductControllerIntTest {

    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;

    @Autowired
    private ProductRepo productRepo;

    @Test
    public void test_CreateProductDTO_Success() {
        HttpHeaders headers = new HttpHeaders();
        HttpEntity<ProductDTO> request = new HttpEntity<>(new ProductDTO("proddto","category","ACTIVE","LD","SD", Collections.emptyList()),headers);
        String url = "http://localhost:" + port + "/product/create";
        ResponseEntity<ProductDTO> productDTOResponseEntity = restTemplate
                .postForEntity(url,request,ProductDTO.class);
        assertThat(productDTOResponseEntity.getStatusCode().value()).isEqualTo(HttpStatus.CREATED.value());
    }

    @Test
    public void test_getProductDTO() {
        saveProduct();
        String url = "http://localhost:" + port + "/product?name=product";
        ResponseEntity<ProductDTO> productDTOResponseEntity = restTemplate
                .getForEntity(url,ProductDTO.class);
        assertThat(productDTOResponseEntity.getStatusCode().value()).isEqualTo(HttpStatus.OK.value());
        assertThat(productDTOResponseEntity.getBody().getName()).isNotNull();
        assertThat(productDTOResponseEntity.getBody().getOnlineStatus()).isNotEmpty();
        assertThat(productDTOResponseEntity.getBody().getCategory()).isNotEmpty();
        productRepo.deleteAll();
    }

    @Test
    public void test_getProductDTOWithPaging() {
        saveProducts();
        Integer pageSize = 5;
        Integer pageNumber = 1;
        String url = "http://localhost:" + port + "/product/all?size="+pageSize+"&page="+pageNumber;
        ResponseEntity<String> productDTOResponseEntity = restTemplate
                .getForEntity(url, String.class);
        Gson gson = new Gson();
        JSONObject jsonObject = gson.fromJson(productDTOResponseEntity.getBody(),JSONObject.class);
        List<ProductDTO> buyOrderList = (List<ProductDTO>) jsonObject.get("content");
        assertThat(jsonObject.get("totalPages")).isEqualTo(pageNumber.doubleValue());
        assertThat(jsonObject.get("size")).isEqualTo(pageSize.doubleValue());
        productRepo.deleteAll();
    }

    @Test
    public void test_updateProductDTO() {
        saveProduct();
        HttpHeaders headers = new HttpHeaders();
        HttpEntity<ProductDTO> request = new HttpEntity<>(new ProductDTO("updatedProdDTO","category","ACTIVE","LD","SD", Collections.emptyList()),headers);
        String url = "http://localhost:" + port + "/product?name=product";
        ResponseEntity<ProductDTO> productDTOResponseEntity = restTemplate
              .exchange(url, HttpMethod.PUT,request,ProductDTO.class);
        assertThat(productDTOResponseEntity.getStatusCode().value()).isEqualTo(HttpStatus.OK.value());
        assertThat(productDTOResponseEntity.getBody().getName()).isNotNull();
        assertThat(productDTOResponseEntity.getBody().getName()).isEqualTo("updatedProdDTO");
        assertThat(productDTOResponseEntity.getBody().getOnlineStatus()).isNotEmpty();
        assertThat(productDTOResponseEntity.getBody().getCategory()).isNotEmpty();
        productRepo.deleteAll();
    }

    @Test
    public void test_deleteProductDTO() {
        saveProduct();
        HttpHeaders headers = new HttpHeaders();
        String url = "http://localhost:" + port + "/product?name=product";
        ResponseEntity<Void> productDTOResponseEntity = restTemplate
           .exchange(url, HttpMethod.DELETE,new HttpEntity<>(headers),Void.class);
        assertThat(productDTOResponseEntity.getStatusCode().value()).isEqualTo(HttpStatus.NO_CONTENT.value());
       assertThat(productRepo.findById("product")).isEmpty();
    }

    private void saveProduct() {
        Product product = new Product();
        product.setName("product");
        product.setCategory("category");
        product.setOnlineStatus("ACTIVE");
        product.setLongDescription("LD");
        product.setShortDescription("SD");
        productRepo.save(product);
    }
    private void saveProducts() {
        for (int i = 0; i < 10; i++) {
            Product product = new Product();
            product.setName("product" + i);
            product.setCategory("category" + i);
            product.setOnlineStatus("ACTIVE");
            product.setLongDescription("LD" + i);
            product.setShortDescription("SD" + i);
            productRepo.save(product);
        }
    }

    }
