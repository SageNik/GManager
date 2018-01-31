package com.g_manager.entity;

import com.g_manager.entity.base.BaseEntity;
import lombok.Getter;

import javax.persistence.*;
import java.util.Set;

/**
 * Created by Nikolenko Oleh on 11.12.2017.
 */
@Getter
@Entity
@Table(name = "role")
public class Role extends BaseEntity {

    private String role;

    @ManyToMany(mappedBy = "roles")
    Set<User> users;

}
