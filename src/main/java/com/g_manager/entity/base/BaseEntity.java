package com.g_manager.entity.base;

import lombok.Data;

import javax.persistence.*;
import java.time.LocalDateTime;

/**
 * Created by Nikolenko Oleh on 17.01.2018.
 */
@Data
@MappedSuperclass
public abstract class BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false, updatable = false)
    private Long id;

    private LocalDateTime lastChangeDate;
}
