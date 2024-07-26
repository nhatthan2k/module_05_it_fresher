package com.ra.service;

import com.ra.model.dto.request.ProductRequest;
import com.ra.model.entity.Category;
import com.ra.model.entity.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface ProductService {
    Page<Product> getAll(Pageable pageable, String nameSearch);
    Product save(ProductRequest productRequest);
    Product findById(Long id);
    void delete(Long id);
    List<Product> getByNameOrDes(String name, String description);
    Page<Product> getByCategoryStatus(Pageable pageable, Boolean status);

    Page<Product> getByCategoryId(Long id, Pageable pageable);

    List<Product> searchByName(String keyword);

    List<Product> sortByAsc(String name);

    List<Product> sortByDesc(String name);

    int countByCategory(Category category);
}
