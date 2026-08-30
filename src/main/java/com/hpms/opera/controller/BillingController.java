package com.hpms.opera.controller;

import com.hpms.opera.dto.ApiResponseDTO;
import com.hpms.opera.dto.FolioChargeDTO;
import com.hpms.opera.dto.FolioDTO;
import com.hpms.opera.dto.PaymentDTO;
import com.hpms.opera.service.BillingService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * REST Controller for billing and folio management operations.
 */
@RestController
@RequestMapping("/api/folios")
@RequiredArgsConstructor
@CrossOrigin(origins = "*", maxAge = 3600)
public class BillingController {

    private final BillingService billingService;

    /**
     * Get folio by ID.
     */
    @GetMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN', 'MANAGER', 'ACCOUNTANT', 'RECEPTIONIST')")
    public ResponseEntity<?> getFolio(@PathVariable Long id) {
        FolioDTO folio = billingService.getFolioById(id);
        return ResponseEntity.ok(
                ApiResponseDTO.success("Folio retrieved successfully", folio)
        );
    }

    /**
     * Get all folios for a guest.
     */
    @GetMapping("/guest/{guestId}")
    @PreAuthorize("hasAnyRole('ADMIN', 'MANAGER', 'ACCOUNTANT', 'RECEPTIONIST')")
    public ResponseEntity<?> getGuestFolios(@PathVariable Long guestId) {
        List<FolioDTO> folios = billingService.getGuestFolios(guestId);
        return ResponseEntity.ok(
                ApiResponseDTO.success("Guest folios retrieved successfully", folios)
        );
    }

    /**
     * Add charge to folio.
     */
    @PostMapping("/{id}/charges")
    @PreAuthorize("hasAnyRole('ADMIN', 'MANAGER', 'ACCOUNTANT', 'RECEPTIONIST')")
    public ResponseEntity<?> addCharge(@PathVariable Long id, @RequestBody FolioChargeDTO chargeDTO) {
        Long userId = (Long) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        FolioChargeDTO charge = billingService.addChargeToFolio(id, chargeDTO, userId);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(ApiResponseDTO.success("Charge added successfully", charge));
    }

    /**
     * Get folio charges.
     */
    @GetMapping("/{id}/charges")
    @PreAuthorize("hasAnyRole('ADMIN', 'MANAGER', 'ACCOUNTANT', 'RECEPTIONIST')")
    public ResponseEntity<?> getFolioCharges(@PathVariable Long id) {
        List<FolioChargeDTO> charges = billingService.getFolioCharges(id);
        return ResponseEntity.ok(
                ApiResponseDTO.success("Folio charges retrieved successfully", charges)
        );
    }

    /**
     * Process payment for folio.
     */
    @PostMapping("/{id}/payments")
    @PreAuthorize("hasAnyRole('ADMIN', 'MANAGER', 'ACCOUNTANT')")
    public ResponseEntity<?> processPayment(@PathVariable Long id, @RequestBody PaymentDTO paymentDTO) {
        Long userId = (Long) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        PaymentDTO payment = billingService.processPayment(id, paymentDTO, userId);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(ApiResponseDTO.success("Payment processed successfully", payment));
    }

    /**
     * Get folio payments.
     */
    @GetMapping("/{id}/payments")
    @PreAuthorize("hasAnyRole('ADMIN', 'MANAGER', 'ACCOUNTANT', 'RECEPTIONIST')")
    public ResponseEntity<?> getFolioPayments(@PathVariable Long id) {
        List<PaymentDTO> payments = billingService.getFolioPayments(id);
        return ResponseEntity.ok(
                ApiResponseDTO.success("Folio payments retrieved successfully", payments)
        );
    }

    /**
     * Close folio.
     */
    @PostMapping("/{id}/close")
    @PreAuthorize("hasAnyRole('ADMIN', 'MANAGER', 'ACCOUNTANT')")
    public ResponseEntity<?> closeFolio(@PathVariable Long id) {
        FolioDTO folio = billingService.closeFolio(id);
        return ResponseEntity.ok(
                ApiResponseDTO.success("Folio closed successfully", folio)
        );
    }
}
