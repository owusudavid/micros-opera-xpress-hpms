package com.hpms.opera.repository;

import com.hpms.opera.entity.Reservation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

/**
 * Repository interface for Reservation entity database operations.
 */
@Repository
public interface ReservationRepository extends JpaRepository<Reservation, Long> {
    Optional<Reservation> findByConfirmationNumber(String confirmationNumber);
    
    List<Reservation> findByGuestId(Long guestId);
    
    List<Reservation> findByRoomId(Long roomId);
    
    List<Reservation> findByPropertyId(Long propertyId);
    
    @Query("SELECT r FROM Reservation r WHERE r.room.id = ?1 AND " +
           "((r.checkInDate <= ?2 AND r.checkOutDate >= ?2) OR " +
           "(r.checkInDate <= ?3 AND r.checkOutDate >= ?3))")
    List<Reservation> findConflictingReservations(Long roomId, LocalDate startDate, LocalDate endDate);
    
    @Query("SELECT r FROM Reservation r WHERE r.property.id = ?1 AND r.status != 'CANCELLED'")
    List<Reservation> findActiveReservationsByProperty(Long propertyId);
}
