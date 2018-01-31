package com.g_manager.controller;

import com.g_manager.config.StageManager;
import com.g_manager.view.FxmlView;
import javafx.scene.Parent;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.assertNotNull;

/**
 * Created by Nikolenko Oleh on 09.01.2018.
 */
@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.NONE)
public class LoginControllerIntegrationTest extends BaseTestFX {

    PasswordField password;
    TextField username;
    Label errorLog;
    Label lblLogin;

    private StageManager stageManager;

    @Autowired
    private ConfigurableApplicationContext context;

    @Override
    public void start(Stage stage) throws Exception {
        stageManager = context.getBean(StageManager.class, stage);
        stageManager.switchScene(FxmlView.LOGIN);
    }

    @Before
    public void setUp(){
        password = find("#password",PasswordField.class);
        username = find("#username", TextField.class);
        errorLog = find("#errorLog", Label.class);
        lblLogin = find("#lblLogin",Label.class);
    }

    @Test
    public void testWidgetsExist() {
        final String errMsg = "One of the widget cannot be retrieved anymore";
        assertNotNull(errMsg, password);
        assertNotNull(errMsg, username);
        assertNotNull(errMsg, errorLog);
        assertNotNull(errMsg, lblLogin);
    }

}
