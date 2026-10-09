package com.example.saleappv1.controller;

import com.example.saleappv1.model.Category;
import com.example.saleappv1.model.Product;
import com.example.saleappv1.service.ProductService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;
import java.util.Optional;

@Controller
public class ProductController {

    private final ProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    // 1. DSHS / Danh sách Sản phẩm
    @GetMapping("/products")
    public String listProducts(
            @RequestParam(name = "categoryId", required = false) Long categoryId,
            @RequestParam(name = "keyword", required = false) String keyword,
            @RequestParam(name = "fromPrice", required = false) Double fromPrice,
            @RequestParam(name = "toPrice", required = false) Double toPrice,
            Model model) {

        List<Product> products = productService.filterProducts(categoryId, keyword, fromPrice, toPrice);
        List<Category> categories = productService.getAllCategories();

        model.addAttribute("products", products);
        model.addAttribute("categories", categories);
        model.addAttribute("selectedCategoryId", categoryId);
        model.addAttribute("keyword", keyword);
        model.addAttribute("fromPrice", fromPrice);
        model.addAttribute("toPrice", toPrice);

        return "products";
    }

    // 2. Màn hình Thêm sản phẩm mới
    @GetMapping("/products/add")
    public String showAddForm(Model model) {
        Product product = new Product();
        product.setStock(10); // Mặc định
        model.addAttribute("product", product);
        model.addAttribute("categories", productService.getAllCategories());
        model.addAttribute("pageTitle", "Thêm sản phẩm mới");
        return "product-form";
    }

    // 3. Màn hình Chỉnh sửa sản phẩm
    @GetMapping("/products/edit/{id}")
    public String showEditForm(@PathVariable("id") Long id, Model model, RedirectAttributes redirectAttributes) {
        Optional<Product> productOpt = productService.getProductById(id);
        if (productOpt.isPresent()) {
            model.addAttribute("product", productOpt.get());
            model.addAttribute("categories", productService.getAllCategories());
            model.addAttribute("pageTitle", "Chỉnh sửa sản phẩm (ID: " + id + ")");
            return "product-form";
        } else {
            redirectAttributes.addFlashAttribute("errorMessage", "Không tìm thấy sản phẩm có ID: " + id);
            return "redirect:/products";
        }
    }

    // 4. Xử lý Lưu sản phẩm (Thêm mới hoặc Cập nhật)
    @PostMapping("/products/save")
    public String saveProduct(@ModelAttribute("product") Product product, RedirectAttributes redirectAttributes) {
        boolean isNew = (product.getId() == null);
        productService.saveProduct(product);
        
        if (isNew) {
            redirectAttributes.addFlashAttribute("successMessage", "Thêm sản phẩm mới thành công!");
        } else {
            redirectAttributes.addFlashAttribute("successMessage", "Cập nhật sản phẩm thành công!");
        }
        return "redirect:/products";
    }

    // 5. Xử lý Xóa sản phẩm
    @GetMapping("/products/delete/{id}")
    public String deleteProduct(@PathVariable("id") Long id, RedirectAttributes redirectAttributes) {
        productService.deleteProductById(id);
        redirectAttributes.addFlashAttribute("successMessage", "Đã xóa sản phẩm thành công!");
        return "redirect:/products";
    }

    // 6. Chi tiết sản phẩm
    @GetMapping("/products/{id}")
    public String productDetail(@PathVariable("id") Long id, Model model) {
        Optional<Product> productOpt = productService.getProductById(id);

        if (productOpt.isEmpty()) {
            return "redirect:/products";
        }

        Product product = productOpt.get();
        Optional<Category> categoryOpt = productService.getCategoryById(product.getCategoryId());

        model.addAttribute("product", product);
        model.addAttribute("category", categoryOpt.orElse(null));
        model.addAttribute("categories", productService.getAllCategories());

        return "product-detail";
    }
}

