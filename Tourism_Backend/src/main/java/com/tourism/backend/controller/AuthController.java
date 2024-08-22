package com.tourism.backend.controller;

import com.tourism.backend.model.User;
import com.tourism.backend.security.JWTUtil;
import com.tourism.backend.service.UserService;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    private UserService userService;

    @Autowired
    private JWTUtil jwtUtil;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @PostMapping("/register")
    public String register(@RequestBody User user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        userService.registerUser(user);
        return "User registered successfully";
    }

    @PostMapping("/login")
    public Map<String, String> login(@RequestBody User user) {
        UserDetails userDetails = userService.loadUserByUsername(user.getUsername());
        if (userDetails != null && passwordEncoder.matches(user.getPassword(), userDetails.getPassword())) {
            String accessToken = jwtUtil.generateToken(userDetails);
            String refreshToken = jwtUtil.generateRefreshToken(userDetails);

            // Lưu Refresh Token vào cơ sở dữ liệu
            // User dbUser = userService.findByUsername(user.getUsername());
            // dbUser.setRefreshToken(refreshToken);
            // userService.saveUser(dbUser);

            Map<String, String> tokens = new HashMap<>();
            tokens.put("accessToken", accessToken);
            tokens.put("refreshToken", refreshToken);
            return tokens;
        }
        throw new RuntimeException("Invalid refresh token");
    }

    @PostMapping("/refresh-token")
    public ResponseEntity<Map<String, String>> refreshToken(@RequestBody Map<String, String> request) {
        String refreshToken = request.get("refreshToken");
        String username = jwtUtil.extractUsername(refreshToken, true); // Sử dụng true cho refreshToken

        if (username != null) {
            UserDetails userDetails = userService.loadUserByUsername(username);
            if (jwtUtil.validateRefreshToken(refreshToken, userDetails)) {
                String newAccessToken = jwtUtil.generateToken(userDetails);
                Map<String, String> tokens = new HashMap<>();
                tokens.put("accessToken", newAccessToken);
                tokens.put("refreshToken", refreshToken);
                return ResponseEntity.ok(tokens);
            }
        }
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(null);
    }

}
