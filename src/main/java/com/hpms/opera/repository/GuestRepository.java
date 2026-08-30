package com.hpms.opera.repository;

import com.hpms.opera.entity.Guest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * Repository interface for Guest entity database operations.
 */
@Repository
public interface GuestRepository extends JpaRepository<Guest, Long> {
    Optional<Guest> findByEmail(String email);
    Optional<Guest> findByPhoneNumber(String phoneNumber);
    
    @Query("SELECT g FROM Guest g WHERE g.firstName LIKE %?1% OR g.lastName LIKE %?1%")
    List<Guest> searchByName(String name);
}
