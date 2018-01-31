package com.g_manager.config;

import com.g_manager.enums.WindowType;
import com.g_manager.view.FxmlView;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Modality;
import javafx.stage.Stage;
import org.slf4j.Logger;

import java.io.IOException;
import java.util.Objects;

import static org.slf4j.LoggerFactory.getLogger;

/**
 * Manages switching Scenes on the Primary Stage and creating dialog Stages
 */
public class StageManager {

    private static final Logger LOGGER = getLogger(StageManager.class);
    private final Stage primaryStage;
    private final SpringFXMLLoader springFXMLLoader;
    private  Object dialogController;

    public StageManager(SpringFXMLLoader springFXMLLoader, Stage stage) {
        this.springFXMLLoader = springFXMLLoader;
        this.primaryStage = stage;
    }

    public void switchScene(final FxmlView view) {
        Parent viewRootNodeHierarchy = loadViewNodeHierarchy(view.getFxmlFile());
        show(viewRootNodeHierarchy, view);
    }

    private void show(Parent viewRootNodeHierarchy, FxmlView view) {

        Scene scene = prepareScene(viewRootNodeHierarchy);
        String title = view.getTitle();
        //scene.getStylesheets().add("/styles/Styles.css");
        //primaryStage.initStyle(StageStyle.TRANSPARENT);
        primaryStage.setTitle(title);
        primaryStage.setScene(scene);
        setStageSize(view, primaryStage);

        try {
            primaryStage.show();
        } catch (Exception exception) {
            logAndExit("Unable to show scene for title" + title, exception);
        }
    }

    private void setStageSize(FxmlView view, Stage stage) {
        if (view.getWindowType() == WindowType.MODAL) {
            stage.sizeToScene();
            stage.centerOnScreen();
            stage.setResizable(false);
        } else {
            primaryStage.setMaximized(true);
        }
    }

    private Scene prepareScene(Parent rootnode) {
        Scene scene = primaryStage.getScene();

        if (scene == null) {
            scene = new Scene(rootnode);
        }
        scene.setRoot(rootnode);
        return scene;
    }

    /**
     * Loads the object hierarchy from a FXML document and returns to root node
     * of that hierarchy.
     *
     * @return Parent root node of the FXML document hierarchy
     */
    private Parent loadViewNodeHierarchy(String fxmlFilePath) {
        Parent rootNode = null;
        try {
            rootNode = springFXMLLoader.load(fxmlFilePath);
            Objects.requireNonNull(rootNode, "A Root FXML node must not be null");
        } catch (Exception exception) {
            logAndExit("Unable to load FXML view " + fxmlFilePath, exception);
        }
        return rootNode;
    }


    private void logAndExit(String errorMsg, Exception exception) {
        LOGGER.error(errorMsg, exception, exception.getCause());
        Platform.exit();
    }

    public Stage getPrimaryStage() {
        return primaryStage;
    }

    public Stage getStageDialog(FxmlView view, ActionEvent actionEvent) {

        Parent fxmlDialog = loadDialogViewNodeHierarchy(view);
        Stage dialogStage = new Stage();
        dialogStage.setScene(new Scene(fxmlDialog));
        setStageSize(view, dialogStage);
        dialogStage.initModality(Modality.WINDOW_MODAL);
        dialogStage.initOwner(((Node) actionEvent.getSource()).getScene().getWindow());

        return dialogStage;
    }

    private Parent loadDialogViewNodeHierarchy(FxmlView view){

        FXMLLoader fxmlLoader = new FXMLLoader();
        Parent fxmlDialog = null;
        try{
            fxmlLoader.setResources(springFXMLLoader.getResourceBundle());
            fxmlLoader.setControllerFactory(springFXMLLoader.getApplicationContext()::getBean);
            fxmlLoader.setLocation(getClass().getResource(view.getFxmlFile()));
            fxmlDialog = fxmlLoader.load();
            dialogController = fxmlLoader.getController();
            Objects.requireNonNull(fxmlDialog, "A Dialog FXML node must not be null");
        }catch (IOException e){
            LOGGER.error("Fault to initialization dialog stage with location: "+view.getFxmlFile(), e, e.getCause());
            Platform.exit();
        }
        return fxmlDialog;
    }

    public Object getDialogController() {
        return dialogController;
    }
}
