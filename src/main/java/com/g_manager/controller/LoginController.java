package com.g_manager.controller;

import com.g_manager.config.StageManager;
import com.g_manager.service.UserService;
import com.g_manager.view.FxmlView;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Controller;

import java.net.URL;
import java.util.ResourceBundle;

/**
 * Created by Nikolenko Oleh on 04.12.2017.
 */
@Controller
public class LoginController implements Initializable{

        @FXML
        private PasswordField password;

        @FXML
        private TextField username;

        @FXML
        private Label errorLog;

        @FXML
        private Label lblLogin;

    private ResourceBundle resourceBundle;

    @Autowired
    private UserService userService;

    @Lazy
    @Autowired
    private StageManager stageManager;

    @FXML
    private void login (ActionEvent event){

            if(userService.authenticate(getUsername(), getPassword())){
                stageManager.switchScene(FxmlView.HOME);
            }else{
                errorLog.setVisible(true);
            }
        }

    private String getPassword() {
        return password.getText();
    }

    private String getUsername() {
        return username.getText();
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
                resourceBundle = resources;
    }


}

