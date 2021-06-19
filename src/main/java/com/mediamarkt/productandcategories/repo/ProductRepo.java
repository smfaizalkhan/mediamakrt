package com.mediamarkt.productandcategories.repo;

import com.mediamarkt.productandcategories.domain.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductRepo extends JpaRepository<Product,String> {
}
