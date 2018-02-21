package com.g_manager.entity;

import com.g_manager.entity.base.BaseEntity;
import lombok.Data;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.Month;
import java.util.List;

/**
 * Created by Nikolenko Oleh on 16.02.2018.
 */
@Data
@Entity
@Table(name="salary")
public class Salary extends BaseEntity{

    private BigDecimal rate;
    private LocalDate payDate;
    private Month payMonth;

    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "bonus_id")
    private List<Bonus> bonuses;
}
