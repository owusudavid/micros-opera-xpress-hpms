package com.hpms.opera.dto;

import lombok.*;
import java.time.LocalDateTime;

/**
 * Data Transfer Object for Guest entity.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class GuestDTO {
    private Long id;
    private String firstName;
    private String lastName;
    private String email;
    private String phoneNumber;
    private String idType;
    private String idNumber;
    private String address;
    private String city;
    private String state;
    private String postalCode;
    private String country;
    private String company;
    private String title;
    private String guestType;
    private String nationality;
    private String preferences;
    private String notes;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private LocalDateTime lastStay;
    private Integer totalStays;
    private Double totalSpent;
}
