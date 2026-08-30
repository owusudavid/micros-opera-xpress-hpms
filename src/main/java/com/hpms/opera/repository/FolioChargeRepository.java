package com.hpms.opera.repository;

import com.hpms.opera.entity.FolioCharge;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Repository interface for FolioCharge entity database operations.
 */
@Repository
public interface FolioChargeRepository extends JpaRepository<FolioCharge, Long> {
    List<FolioCharge> findByFolioId(Long folioId);
}
