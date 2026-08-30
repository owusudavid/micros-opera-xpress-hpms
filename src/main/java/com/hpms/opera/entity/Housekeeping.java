package com.hpms.opera.entity;

import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.time.LocalDateTime;

/**
 * Housekeeping entity for tracking room cleaning and maintenance tasks.
 */
@Entity
@Table(name = "housekeeping")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Housekeeping {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(optional = false)
    @JoinColumn(name = "room_id")
    private Room room;

    @ManyToOne(optional = false)
    @JoinColumn(name = "assigned_to_id")
    private User assignedTo;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private TaskType taskType;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private TaskStatus status;

    @Column
    private String description;

    @CreationTimestamp
    @Column(nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @Column
    private LocalDateTime scheduledAt;

    @Column
    private LocalDateTime startedAt;

    @Column
    private LocalDateTime completedAt;

    @Column
    private String completionNotes;

    @Enumerated(EnumType.STRING)
    @Column
    private Priority priority;

    public enum TaskType {
        CLEANING,
        MAINTENANCE,
        INSPECTION,
        REPAIR,
        DEEP_CLEAN
    }

    public enum TaskStatus {
        PENDING,
        ASSIGNED,
        IN_PROGRESS,
        COMPLETED,
        CANCELLED,
        ON_HOLD
    }

    public enum Priority {
        LOW,
        NORMAL,
        HIGH,
        URGENT
    }
}
