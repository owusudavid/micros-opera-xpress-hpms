package com.hpms.opera.dto;

import lombok.*;
import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * Data Transfer Object for Reservation entity.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ReservationDTO {
    private Long id;
    private String confirmationNumber;
    private Long guestId;
    private Long roomId;
    private Long propertyId;
    private LocalDate checkInDate;
    private LocalDate checkOutDate;
    private LocalDateTime actualCheckIn;
    private LocalDateTime actualCheckOut;
    private Integer numberOfGuests;
    private Double dailyRate;
    private Double totalAmount;
    private Double advancePayment;
    private String status;
    private String specialRequests;
    private String notes;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private String source;
}
