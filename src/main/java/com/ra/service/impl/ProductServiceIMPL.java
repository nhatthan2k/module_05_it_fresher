package com.ra.service.impl;

import com.ra.model.dto.request.ProductRequest;
import com.ra.model.entity.Category;
import com.ra.model.entity.Product;
import com.ra.repository.ProductRepository;
import com.ra.service.CategoryService;
import com.ra.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.UUID;

@Service
public class ProductServiceIMPL implements ProductService {
    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private CategoryService categoryService;
    @Override
    public Page<Product> getAll(Pageable pageable) {
        return productRepository.findAll(pageable);
    }

    @Override
    public Product add(ProductRequest productRequest) {
        if (productRepository.existsByName(productRequest.getName())){
            throw new RuntimeException("Tên sản phẩm đã tồn tại!");
        }

        Category category = categoryService.findById(productRequest.getCategoryId());

        if (category == null) {
            throw new RuntimeException("Không tồn tại danh mục!");
        }

        Product product = Product.builder()
                .sku(UUID.randomUUID().toString())
                .name(productRequest.getName())
                .description(productRequest.getDescription())
                .price(productRequest.getPrice())
                .quantity(productRequest.getQuantity())
                .image(productRequest.getImage())
                .category(category)
                .created(new java.sql.Date(new Date().getTime()))
                .build();
        return productRepository.save(product);
    }

    @Override
    public Product edit(ProductRequest productRequest, Long id) {
        if (productRepository.existsByName(productRequest.getName())){
            throw new RuntimeException("Tên sản phẩm đã tồn tại!");
        }

        Category category = categoryService.findById(productRequest.getCategoryId());

        if (category == null) {
            throw new RuntimeException("Không tồn tại danh mục!");
        }

        Product productOll = findById(id);

        Product product = Product.builder()
                .id(id)
                .sku(productOll.getSku())
                .name(productRequest.getName())
                .description(productRequest.getDescription())
                .price(productRequest.getPrice())
                .quantity(productRequest.getQuantity())
                .image(productRequest.getImage())
                .category(category)
                .created(productOll.getCreated())
                .updated(new java.sql.Date(new Date().getTime()))
                .build();
        return productRepository.save(product);
    }

    @Override
    public Product findById(Long id) {
        return productRepository.findById(id).orElse(null);
    }

    @Override
    public void delete(Long id) {
        productRepository.deleteById(id);
    }

    @Override
    public List<Product> getByNameOrDes(String name, String description) {
        return productRepository.findByNameOrDescription(name, description);
    }

    @Override
    public Page<Product> getByCategoryStatus(Pageable pageable, Boolean status) {
        return productRepository.findByCategoryStatus(pageable, status);
    }

    @Override
    public List<Product> getByCategoryId(Long id) {
        return productRepository.findByCategoryId(id);
    }
}
