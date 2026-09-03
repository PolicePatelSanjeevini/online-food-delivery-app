package com.fooddelivery.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.fooddelivery.entity.Restaurant;

public interface RestaurantRepository extends JpaRepository<Restaurant, Long> {
    List<Restaurant> findByNameContainingIgnoreCaseOrCuisineTypeContainingIgnoreCase(String name, String cuisineType);
    List<Restaurant> findByOwnerId(Long ownerId);
}
