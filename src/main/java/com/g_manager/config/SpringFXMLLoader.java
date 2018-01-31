package com.g_manager.config;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.ResourceBundle;

/**
 * Created by Nikolenko Oleh on 01.12.2017.
 */
@Component
public class SpringFXMLLoader {

    private final ResourceBundle resourceBundle;
    private final ApplicationContext applicationContext;

    @Autowired
    public SpringFXMLLoader (ApplicationContext context, ResourceBundle resourceBundle){
        this.resourceBundle = resourceBundle;
        this.applicationContext = context;
    }

    public Parent load (String fxmlPath) throws IOException{

        FXMLLoader loader = new FXMLLoader();
        loader.setControllerFactory(applicationContext::getBean);   //Spring now FXML Controller Factory
        loader.setResources(resourceBundle);
        loader.setLocation(getClass().getResource(fxmlPath));
        return loader.load();
    }


    public ResourceBundle getResourceBundle() {
        return resourceBundle;
    }

    public ApplicationContext getApplicationContext() {
        return applicationContext;
    }
}
