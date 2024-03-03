package com.ra.Controller.user;

import com.ra.model.dto.request.ShopingCartRequest;
import com.ra.model.entity.Product;
import com.ra.model.entity.ShopingCart;
import com.ra.sercurity.UserDetail.UserPrincipal;
import com.ra.service.ProductService;
import com.ra.service.ShopingCartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/user/cart")
public class CartController {
    @Autowired
    private ShopingCartService shopingCartService;

    @Autowired
    private ProductService productService;

    public static Long getUserId() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserPrincipal userPrincipal = (UserPrincipal) authentication.getPrincipal();
        return userPrincipal.getUsers().getId();
    }

    @GetMapping("")
    public String cartShop(Model model) {
        Long userId = getUserId();
        List<ShopingCart> shopingCarts = shopingCartService.getAll(userId);
        model.addAttribute("shopingCarts", shopingCarts);
        return "/shop/cart";
    }

    @GetMapping("/{id}")
    public String addCart(@PathVariable("id") Long id) {
        Long userId = getUserId();
        ShopingCart shopingCart = shopingCartService.findByProductId(userId, id);
        if (shopingCart == null) {
            ShopingCartRequest shopingCartRequest = new ShopingCartRequest();
            shopingCartRequest.setProductId(id);
            shopingCartRequest.setQuantity(1);
            shopingCartService.add(shopingCartRequest, userId);
        } else {
            shopingCart.setQuantity(shopingCart.getQuantity() + 1);
            shopingCartService.save(shopingCart);
        }
        return "redirect:/user/cart";
    }

    @PostMapping("/add-cart/{id}")
    public String createCart(@PathVariable("id") Long id, @ModelAttribute("shopingCartRequest") ShopingCartRequest shopingCartRequest) {
        Long userId = getUserId();
        ShopingCart shopingCart = shopingCartService.findByProductId(userId, id);
        if (shopingCart == null) {
            shopingCartRequest.setProductId(id);
            shopingCartService.add(shopingCartRequest, userId);
        } else {
            shopingCart.setQuantity(shopingCart.getQuantity() + shopingCartRequest.getQuantity());
            shopingCartService.save(shopingCart);
        }
        return "redirect:/user/cart";
    }

    @GetMapping("/delete/{id}")
    public String deleteCart(@PathVariable("id") Long id) {
        Long userId = getUserId();
        ShopingCart shopingCart = shopingCartService.findByProductId(userId, id);
        if (shopingCart != null) {
            shopingCartService.delete(shopingCart.getId());
        }
        return "/shop/cart";
    }

}
