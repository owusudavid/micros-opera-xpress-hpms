package com.hpms.opera.dto;

import lombok.*;

/**
 * Data Transfer Object for authentication responses.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class LoginResponseDTO {
    private String token;
    private String refreshToken;
    private UserDTO user;
    private String tokenType = "Bearer";
    private Long expiresIn;
}
