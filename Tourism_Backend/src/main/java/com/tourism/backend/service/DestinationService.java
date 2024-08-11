package com.tourism.backend.service;

import com.tourism.backend.model.Destination;
import com.tourism.backend.repository.DestinationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class DestinationService {
    
    @Autowired
    private DestinationRepository destinationRepository;

    public Destination createDestination(Destination destination) {
        return destinationRepository.save(destination);
    }

    // public Destination updateDestination(Long id, Destination updatedDestination) {
    //     Optional<Destination> existingDestinationOpt = destinationRepository.findById(id);
    //     if (existingDestinationOpt.isPresent()) {
    //         Destination existingDestination = existingDestinationOpt.get();
    //         existingDestination.setName(updatedDestination.getName());
    //         existingDestination.setLocation(updatedDestination.getLocation());
    //         existingDestination.setDescription(updatedDestination.getDescription());
    //         existingDestination.setImageUrl(updatedDestination.getImageUrl());

    //         return destinationRepository.save(existingDestination);
    //     } else {
    //         throw new RuntimeException("Destination not found with id " + id);
    //     }
    // }

    public void deleteDestination(Long id) {
        destinationRepository.deleteById(id);
    }

}
