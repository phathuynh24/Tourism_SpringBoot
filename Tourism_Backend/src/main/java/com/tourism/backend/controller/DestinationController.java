package com.tourism.backend.controller;

import com.tourism.backend.model.Destination;
import com.tourism.backend.service.DestinationService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/destination")
public class DestinationController {

    @Autowired
    private DestinationService destinationService;

    @GetMapping("/hello")
    public String hello() {
        return "Test Hello from Destination Controller";
    }

    @PostMapping()
    public String addDestination(@RequestBody Destination destination) {
        destinationService.createDestination(destination);
        return "Destination added successfully";
    }
}
