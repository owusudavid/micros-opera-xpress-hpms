package com.hpms.opera.dto;

import lombok.*;

/**
 * Data Transfer Object for authentication requests.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class LoginRequestDTO {
    private String username;
    private String password;
}
