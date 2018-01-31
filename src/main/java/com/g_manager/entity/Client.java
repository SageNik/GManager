package com.g_manager.entity;

import com.g_manager.entity.base.BasePerson;
import lombok.Data;
import javax.persistence.*;
import java.util.Set;

/**
 * Created by Nikolenko Oleh on 12.12.2017.
 */
@Data
@Entity
@Table(name = "client")
public class Client extends BasePerson {

    @ManyToOne
    @JoinColumn(name = "client_category_id")
    private ClientCategory clientCategory;

    @ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinTable(name = "client_employee", joinColumns = @JoinColumn(name = "client_id"),
    inverseJoinColumns = @JoinColumn(name = "employee_id"))
    private Set<Employee> serviceStaff;

}
