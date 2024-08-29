package com.tourism.backend.controller;

import com.tourism.backend.constants.ApiPaths;
import com.tourism.backend.dto.user.UserDTO;
import com.tourism.backend.dto.user.UserUpdateDTO;
import com.tourism.backend.dto.user.UserPasswordDTO;
import com.tourism.backend.model.User;
import com.tourism.backend.service.UserService;

import jakarta.validation.Valid;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.stream.Collectors;
import java.util.List;

@RestController
@RequestMapping(ApiPaths.USERS)
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
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

    @GetMapping
    public ResponseEntity<List<UserDTO>> getAllUsers() {
        List<User> users = userService.getAllUsers();
        List<UserDTO> userDTOs = users.stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
        return ResponseEntity.ok(userDTOs);
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserDTO> getUserById(@PathVariable Long id) {
        User user = userService.getUserById(id);
        UserDTO userDTO = convertToDto(user);
        return ResponseEntity.ok(userDTO);
    }

    @PutMapping("/{id}")
    public ResponseEntity<UserDTO> updateUser(@PathVariable Long id, @Valid @RequestBody UserUpdateDTO updatedUserDTO) {
        User updatedUser = convertToEntity(updatedUserDTO);
        User user = userService.updateUser(id, updatedUser);
        UserDTO userDTO = convertToDto(user);
        return ResponseEntity.ok(userDTO);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable Long id) {
        userService.deleteUser(id);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/{id}/password")
    public ResponseEntity<Void> updateUserPassword(@PathVariable Long id, @Valid @RequestBody UserPasswordDTO newPasswordDTO) {
        userService.updateUserPassword(id, newPasswordDTO.getNewPassword());
        return ResponseEntity.ok().build();
    }

    @GetMapping("/search")
    public ResponseEntity<List<UserDTO>> searchUsersByName(@RequestParam String name) {
        List<User> users = userService.searchUsersByName(name);
        List<UserDTO> userDTOs = users.stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
        return ResponseEntity.ok(userDTOs);
    }

    @PutMapping("/{id}/lock")
    public ResponseEntity<Void> lockUserAccount(@PathVariable Long id) {
        userService.lockUserAccount(id);
        return ResponseEntity.ok().build();
    }

    @PutMapping("/{id}/unlock")
    public ResponseEntity<Void> unlockUserAccount(@PathVariable Long id) {
        userService.unlockUserAccount(id);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/profile")
    public ResponseEntity<UserDTO> getCurrentUserProfile() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String currentUsername = authentication.getName();
        User user = userService.getUserByUsername(currentUsername);
        UserDTO userDTO = convertToDto(user);
        return ResponseEntity.ok(userDTO);
    }
}
