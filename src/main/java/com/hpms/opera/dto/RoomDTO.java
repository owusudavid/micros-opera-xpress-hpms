package com.hpms.opera.dto;

import lombok.*;
import java.time.LocalDateTime;

/**
 * Data Transfer Object for Room entity.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RoomDTO {
    private Long id;
    private String roomNumber;
    private String floor;
    private String roomType;
    private String status;
    private Integer capacity;
    private Integer beds;
    private String bedType;
    private Double baseRate;
    private Double weekendRate;
    private Boolean hasAC;
    private Boolean hasBalcony;
    private Boolean hasKitchen;
    private Boolean hasJacuzzi;
    private String amenities;
    private String description;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private LocalDateTime lastInspection;
    private String inspectionNotes;
    private Long propertyId;
}
