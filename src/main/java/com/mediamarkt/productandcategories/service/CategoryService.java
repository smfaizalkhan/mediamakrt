package com.mediamarkt.productandcategories.service;



import com.mediamarkt.productandcategories.domain.Category;
import com.mediamarkt.productandcategories.domainmapper.CategoryDTOToCategoryMapper;
import com.mediamarkt.productandcategories.domainmapper.CategoryToCategoryDTOMapper;
import com.mediamarkt.productandcategories.dto.CategoryDTO;
import com.mediamarkt.productandcategories.repo.CategoryRepo;
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
public class CategoryService {

    private final CategoryRepo categoryRepo;
    private final CategoryToCategoryDTOMapper categoryToCategoryDTOMapper;
    private final CategoryDTOToCategoryMapper categoryDTOToCategoryMapper;


    public Page<CategoryDTO> getCategories(Pageable pageable) {
        Page<Category> categoryList= categoryRepo.findAll(pageable);
        List<CategoryDTO> categoryDTOList = categoryList.stream()
                .map(category -> categoryToCategoryDTOMapper.apply(category)).collect(Collectors.toList());
        return new PageImpl<CategoryDTO>(categoryDTOList);
    }

    public CategoryDTO save(CategoryDTO categoryDTO) {
        Category category = categoryDTOToCategoryMapper.apply(categoryDTO);
        category  = categoryRepo.save(category);
        return categoryToCategoryDTOMapper.apply(category);
    }

    public CategoryDTO update(CategoryDTO categoryDTO, String id) {
        if(!categoryRepo.existsById(id))
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        Category category = categoryDTOToCategoryMapper.apply(categoryDTO);
        category = categoryRepo.save(category);
        return categoryToCategoryDTOMapper.apply(category);
    }

    public void delete(String categoryId) {
        if (categoryRepo.existsById(categoryId)) {
            categoryRepo.deleteById(categoryId);
        } else {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
    }

    public CategoryDTO getCategory(String categoryId) {
       Category category= categoryRepo.findById(categoryId).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
        return categoryToCategoryDTOMapper.apply(category);
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
            ClassPathResource classPathResource = new ClassPathResource("TV & Audio categories DataSet.tsv");
            List<String[]> allRows = parser.parseAll(new FileReader(classPathResource.getFile()));
//allRows.size()
            for(int i=1;i<30;i++){
                String[] values = allRows.get(i);
                Category category = new Category(values[0],values[1],values[2]);
                categoryRepo.save(category);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }



}
