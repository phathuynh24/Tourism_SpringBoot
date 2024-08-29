package com.tourism.backend.controller;

import com.tourism.backend.constants.ApiPaths;
import com.tourism.backend.dto.user.UserDTO;
import com.tourism.backend.dto.user.UserUpdateDTO;
import com.tourism.backend.dto.user.UserPasswordDTO;
import com.tourism.backend.model.User;
import com.tourism.backend.service.UserService;

import jakarta.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.stream.Collectors;
import java.util.List;

@RestController
@RequestMapping(ApiPaths.USERS)
public class UserController {

    private static final Logger logger = LoggerFactory.getLogger(UserController.class);

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping
    public ResponseEntity<List<UserDTO>> getAllUsers() {
        logger.info("Request to get all users received");
        List<User> users = userService.getAllUsers();
        List<UserDTO> userDTOs = users.stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
        logger.info("Successfully retrieved {} users", userDTOs.size());
        return ResponseEntity.ok(userDTOs);
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserDTO> getUserById(@PathVariable Long id) {
        logger.info("Request to get user with id: {}", id);
        User user = userService.getUserById(id);
        UserDTO userDTO = convertToDto(user);
        logger.info("User with id {} retrieved successfully", id);
        return ResponseEntity.ok(userDTO);
    }

    @PutMapping("/{id}")
    public ResponseEntity<UserDTO> updateUser(@PathVariable Long id, @Valid @RequestBody UserUpdateDTO updatedUserDTO) {
        logger.info("Request to update user with id: {}", id);
        User updatedUser = convertToEntity(updatedUserDTO);
        User user = userService.updateUser(id, updatedUser);
        UserDTO userDTO = convertToDto(user);
        logger.info("User with id {} updated successfully", id);
        return ResponseEntity.ok(userDTO);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable Long id) {
        logger.info("Request to delete user with id: {}", id);
        userService.deleteUser(id);
        logger.info("User with id {} deleted successfully", id);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/{id}/password")
    public ResponseEntity<Void> updateUserPassword(@PathVariable Long id, @Valid @RequestBody UserPasswordDTO newPasswordDTO) {
        logger.info("Request to update password for user with id: {}", id);
        userService.updateUserPassword(id, newPasswordDTO.getNewPassword());
        logger.info("Password for user with id {} updated successfully", id);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/search")
    public ResponseEntity<List<UserDTO>> searchUsersByName(@RequestParam String name) {
        logger.info("Request to search users by name: {}", name);
        List<User> users = userService.searchUsersByName(name);
        List<UserDTO> userDTOs = users.stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
        logger.info("Found {} users with name: {}", userDTOs.size(), name);
        return ResponseEntity.ok(userDTOs);
    }

    @PutMapping("/{id}/lock")
    public ResponseEntity<Void> lockUserAccount(@PathVariable Long id) {
        logger.info("Request to lock user account with id: {}", id);
        userService.lockUserAccount(id);
        logger.info("User account with id {} locked successfully", id);
        return ResponseEntity.ok().build();
    }

    @PutMapping("/{id}/unlock")
    public ResponseEntity<Void> unlockUserAccount(@PathVariable Long id) {
        logger.info("Request to unlock user account with id: {}", id);
        userService.unlockUserAccount(id);
        logger.info("User account with id {} unlocked successfully", id);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/profile")
    public ResponseEntity<UserDTO> getCurrentUserProfile() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String currentUsername = authentication.getName();
        logger.info("Request to get profile of current user: {}", currentUsername);
        User user = userService.getUserByUsername(currentUsername);
        UserDTO userDTO = convertToDto(user);
        logger.info("Profile of user {} retrieved successfully", currentUsername);
        return ResponseEntity.ok(userDTO);
    }

    private UserDTO convertToDto(User user) {
        UserDTO userDTO = new UserDTO();
        userDTO.setId(user.getId());
        userDTO.setUsername(user.getUsername());
        userDTO.setEmail(user.getEmail());
        userDTO.setFirstName(user.getFirstName());
        userDTO.setLastName(user.getLastName());
        return userDTO;
    }

    private User convertToEntity(UserUpdateDTO userUpdateDTO) {
        User user = new User();
        user.setEmail(userUpdateDTO.getEmail());
        user.setFirstName(userUpdateDTO.getFirstName());
        user.setLastName(userUpdateDTO.getLastName());
        user.setBio(userUpdateDTO.getBio());
        user.setProfilePicture(userUpdateDTO.getProfilePicture());
        return user;
    }
}
