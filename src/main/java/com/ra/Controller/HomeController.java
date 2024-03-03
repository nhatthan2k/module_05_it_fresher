package com.ra.Controller;

import com.ra.model.dto.request.ShopingCartRequest;
import com.ra.model.entity.Category;
import com.ra.model.entity.Product;
import com.ra.service.CategoryService;
import com.ra.service.ProductService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@Controller
public class HomeController {
    @Autowired
    private CategoryService categoryService;

    @Autowired
    private ProductService productService;

    @GetMapping("/")
    public String Home(HttpSession session) {
        List<Category> categories = categoryService.getbyStatus();
        session.setAttribute("categories", categories);
        return "/index";
    }

    @GetMapping("/categories/{id}")
    public String CategoryShop(Model model,@PathVariable("id") Long id) {
        List<Product> products = productService.getByCategoryId(id);
        model.addAttribute("products", products);
        return "/shop/shop-4-column";
    }

    @GetMapping("/products/{id}")
    public String singleProduct(Model model, @PathVariable("id") Long id) {
        ShopingCartRequest shopingCartRequest = new ShopingCartRequest();
        shopingCartRequest.setQuantity(1);
        Product product = productService.findById(id);
        model.addAttribute("product", product);
        model.addAttribute("shopingCartRequest", shopingCartRequest);
        return "/shop/single-product";
    }
}
