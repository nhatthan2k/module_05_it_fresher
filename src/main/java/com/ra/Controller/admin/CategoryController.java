package com.ra.Controller.admin;

import com.ra.model.entity.Category;
import com.ra.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/admin")
public class CategoryController {
    @Autowired
    private CategoryService categoryService;

    @GetMapping("/category")
    public String categoryPage() {
        return "/admin/category/category";
    }

//  add Category
    @GetMapping("/category/add")
    public String add(Model model) {
        Category category = new Category();
        model.addAttribute("category", category);
        return "/admin/category/add-category";
    }

    @PostMapping("category/add-category")
    public String save(@ModelAttribute("category") Category category) {
        categoryService.save(category);
        return "redirect:/admin/category";
    }

//  edit Category
//    @GetMapping("/category/edit")
//    public String edit() {
//
//    }
}
