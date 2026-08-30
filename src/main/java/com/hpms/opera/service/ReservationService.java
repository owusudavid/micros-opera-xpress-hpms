package com.hpms.opera.service;

import com.hpms.opera.dto.ReservationDTO;
import com.hpms.opera.entity.*;
import com.hpms.opera.exception.BusinessException;
import com.hpms.opera.exception.ResourceNotFoundException;
import com.hpms.opera.repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

/**
 * Service class for reservation management operations.
 */
@Service
@RequiredArgsConstructor
public class ReservationService {

    private final ReservationRepository reservationRepository;
    private final GuestRepository guestRepository;
    private final RoomRepository roomRepository;
    private final PropertyRepository propertyRepository;
    private final FolioRepository folioRepository;

    /**
     * Create a new reservation.
     */
    public ReservationDTO createReservation(ReservationDTO reservationDTO) {
        Guest guest = guestRepository.findById(reservationDTO.getGuestId())
                .orElseThrow(() -> new ResourceNotFoundException("Guest not found"));
        
        Room room = roomRepository.findById(reservationDTO.getRoomId())
                .orElseThrow(() -> new ResourceNotFoundException("Room not found"));
        
        Property property = propertyRepository.findById(reservationDTO.getPropertyId())
                .orElseThrow(() -> new ResourceNotFoundException("Property not found"));

        // Check for conflicting reservations
        List<Reservation> conflicts = reservationRepository.findConflictingReservations(
                room.getId(), 
                reservationDTO.getCheckInDate(), 
                reservationDTO.getCheckOutDate()
        );
        
        if (!conflicts.isEmpty()) {
            throw new BusinessException("Room is not available for the selected dates");
        }

        Reservation reservation = Reservation.builder()
                .confirmationNumber(generateConfirmationNumber())
                .guest(guest)
                .room(room)
                .property(property)
                .checkInDate(reservationDTO.getCheckInDate())
                .checkOutDate(reservationDTO.getCheckOutDate())
                .numberOfGuests(reservationDTO.getNumberOfGuests())
                .dailyRate(reservationDTO.getDailyRate())
                .totalAmount(reservationDTO.getTotalAmount())
                .advancePayment(reservationDTO.getAdvancePayment() != null ? reservationDTO.getAdvancePayment() : 0.0)
                .status(Reservation.ReservationStatus.CONFIRMED)
                .specialRequests(reservationDTO.getSpecialRequests())
                .notes(reservationDTO.getNotes())
                .source(reservationDTO.getSource())
                .build();

        Reservation savedReservation = reservationRepository.save(reservation);
        
        // Create folio for reservation
        Folio folio = Folio.builder()
                .guest(guest)
                .reservation(savedReservation)
                .roomCharges(reservationDTO.getTotalAmount())
                .otherCharges(0.0)
                .totalCharges(reservationDTO.getTotalAmount())
                .totalPayments(reservationDTO.getAdvancePayment() != null ? reservationDTO.getAdvancePayment() : 0.0)
                .balance(reservationDTO.getTotalAmount() - (reservationDTO.getAdvancePayment() != null ? reservationDTO.getAdvancePayment() : 0.0))
                .status(Folio.FolioStatus.ACTIVE)
                .build();
        folioRepository.save(folio);
        
        // Update room status
        room.setStatus(Room.RoomStatus.RESERVED);
        roomRepository.save(room);

        return mapToDTO(savedReservation);
    }

    /**
     * Get reservation by ID.
     */
    public ReservationDTO getReservationById(Long id) {
        Reservation reservation = reservationRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Reservation not found with id: " + id));
        return mapToDTO(reservation);
    }

    /**
     * Get all reservations.
     */
    public List<ReservationDTO> getAllReservations() {
        return reservationRepository.findAll().stream()
                .map(this::mapToDTO)
                .collect(Collectors.toList());
    }

    /**
     * Get reservations for a guest.
     */
    public List<ReservationDTO> getGuestReservations(Long guestId) {
        return reservationRepository.findByGuestId(guestId).stream()
                .map(this::mapToDTO)
                .collect(Collectors.toList());
    }

    /**
     * Check-in guest.
     */
    public ReservationDTO checkInGuest(Long reservationId) {
        Reservation reservation = reservationRepository.findById(reservationId)
                .orElseThrow(() -> new ResourceNotFoundException("Reservation not found"));
        
        if (!reservation.getStatus().equals(Reservation.ReservationStatus.CONFIRMED)) {
            throw new BusinessException("Only confirmed reservations can be checked in");
        }
        
        reservation.setActualCheckIn(LocalDateTime.now());
        reservation.setStatus(Reservation.ReservationStatus.CHECKED_IN);
        reservation.getRoom().setStatus(Room.RoomStatus.OCCUPIED);
        
        Reservation updatedReservation = reservationRepository.save(reservation);
        roomRepository.save(reservation.getRoom());
        
        return mapToDTO(updatedReservation);
    }

    /**
     * Check-out guest.
     */
    public ReservationDTO checkOutGuest(Long reservationId) {
        Reservation reservation = reservationRepository.findById(reservationId)
                .orElseThrow(() -> new ResourceNotFoundException("Reservation not found"));
        
        if (!reservation.getStatus().equals(Reservation.ReservationStatus.CHECKED_IN)) {
            throw new BusinessException("Only checked-in reservations can be checked out");
        }
        
        reservation.setActualCheckOut(LocalDateTime.now());
        reservation.setStatus(Reservation.ReservationStatus.CHECKED_OUT);
        reservation.getRoom().setStatus(Room.RoomStatus.CLEANING);
        
        // Update guest statistics
        Guest guest = reservation.getGuest();
        guest.setLastStay(LocalDateTime.now());
        guest.setTotalStays(guest.getTotalStays() + 1);
        guestRepository.save(guest);
        
        Reservation updatedReservation = reservationRepository.save(reservation);
        roomRepository.save(reservation.getRoom());
        
        return mapToDTO(updatedReservation);
    }

    /**
     * Cancel reservation.
     */
    public ReservationDTO cancelReservation(Long reservationId) {
        Reservation reservation = reservationRepository.findById(reservationId)
                .orElseThrow(() -> new ResourceNotFoundException("Reservation not found"));
        
        reservation.setStatus(Reservation.ReservationStatus.CANCELLED);
        reservation.getRoom().setStatus(Room.RoomStatus.AVAILABLE);
        
        Reservation updatedReservation = reservationRepository.save(reservation);
        roomRepository.save(reservation.getRoom());
        
        return mapToDTO(updatedReservation);
    }

    private String generateConfirmationNumber() {
        return "CONF-" + System.currentTimeMillis() + "-" + UUID.randomUUID().toString().substring(0, 6).toUpperCase();
    }

    private ReservationDTO mapToDTO(Reservation reservation) {
        return ReservationDTO.builder()
                .id(reservation.getId())
                .confirmationNumber(reservation.getConfirmationNumber())
                .guestId(reservation.getGuest().getId())
                .roomId(reservation.getRoom().getId())
                .propertyId(reservation.getProperty().getId())
                .checkInDate(reservation.getCheckInDate())
                .checkOutDate(reservation.getCheckOutDate())
                .actualCheckIn(reservation.getActualCheckIn())
                .actualCheckOut(reservation.getActualCheckOut())
                .numberOfGuests(reservation.getNumberOfGuests())
                .dailyRate(reservation.getDailyRate())
                .totalAmount(reservation.getTotalAmount())
                .advancePayment(reservation.getAdvancePayment())
                .status(reservation.getStatus().toString())
                .specialRequests(reservation.getSpecialRequests())
                .notes(reservation.getNotes())
                .createdAt(reservation.getCreatedAt())
                .updatedAt(reservation.getUpdatedAt())
                .source(reservation.getSource())
                .build();
    }
}
