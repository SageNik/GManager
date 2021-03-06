package com.g_manager.entity;

import com.g_manager.entity.base.BaseEntity;
import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import java.math.BigDecimal;

/**
 * Created by Nikolenko Oleh on 16.02.2018.
 */
@Data
@Entity
@Table(name="bonus")
public class Bonus extends BaseEntity{

    private BigDecimal amount;
    private String description;

    @ManyToOne
    @JoinColumn(name = "salary_id")
    private Salary salary;
}
