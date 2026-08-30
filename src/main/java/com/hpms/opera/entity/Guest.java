package com.hpms.opera.entity;

import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

/**
 * Guest entity representing hotel guests and their profiles.
 */
@Entity
@Table(name = "guests")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Guest {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String firstName;

    @Column(nullable = false)
    private String lastName;

    @Column(unique = true)
    private String email;

    @Column(nullable = false)
    private String phoneNumber;

    @Column
    private String idType;

    @Column
    private String idNumber;

    @Column
    private String address;

    @Column
    private String city;

    @Column
    private String state;

    @Column
    private String postalCode;

    @Column
    private String country;

    @Column
    private String company;

    @Column
    private String title;

    @Enumerated(EnumType.STRING)
    private GuestType guestType;

    @Column
    private String nationality;

    @Column
    private String preferences;

    @Column
    private String notes;

    @CreationTimestamp
    @Column(nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @UpdateTimestamp
    @Column(nullable = false)
    private LocalDateTime updatedAt;

    @Column
    private LocalDateTime lastStay;

    @Column(nullable = false)
    private Integer totalStays = 0;

    @Column(nullable = false)
    private Double totalSpent = 0.0;

    @OneToMany(mappedBy = "guest", cascade = CascadeType.ALL)
    private Set<Reservation> reservations = new HashSet<>();

    @OneToMany(mappedBy = "guest", cascade = CascadeType.ALL)
    private Set<Folio> folios = new HashSet<>();

    public enum GuestType {
        INDIVIDUAL,
        CORPORATE,
        GROUP,
        VIP
    }
}
