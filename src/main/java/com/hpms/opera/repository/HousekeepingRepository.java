package com.hpms.opera.repository;

import com.hpms.opera.entity.Housekeeping;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Repository interface for Housekeeping entity database operations.
 */
@Repository
public interface HousekeepingRepository extends JpaRepository<Housekeeping, Long> {
    List<Housekeeping> findByRoomId(Long roomId);
    
    List<Housekeeping> findByAssignedToId(Long userId);
    
    List<Housekeeping> findByStatus(String status);
}
