package com.g_manager.entity.base;

import com.g_manager.enums.GenderType;
import lombok.Data;

import javax.persistence.*;
import java.time.LocalDate;

/**
 * Created by Nikolenko Oleh on 19.01.2018.
 */
@Data
@MappedSuperclass
public abstract class BasePerson extends BaseEntity {

    @Column(name = "phone", nullable = false, unique = true)
    private String phone;
    private String fullName;
    private String address;
    private String email;
    private GenderType gender;
    private LocalDate birthday;
    private String titleFotoPath;
}
