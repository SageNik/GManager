package com.g_manager.entity.base;

import lombok.Data;
import javax.persistence.Entity;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.MappedSuperclass;

/**
 * Created by NikolenkoOleh on 19.01.2018.
 */
@Data
@MappedSuperclass
public abstract class BaseCategory extends BaseEntity {

    private String category;
    private String description;

    @Override
    public String toString() {
        return category;
    }
}
