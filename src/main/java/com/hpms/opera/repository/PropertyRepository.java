package com.hpms.opera.repository;

import com.hpms.opera.entity.Property;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * Repository interface for Property entity database operations.
 */
@Repository
public interface PropertyRepository extends JpaRepository<Property, Long> {
    Optional<Property> findByCode(String code);
    boolean existsByCode(String code);
}
