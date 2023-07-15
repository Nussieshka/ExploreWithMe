package ru.practicum.main_service.model.entity;

import lombok.Data;

import javax.persistence.*;

@Entity
@Table(name = "categories")
@Data
public class CategoryEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name")
    private String name;
}
