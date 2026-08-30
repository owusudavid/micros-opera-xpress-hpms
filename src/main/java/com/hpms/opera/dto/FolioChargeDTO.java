package com.hpms.opera.dto;

import lombok.*;
import java.time.LocalDateTime;

/**
 * Data Transfer Object for FolioCharge entity.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class FolioChargeDTO {
    private Long id;
    private Long folioId;
    private String description;
    private Double amount;
    private String chargeType;
    private Integer quantity;
    private Double unitPrice;
    private LocalDateTime createdAt;
    private String notes;
}
