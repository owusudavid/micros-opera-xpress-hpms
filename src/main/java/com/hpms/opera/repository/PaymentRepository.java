package com.hpms.opera.repository;

import com.hpms.opera.entity.Payment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Repository interface for Payment entity database operations.
 */
@Repository
public interface PaymentRepository extends JpaRepository<Payment, Long> {
    List<Payment> findByFolioId(Long folioId);
}
