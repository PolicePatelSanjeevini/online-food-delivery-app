package com.fooddelivery.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.fooddelivery.entity.Order;
import com.fooddelivery.entity.OrderStatus;

public interface OrderRepository extends JpaRepository<Order, Long> {
    List<Order> findByUserIdOrderByPlacedAtDesc(Long userId);
    List<Order> findByRestaurantIdOrderByPlacedAtDesc(Long restaurantId);
    long countByStatus(OrderStatus status);
}
