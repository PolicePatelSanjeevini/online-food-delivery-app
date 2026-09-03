package com.fooddelivery.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.fooddelivery.dto.RestaurantRequest;
import com.fooddelivery.entity.Restaurant;
import com.fooddelivery.entity.Role;
import com.fooddelivery.exception.ResourceNotFoundException;
import com.fooddelivery.repository.RestaurantRepository;

@Service
public class RestaurantService {
    private final RestaurantRepository restaurantRepository;
    private final AuthService authService;

    public RestaurantService(RestaurantRepository restaurantRepository, AuthService authService) {
        this.restaurantRepository = restaurantRepository;
        this.authService = authService;
    }

    @Transactional(readOnly = true)
    public List<Restaurant> findAll(String search) {
        return search == null || search.isBlank() ? restaurantRepository.findAll()
            : restaurantRepository.findByNameContainingIgnoreCaseOrCuisineTypeContainingIgnoreCase(search, search);
    }

    @Transactional(readOnly = true)
    public Restaurant findById(Long id) {
        return restaurantRepository.findById(id)
            .orElseThrow(() -> new ResourceNotFoundException("Restaurant not found: " + id));
    }

    @Transactional(readOnly = true)
    public List<Restaurant> findByOwner(Long ownerId) { return restaurantRepository.findByOwnerId(ownerId); }

    @Transactional
    public Restaurant create(RestaurantRequest request) {
        Restaurant restaurant = new Restaurant();
        restaurant.setOwner(authService.findUser(request.ownerId()));
        if (restaurant.getOwner().getRole() != Role.RESTAURANT_OWNER && restaurant.getOwner().getRole() != Role.ADMIN) {
            throw new IllegalArgumentException("Restaurant owner must have an owner or admin role");
        }
        copy(request, restaurant);
        return restaurantRepository.save(restaurant);
    }

    @Transactional
    public Restaurant update(Long id, RestaurantRequest request) {
        Restaurant restaurant = findById(id);
        copy(request, restaurant);
        return restaurantRepository.save(restaurant);
    }

    public void delete(Long id) {
        restaurantRepository.delete(findById(id));
    }

    private void copy(RestaurantRequest request, Restaurant restaurant) {
        restaurant.setName(request.name());
        restaurant.setDescription(request.description());
        restaurant.setAddress(request.address());
        restaurant.setPhone(request.phone());
        restaurant.setCuisineType(request.cuisineType());
        restaurant.setImageUrl(request.imageUrl());
    }
}
