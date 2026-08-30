package com.hpms.opera.dto;

import lombok.*;
import java.time.LocalDateTime;

/**
 * Data Transfer Object for Folio entity.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class FolioDTO {
    private Long id;
    private Long guestId;
    private Long reservationId;
    private Double roomCharges;
    private Double otherCharges;
    private Double totalCharges;
    private Double totalPayments;
    private Double balance;
    private String status;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
