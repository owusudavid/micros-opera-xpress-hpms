package com.hpms.opera.service;

import com.hpms.opera.dto.GuestDTO;
import com.hpms.opera.entity.Guest;
import com.hpms.opera.exception.ResourceNotFoundException;
import com.hpms.opera.repository.GuestRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Service class for guest management operations.
 */
@Service
@RequiredArgsConstructor
public class GuestService {

    private final GuestRepository guestRepository;

    /**
     * Create a new guest.
     */
    public GuestDTO createGuest(GuestDTO guestDTO) {
        Guest guest = Guest.builder()
                .firstName(guestDTO.getFirstName())
                .lastName(guestDTO.getLastName())
                .email(guestDTO.getEmail())
                .phoneNumber(guestDTO.getPhoneNumber())
                .idType(guestDTO.getIdType())
                .idNumber(guestDTO.getIdNumber())
                .address(guestDTO.getAddress())
                .city(guestDTO.getCity())
                .state(guestDTO.getState())
                .postalCode(guestDTO.getPostalCode())
                .country(guestDTO.getCountry())
                .company(guestDTO.getCompany())
                .title(guestDTO.getTitle())
                .nationality(guestDTO.getNationality())
                .preferences(guestDTO.getPreferences())
                .notes(guestDTO.getNotes())
                .build();

        Guest savedGuest = guestRepository.save(guest);
        return mapToDTO(savedGuest);
    }

    /**
     * Get guest by ID.
     */
    public GuestDTO getGuestById(Long id) {
        Guest guest = guestRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Guest not found with id: " + id));
        return mapToDTO(guest);
    }

    /**
     * Get all guests.
     */
    public List<GuestDTO> getAllGuests() {
        return guestRepository.findAll().stream()
                .map(this::mapToDTO)
                .collect(Collectors.toList());
    }

    /**
     * Update guest details.
     */
    public GuestDTO updateGuest(Long id, GuestDTO guestDTO) {
        Guest guest = guestRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Guest not found with id: " + id));

        guest.setFirstName(guestDTO.getFirstName());
        guest.setLastName(guestDTO.getLastName());
        guest.setEmail(guestDTO.getEmail());
        guest.setPhoneNumber(guestDTO.getPhoneNumber());
        guest.setIdType(guestDTO.getIdType());
        guest.setIdNumber(guestDTO.getIdNumber());
        guest.setAddress(guestDTO.getAddress());
        guest.setCity(guestDTO.getCity());
        guest.setState(guestDTO.getState());
        guest.setPostalCode(guestDTO.getPostalCode());
        guest.setCountry(guestDTO.getCountry());
        guest.setCompany(guestDTO.getCompany());
        guest.setTitle(guestDTO.getTitle());
        guest.setNationality(guestDTO.getNationality());
        guest.setPreferences(guestDTO.getPreferences());
        guest.setNotes(guestDTO.getNotes());

        Guest updatedGuest = guestRepository.save(guest);
        return mapToDTO(updatedGuest);
    }

    /**
     * Delete guest by ID.
     */
    public void deleteGuest(Long id) {
        if (!guestRepository.existsById(id)) {
            throw new ResourceNotFoundException("Guest not found with id: " + id);
        }
        guestRepository.deleteById(id);
    }

    /**
     * Search guests by name.
     */
    public List<GuestDTO> searchGuestsByName(String name) {
        return guestRepository.searchByName(name).stream()
                .map(this::mapToDTO)
                .collect(Collectors.toList());
    }

    private GuestDTO mapToDTO(Guest guest) {
        return GuestDTO.builder()
                .id(guest.getId())
                .firstName(guest.getFirstName())
                .lastName(guest.getLastName())
                .email(guest.getEmail())
                .phoneNumber(guest.getPhoneNumber())
                .idType(guest.getIdType())
                .idNumber(guest.getIdNumber())
                .address(guest.getAddress())
                .city(guest.getCity())
                .state(guest.getState())
                .postalCode(guest.getPostalCode())
                .country(guest.getCountry())
                .company(guest.getCompany())
                .title(guest.getTitle())
                .guestType(guest.getGuestType() != null ? guest.getGuestType().toString() : null)
                .nationality(guest.getNationality())
                .preferences(guest.getPreferences())
                .notes(guest.getNotes())
                .createdAt(guest.getCreatedAt())
                .updatedAt(guest.getUpdatedAt())
                .lastStay(guest.getLastStay())
                .totalStays(guest.getTotalStays())
                .totalSpent(guest.getTotalSpent())
                .build();
    }
}
