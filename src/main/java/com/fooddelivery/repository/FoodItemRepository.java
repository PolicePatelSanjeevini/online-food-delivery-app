package com.fooddelivery.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.fooddelivery.entity.FoodItem;

public interface FoodItemRepository extends JpaRepository<FoodItem, Long> {
    List<FoodItem> findByRestaurantIdAndAvailableTrue(Long restaurantId);
    List<FoodItem> findByRestaurantId(Long restaurantId);
    List<FoodItem> findByNameContainingIgnoreCaseAndAvailableTrue(String name);
}
