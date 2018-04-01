package com.g_manager.controller;

import com.g_manager.service.UserService;
import com.g_manager.view.FxmlView;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.input.KeyCode;
import javafx.stage.Stage;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.springframework.test.context.junit4.SpringRunner;
import org.testfx.util.WaitForAsyncUtils;

import java.util.ResourceBundle;
import static org.junit.Assert.assertEquals;

/**
 * Created by Nikolenko Oleh on 05.01.2018.
 */
@RunWith(SpringRunner.class)
//@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.NONE)
public class LoginControllerUnitTest extends BaseTestFX{

    @Override
    public void start(Stage stage) throws Exception {
        FxmlView view = FxmlView.LOGIN;
        FXMLLoader loader = new FXMLLoader();
        loader.setResources(ResourceBundle.getBundle("Bundle"));
        loader.setLocation(getClass().getResource(view.getFxmlFile()));
        Parent rootNode = loader.load();
       stage.setScene(new Scene(rootNode));
       stage.sizeToScene();
       stage.setResizable(false);
       stage.setTitle(view.getTitle());
       stage.show();
    }
@Ignore
    @Test
    public void testLoginErrorMessage(){
        clickOn("#username");
        write("tesla").type(KeyCode.TAB);
        write("555");
        clickOn("#btnLogin");
        WaitForAsyncUtils.waitForFxEvents();

        assertEquals (find("#errorLog",Label.class).isVisible(), true);
    }

//    @Test
//    public void testLogin(){
//
//        write("daddy").type(KeyCode.TAB);
//        write("123");
//        clickOn("#btnLogin");
//        WaitForAsyncUtils.waitForFxEvents();
//
//        when(userService.authenticate("daddy","123")).thenReturn(true);
//        assertEquals (find("#errorLog",Label.class).isVisible(), false);
//    }
}
