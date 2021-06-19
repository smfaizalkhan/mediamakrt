package com.mediamarkt.productandcategories.controller;

import com.mediamarkt.productandcategories.domain.Category;
import com.mediamarkt.productandcategories.dto.CategoryDTO;
import com.mediamarkt.productandcategories.service.CategoryService;
import com.mediamarkt.productandcategories.util.DomainFactory;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static com.mediamarkt.productandcategories.util.TestUtil.asJsonString;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(CategoryController.class)
class CategoryControllerTest {
    @MockBean
    private CategoryService categoryService;
    @Autowired
    private MockMvc mockMvc;

    @Test
    void getCategories() throws Exception {
        Page<CategoryDTO> page = DomainFactory.pageOfCategoryDTO();
        when(categoryService.getCategories(any(Pageable.class))).thenReturn(page);
        mockMvc.perform(get("/category?size=5&page=1")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)).andExpect(status().isOk())
                .andExpect(jsonPath("$.content").exists())
                .andExpect(jsonPath("$.pageable").exists());
    }

    @Test
    void getCategoryById() throws Exception {
        CategoryDTO categoryDTO = DomainFactory.createCategoryDTO();
        when(categoryService.getCategory(anyString())).thenReturn(categoryDTO);
        mockMvc.perform(get("/category/207")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)).andExpect(status().isOk())
                .andExpect(jsonPath("$.id").exists())
                .andExpect(jsonPath("$.name").exists())
                .andExpect(jsonPath("$.parentId").exists());
    }

    @Test
    void createCategory() throws Exception {
        CategoryDTO categoryDTO = DomainFactory.createCategoryDTO();
        when(categoryService.save(any(CategoryDTO.class))).thenReturn(categoryDTO);
        mockMvc.perform(post("/category/create")
                .content(asJsonString(categoryDTO))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)).andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").exists())
                .andExpect(jsonPath("$.name").exists())
                .andExpect(jsonPath("$.parentId").exists());
    }

    @Test
    void updateCategory() throws Exception {
        CategoryDTO categoryDTO = DomainFactory.createCategoryDTO();
        categoryDTO.setId(categoryDTO.getId()+"updated Id");
        when(categoryService.update(any(CategoryDTO.class),anyString())).thenReturn(categoryDTO);
        mockMvc.perform(put("/category/207")
                .content(asJsonString(categoryDTO))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)).andExpect(status().isOk())
                .andExpect(jsonPath("$.id").exists())
                .andExpect(jsonPath("$.id").value(categoryDTO.getId()))
                .andExpect(jsonPath("$.name").exists())
                .andExpect(jsonPath("$.parentId").exists());
    }

    @Test
    void deleteCategory() throws Exception {
        CategoryDTO categoryDTO = DomainFactory.createCategoryDTO();
        doNothing().when(categoryService).delete(anyString());
        mockMvc.perform(delete("/category/207")
                .content(asJsonString(categoryDTO))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)).andExpect(status().is(HttpStatus.NO_CONTENT.value()));
    }
}