package com.hpms.opera.repository;

import com.hpms.opera.entity.Folio;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * Repository interface for Folio entity database operations.
 */
@Repository
public interface FolioRepository extends JpaRepository<Folio, Long> {
    List<Folio> findByGuestId(Long guestId);
    
    List<Folio> findByReservationId(Long reservationId);
    
    Optional<Folio> findByReservationIdAndStatus(Long reservationId, String status);
}
