package com.mediamarkt.productandcategories.controller;

import com.mediamarkt.productandcategories.dto.ProductDTO;
import com.mediamarkt.productandcategories.service.ProductService;
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
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(ProductController.class)
class ProductControllerTest {

    @MockBean
    private ProductService productService;
    @Autowired
    private MockMvc mockMvc;

    @Test
    void getProducts() throws Exception {
        Page<ProductDTO> page = DomainFactory.pageOfProductDTO();
        when(productService.getProducts(any(Pageable.class))).thenReturn(page);
        mockMvc.perform(get("/product/all?size=5&page=1")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)).andExpect(status().isOk())
                .andExpect(jsonPath("$.content").exists())
                .andExpect(jsonPath("$.pageable").exists());
    }

    @Test
    void getProductById() throws Exception {
        ProductDTO productDTO = DomainFactory.createProductDTO();
        when(productService.getProduct(anyString())).thenReturn(productDTO);
        mockMvc.perform(get("/product").queryParam("name","123456789")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)).andExpect(status().isOk())
                .andExpect(jsonPath("$.name").exists())
                .andExpect(jsonPath("$.category").exists())
                .andExpect(jsonPath("$.onlineStatus").exists())
                .andExpect(jsonPath("$.longDescription").exists())
                .andExpect(jsonPath("$.shortDescription").exists());
    }

    @Test
    void createProduct() throws Exception {
        ProductDTO productDTO = DomainFactory.createProductDTO();
        when(productService.save(any(ProductDTO.class))).thenReturn(productDTO);
        mockMvc.perform(post("/product/create")
                .content(asJsonString(productDTO))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)).andExpect(status().isCreated())
                .andExpect(jsonPath("$.name").exists())
                .andExpect(jsonPath("$.category").exists())
                .andExpect(jsonPath("$.onlineStatus").exists())
                .andExpect(jsonPath("$.longDescription").exists())
                .andExpect(jsonPath("$.shortDescription").exists());
    }

    @Test
    void updateProduct() throws Exception {
        ProductDTO productDTO = DomainFactory.createProductDTO();
        productDTO.setName(productDTO.getName()+"updated Id");
        when(productService.update(any(ProductDTO.class),anyString())).thenReturn(productDTO);
        mockMvc.perform(put("/product").queryParam("name","test rest")
                .content(asJsonString(productDTO))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)).andExpect(status().isOk())
                .andExpect(jsonPath("$.name").exists())
                .andExpect(jsonPath("$.category").exists())
                .andExpect(jsonPath("$.onlineStatus").exists())
                .andExpect(jsonPath("$.longDescription").exists())
                .andExpect(jsonPath("$.shortDescription").exists());
    }

    @Test
    void deleteProduct() throws Exception {
        ProductDTO productDTO = DomainFactory.createProductDTO();
        doNothing().when(productService).delete(anyString());
        mockMvc.perform(delete("/product").queryParam("name","asdsd")
                .content(asJsonString(productDTO))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)).andExpect(status().is(HttpStatus.NO_CONTENT.value()));
    }
}