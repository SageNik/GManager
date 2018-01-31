package com.g_manager.view;

import com.g_manager.enums.WindowType;

import java.util.ResourceBundle;

/**
 * Created by Nikolenko Oleh on 01.12.2017.
 */
public enum FxmlView {

    HOME {
        @Override
        public String getTitle() {
            return "GM Manager";
        }

        @Override
        public String getFxmlFile() {
            return "/fxml/Home.fxml";
        }

        @Override
        public WindowType getWindowType() {
            return WindowType.FULLSCREEN;
        }
    },
    LOGIN {
        @Override
        public String getTitle() {
            return getStringFromResourceBundle("login.title");
        }

        @Override
        public String getFxmlFile() {
            return "/fxml/Login.fxml";
        }

        @Override
        public WindowType getWindowType() {
            return WindowType.MODAL;
        }
    },
    CLIENT_DIALOG {
        @Override
        public String getTitle() {
            return getStringFromResourceBundle("person");
        }

        @Override
        public String getFxmlFile() {
            return "/fxml/ClientDialog.fxml";
        }

        @Override
        public WindowType getWindowType() {
            return WindowType.MODAL;
        }
    },
    EMPLOYEE_DIALOG {
        @Override
        public String getTitle() {
            return getStringFromResourceBundle("person");
        }

        @Override
        public String getFxmlFile() {
            return "/fxml/EmployeeDialog.fxml";
        }

        @Override
        public WindowType getWindowType() {
            return WindowType.MODAL;
        }
    };

    public abstract String getTitle();
    public abstract String getFxmlFile();
    public abstract WindowType getWindowType();

    String getStringFromResourceBundle(String key){
        return ResourceBundle.getBundle("Bundle").getString(key);
    }

}
