package com.fooddelivery.controller;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Map;

import org.junit.jupiter.api.Test;

class HealthControllerTest {
    @Test
    void returnsApplicationHealth() {
        Map<String, String> response = new HealthController().health();
        assertThat(response).containsEntry("status", "UP");
        assertThat(response).containsEntry("application", "online-food-delivery");
    }
}
