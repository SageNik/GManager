package com.g_manager.entity;

import com.g_manager.entity.base.BasePerson;
import lombok.Data;

import javax.persistence.*;
import java.util.Set;

/**
 * Created by Nikolenko Oleh on 19.01.2018.
 */
@Data
@Entity
@Table(name = "employee")
public class Employee extends BasePerson {

    private String position;
    private Double salary;

    @ManyToMany(mappedBy = "serviceStaff",fetch = FetchType.EAGER)
    private Set<Client> personalClients;

    @ManyToOne
    @JoinColumn(name = "staff_category_id")
    private StaffCategory staffCategory;
}
