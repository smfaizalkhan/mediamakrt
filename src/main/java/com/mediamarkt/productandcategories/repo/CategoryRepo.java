package com.mediamarkt.productandcategories.repo;

import com.mediamarkt.productandcategories.domain.Category;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface CategoryRepo extends PagingAndSortingRepository<Category,String> {
}
