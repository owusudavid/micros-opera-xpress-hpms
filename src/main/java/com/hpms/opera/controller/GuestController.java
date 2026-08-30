package com.hpms.opera.controller;

import com.hpms.opera.dto.ApiResponseDTO;
import com.hpms.opera.dto.GuestDTO;
import com.hpms.opera.service.GuestService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * REST Controller for guest management operations.
 */
@RestController
@RequestMapping("/api/guests")
@RequiredArgsConstructor
@CrossOrigin(origins = "*", maxAge = 3600)
public class GuestController {

    private final GuestService guestService;

    /**
     * Create new guest.
     */
    @PostMapping
    @PreAuthorize("hasAnyRole('ADMIN', 'MANAGER', 'RECEPTIONIST')")
    public ResponseEntity<?> createGuest(@RequestBody GuestDTO guestDTO) {
        GuestDTO newGuest = guestService.createGuest(guestDTO);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(ApiResponseDTO.success("Guest created successfully", newGuest));
    }

    /**
     * Get guest by ID.
     */
    @GetMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN', 'MANAGER', 'RECEPTIONIST', 'ACCOUNTANT')")
    public ResponseEntity<?> getGuest(@PathVariable Long id) {
        GuestDTO guest = guestService.getGuestById(id);
        return ResponseEntity.ok(
                ApiResponseDTO.success("Guest retrieved successfully", guest)
        );
    }

    /**
     * Get all guests.
     */
    @GetMapping
    @PreAuthorize("hasAnyRole('ADMIN', 'MANAGER', 'RECEPTIONIST', 'ACCOUNTANT')")
    public ResponseEntity<?> getAllGuests() {
        List<GuestDTO> guests = guestService.getAllGuests();
        return ResponseEntity.ok(
                ApiResponseDTO.success("Guests retrieved successfully", guests)
        );
    }

    /**
     * Update guest details.
     */
    @PutMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN', 'MANAGER', 'RECEPTIONIST')")
    public ResponseEntity<?> updateGuest(@PathVariable Long id, @RequestBody GuestDTO guestDTO) {
        GuestDTO updatedGuest = guestService.updateGuest(id, guestDTO);
        return ResponseEntity.ok(
                ApiResponseDTO.success("Guest updated successfully", updatedGuest)
        );
    }

    /**
     * Delete guest.
     */
    @DeleteMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN', 'MANAGER')")
    public ResponseEntity<?> deleteGuest(@PathVariable Long id) {
        guestService.deleteGuest(id);
        return ResponseEntity.ok(
                ApiResponseDTO.success("Guest deleted successfully", null)
        );
    }

    /**
     * Search guests by name.
     */
    @GetMapping("/search/{name}")
    @PreAuthorize("hasAnyRole('ADMIN', 'MANAGER', 'RECEPTIONIST', 'ACCOUNTANT')")
    public ResponseEntity<?> searchGuests(@PathVariable String name) {
        List<GuestDTO> guests = guestService.searchGuestsByName(name);
        return ResponseEntity.ok(
                ApiResponseDTO.success("Guests searched successfully", guests)
        );
    }
}
