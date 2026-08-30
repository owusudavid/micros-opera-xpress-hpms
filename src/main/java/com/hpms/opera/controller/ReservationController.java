package com.hpms.opera.controller;

import com.hpms.opera.dto.ApiResponseDTO;
import com.hpms.opera.dto.ReservationDTO;
import com.hpms.opera.service.ReservationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * REST Controller for reservation management operations.
 */
@RestController
@RequestMapping("/api/reservations")
@RequiredArgsConstructor
@CrossOrigin(origins = "*", maxAge = 3600)
public class ReservationController {

    private final ReservationService reservationService;

    /**
     * Create new reservation.
     */
    @PostMapping
    @PreAuthorize("hasAnyRole('ADMIN', 'MANAGER', 'RECEPTIONIST')")
    public ResponseEntity<?> createReservation(@RequestBody ReservationDTO reservationDTO) {
        ReservationDTO newReservation = reservationService.createReservation(reservationDTO);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(ApiResponseDTO.success("Reservation created successfully", newReservation));
    }

    /**
     * Get reservation by ID.
     */
    @GetMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN', 'MANAGER', 'RECEPTIONIST', 'ACCOUNTANT')")
    public ResponseEntity<?> getReservation(@PathVariable Long id) {
        ReservationDTO reservation = reservationService.getReservationById(id);
        return ResponseEntity.ok(
                ApiResponseDTO.success("Reservation retrieved successfully", reservation)
        );
    }

    /**
     * Get all reservations.
     */
    @GetMapping
    @PreAuthorize("hasAnyRole('ADMIN', 'MANAGER', 'RECEPTIONIST', 'ACCOUNTANT')")
    public ResponseEntity<?> getAllReservations() {
        List<ReservationDTO> reservations = reservationService.getAllReservations();
        return ResponseEntity.ok(
                ApiResponseDTO.success("Reservations retrieved successfully", reservations)
        );
    }

    /**
     * Get guest reservations.
     */
    @GetMapping("/guest/{guestId}")
    @PreAuthorize("hasAnyRole('ADMIN', 'MANAGER', 'RECEPTIONIST', 'ACCOUNTANT')")
    public ResponseEntity<?> getGuestReservations(@PathVariable Long guestId) {
        List<ReservationDTO> reservations = reservationService.getGuestReservations(guestId);
        return ResponseEntity.ok(
                ApiResponseDTO.success("Guest reservations retrieved successfully", reservations)
        );
    }

    /**
     * Check-in guest.
     */
    @PostMapping("/{id}/check-in")
    @PreAuthorize("hasAnyRole('ADMIN', 'MANAGER', 'RECEPTIONIST')")
    public ResponseEntity<?> checkInGuest(@PathVariable Long id) {
        ReservationDTO reservation = reservationService.checkInGuest(id);
        return ResponseEntity.ok(
                ApiResponseDTO.success("Guest checked in successfully", reservation)
        );
    }

    /**
     * Check-out guest.
     */
    @PostMapping("/{id}/check-out")
    @PreAuthorize("hasAnyRole('ADMIN', 'MANAGER', 'RECEPTIONIST')")
    public ResponseEntity<?> checkOutGuest(@PathVariable Long id) {
        ReservationDTO reservation = reservationService.checkOutGuest(id);
        return ResponseEntity.ok(
                ApiResponseDTO.success("Guest checked out successfully", reservation)
        );
    }

    /**
     * Cancel reservation.
     */
    @PostMapping("/{id}/cancel")
    @PreAuthorize("hasAnyRole('ADMIN', 'MANAGER', 'RECEPTIONIST')")
    public ResponseEntity<?> cancelReservation(@PathVariable Long id) {
        ReservationDTO reservation = reservationService.cancelReservation(id);
        return ResponseEntity.ok(
                ApiResponseDTO.success("Reservation cancelled successfully", reservation)
        );
    }
}
