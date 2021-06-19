/*
package com.mediamarkt.productandcategories.integration;

import com.google.gson.Gson;
import com.mediamarkt.productandcategories.ProductAndCategories;
import com.mediamarkt.productandcategories.domain.Category;
import com.mediamarkt.productandcategories.dto.CategoryDTO;
import com.mediamarkt.productandcategories.dto.ProductDTO;
import com.mediamarkt.productandcategories.repo.CategoryRepo;
import net.minidev.json.JSONObject;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.*;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(classes = ProductAndCategories.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class CategoryControllerIntTest {


    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;

    @Autowired
    private CategoryRepo categoryRepo;

    @Test
    public void test_CreateCategory_Success() {
        HttpHeaders headers = new HttpHeaders();
        HttpEntity<CategoryDTO> request = new HttpEntity<>(new CategoryDTO("1234567", "category", "ACTIVE"), headers);
        String url = "http://localhost:" + port + "/category/create";
        ResponseEntity<CategoryDTO> categoryDTOResponseEntity = restTemplate
                .postForEntity(url, request, CategoryDTO.class);
        assertThat(categoryDTOResponseEntity.getStatusCode().value()).isEqualTo(HttpStatus.CREATED.value());
    }

    @Test
    public void test_getCategoryDTO() {
        saveCategory();
        String url = "http://localhost:" + port + "/category/category";
        ResponseEntity<CategoryDTO> categoryDTOResponseEntity = restTemplate
                .getForEntity(url, CategoryDTO.class);
        assertThat(categoryDTOResponseEntity.getStatusCode().value()).isEqualTo(HttpStatus.OK.value());
        assertThat(categoryDTOResponseEntity.getBody().getId()).isNotEmpty();
        assertThat(categoryDTOResponseEntity.getBody().getName()).isNotNull();
        assertThat(categoryDTOResponseEntity.getBody().getParentId()).isNotEmpty();
        categoryRepo.deleteAll();
    }

    @Test
    public void test_getCategoryDTOWithPaging() {
        saveCategories();
        Integer pageSize = 5;
        Integer pageNumber = 1;
        String url = "http://localhost:" + port + "/category?size=" + pageSize + "&page=" + pageNumber;
        ResponseEntity<String> categoryDTOResponseEntity = restTemplate
                .getForEntity(url, String.class);
        Gson gson = new Gson();
        JSONObject jsonObject = gson.fromJson(categoryDTOResponseEntity.getBody(), JSONObject.class);
        List<ProductDTO> buyOrderList = (List<ProductDTO>) jsonObject.get("content");
        assertThat(jsonObject.get("totalPages")).isEqualTo(pageNumber.doubleValue());
        assertThat(jsonObject.get("size")).isEqualTo(pageSize.doubleValue());
        categoryRepo.deleteAll();
    }

    @Test
    public void test_updateCategoryDTO() {
        saveCategory();
        HttpHeaders headers = new HttpHeaders();
        HttpEntity<CategoryDTO> request = new HttpEntity<>(new CategoryDTO("1234567", "category", "ACTIVE"), headers);
        String url = "http://localhost:" + port + "/category/category";
        ResponseEntity<CategoryDTO> categoryDTOResponseEntity = restTemplate
                .exchange(url, HttpMethod.PUT, request, CategoryDTO.class);
        assertThat(categoryDTOResponseEntity.getStatusCode().value()).isEqualTo(HttpStatus.OK.value());
        assertThat(categoryDTOResponseEntity.getBody().getId()).isNotNull();
        assertThat(categoryDTOResponseEntity.getBody().getId()).isEqualTo("1234567");
        assertThat(categoryDTOResponseEntity.getBody().getName()).isNotNull();
        assertThat(categoryDTOResponseEntity.getBody().getParentId()).isNotNull();
        categoryRepo.deleteAll();
    }

    @Test
    public void test_deleteCategoryDTO() {
        saveCategory();
        HttpHeaders headers = new HttpHeaders();
        String url = "http://localhost:" + port + "/category/category";
        ResponseEntity<Void> categoryDTOResponseEntity = restTemplate
                .exchange(url, HttpMethod.DELETE, new HttpEntity<>(headers), Void.class);
        assertThat(categoryDTOResponseEntity.getStatusCode().value()).isEqualTo(HttpStatus.NO_CONTENT.value());
        assertThat(categoryRepo.findById("123456")).isEmpty();
        categoryRepo.deleteAll();
    }

    private void saveCategory() {
        Category category = new Category();
        category.setId("category");
        category.setName("categoryName");
        category.setParentId("categoryParent");
        categoryRepo.save(category);
    }

    private void saveCategories() {
        for (int i = 0; i < 10; i++) {
            Category category = new Category();
            category.setId("category" + i);
            category.setName("categoryName" + i);
            category.setParentId("categoryParent" + i);
            categoryRepo.save(category);
        }
    }
}*/
