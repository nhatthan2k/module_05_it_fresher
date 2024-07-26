package com.ra.Controller;

import com.ra.model.dto.request.UserRegister;
import com.ra.model.entity.Users;
import com.ra.service.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class AuthController {
    @Autowired
    private UserService userService;

    @GetMapping("/login")
    public String Login() {
        return "auth/login";
    }

    @GetMapping("/register")
    public String register(Model model) {
        Users user = new Users();
        user.setStatus(true);
        model.addAttribute("user", user);
        return "auth/register";
    }

    @PostMapping("/register")
    public String save(@Valid @ModelAttribute("user") UserRegister userRegister, BindingResult bindingResult, Model model) {
        if (bindingResult.hasErrors()) {
            return "auth/register";
        }

        try {
            userService.handleRegister(userRegister);
        } catch (IllegalArgumentException e) {
            model.addAttribute("err", e.getMessage());
            return "auth/register";
        }

        return "redirect:/login";
    }

}
