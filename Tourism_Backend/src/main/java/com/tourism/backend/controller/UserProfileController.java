package com.tourism.backend.controller;

import com.tourism.backend.model.User;
import com.tourism.backend.service.UserService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserProfileController {

    @Autowired
    private UserService userService;

    @GetMapping("/user/profile")
    public User getUserProfile() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null || !authentication.isAuthenticated()) {
            throw new RuntimeException("Unauthorized");
        }
        String username = authentication.getName();
        return userService.findByUsername(username);
    }

    @PutMapping("/user/profile")
    public User updateUserProfile(@RequestBody User updatedUser) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null || !authentication.isAuthenticated()) {
            throw new RuntimeException("Unauthorized");
        }
        String username = authentication.getName();
        User user = userService.findByUsername(username);

        // Update the user's fields with the new values from the request
        user.setFirstName(updatedUser.getFirstName());
        user.setLastName(updatedUser.getLastName());
        user.setBio(updatedUser.getBio());
        user.setProfilePicture(updatedUser.getProfilePicture());

        // Save the updated user profile
        userService.registerUser(user); // Reusing the registerUser method to save changes

        return user;
    }
}
