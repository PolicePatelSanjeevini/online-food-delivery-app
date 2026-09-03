package com.fooddelivery.controller;

import java.util.List;

import jakarta.validation.Valid;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.fooddelivery.dto.OrderRequest;
import com.fooddelivery.entity.Order;
import com.fooddelivery.entity.OrderStatus;
import com.fooddelivery.service.OrderService;

@RestController
@RequestMapping("/api/orders")
public class OrderController {
    private final OrderService orderService;
    public OrderController(OrderService orderService) { this.orderService = orderService; }

    @GetMapping
    public List<Order> customerOrders(@RequestHeader("X-User-Id") Long userId) { return orderService.forCustomer(userId); }
    @GetMapping("/restaurant/{restaurantId}")
    public List<Order> restaurantOrders(@PathVariable Long restaurantId) { return orderService.forRestaurant(restaurantId); }
    @GetMapping("/all")
    public List<Order> allOrders() { return orderService.all(); }
    @PostMapping
    public Order create(@RequestHeader("X-User-Id") Long userId, @Valid @RequestBody OrderRequest request) { return orderService.create(userId, request); }
    @PatchMapping("/{id}/status")
    public Order updateStatus(@PathVariable Long id, @RequestParam OrderStatus status) { return orderService.updateStatus(id, status); }
}
