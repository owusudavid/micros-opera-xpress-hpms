package com.hpms.opera.entity;

import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * Reservation entity representing guest room bookings.
 */
@Entity
@Table(name = "reservations")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Reservation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String confirmationNumber;

    @ManyToOne(optional = false)
    @JoinColumn(name = "guest_id")
    private Guest guest;

    @ManyToOne(optional = false)
    @JoinColumn(name = "room_id")
    private Room room;

    @ManyToOne(optional = false)
    @JoinColumn(name = "property_id")
    private Property property;

    @Column(nullable = false)
    private LocalDate checkInDate;

    @Column(nullable = false)
    private LocalDate checkOutDate;

    @Column
    private LocalDateTime actualCheckIn;

    @Column
    private LocalDateTime actualCheckOut;

    @Column(nullable = false)
    private Integer numberOfGuests;

    @Column(nullable = false)
    private Double dailyRate;

    @Column(nullable = false)
    private Double totalAmount;

    @Column
    private Double advancePayment = 0.0;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private ReservationStatus status;

    @Column
    private String specialRequests;

    @Column
    private String notes;

    @CreationTimestamp
    @Column(nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @UpdateTimestamp
    @Column(nullable = false)
    private LocalDateTime updatedAt;

    @ManyToOne
    @JoinColumn(name = "created_by_id")
    private User createdBy;

    @Column
    private String source; // Online, Walk-in, Phone, Agent

    public enum ReservationStatus {
        PENDING,
        CONFIRMED,
        CHECKED_IN,
        CHECKED_OUT,
        CANCELLED,
        NO_SHOW
    }
}
