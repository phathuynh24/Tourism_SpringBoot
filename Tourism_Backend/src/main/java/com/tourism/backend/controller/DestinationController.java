package com.tourism.backend.controller;

import com.tourism.backend.model.Destination;
import com.tourism.backend.repository.DestinationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/destination")
public class DestinationController {

    @Autowired
    private DestinationRepository destinationRepository;

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
