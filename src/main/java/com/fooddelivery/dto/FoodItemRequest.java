package com.fooddelivery.dto;

import java.math.BigDecimal;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record FoodItemRequest(
    @NotNull Long restaurantId,
    Long categoryId,
    @NotBlank @Size(max = 150) String name,
    @Size(max = 500) String description,
    @NotNull @DecimalMin("0.00") BigDecimal price,
    @Size(max = 500) String imageUrl,
    Boolean available
) {}
