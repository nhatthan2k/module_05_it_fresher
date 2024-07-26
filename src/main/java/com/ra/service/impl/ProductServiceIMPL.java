package com.ra.service.impl;

import com.ra.model.dto.request.ProductRequest;
import com.ra.model.entity.Category;
import com.ra.model.entity.Product;
import com.ra.repository.CategoryRepository;
import com.ra.repository.ProductRepository;
import com.ra.sercurity.exception.ResourceNotFoundException;
import com.ra.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.config.ConfigDataResourceNotFoundException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductServiceIMPL implements ProductService {
    @Autowired
    private ProductRepository productRepository;
    @Autowired
    private CategoryRepository categoryRepository;

    @Override
    public Page<Product> getAll(Pageable pageable, String nameSearch) {
        return productRepository.findAll(pageable);
    }

    @Override
    public Product save(ProductRequest productRequest) {

        if (productRepository.existsByName(productRequest.getName())) {
            throw new IllegalArgumentException("Tên sản phẩm đã tồn tại, vui lòng nhập tên sản phẩm khác!");
        }

        // Tạo mới đối tượng Product và lưu vào database
        Product product = new Product();
        product.setName(productRequest.getName());
        product.setDescription(productRequest.getDescription());
        product.setPrice(productRequest.getPrice());
        product.setQuantity(productRequest.getQuantity());
        Category category = categoryRepository.findById(productRequest.getCategoryId())
                .orElseThrow(() -> new IllegalArgumentException("Invalid category ID"));
        product.setCategory(category);
        product.setImage(productRequest.getImage());

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
    public Page<Product> getByCategoryId(Long id, Pageable pageable) {
        return productRepository.findByCategoryId(id,pageable);
    }

    @Override
    public List<Product> searchByName(String keyword) {
        return productRepository.searchProductByName(keyword);
    }

    @Override
    public List<Product> sortByAsc(String name) {
        return productRepository.findByCategoryIdAsc(name);
    }

    @Override
    public List<Product> sortByDesc(String name) {
        return productRepository.findByCategoryIdDesc(name);
    }


    @Override
    public int countByCategory(Category category) {
       return productRepository.countByCategory(category);
    }

}
