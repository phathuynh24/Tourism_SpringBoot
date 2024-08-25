package com.tourism.backend.repository;

import com.tourism.backend.model.Destination;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface DestinationRepository extends JpaRepository<Destination, Long> {
    List<Destination> findByNameContaining(String name);
    List<Destination> findByLocationContaining(String location);
}
