package com.hpms.opera.service;

import com.hpms.opera.dto.FolioChargeDTO;
import com.hpms.opera.dto.FolioDTO;
import com.hpms.opera.dto.PaymentDTO;
import com.hpms.opera.entity.*;
import com.hpms.opera.exception.BusinessException;
import com.hpms.opera.exception.ResourceNotFoundException;
import com.hpms.opera.repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Service class for billing and folio management operations.
 */
@Service
@RequiredArgsConstructor
public class BillingService {

    private final FolioRepository folioRepository;
    private final FolioChargeRepository folioChargeRepository;
    private final PaymentRepository paymentRepository;
    private final GuestRepository guestRepository;
    private final UserRepository userRepository;

    /**
     * Get folio by ID.
     */
    public FolioDTO getFolioById(Long id) {
        Folio folio = folioRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Folio not found"));
        return mapToDTO(folio);
    }

    /**
     * Get all folios for a guest.
     */
    public List<FolioDTO> getGuestFolios(Long guestId) {
        return folioRepository.findByGuestId(guestId).stream()
                .map(this::mapToDTO)
                .collect(Collectors.toList());
    }

    /**
     * Add charge to folio.
     */
    public FolioChargeDTO addChargeToFolio(Long folioId, FolioChargeDTO chargeDTO, Long userId) {
        Folio folio = folioRepository.findById(folioId)
                .orElseThrow(() -> new ResourceNotFoundException("Folio not found"));
        
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));

        FolioCharge charge = FolioCharge.builder()
                .folio(folio)
                .description(chargeDTO.getDescription())
                .amount(chargeDTO.getAmount())
                .chargeType(FolioCharge.ChargeType.valueOf(chargeDTO.getChargeType()))
                .quantity(chargeDTO.getQuantity() != null ? chargeDTO.getQuantity() : 1)
                .unitPrice(chargeDTO.getUnitPrice())
                .notes(chargeDTO.getNotes())
                .createdBy(user)
                .build();

        FolioCharge savedCharge = folioChargeRepository.save(charge);
        
        // Update folio totals
        updateFolioTotals(folio);
        folioRepository.save(folio);

        return mapChargeToDTO(savedCharge);
    }

    /**
     * Process payment for folio.
     */
    public PaymentDTO processPayment(Long folioId, PaymentDTO paymentDTO, Long userId) {
        Folio folio = folioRepository.findById(folioId)
                .orElseThrow(() -> new ResourceNotFoundException("Folio not found"));
        
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));

        if (paymentDTO.getAmount() <= 0) {
            throw new BusinessException("Payment amount must be greater than zero");
        }

        if (paymentDTO.getAmount() > folio.getBalance()) {
            throw new BusinessException("Payment amount exceeds outstanding balance");
        }

        Payment payment = Payment.builder()
                .folio(folio)
                .amount(paymentDTO.getAmount())
                .paymentMethod(Payment.PaymentMethod.valueOf(paymentDTO.getPaymentMethod()))
                .referenceNumber(paymentDTO.getReferenceNumber())
                .notes(paymentDTO.getNotes())
                .processedBy(user)
                .build();

        Payment savedPayment = paymentRepository.save(payment);
        
        // Update folio totals
        folio.setTotalPayments(folio.getTotalPayments() + paymentDTO.getAmount());
        folio.setBalance(folio.getTotalCharges() - folio.getTotalPayments());
        
        if (folio.getBalance() <= 0) {
            folio.setStatus(Folio.FolioStatus.SETTLED);
        }
        
        folioRepository.save(folio);
        
        // Update guest total spent
        Guest guest = folio.getGuest();
        guest.setTotalSpent(guest.getTotalSpent() + paymentDTO.getAmount());
        guestRepository.save(guest);

        return mapPaymentToDTO(savedPayment);
    }

    /**
     * Close folio.
     */
    public FolioDTO closeFolio(Long folioId) {
        Folio folio = folioRepository.findById(folioId)
                .orElseThrow(() -> new ResourceNotFoundException("Folio not found"));
        
        if (folio.getBalance() > 0) {
            throw new BusinessException("Cannot close folio with outstanding balance");
        }
        
        folio.setStatus(Folio.FolioStatus.CLOSED);
        Folio closedFolio = folioRepository.save(folio);
        
        return mapToDTO(closedFolio);
    }

    /**
     * Get folio charges.
     */
    public List<FolioChargeDTO> getFolioCharges(Long folioId) {
        return folioChargeRepository.findByFolioId(folioId).stream()
                .map(this::mapChargeToDTO)
                .collect(Collectors.toList());
    }

    /**
     * Get folio payments.
     */
    public List<PaymentDTO> getFolioPayments(Long folioId) {
        return paymentRepository.findByFolioId(folioId).stream()
                .map(this::mapPaymentToDTO)
                .collect(Collectors.toList());
    }

    private void updateFolioTotals(Folio folio) {
        double totalCharges = folio.getRoomCharges() + folio.getOtherCharges();
        
        folio.getCharges().forEach(charge -> {
            totalCharges += charge.getAmount();
        });
        
        folio.setTotalCharges(totalCharges);
        folio.setBalance(totalCharges - folio.getTotalPayments());
    }

    private FolioDTO mapToDTO(Folio folio) {
        return FolioDTO.builder()
                .id(folio.getId())
                .guestId(folio.getGuest().getId())
                .reservationId(folio.getReservation().getId())
                .roomCharges(folio.getRoomCharges())
                .otherCharges(folio.getOtherCharges())
                .totalCharges(folio.getTotalCharges())
                .totalPayments(folio.getTotalPayments())
                .balance(folio.getBalance())
                .status(folio.getStatus().toString())
                .createdAt(folio.getCreatedAt())
                .updatedAt(folio.getUpdatedAt())
                .build();
    }

    private FolioChargeDTO mapChargeToDTO(FolioCharge charge) {
        return FolioChargeDTO.builder()
                .id(charge.getId())
                .folioId(charge.getFolio().getId())
                .description(charge.getDescription())
                .amount(charge.getAmount())
                .chargeType(charge.getChargeType().toString())
                .quantity(charge.getQuantity())
                .unitPrice(charge.getUnitPrice())
                .createdAt(charge.getCreatedAt())
                .notes(charge.getNotes())
                .build();
    }

    private PaymentDTO mapPaymentToDTO(Payment payment) {
        return PaymentDTO.builder()
                .id(payment.getId())
                .folioId(payment.getFolio().getId())
                .amount(payment.getAmount())
                .paymentMethod(payment.getPaymentMethod().toString())
                .referenceNumber(payment.getReferenceNumber())
                .paymentDate(payment.getPaymentDate())
                .notes(payment.getNotes())
                .build();
    }
}
