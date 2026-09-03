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

import com.fooddelivery.dto.RestaurantRequest;
import com.fooddelivery.entity.Restaurant;
import com.fooddelivery.service.RestaurantService;

@RestController
@CrossOrigin
@RequestMapping("/api/restaurants")
public class RestaurantController {
    private final RestaurantService restaurantService;
    public RestaurantController(RestaurantService restaurantService) { this.restaurantService = restaurantService; }

    @GetMapping
    public List<Restaurant> list(@RequestParam(required = false) String search) { return restaurantService.findAll(search); }
    @GetMapping("/{id}")
    public Restaurant get(@PathVariable Long id) { return restaurantService.findById(id); }
    @GetMapping("/owner/{ownerId}")
    public List<Restaurant> byOwner(@PathVariable Long ownerId) { return restaurantService.findByOwner(ownerId); }
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Restaurant create(@Valid @RequestBody RestaurantRequest request) { return restaurantService.create(request); }
    @PutMapping("/{id}")
    public Restaurant update(@PathVariable Long id, @Valid @RequestBody RestaurantRequest request) { return restaurantService.update(id, request); }
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Long id) { restaurantService.delete(id); }
}
