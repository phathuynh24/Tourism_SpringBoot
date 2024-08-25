package com.tourism.backend.service;

import com.tourism.backend.model.Destination;
import com.tourism.backend.repository.DestinationRepository;

import org.springframework.stereotype.Service;

@Service
public class DestinationService {
    
    private final DestinationRepository destinationRepository;

    public DestinationService(DestinationRepository destinationRepository) {
        this.destinationRepository = destinationRepository;
    }

    public Destination createDestination(Destination destination) {
        return destinationRepository.save(destination);
    }

    public void deleteDestination(Long id) {
        destinationRepository.deleteById(id);
    }

}
