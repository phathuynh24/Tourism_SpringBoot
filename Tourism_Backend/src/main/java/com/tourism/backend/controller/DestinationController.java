package com.tourism.backend.controller;

import com.tourism.backend.constants.ApiPaths;
import com.tourism.backend.model.Destination;
import com.tourism.backend.repository.DestinationRepository;

import org.springframework.web.bind.annotation.*;

import java.util.List;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@RestController
@RequestMapping(ApiPaths.DESTINATIONS)
public class DestinationController {

    private final DestinationRepository destinationRepository;

    public DestinationController(DestinationRepository destinationRepository) {
        this.destinationRepository = destinationRepository;
    }

    @PostMapping
    public Destination createDestination(@RequestBody Destination destination) {
        return destinationRepository.save(destination);
    }

    @GetMapping
    public List<Destination> getAllDestinations() {
        return destinationRepository.findAll();
    }

    @GetMapping("/{id}")
    public Destination getDestination(@PathVariable Long id) {
        return destinationRepository.findById(id).orElseThrow(() -> new RuntimeException("Destination not found"));
    }

    @PutMapping("/{id}")
    public Destination updateDestination(@PathVariable Long id, @RequestBody Destination updatedDestination) {
        Destination destination = destinationRepository.findById(id).orElseThrow(() -> new RuntimeException("Destination not found"));
        destination.setName(updatedDestination.getName());
        destination.setLocation(updatedDestination.getLocation());
        destination.setDescription(updatedDestination.getDescription());
        return destinationRepository.save(destination);
    }

    @DeleteMapping("/{id}")
    public void deleteDestination(@PathVariable Long id) {
        destinationRepository.deleteById(id);
    }
    
}
