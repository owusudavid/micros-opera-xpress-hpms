package com.hpms.opera.entity;

import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.time.LocalDateTime;

/**
 * FolioCharge entity representing individual charges on a guest folio.
 */
@Entity
@Table(name = "folio_charges")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class FolioCharge {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(optional = false)
    @JoinColumn(name = "folio_id")
    private Folio folio;

    @Column(nullable = false)
    private String description;

    @Column(nullable = false)
    private Double amount;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private ChargeType chargeType;

    @Column
    private Integer quantity = 1;

    @Column
    private Double unitPrice;

    @CreationTimestamp
    @Column(nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @Column
    private String notes;

    @ManyToOne
    @JoinColumn(name = "created_by_id")
    private User createdBy;

    public enum ChargeType {
        ROOM_CHARGE,
        RESTAURANT,
        SPA,
        LAUNDRY,
        MINIBAR,
        PARKING,
        EXTRA_BED,
        SERVICE_CHARGE,
        RESORT_FEE,
        OTHER
    }
}
