package com.hpms.opera.entity;

import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

/**
 * Folio entity representing guest billing accounts and charges.
 */
@Entity
@Table(name = "folios")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Folio {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(optional = false)
    @JoinColumn(name = "guest_id")
    private Guest guest;

    @ManyToOne(optional = false)
    @JoinColumn(name = "reservation_id")
    private Reservation reservation;

    @Column(nullable = false)
    private Double roomCharges = 0.0;

    @Column(nullable = false)
    private Double otherCharges = 0.0;

    @Column(nullable = false)
    private Double totalCharges = 0.0;

    @Column(nullable = false)
    private Double totalPayments = 0.0;

    @Column(nullable = false)
    private Double balance = 0.0;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private FolioStatus status;

    @CreationTimestamp
    @Column(nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @UpdateTimestamp
    @Column(nullable = false)
    private LocalDateTime updatedAt;

    @OneToMany(mappedBy = "folio", cascade = CascadeType.ALL)
    private Set<FolioCharge> charges = new HashSet<>();

    @OneToMany(mappedBy = "folio", cascade = CascadeType.ALL)
    private Set<Payment> payments = new HashSet<>();

    public enum FolioStatus {
        ACTIVE,
        SETTLED,
        CLOSED,
        SUSPENDED
    }
}
