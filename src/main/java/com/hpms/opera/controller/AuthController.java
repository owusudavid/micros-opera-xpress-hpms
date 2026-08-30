package com.hpms.opera.controller;

import com.hpms.opera.dto.ApiResponseDTO;
import com.hpms.opera.dto.LoginRequestDTO;
import com.hpms.opera.dto.LoginResponseDTO;
import com.hpms.opera.dto.UserDTO;
import com.hpms.opera.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * REST Controller for authentication operations.
 */
@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
@CrossOrigin(origins = "*", maxAge = 3600)
public class AuthController {

    private final AuthService authService;

    /**
     * Login endpoint.
     */
    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequestDTO request) {
        LoginResponseDTO response = authService.login(request);
        return ResponseEntity.ok(
                ApiResponseDTO.success("Login successful", response)
        );
    }

    /**
     * Register new user endpoint.
     */
    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody UserDTO userDTO) {
        UserDTO newUser = authService.register(userDTO);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(ApiResponseDTO.success("User registered successfully", newUser));
    }

    /**
     * Refresh token endpoint.
     */
    @PostMapping("/refresh")
    public ResponseEntity<?> refreshToken(@RequestHeader("Authorization") String token) {
        String refreshToken = token.substring(7); // Remove "Bearer " prefix
        LoginResponseDTO response = authService.refreshToken(refreshToken);
        return ResponseEntity.ok(
                ApiResponseDTO.success("Token refreshed successfully", response)
        );
    }

    /**
     * Get current user endpoint.
     */
    @GetMapping("/me")
    public ResponseEntity<?> getCurrentUser(@RequestHeader("Authorization") String token) {
        String jwtToken = token.substring(7); // Remove "Bearer " prefix
        UserDTO user = authService.getCurrentUser(jwtToken);
        return ResponseEntity.ok(
                ApiResponseDTO.success("User retrieved successfully", user)
        );
    }
}
