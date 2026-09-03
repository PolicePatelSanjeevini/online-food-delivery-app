package com.fooddelivery.controller;

import jakarta.validation.Valid;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.fooddelivery.dto.CartItemRequest;
import com.fooddelivery.entity.Cart;
import com.fooddelivery.service.CartService;

@RestController
@RequestMapping("/api/cart")
public class CartController {
    private final CartService cartService;
    public CartController(CartService cartService) { this.cartService = cartService; }

    @GetMapping
    public Cart get(@RequestHeader("X-User-Id") Long userId) { return cartService.getOrCreate(userId); }
    @PostMapping("/items")
    public Cart add(@RequestHeader("X-User-Id") Long userId, @Valid @RequestBody CartItemRequest request) { return cartService.addItem(userId, request); }
    @PutMapping("/items/{itemId}")
    public Cart update(@RequestHeader("X-User-Id") Long userId, @PathVariable Long itemId, @RequestParam int quantity) {
        return cartService.updateItem(userId, itemId, quantity);
    }
    @DeleteMapping("/items/{itemId}")
    public Cart remove(@RequestHeader("X-User-Id") Long userId, @PathVariable Long itemId) { return cartService.removeItem(userId, itemId); }
}
