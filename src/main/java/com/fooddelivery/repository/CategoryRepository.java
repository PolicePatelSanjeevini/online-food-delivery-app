package com.fooddelivery.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.fooddelivery.entity.Category;

public interface CategoryRepository extends JpaRepository<Category, Long> {
}
