package com.fooddelivery.service;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.fooddelivery.dto.OrderRequest;
import com.fooddelivery.entity.Address;
import com.fooddelivery.entity.Cart;
import com.fooddelivery.entity.CartItem;
import com.fooddelivery.entity.Order;
import com.fooddelivery.entity.OrderItem;
import com.fooddelivery.entity.OrderStatus;
import com.fooddelivery.entity.Restaurant;
import com.fooddelivery.exception.ResourceNotFoundException;
import com.fooddelivery.repository.AddressRepository;
import com.fooddelivery.repository.CartRepository;
import com.fooddelivery.repository.OrderRepository;
import com.fooddelivery.repository.RestaurantRepository;

@Service
public class OrderService {
    private final OrderRepository orderRepository;
    private final CartRepository cartRepository;
    private final AddressRepository addressRepository;
    private final RestaurantRepository restaurantRepository;
    private final AuthService authService;

    public OrderService(OrderRepository orderRepository, CartRepository cartRepository, AddressRepository addressRepository,
            RestaurantRepository restaurantRepository, AuthService authService) {
        this.orderRepository = orderRepository;
        this.cartRepository = cartRepository;
        this.addressRepository = addressRepository;
        this.restaurantRepository = restaurantRepository;
        this.authService = authService;
    }

    @Transactional
    public Order create(Long userId, OrderRequest request) {
        Cart cart = cartRepository.findByUserId(userId)
            .orElseThrow(() -> new IllegalArgumentException("Cart is empty"));
        if (cart.getItems().isEmpty()) {
            throw new IllegalArgumentException("Cart is empty");
        }
        Address address = addressRepository.findById(request.addressId())
            .orElseThrow(() -> new ResourceNotFoundException("Address not found: " + request.addressId()));
        if (!address.getUser().getId().equals(userId)) {
            throw new IllegalArgumentException("Address does not belong to this user");
        }
        Restaurant restaurant = cart.getItems().get(0).getFoodItem().getRestaurant();
        if (cart.getItems().stream().anyMatch(item -> !item.getFoodItem().getRestaurant().getId().equals(restaurant.getId()))) {
            throw new IllegalArgumentException("Cart items must come from one restaurant");
        }
        Order order = new Order();
        order.setUser(authService.findUser(userId));
        order.setRestaurant(restaurantRepository.getReferenceById(restaurant.getId()));
        order.setAddress(address);
        order.setNotes(request.notes());
        BigDecimal total = BigDecimal.ZERO;
        for (CartItem cartItem : cart.getItems()) {
            OrderItem orderItem = new OrderItem();
            orderItem.setOrder(order);
            orderItem.setFoodItem(cartItem.getFoodItem());
            orderItem.setItemName(cartItem.getFoodItem().getName());
            orderItem.setQuantity(cartItem.getQuantity());
            orderItem.setUnitPrice(cartItem.getFoodItem().getPrice());
            BigDecimal subtotal = cartItem.getFoodItem().getPrice().multiply(BigDecimal.valueOf(cartItem.getQuantity()));
            orderItem.setSubtotal(subtotal);
            order.getItems().add(orderItem);
            total = total.add(subtotal);
        }
        order.setTotalAmount(total);
        Order saved = orderRepository.save(order);
        cart.getItems().clear();
        cartRepository.save(cart);
        return saved;
    }

    @Transactional(readOnly = true)
    public List<Order> forCustomer(Long userId) { return orderRepository.findByUserIdOrderByPlacedAtDesc(userId); }

    @Transactional(readOnly = true)
    public List<Order> forRestaurant(Long restaurantId) { return orderRepository.findByRestaurantIdOrderByPlacedAtDesc(restaurantId); }

    @Transactional(readOnly = true)
    public List<Order> all() { return orderRepository.findAll(); }

    @Transactional
    public Order updateStatus(Long id, OrderStatus status) {
        Order order = orderRepository.findById(id)
            .orElseThrow(() -> new ResourceNotFoundException("Order not found: " + id));
        order.setStatus(status);
        return orderRepository.save(order);
    }
}
