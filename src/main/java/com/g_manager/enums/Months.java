package com.g_manager.enums;

import java.util.ResourceBundle;

/**
 * Created by Nikolenko Oleh on 16.02.2018.
 */
public enum Months {
    JAN {
        @Override
        public String getName() {
            return getStringFromResourceBundle("month.january");
        }
    },
    FEB {
        @Override
        public String getName() {
            return getStringFromResourceBundle("month.february");
        }
    },
    MAR {
        @Override
        public String getName() {
            return getStringFromResourceBundle("month.march");
        }
    },
    APR {
        @Override
        public String getName() {
            return getStringFromResourceBundle("month.april");
        }
    },
    MAY {
        @Override
        public String getName() {
            return getStringFromResourceBundle("month.may");
        }
    },
    JUN {
        @Override
        public String getName() {
            return getStringFromResourceBundle("month.june");
        }
    },
    JUL {
        @Override
        public String getName() {
            return getStringFromResourceBundle("month.july");
        }
    },
    AUG {
        @Override
        public String getName() {
            return getStringFromResourceBundle("month.august");
        }
    },
    SEP {
        @Override
        public String getName() {
            return getStringFromResourceBundle("month.september");
        }
    },
    OCT {
        @Override
        public String getName() {
            return getStringFromResourceBundle("month.october");
        }
    },
    NOV {
        @Override
        public String getName() {
            return getStringFromResourceBundle("month.november");
        }
    },
    DEC {
        @Override
        public String getName() {
            return getStringFromResourceBundle("month.december");
        }
    };

    public abstract String getName();
    String getStringFromResourceBundle(String key){
        return ResourceBundle.getBundle("Bundle").getString(key);
    }
}
