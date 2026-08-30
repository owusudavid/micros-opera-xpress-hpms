package com.hpms.opera.service;

import com.hpms.opera.dto.LoginRequestDTO;
import com.hpms.opera.dto.LoginResponseDTO;
import com.hpms.opera.dto.UserDTO;
import com.hpms.opera.entity.User;
import com.hpms.opera.exception.ResourceNotFoundException;
import com.hpms.opera.exception.UnauthorizedException;
import com.hpms.opera.repository.UserRepository;
import com.hpms.opera.security.JwtTokenProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;

/**
 * Service class for authentication and authorization operations.
 */
@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtTokenProvider jwtTokenProvider;

    /**
     * Authenticate user with credentials and return JWT token.
     */
    public LoginResponseDTO login(LoginRequestDTO request) {
        User user = userRepository.findByUsername(request.getUsername())
                .orElseThrow(() -> new UnauthorizedException("Invalid username or password"));

        if (!passwordEncoder.matches(request.getPassword(), user.getPassword())) {
            throw new UnauthorizedException("Invalid username or password");
        }

        if (!user.getActive()) {
            throw new UnauthorizedException("User account is disabled");
        }

        // Update last login
        user.setLastLogin(LocalDateTime.now());
        userRepository.save(user);

        // Generate tokens
        String token = jwtTokenProvider.generateToken(user.getId(), user.getUsername(), user.getRole().toString());
        String refreshToken = jwtTokenProvider.generateRefreshToken(user.getId());

        return LoginResponseDTO.builder()
                .token(token)
                .refreshToken(refreshToken)
                .user(mapToDTO(user))
                .expiresIn(jwtTokenProvider.getExpirationTime())
                .build();
    }

    /**
     * Register a new user.
     */
    public UserDTO register(UserDTO userDTO) {
        if (userRepository.existsByUsername(userDTO.getUsername())) {
            throw new IllegalArgumentException("Username already exists");
        }

        if (userRepository.existsByEmail(userDTO.getEmail())) {
            throw new IllegalArgumentException("Email already exists");
        }

        User user = User.builder()
                .username(userDTO.getUsername())
                .email(userDTO.getEmail())
                .firstName(userDTO.getFirstName())
                .lastName(userDTO.getLastName())
                .phoneNumber(userDTO.getPhoneNumber())
                .password(passwordEncoder.encode("DefaultPassword123!")) // Should be changed on first login
                .role(User.UserRole.STAFF)
                .active(true)
                .build();

        User savedUser = userRepository.save(user);
        return mapToDTO(savedUser);
    }

    /**
     * Get current user from JWT token.
     */
    public UserDTO getCurrentUser(String token) {
        Long userId = jwtTokenProvider.getUserIdFromToken(token);
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));
        return mapToDTO(user);
    }

    /**
     * Refresh JWT token.
     */
    public LoginResponseDTO refreshToken(String refreshToken) {
        if (!jwtTokenProvider.validateToken(refreshToken)) {
            throw new UnauthorizedException("Invalid refresh token");
        }

        Long userId = jwtTokenProvider.getUserIdFromToken(refreshToken);
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));

        String newToken = jwtTokenProvider.generateToken(user.getId(), user.getUsername(), user.getRole().toString());

        return LoginResponseDTO.builder()
                .token(newToken)
                .refreshToken(refreshToken)
                .user(mapToDTO(user))
                .expiresIn(jwtTokenProvider.getExpirationTime())
                .build();
    }

    private UserDTO mapToDTO(User user) {
        return UserDTO.builder()
                .id(user.getId())
                .username(user.getUsername())
                .email(user.getEmail())
                .firstName(user.getFirstName())
                .lastName(user.getLastName())
                .phoneNumber(user.getPhoneNumber())
                .role(user.getRole().toString())
                .active(user.getActive())
                .createdAt(user.getCreatedAt())
                .updatedAt(user.getUpdatedAt())
                .lastLogin(user.getLastLogin())
                .profileImage(user.getProfileImage())
                .build();
    }
}
