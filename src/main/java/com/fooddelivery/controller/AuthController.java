package com.fooddelivery.controller;

import java.util.Map;

import jakarta.validation.Valid;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.fooddelivery.dto.LoginRequest;
import com.fooddelivery.dto.OwnerRegisterRequest;
import com.fooddelivery.dto.RegisterRequest;
import com.fooddelivery.service.AuthService;

@RestController
@RequestMapping("/api/auth")
public class AuthController {
    private final AuthService authService;

    public AuthController(AuthService authService) { this.authService = authService; }

    @PostMapping("/register")
    @ResponseStatus(HttpStatus.CREATED)
    public Map<String, Object> register(@Valid @RequestBody RegisterRequest request) { return authService.register(request); }

    @PostMapping("/owner/register")
    @ResponseStatus(HttpStatus.CREATED)
    public Map<String, Object> registerOwner(@Valid @RequestBody OwnerRegisterRequest request) {
        return authService.registerOwner(request);
    }

    @PostMapping("/login")
    public Map<String, Object> login(@Valid @RequestBody LoginRequest request) { return authService.login(request); }
}
