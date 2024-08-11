package com.tourism.backend.repository;

import com.tourism.backend.model.Destination;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface DestinationRepository extends JpaRepository<Destination, Long> {
    // List<Destination> findByCity(String city);
    // List<Destination> findByCountry(String country);
    // List<Destination> findByCityAndCountry(String city, String country);
    // List<Destination> findByNameContainingIgnoreCase(String name);
    // List<Destination> findByLocationContainingIgnoreCase(String location);
}
