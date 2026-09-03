package com.fooddelivery.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.fooddelivery.dto.CartItemRequest;
import com.fooddelivery.entity.Cart;
import com.fooddelivery.entity.CartItem;
import com.fooddelivery.entity.FoodItem;
import com.fooddelivery.exception.ResourceNotFoundException;
import com.fooddelivery.repository.CartRepository;
import com.fooddelivery.repository.FoodItemRepository;

@Service
public class CartService {
    private final CartRepository cartRepository;
    private final FoodItemRepository foodItemRepository;
    private final AuthService authService;

    public CartService(CartRepository cartRepository, FoodItemRepository foodItemRepository, AuthService authService) {
        this.cartRepository = cartRepository;
        this.foodItemRepository = foodItemRepository;
        this.authService = authService;
    }

    @Transactional
    public Cart getOrCreate(Long userId) {
        return cartRepository.findByUserId(userId).orElseGet(() -> {
            Cart cart = new Cart();
            cart.setUser(authService.findUser(userId));
            return cartRepository.save(cart);
        });
    }

    @Transactional
    public Cart addItem(Long userId, CartItemRequest request) {
        Cart cart = getOrCreate(userId);
        FoodItem foodItem = foodItemRepository.findById(request.foodItemId())
            .orElseThrow(() -> new ResourceNotFoundException("Food item not found: " + request.foodItemId()));
        if (!foodItem.isAvailable()) {
            throw new IllegalArgumentException("Food item is not currently available");
        }
        CartItem existing = cart.getItems().stream()
            .filter(item -> item.getFoodItem().getId().equals(foodItem.getId()))
            .findFirst().orElse(null);
        if (existing == null) {
            CartItem item = new CartItem();
            item.setCart(cart);
            item.setFoodItem(foodItem);
            item.setQuantity(request.quantity());
            cart.getItems().add(item);
        } else {
            existing.setQuantity(existing.getQuantity() + request.quantity());
        }
        return cartRepository.save(cart);
    }

    @Transactional
    public Cart updateItem(Long userId, Long itemId, int quantity) {
        if (quantity < 1) {
            throw new IllegalArgumentException("Quantity must be at least 1");
        }
        Cart cart = getOrCreate(userId);
        CartItem item = cart.getItems().stream().filter(current -> current.getId().equals(itemId))
            .findFirst().orElseThrow(() -> new ResourceNotFoundException("Cart item not found: " + itemId));
        item.setQuantity(quantity);
        return cartRepository.save(cart);
    }

    @Transactional
    public Cart removeItem(Long userId, Long itemId) {
        Cart cart = getOrCreate(userId);
        boolean removed = cart.getItems().removeIf(item -> item.getId().equals(itemId));
        if (!removed) {
            throw new ResourceNotFoundException("Cart item not found: " + itemId);
        }
        return cartRepository.save(cart);
    }
}
