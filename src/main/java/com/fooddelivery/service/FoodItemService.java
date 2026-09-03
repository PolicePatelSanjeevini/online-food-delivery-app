package com.fooddelivery.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.fooddelivery.dto.FoodItemRequest;
import com.fooddelivery.entity.FoodItem;
import com.fooddelivery.exception.ResourceNotFoundException;
import com.fooddelivery.repository.CategoryRepository;
import com.fooddelivery.repository.FoodItemRepository;

@Service
public class FoodItemService {
    private final FoodItemRepository foodItemRepository;
    private final RestaurantService restaurantService;
    private final CategoryRepository categoryRepository;

    public FoodItemService(FoodItemRepository foodItemRepository, RestaurantService restaurantService,
            CategoryRepository categoryRepository) {
        this.foodItemRepository = foodItemRepository;
        this.restaurantService = restaurantService;
        this.categoryRepository = categoryRepository;
    }

    @Transactional(readOnly = true)
    public List<FoodItem> findByRestaurant(Long restaurantId, boolean availableOnly) {
        return availableOnly ? foodItemRepository.findByRestaurantIdAndAvailableTrue(restaurantId)
            : foodItemRepository.findByRestaurantId(restaurantId);
    }

    @Transactional(readOnly = true)
    public List<FoodItem> search(String name) {
        return foodItemRepository.findByNameContainingIgnoreCaseAndAvailableTrue(name == null ? "" : name);
    }

    @Transactional
    public FoodItem create(FoodItemRequest request) {
        FoodItem item = new FoodItem();
        copy(request, item);
        return foodItemRepository.save(item);
    }

    @Transactional
    public FoodItem update(Long id, FoodItemRequest request) {
        FoodItem item = foodItemRepository.findById(id)
            .orElseThrow(() -> new ResourceNotFoundException("Food item not found: " + id));
        copy(request, item);
        return foodItemRepository.save(item);
    }

    public void delete(Long id) {
        foodItemRepository.delete(foodItemRepository.findById(id)
            .orElseThrow(() -> new ResourceNotFoundException("Food item not found: " + id)));
    }

    private void copy(FoodItemRequest request, FoodItem item) {
        item.setRestaurant(restaurantService.findById(request.restaurantId()));
        item.setCategory(request.categoryId() == null ? null : categoryRepository.findById(request.categoryId())
            .orElseThrow(() -> new ResourceNotFoundException("Category not found: " + request.categoryId())));
        item.setName(request.name());
        item.setDescription(request.description());
        item.setPrice(request.price());
        item.setImageUrl(request.imageUrl());
        item.setAvailable(request.available() == null || request.available());
    }
}
