package com.fooddelivery.controller;

import java.util.List;

import jakarta.validation.Valid;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.fooddelivery.dto.FoodItemRequest;
import com.fooddelivery.entity.FoodItem;
import com.fooddelivery.service.FoodItemService;

@RestController
@CrossOrigin
@RequestMapping("/api/food-items")
public class FoodItemController {
    private final FoodItemService foodItemService;
    public FoodItemController(FoodItemService foodItemService) { this.foodItemService = foodItemService; }

    @GetMapping
    public List<FoodItem> byRestaurant(@RequestParam Long restaurantId, @RequestParam(defaultValue = "true") boolean availableOnly) {
        return foodItemService.findByRestaurant(restaurantId, availableOnly);
    }
    @GetMapping("/search")
    public List<FoodItem> search(@RequestParam String name) { return foodItemService.search(name); }
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public FoodItem create(@Valid @RequestBody FoodItemRequest request) { return foodItemService.create(request); }
    @PutMapping("/{id}")
    public FoodItem update(@PathVariable Long id, @Valid @RequestBody FoodItemRequest request) { return foodItemService.update(id, request); }
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Long id) { foodItemService.delete(id); }
}
