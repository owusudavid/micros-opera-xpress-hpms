package com.hpms.opera.service;

import com.hpms.opera.dto.RoomDTO;
import com.hpms.opera.entity.Property;
import com.hpms.opera.entity.Room;
import com.hpms.opera.exception.ResourceNotFoundException;
import com.hpms.opera.repository.PropertyRepository;
import com.hpms.opera.repository.RoomRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Service class for room management operations.
 */
@Service
@RequiredArgsConstructor
public class RoomService {

    private final RoomRepository roomRepository;
    private final PropertyRepository propertyRepository;

    /**
     * Create a new room.
     */
    public RoomDTO createRoom(RoomDTO roomDTO) {
        Property property = propertyRepository.findById(roomDTO.getPropertyId())
                .orElseThrow(() -> new ResourceNotFoundException("Property not found"));

        Room room = Room.builder()
                .property(property)
                .roomNumber(roomDTO.getRoomNumber())
                .floor(roomDTO.getFloor())
                .roomType(Room.RoomType.valueOf(roomDTO.getRoomType()))
                .status(Room.RoomStatus.AVAILABLE)
                .capacity(roomDTO.getCapacity())
                .beds(roomDTO.getBeds())
                .bedType(roomDTO.getBedType())
                .baseRate(roomDTO.getBaseRate())
                .weekendRate(roomDTO.getWeekendRate())
                .hasAC(roomDTO.getHasAC())
                .hasBalcony(roomDTO.getHasBalcony())
                .hasKitchen(roomDTO.getHasKitchen())
                .hasJacuzzi(roomDTO.getHasJacuzzi())
                .amenities(roomDTO.getAmenities())
                .description(roomDTO.getDescription())
                .build();

        Room savedRoom = roomRepository.save(room);
        return mapToDTO(savedRoom);
    }

    /**
     * Get room by ID.
     */
    public RoomDTO getRoomById(Long id) {
        Room room = roomRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Room not found with id: " + id));
        return mapToDTO(room);
    }

    /**
     * Get all rooms.
     */
    public List<RoomDTO> getAllRooms() {
        return roomRepository.findAll().stream()
                .map(this::mapToDTO)
                .collect(Collectors.toList());
    }

    /**
     * Get available rooms by property.
     */
    public List<RoomDTO> getAvailableRooms(Long propertyId) {
        return roomRepository.findAvailableRoomsByProperty(propertyId).stream()
                .map(this::mapToDTO)
                .collect(Collectors.toList());
    }

    /**
     * Update room status.
     */
    public RoomDTO updateRoomStatus(Long id, String status) {
        Room room = roomRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Room not found with id: " + id));
        room.setStatus(Room.RoomStatus.valueOf(status));
        Room updatedRoom = roomRepository.save(room);
        return mapToDTO(updatedRoom);
    }

    /**
     * Update room details.
     */
    public RoomDTO updateRoom(Long id, RoomDTO roomDTO) {
        Room room = roomRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Room not found with id: " + id));

        room.setRoomNumber(roomDTO.getRoomNumber());
        room.setFloor(roomDTO.getFloor());
        room.setCapacity(roomDTO.getCapacity());
        room.setBeds(roomDTO.getBeds());
        room.setBedType(roomDTO.getBedType());
        room.setBaseRate(roomDTO.getBaseRate());
        room.setWeekendRate(roomDTO.getWeekendRate());
        room.setHasAC(roomDTO.getHasAC());
        room.setHasBalcony(roomDTO.getHasBalcony());
        room.setHasKitchen(roomDTO.getHasKitchen());
        room.setHasJacuzzi(roomDTO.getHasJacuzzi());
        room.setAmenities(roomDTO.getAmenities());
        room.setDescription(roomDTO.getDescription());

        Room updatedRoom = roomRepository.save(room);
        return mapToDTO(updatedRoom);
    }

    /**
     * Delete room by ID.
     */
    public void deleteRoom(Long id) {
        if (!roomRepository.existsById(id)) {
            throw new ResourceNotFoundException("Room not found with id: " + id);
        }
        roomRepository.deleteById(id);
    }

    private RoomDTO mapToDTO(Room room) {
        return RoomDTO.builder()
                .id(room.getId())
                .roomNumber(room.getRoomNumber())
                .floor(room.getFloor())
                .roomType(room.getRoomType().toString())
                .status(room.getStatus().toString())
                .capacity(room.getCapacity())
                .beds(room.getBeds())
                .bedType(room.getBedType())
                .baseRate(room.getBaseRate())
                .weekendRate(room.getWeekendRate())
                .hasAC(room.getHasAC())
                .hasBalcony(room.getHasBalcony())
                .hasKitchen(room.getHasKitchen())
                .hasJacuzzi(room.getHasJacuzzi())
                .amenities(room.getAmenities())
                .description(room.getDescription())
                .createdAt(room.getCreatedAt())
                .updatedAt(room.getUpdatedAt())
                .lastInspection(room.getLastInspection())
                .inspectionNotes(room.getInspectionNotes())
                .propertyId(room.getProperty().getId())
                .build();
    }
}
