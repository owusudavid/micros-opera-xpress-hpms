package com.hpms.opera.entity;

import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.time.LocalDateTime;

/**
 * Payment entity representing payments received against guest folios.
 */
@Entity
@Table(name = "payments")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Payment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(optional = false)
    @JoinColumn(name = "folio_id")
    private Folio folio;

    @Column(nullable = false)
    private Double amount;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private PaymentMethod paymentMethod;

    @Column
    private String referenceNumber;

    @CreationTimestamp
    @Column(nullable = false, updatable = false)
    private LocalDateTime paymentDate;

    @Column
    private String notes;

    @ManyToOne
    @JoinColumn(name = "processed_by_id")
    private User processedBy;

    public enum PaymentMethod {
        CASH,
        CREDIT_CARD,
        DEBIT_CARD,
        BANK_TRANSFER,
        CHECK,
        MOBILE_WALLET,
        ADVANCE_DEPOSIT
    }
}
