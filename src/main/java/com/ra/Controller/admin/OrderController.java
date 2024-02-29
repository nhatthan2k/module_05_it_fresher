package com.ra.Controller.admin;

import com.ra.model.entity.Orders;
import com.ra.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/admin")
public class OrderController {
    @Autowired
    private OrderService orderService;

    @GetMapping("/orders")
    public String ordersPage(Model model) {
        List<Orders> orders = orderService.findAll();
        model.addAttribute("orders", orders);
        return "/admin/orders/orders";
    }



    @GetMapping("/orders_details")
    public String ordersDetailsPage() {
        return "/admin/orders/ordersDetails";
    }
}
