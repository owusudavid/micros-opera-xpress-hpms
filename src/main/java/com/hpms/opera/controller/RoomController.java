package com.hpms.opera.controller;

import com.hpms.opera.dto.ApiResponseDTO;
import com.hpms.opera.dto.RoomDTO;
import com.hpms.opera.service.RoomService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * REST Controller for room management operations.
 */
@RestController
@RequestMapping("/api/rooms")
@RequiredArgsConstructor
@CrossOrigin(origins = "*", maxAge = 3600)
public class RoomController {

    private final RoomService roomService;

    /**
     * Create new room.
     */
    @PostMapping
    @PreAuthorize("hasAnyRole('ADMIN', 'MANAGER')")
    public ResponseEntity<?> createRoom(@RequestBody RoomDTO roomDTO) {
        RoomDTO newRoom = roomService.createRoom(roomDTO);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(ApiResponseDTO.success("Room created successfully", newRoom));
    }

    /**
     * Get room by ID.
     */
    @GetMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN', 'MANAGER', 'RECEPTIONIST')")
    public ResponseEntity<?> getRoom(@PathVariable Long id) {
        RoomDTO room = roomService.getRoomById(id);
        return ResponseEntity.ok(
                ApiResponseDTO.success("Room retrieved successfully", room)
        );
    }

    /**
     * Get all rooms.
     */
    @GetMapping
    @PreAuthorize("hasAnyRole('ADMIN', 'MANAGER', 'RECEPTIONIST')")
    public ResponseEntity<?> getAllRooms() {
        List<RoomDTO> rooms = roomService.getAllRooms();
        return ResponseEntity.ok(
                ApiResponseDTO.success("Rooms retrieved successfully", rooms)
        );
    }

    /**
     * Get available rooms by property.
     */
    @GetMapping("/available/{propertyId}")
    @PreAuthorize("hasAnyRole('ADMIN', 'MANAGER', 'RECEPTIONIST')")
    public ResponseEntity<?> getAvailableRooms(@PathVariable Long propertyId) {
        List<RoomDTO> rooms = roomService.getAvailableRooms(propertyId);
        return ResponseEntity.ok(
                ApiResponseDTO.success("Available rooms retrieved successfully", rooms)
        );
    }

    /**
     * Update room status.
     */
    @PatchMapping("/{id}/status")
    @PreAuthorize("hasAnyRole('ADMIN', 'MANAGER', 'HOUSEKEEPER')")
    public ResponseEntity<?> updateRoomStatus(@PathVariable Long id, @RequestParam String status) {
        RoomDTO updatedRoom = roomService.updateRoomStatus(id, status);
        return ResponseEntity.ok(
                ApiResponseDTO.success("Room status updated successfully", updatedRoom)
        );
    }

    /**
     * Update room details.
     */
    @PutMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN', 'MANAGER')")
    public ResponseEntity<?> updateRoom(@PathVariable Long id, @RequestBody RoomDTO roomDTO) {
        RoomDTO updatedRoom = roomService.updateRoom(id, roomDTO);
        return ResponseEntity.ok(
                ApiResponseDTO.success("Room updated successfully", updatedRoom)
        );
    }

    /**
     * Delete room.
     */
    @DeleteMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN', 'MANAGER')")
    public ResponseEntity<?> deleteRoom(@PathVariable Long id) {
        roomService.deleteRoom(id);
        return ResponseEntity.ok(
                ApiResponseDTO.success("Room deleted successfully", null)
        );
    }
}
