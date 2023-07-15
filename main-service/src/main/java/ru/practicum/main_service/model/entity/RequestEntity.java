package ru.practicum.main_service.model.entity;

import lombok.Data;
import ru.practicum.main_service.model.RequestStatus;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "requests")
@Data
public class RequestEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "created_on")
    private LocalDateTime created;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "event_id")
    private EventEntity event;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "requester_id")
    private UserEntity user;

    @Column(name = "category_status")
    @Enumerated(EnumType.ORDINAL)
    private RequestStatus status;
}
