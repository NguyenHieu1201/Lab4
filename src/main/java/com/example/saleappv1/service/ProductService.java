package com.example.saleappv1.service;

import com.example.saleappv1.model.Category;
import com.example.saleappv1.model.Product;
import com.example.saleappv1.repository.CategoryRepository;
import com.example.saleappv1.repository.ProductRepository;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.annotation.PostConstruct;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;

import java.io.InputStream;
import java.util.List;
import java.util.Optional;

@Service
public class ProductService {

    private final CategoryRepository categoryRepository;
    private final ProductRepository productRepository;

    public ProductService(CategoryRepository categoryRepository, ProductRepository productRepository) {
        this.categoryRepository = categoryRepository;
        this.productRepository = productRepository;
    }

    @PostConstruct
    public void initData() {
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            if (categoryRepository.count() == 0) {
                InputStream categoriesStream = new ClassPathResource("data/categories.json").getInputStream();
                List<Category> categories = objectMapper.readValue(categoriesStream, new TypeReference<List<Category>>() {});
                categoryRepository.saveAll(categories);
                System.out.println("Seeded " + categories.size() + " categories to MySQL database.");
            }

            if (productRepository.count() == 0) {
                InputStream productsStream = new ClassPathResource("data/products.json").getInputStream();
                List<Product> products = objectMapper.readValue(productsStream, new TypeReference<List<Product>>() {});
                productRepository.saveAll(products);
                System.out.println("Seeded " + products.size() + " products to MySQL database.");
            }
        } catch (Exception e) {
            System.err.println("Error initializing/seeding JSON data to MySQL: " + e.getMessage());
            e.printStackTrace();
        }
    }

    public Product saveProduct(Product product) {
        return productRepository.save(product);
    }

    public void deleteProductById(Long id) {
        if (id != null) {
            productRepository.deleteById(id);
        }
    }

    public List<Category> getAllCategories() {
        return categoryRepository.findAll();
    }

    public Optional<Category> getCategoryById(Long id) {
        if (id == null) return Optional.empty();
        return categoryRepository.findById(id);
    }

    public List<Product> getAllProducts() {
        return productRepository.findAll();
    }

    public Optional<Product> getProductById(Long id) {
        if (id == null) return Optional.empty();
        return productRepository.findById(id);
    }

    public List<Product> getProductsByCategoryId(Long categoryId) {
        if (categoryId == null) return productRepository.findAll();
        return productRepository.findByCategoryId(categoryId);
    }

    public List<Product> searchProducts(String keyword) {
        return filterProducts(null, keyword, null, null);
    }

    public List<Product> filterProducts(Long categoryId, String keyword, Double fromPrice, Double toPrice) {
        return productRepository.filterProducts(categoryId, keyword, fromPrice, toPrice);
    }

    public List<Product> filterProducts(Long categoryId, String keyword) {
        return filterProducts(categoryId, keyword, null, null);
    }

    public List<Product> getFeaturedProducts() {
        return productRepository.findByFeaturedTrue();
    }
}

