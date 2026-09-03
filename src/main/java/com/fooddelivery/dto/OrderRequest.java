package com.fooddelivery.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record OrderRequest(@NotNull Long addressId, @Size(max = 500) String notes) {}
