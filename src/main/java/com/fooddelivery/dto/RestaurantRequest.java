package com.fooddelivery.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record RestaurantRequest(
    Long ownerId,
    @NotBlank @Size(max = 150) String name,
    @Size(max = 500) String description,
    @NotBlank @Size(max = 255) String address,
    @Size(max = 20) String phone,
    @Size(max = 100) String cuisineType,
    @Size(max = 500) String imageUrl
) {}
