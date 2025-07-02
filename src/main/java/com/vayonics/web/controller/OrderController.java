package com.vayonics.web.controller;

import com.vayonics.web.model.Order;
import com.vayonics.web.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Map;

@Controller
public class OrderController {

    @Autowired
    OrderService orderService;

    @GetMapping("/admin")
    public String dashboard(Model model) {
        Map<String, Order> allOrders = orderService.getAllOrders();
        model.addAttribute("orders", allOrders); // Used for both tables
        model.addAttribute("drones", orderService.getAllDrones().keySet());
        return "admin_panel";
    }


    @PostMapping("/assign")
    public String assignDrone(@RequestParam String orderId, @RequestParam String droneId) {
        orderService.assignDroneToOrder(orderId, droneId);
        return "redirect:/admin";
    }

    @PostMapping("/complete")
    public String completeOrder(@RequestParam String orderId, @RequestParam String droneId) {
        orderService.completeOrder(orderId, droneId);
        return "redirect:/admin";
    }
}
