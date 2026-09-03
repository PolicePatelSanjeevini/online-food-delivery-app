package com.fooddelivery.service;

import java.util.Map;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.fooddelivery.dto.LoginRequest;
import com.fooddelivery.dto.RegisterRequest;
import com.fooddelivery.dto.OwnerRegisterRequest;
import com.fooddelivery.entity.Role;
import com.fooddelivery.entity.Restaurant;
import com.fooddelivery.entity.User;
import com.fooddelivery.exception.ResourceNotFoundException;
import com.fooddelivery.repository.RestaurantRepository;
import com.fooddelivery.repository.UserRepository;

@Service
public class AuthService {
    private final UserRepository userRepository;
    private final RestaurantRepository restaurantRepository;
    private final PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    public AuthService(UserRepository userRepository, RestaurantRepository restaurantRepository) {
        this.userRepository = userRepository;
        this.restaurantRepository = restaurantRepository;
    }

    @Transactional
    public Map<String, Object> register(RegisterRequest request) {
        if (userRepository.existsByEmailIgnoreCase(request.email())) {
            throw new IllegalArgumentException("Email is already registered");
        }
        User user = new User();
        user.setFullName(request.fullName());
        user.setEmail(request.email().trim().toLowerCase());
        user.setPassword(passwordEncoder.encode(request.password()));
        user.setPhone(request.phone());
        user.setRole(Role.CUSTOMER);
        return userResponse(userRepository.save(user));
    }

    @Transactional(readOnly = true)
    public Map<String, Object> login(LoginRequest request) {
        User user = userRepository.findByEmailIgnoreCase(request.email())
            .filter(User::isActive)
            .orElseThrow(() -> new IllegalArgumentException("Invalid email or password"));
        if (!passwordEncoder.matches(request.password(), user.getPassword())) {
            throw new IllegalArgumentException("Invalid email or password");
        }
        return userResponse(user);
    }

    @Transactional
    public Map<String, Object> registerOwner(OwnerRegisterRequest request) {
        if (userRepository.existsByEmailIgnoreCase(request.email())) {
            throw new IllegalArgumentException("Email is already registered");
        }
        User owner = new User();
        owner.setFullName(request.ownerName());
        owner.setEmail(request.email().trim().toLowerCase());
        owner.setPassword(passwordEncoder.encode(request.password()));
        owner.setPhone(request.phone());
        owner.setRole(Role.RESTAURANT_OWNER);
        User savedOwner = userRepository.save(owner);

        Restaurant restaurant = new Restaurant();
        restaurant.setOwner(savedOwner);
        restaurant.setName(request.restaurantName());
        restaurant.setDescription(request.restaurantDescription());
        restaurant.setCuisineType(request.cuisineType());
        restaurant.setImageUrl(request.restaurantImage());
        restaurant.setAddress(request.address());
        restaurant.setPhone(request.phone());
        restaurantRepository.save(restaurant);
        return userResponse(savedOwner);
    }

    @Transactional(readOnly = true)
    public User findUser(Long id) {
        return userRepository.findById(id)
            .orElseThrow(() -> new ResourceNotFoundException("User not found: " + id));
    }

    public Map<String, Object> userResponse(User user) {
        return Map.of("id", user.getId(), "fullName", user.getFullName(), "email", user.getEmail(),
            "phone", user.getPhone() == null ? "" : user.getPhone(), "role", user.getRole());
    }
}
