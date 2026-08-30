package com.hpms.opera.dto;

import lombok.*;
import java.time.LocalDateTime;

/**
 * Data Transfer Object for Payment entity.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PaymentDTO {
    private Long id;
    private Long folioId;
    private Double amount;
    private String paymentMethod;
    private String referenceNumber;
    private LocalDateTime paymentDate;
    private String notes;
}
