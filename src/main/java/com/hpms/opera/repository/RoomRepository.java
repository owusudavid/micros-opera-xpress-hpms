package com.hpms.opera.repository;

import com.hpms.opera.entity.Room;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * Repository interface for Room entity database operations.
 */
@Repository
public interface RoomRepository extends JpaRepository<Room, Long> {
    Optional<Room> findByRoomNumberAndPropertyId(String roomNumber, Long propertyId);
    
    List<Room> findByPropertyIdAndStatus(Long propertyId, String status);
    
    @Query("SELECT r FROM Room r WHERE r.property.id = ?1 AND r.status = 'AVAILABLE'")
    List<Room> findAvailableRoomsByProperty(Long propertyId);
    
    List<Room> findByPropertyId(Long propertyId);
}
