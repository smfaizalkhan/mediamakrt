package com.mediamarkt.productandcategories.controller;

import com.mediamarkt.productandcategories.domain.Product;
import com.mediamarkt.productandcategories.dto.ProductDTO;
import com.mediamarkt.productandcategories.error.ApiError;
import com.mediamarkt.productandcategories.service.ProductService;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;

@RestController
@RequiredArgsConstructor
@RequestMapping("/product")
public class ProductController {

    private final ProductService productService;

    @GetMapping("/all")
    public ResponseEntity<Page<ProductDTO>> getProducts(Pageable pageable){
        Page productDTOPage = productService.getProducts(pageable);
        return ResponseEntity.ok(productDTOPage);
    }

    @GetMapping
    public ResponseEntity<ProductDTO> getProductById(@RequestParam("name") String name){
        ProductDTO productDTO = productService.getProduct(name);
        return ResponseEntity.ok(productDTO);
    }

    @PostMapping("/create")
    @ApiOperation(value = "Create Product")
    @ApiResponses(value = {
            @ApiResponse(code = 201 ,message = "Creating a Product" ,response = ProductDTO.class),
            @ApiResponse(code = 400 ,message = "Bad Request" ,response = ApiError.class),
            @ApiResponse(code = 404 ,message = "Not Found" ,response = ApiError.class),
            @ApiResponse(code = 500 ,message = "Internal Server" ,response = ApiError.class)

    })
    public ResponseEntity<ProductDTO> createProduct(@Validated @RequestBody ProductDTO productDTO){
        productDTO = productService.save(productDTO);

        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{productName}")
                .build(productDTO.getName());

        return ResponseEntity.created(location).body(productDTO);

    }

    @PutMapping
    public ResponseEntity<ProductDTO> updateProduct(@Validated @RequestBody ProductDTO productDTO, @RequestParam("name") String name){
        productDTO = productService.update(productDTO,name);
        return ResponseEntity.ok(productDTO);
    }

    @DeleteMapping
    public  ResponseEntity<ProductDTO> deleteProduct(@RequestParam("name") String name) {
        productService.delete(name);
        return ResponseEntity.noContent().build();
    }
}