package com.ra.Controller;

import com.ra.model.dto.request.ShopingCartRequest;
import com.ra.model.dto.request.ShoppingCartItemRequest;
import com.ra.model.entity.Category;
import com.ra.model.entity.Product;
import com.ra.service.CategoryService;
import com.ra.service.ProductService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
public class HomeController {
    @Autowired
    private CategoryService categoryService;

    @Autowired
    private ProductService productService;

    @GetMapping("/")
    public String Home(HttpSession session, Model model,
        @RequestParam(defaultValue = "12", name = "limit") int limit,
        @RequestParam(defaultValue = "0", name = "page") int page,
        @RequestParam(defaultValue = "id", name = "sort") String sort,
        @RequestParam(defaultValue = "asc", name = "order") String order
    ) {
        Pageable pageable;
        if (order.equals("asc")) {
            pageable = PageRequest.of(page, limit, Sort.by(sort).ascending());
        } else {
            pageable = PageRequest.of(page, limit, Sort.by(sort).descending());
        }
        Page<Product> products = productService.getByCategoryStatus(pageable, true);

        model.addAttribute("products", products);
        List<Category> categories = categoryService.getbyStatus();
        session.setAttribute("categories", categories);
        return "/index";
    }

    @GetMapping("/categories/{id}")
    public String CategoryShop(Model model, @PathVariable("id") Long id,
                               @RequestParam(defaultValue = "5", name = "limit") int limit,
                               @RequestParam(defaultValue = "0", name = "page") int page,
                               @RequestParam(defaultValue = "name", name = "sort") String sort,
                               @RequestParam(defaultValue = "asc", name = "order") String order) {
        Pageable pageable;
        if ("price_desc".equals(sort)) {
            pageable = PageRequest.of(page, limit, Sort.by("price").descending());
        } else if ("price".equals(sort)) {
            pageable = PageRequest.of(page, limit, Sort.by("price").ascending());
        } else if ("name_desc".equals(sort)) {
            pageable = PageRequest.of(page, limit, Sort.by("name").descending());
        } else {
            pageable = PageRequest.of(page, limit, Sort.by("name").ascending());
        }

        Page<Product> productsPage = productService.getByCategoryId(id, pageable);
        model.addAttribute("products", productsPage.getContent());
        model.addAttribute("totalPages", productsPage.getTotalPages());
        model.addAttribute("currentPage", page);
        model.addAttribute("id", id);
        model.addAttribute("order", order);
        model.addAttribute("limit", limit);
        model.addAttribute("sort", sort);
        return "/shop/shop-4-column";
    }


    @GetMapping("/products/{id}")
    public String singleProduct(Model model, @PathVariable("id") Long id) {
        ShoppingCartItemRequest shopingCartRequest = new ShoppingCartItemRequest();
        shopingCartRequest.setQuantity(1);
        Product product = productService.findById(id);
        List<Product> relatedProducts = productService.getByCategoryId(product.getCategory().getId());
        relatedProducts.remove(product);
        int count = relatedProducts.size();
        model.addAttribute("product", product);
        model.addAttribute("shopingCartRequest", shopingCartRequest);
        model.addAttribute("relatedProducts", relatedProducts);
        model.addAttribute("count", count);
        return "/shop/single-product";
    }

    @GetMapping("/search")
    public String searchProduct(Model model ,@RequestParam("keyword") String query) {
        List<Product> products = productService.searchByName(query);
        model.addAttribute("products", products);
        return "/shop/shop-4-column";
    }
}
