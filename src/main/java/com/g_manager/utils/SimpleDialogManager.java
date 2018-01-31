package com.g_manager.utils;

import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;

import java.util.List;
import java.util.Optional;

/**
 * Created by Nikolenko Oleh on 17.12.2017.
 */
public class SimpleDialogManager {

    public static void showInfoDialog(String title,String text){

        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setContentText(text);
        alert.setHeaderText("");
        alert.showAndWait();
    }

    public static boolean showConfirmDialog(String title,String text){

        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle(title);
        alert.setContentText(text);
        alert.setHeaderText("");
        Optional<ButtonType> result = alert.showAndWait();
        if (result.get() == ButtonType.OK){
           return  true;
        } else {
           return false;
        }
    }

    public static void showErrorDialog(String title,String text){

        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(title);
        alert.setContentText(text);
        alert.setHeaderText("");
        alert.showAndWait();
    }

    public static void showErrorDialog(String title,List<String> texts){

        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(title);
        StringBuffer message = new StringBuffer("");
        for (String mess:texts) {
            message.append(mess + "\n");
        }
        alert.setContentText(message.toString());
        alert.setHeaderText("");
        alert.showAndWait();
    }
}
