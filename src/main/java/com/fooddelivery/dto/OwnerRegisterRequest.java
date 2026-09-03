package com.fooddelivery.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record OwnerRegisterRequest(
    @NotBlank @Size(max = 100) String ownerName,
    @NotBlank @Email @Size(max = 150) String email,
    @NotBlank @Size(min = 8, max = 100) String password,
    @NotBlank @Size(max = 20) String phone,
    @NotBlank @Size(max = 150) String restaurantName,
    @Size(max = 500) String restaurantDescription,
    @NotBlank @Size(max = 100) String cuisineType,
    @Size(max = 500) String restaurantImage,
    @NotBlank @Size(max = 255) String address
) {}