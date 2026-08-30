package com.hpms.opera.entity;

import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.time.LocalDateTime;

/**
 * Room entity representing individual guest rooms in the property.
 */
@Entity
@Table(name = "rooms")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Room {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(optional = false)
    @JoinColumn(name = "property_id")
    private Property property;

    @Column(nullable = false)
    private String roomNumber;

    @Column(nullable = false)
    private String floor;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private RoomType roomType;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private RoomStatus status;

    @Column
    private Integer capacity;

    @Column
    private Integer beds;

    @Column
    private String bedType;

    @Column
    private Double baseRate;

    @Column
    private Double weekendRate;

    @Column
    private Boolean hasAC;

    @Column
    private Boolean hasBalcony;

    @Column
    private Boolean hasKitchen;

    @Column
    private Boolean hasJacuzzi;

    @Column
    private String amenities;

    @Column
    private String description;

    @CreationTimestamp
    @Column(nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @UpdateTimestamp
    @Column(nullable = false)
    private LocalDateTime updatedAt;

    @Column
    private LocalDateTime lastInspection;

    @Column
    private String inspectionNotes;

    public enum RoomType {
        SINGLE,
        DOUBLE,
        TWIN,
        SUITE,
        PRESIDENTIAL,
        DELUXE,
        STANDARD,
        ECONOMY
    }

    public enum RoomStatus {
        AVAILABLE,
        OCCUPIED,
        MAINTENANCE,
        CLEANING,
        RESERVED,
        BLOCKED,
        OUT_OF_ORDER
    }
}
