package com.g_manager.models;

import com.g_manager.entity.Bonus;
import com.g_manager.entity.Penalty;
import com.g_manager.enums.AccrualType;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * Created by Nikolenko Oleh on 19.03.2018.
 */
@Data
public class AccrualModel {

    private AccrualType accrual;
    private String description;
    private BigDecimal amount;
    private LocalDateTime createDate;

    public static AccrualModel buildByBonus(Bonus bonus){
        AccrualModel model = new AccrualModel();
        model.accrual = AccrualType.BONUS;
        model.description = bonus.getDescription();
        model.amount = bonus.getAmount();
        model.createDate = bonus.getLastChangeDate();

        return model;
    }

    public static AccrualModel buildByPenalty(Penalty penalty){
        AccrualModel model = new AccrualModel();
        model.accrual = AccrualType.PENALTY;
        model.description = penalty.getDescription();
        model.amount = penalty.getAmount();
        model.createDate = penalty.getLastChangeDate();

        return model;
    }
}
