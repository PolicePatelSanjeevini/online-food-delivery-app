package com.fooddelivery.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.fooddelivery.dto.AddressRequest;
import com.fooddelivery.entity.Address;
import com.fooddelivery.entity.User;
import com.fooddelivery.exception.ResourceNotFoundException;
import com.fooddelivery.repository.AddressRepository;

@Service
public class AddressService {
    private final AddressRepository addressRepository;
    private final AuthService authService;

    public AddressService(AddressRepository addressRepository, AuthService authService) {
        this.addressRepository = addressRepository;
        this.authService = authService;
    }

    public List<Address> findForUser(Long userId) {
        return addressRepository.findByUserId(userId);
    }

    @Transactional
    public Address create(Long userId, AddressRequest request) {
        User user = authService.findUser(userId);
        if (Boolean.TRUE.equals(request.defaultAddress())) {
            addressRepository.findByUserId(userId).forEach(address -> address.setDefaultAddress(false));
        }
        Address address = new Address();
        address.setUser(user);
        address.setLabel(request.label() == null || request.label().isBlank() ? "Home" : request.label());
        address.setAddressLine(request.addressLine());
        address.setCity(request.city());
        address.setState(request.state());
        address.setPostalCode(request.postalCode());
        address.setDefaultAddress(Boolean.TRUE.equals(request.defaultAddress()));
        return addressRepository.save(address);
    }

    @Transactional
    public void delete(Long userId, Long addressId) {
        Address address = addressRepository.findById(addressId)
            .orElseThrow(() -> new ResourceNotFoundException("Address not found: " + addressId));
        if (!address.getUser().getId().equals(userId)) {
            throw new IllegalArgumentException("Address does not belong to this user");
        }
        addressRepository.delete(address);
    }
}
