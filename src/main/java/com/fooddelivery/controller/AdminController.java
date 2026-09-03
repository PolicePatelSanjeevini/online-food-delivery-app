package com.fooddelivery.controller;

import java.util.Map;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fooddelivery.entity.OrderStatus;
import com.fooddelivery.repository.FoodItemRepository;
import com.fooddelivery.repository.OrderRepository;
import com.fooddelivery.repository.RestaurantRepository;
import com.fooddelivery.repository.UserRepository;

@RestController
@RequestMapping("/api/admin")
public class AdminController {
    private final UserRepository userRepository;
    private final RestaurantRepository restaurantRepository;
    private final FoodItemRepository foodItemRepository;
    private final OrderRepository orderRepository;

    public AdminController(UserRepository userRepository, RestaurantRepository restaurantRepository,
            FoodItemRepository foodItemRepository, OrderRepository orderRepository) {
        this.userRepository = userRepository;
        this.restaurantRepository = restaurantRepository;
        this.foodItemRepository = foodItemRepository;
        this.orderRepository = orderRepository;
    }

    @GetMapping("/stats")
    public Map<String, Long> statistics() {
        return Map.of(
            "users", userRepository.count(),
            "restaurants", restaurantRepository.count(),
            "foodItems", foodItemRepository.count(),
            "orders", orderRepository.count(),
            "placedOrders", orderRepository.countByStatus(OrderStatus.PLACED),
            "deliveredOrders", orderRepository.countByStatus(OrderStatus.DELIVERED)
        );
    }
}
