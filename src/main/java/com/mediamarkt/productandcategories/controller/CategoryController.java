package com.mediamarkt.productandcategories.controller;


import com.mediamarkt.productandcategories.domain.Category;
import com.mediamarkt.productandcategories.dto.CategoryDTO;
import com.mediamarkt.productandcategories.error.ApiError;
import com.mediamarkt.productandcategories.service.CategoryService;
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
@RequestMapping("/category")
public class CategoryController {

    private final CategoryService categoryService;

    @GetMapping
    public ResponseEntity<Page<CategoryDTO>> getCategories(Pageable page){
        Page categoryDTOPage = categoryService.getCategories(page);
        return ResponseEntity.ok(categoryDTOPage);

    }

    @GetMapping("/{id}")
    public ResponseEntity<CategoryDTO> getCategoryById(@PathVariable("id") String categoryId){
      CategoryDTO categoryDTO = categoryService.getCategory(categoryId);
      return ResponseEntity.ok(categoryDTO);
    }

    @PostMapping("/create")
    @ApiOperation(value = "Create Category")
    @ApiResponses(value = {
            @ApiResponse(code = 201 ,message = "Creating a Category" ,response = CategoryDTO.class),
            @ApiResponse(code = 400 ,message = "Bad Request" ,response = ApiError.class),
            @ApiResponse(code = 404 ,message = "Not Found" ,response = ApiError.class),
            @ApiResponse(code = 500 ,message = "Internal Server" ,response = ApiError.class)

    })
    public ResponseEntity<CategoryDTO> createCategory(@Validated @RequestBody CategoryDTO category){
        category = categoryService.save(category);

        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{categoryId}")
                .build(category.getId());

        return ResponseEntity.created(location).body(category);

    }

    @PutMapping("/{id}")
    public ResponseEntity<CategoryDTO> updateCategory(@Validated @RequestBody CategoryDTO category, @PathVariable String id){
        category = categoryService.update(category,id);
        return ResponseEntity.ok(category);
    }

    @DeleteMapping("/{id}")
    public  ResponseEntity<CategoryDTO> deleteCategory(@PathVariable("id") String categoryId) {
        categoryService.delete(categoryId);
        return ResponseEntity.noContent().build();
    }
}
