package com.g_manager.entity;

import com.g_manager.entity.base.BaseEntity;
import lombok.Data;

import javax.persistence.*;
import java.util.Set;

/**
 * Created by Nikolenko Oleh on 04.12.2017.
 */
@Data
@Entity
@Table(name = "user")
public class User extends BaseEntity {

    @Column(name = "login", nullable = false, unique = true)
    private String login;
    private String password;

    @ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinTable(name = "user_role", joinColumns = @JoinColumn(name = "user_id"), inverseJoinColumns = @JoinColumn(name = "role_id"))
    private Set<Role> roles;


}
