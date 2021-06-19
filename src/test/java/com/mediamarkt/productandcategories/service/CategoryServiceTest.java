package com.mediamarkt.productandcategories.service;

import com.mediamarkt.productandcategories.domain.Category;
import com.mediamarkt.productandcategories.domainmapper.CategoryDTOToCategoryMapper;
import com.mediamarkt.productandcategories.domainmapper.CategoryToCategoryDTOMapper;
import com.mediamarkt.productandcategories.dto.CategoryDTO;
import com.mediamarkt.productandcategories.repo.CategoryRepo;
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

class CategoryServiceTest {

    @InjectMocks
    private CategoryService categoryService;
    @Mock
    private CategoryRepo categoryRepo;
    @Mock
    private CategoryDTOToCategoryMapper categoryDTOToCategoryMapper;
    @Mock
    private CategoryToCategoryDTOMapper categoryToCategoryDTOMapper;


    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        reset(categoryDTOToCategoryMapper, categoryToCategoryDTOMapper, categoryRepo);
    }

    @Test
    void getCategories() {
        Page<Category> pageOfCategory = DomainFactory.pageOfCategory();
        when(categoryRepo.findAll(any(Pageable.class))).thenReturn(pageOfCategory);
        when(categoryToCategoryDTOMapper.apply(any(Category.class))).thenReturn(DomainFactory.createCategoryDTO());
        Page<CategoryDTO> pageOfCategoryRsp = categoryService.getCategories(PageRequest.of(1,1));
        assertThat(pageOfCategoryRsp.get().count()).isEqualTo(1);
    }

    @Test
    void getCategory_Success() {
        CategoryDTO categoryDTO = DomainFactory.createCategoryDTO();
        Category category = DomainFactory.createCategory();
        when(categoryRepo.findById(anyString())).thenReturn(Optional.of(category));
        when(categoryToCategoryDTOMapper.apply(any(Category.class))).thenReturn(categoryDTO);
        final CategoryDTO createdCategoryDTO = categoryService.getCategory(category.getId());
        assertThat(createdCategoryDTO).isNotNull();
        assertThat(createdCategoryDTO.getId()).isEqualTo(categoryDTO.getId());
        assertThat(createdCategoryDTO.getName()).isEqualTo(categoryDTO.getName());
    }

    @Test
    void getCategory_Failure() {
        CategoryDTO categoryDTO = DomainFactory.createCategoryDTO();
        Category category = DomainFactory.createCategory();
        when(categoryRepo.findById(anyString())).thenThrow(new ResponseStatusException(HttpStatus.NOT_FOUND));
        when(categoryToCategoryDTOMapper.apply(any(Category.class))).thenReturn(categoryDTO);
        assertThrows(ResponseStatusException.class,() -> categoryService.getCategory(category.getId()));
    }
    @Test
    void save() {
        CategoryDTO categoryDTO = DomainFactory.createCategoryDTO();
        Category category = DomainFactory.createCategory();
        when(categoryDTOToCategoryMapper.apply(any(CategoryDTO.class))).thenReturn(category);
        when(categoryRepo.save(any(Category.class))).thenReturn(category);
        when(categoryToCategoryDTOMapper.apply(any(Category.class))).thenReturn(categoryDTO);
        final CategoryDTO createdCategoryDTO = categoryService.save(categoryDTO);
        assertThat(createdCategoryDTO).isNotNull();
        assertThat(createdCategoryDTO.getId()).isEqualTo(categoryDTO.getId());
        assertThat(createdCategoryDTO.getName()).isEqualTo(categoryDTO.getName());
    }

    @Test
    void update_Failure() {
        CategoryDTO categoryDTO = DomainFactory.createCategoryDTO();
        when(categoryRepo.existsById(anyString())).thenReturn(false);
        assertThrows(ResponseStatusException.class,()-> categoryService.update(categoryDTO,categoryDTO.getId()));
    }
    @Test
    void update_Success() {
        CategoryDTO categoryDTO = DomainFactory.createCategoryDTO();
        categoryDTO.setId(categoryDTO.getId()+"updated ID");
        Category category = DomainFactory.createCategory();
        when(categoryRepo.existsById(anyString())).thenReturn(true);
        when(categoryDTOToCategoryMapper.apply(any(CategoryDTO.class))).thenReturn(category);
        when(categoryToCategoryDTOMapper.apply(any(Category.class))).thenReturn(categoryDTO);
        when(categoryRepo.save(any(Category.class))).thenReturn(category);
        CategoryDTO updatedCategoryDTO = categoryService.update(categoryDTO,categoryDTO.getId());
        assertThat(updatedCategoryDTO).isNotNull();
        assertThat(updatedCategoryDTO.getId()).isEqualTo(categoryDTO.getId());
        assertThat(updatedCategoryDTO.getName()).isEqualTo(categoryDTO.getName());
    }

    @Test
    void delete_Failure() {
        Category category = DomainFactory.createCategory();
        when(categoryRepo.existsById(anyString())).thenReturn(false);
        assertThrows(ResponseStatusException.class,()-> categoryService.delete(category.getId()));
    }

    @Test
    void delete_Success() {
        CategoryDTO categoryDTO = DomainFactory.createCategoryDTO();
        Category category = DomainFactory.createCategory();
        when(categoryRepo.existsById(anyString())).thenReturn(true);
        categoryService.delete(category.getId());
    }
}