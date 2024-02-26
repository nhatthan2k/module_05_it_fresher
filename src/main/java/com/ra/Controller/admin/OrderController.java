package com.ra.Controller.admin;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/admin")
public class OrderController {
    @GetMapping("/orders")
    public String ordersPage() {
        return "/admin/orders/orders";
    }

    @GetMapping("/orders_details")
    public String ordersDetailsPage() {
        return "/admin/orders/ordersDetails";
    }
}
