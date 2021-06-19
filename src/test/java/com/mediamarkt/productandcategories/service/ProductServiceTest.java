package com.mediamarkt.productandcategories.service;

import com.mediamarkt.productandcategories.domain.Product;
import com.mediamarkt.productandcategories.domainmapper.ProductDTOToProductMapper;
import com.mediamarkt.productandcategories.domainmapper.ProductToProductDTOMapper;
import com.mediamarkt.productandcategories.dto.ProductDTO;
import com.mediamarkt.productandcategories.repo.ProductRepo;
import com.mediamarkt.productandcategories.util.DomainFactory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.reset;
import static org.mockito.Mockito.when;

class ProductServiceTest {

    @InjectMocks
    private ProductService productService;
    @Mock
    private ProductRepo productRepo;
    @Mock
    private ProductDTOToProductMapper productDTOToProductMapper;
    @Mock
    private ProductToProductDTOMapper productToProductDTOMapper;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        reset(productDTOToProductMapper, productToProductDTOMapper, productRepo);
    }
    @Test
    void getProducts() {
        Page<Product> pageOfProduct = DomainFactory.pageOfProduct();
        when(productRepo.findAll(any(Pageable.class))).thenReturn(pageOfProduct);
        when(productToProductDTOMapper.apply(any(Product.class))).thenReturn(DomainFactory.createProductDTO());
        Page<ProductDTO> pageOfCategoryRsp = productService.getProducts(PageRequest.of(1,1));
        assertThat(pageOfCategoryRsp.get().count()).isEqualTo(1);
    }

    @Test
    void getProduct_Success() {
        ProductDTO productDTO = DomainFactory.createProductDTO();
        Product product = DomainFactory.createProduct();
        when(productRepo.findById(anyString())).thenReturn(Optional.of(product));
        when(productToProductDTOMapper.apply(any(Product.class))).thenReturn(productDTO);
        final ProductDTO createdProductDTO = productService.getProduct(productDTO.getName());
        assertThat(createdProductDTO).isNotNull();
        assertThat(createdProductDTO.getName()).isEqualTo(productDTO.getName());
        assertThat(createdProductDTO.getOnlineStatus()).isEqualTo(productDTO.getOnlineStatus());
    }

    @Test
    void getProduct_Failure() {
        ProductDTO productDTO = DomainFactory.createProductDTO();
        when(productRepo.findById(anyString())).thenThrow(new ResponseStatusException(HttpStatus.NOT_FOUND));
        when(productToProductDTOMapper.apply(any(Product.class))).thenReturn(productDTO);
        assertThrows(ResponseStatusException.class,() -> productService.getProduct(productDTO.getName()));
    }


    @Test
    void save() {
        ProductDTO productDTO = DomainFactory.createProductDTO();
        Product product = DomainFactory.createProduct();
        when(productDTOToProductMapper.apply(any(ProductDTO.class))).thenReturn(product);
        when(productRepo.save(any(Product.class))).thenReturn(product);
        when(productToProductDTOMapper.apply(any(Product.class))).thenReturn(productDTO);
        final ProductDTO createdProductDTO = productService.save(productDTO);
        assertThat(createdProductDTO).isNotNull();
        assertThat(createdProductDTO.getName()).isEqualTo(productDTO.getName());
        assertThat(createdProductDTO.getOnlineStatus()).isEqualTo(productDTO.getOnlineStatus());
    }

    @Test
    void update_Failure() {
        ProductDTO productDTO = DomainFactory.createProductDTO();
        when(productRepo.existsById(anyString())).thenReturn(false);
        assertThrows(ResponseStatusException.class,()-> productService.update(productDTO,productDTO.getName()));
    }
    @Test
    void update_Success() {
        ProductDTO productDTO = DomainFactory.createProductDTO();
        productDTO.setName(productDTO.getName()+"updated ID");
        Product product = DomainFactory.createProduct();
        when(productRepo.existsById(anyString())).thenReturn(true);
        when(productDTOToProductMapper.apply(any(ProductDTO.class))).thenReturn(product);
        when(productToProductDTOMapper.apply(any(Product.class))).thenReturn(productDTO);
        when(productRepo.save(any(Product.class))).thenReturn(product);
        ProductDTO updatedProductDTO = productService.update(productDTO,productDTO.getName());
        assertThat(updatedProductDTO).isNotNull();
        assertThat(updatedProductDTO.getName()).isEqualTo(productDTO.getName());
        assertThat(updatedProductDTO.getOnlineStatus()).isEqualTo(productDTO.getOnlineStatus());
    }

    @Test
    void delete_Failure() {
        Product product = DomainFactory.createProduct();
        when(productRepo.existsById(anyString())).thenReturn(false);
        assertThrows(ResponseStatusException.class,()-> productService.delete(product.getName()));
    }

    @Test
    void delete_Success() {
        Product product = DomainFactory.createProduct();
        when(productRepo.existsById(anyString())).thenReturn(true);
        productService.delete(product.getName());
    }
}