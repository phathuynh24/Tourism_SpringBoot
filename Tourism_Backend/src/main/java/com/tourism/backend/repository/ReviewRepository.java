package com.tourism.backend.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.tourism.backend.model.Review;

import java.util.List;

public interface ReviewRepository extends JpaRepository<Review, Long> {
    List<Review> findByDestinationId(Long destinationId);
    List<Review> findByUserId(Long userId);
    Review findByDestinationIdAndUserId(Long destinationId, Long userId);
}
