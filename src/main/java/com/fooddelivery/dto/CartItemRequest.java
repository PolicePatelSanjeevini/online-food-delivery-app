package com.fooddelivery.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

public record CartItemRequest(@NotNull Long foodItemId, @Positive int quantity) {}
