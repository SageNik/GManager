package com.g_manager.enums;

import java.util.ResourceBundle;

/**
 * Created by Ник on 19.03.2018.
 */
public enum AccrualType {

    BONUS {
        @Override
        public String getAccrual() {
            return getStringFromResourceBundle("bonuses");
        }
    },
    PENALTY {
        @Override
        public String getAccrual() {
            return getStringFromResourceBundle("penalties");
        }
    };

    public abstract String getAccrual();
    String getStringFromResourceBundle(String key){
        return ResourceBundle.getBundle("Bundle").getString(key);
    }
}
