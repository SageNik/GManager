package com.g_manager.controller;

import com.g_manager.entity.Client;
import com.g_manager.entity.ClientCategory;
import com.g_manager.entity.base.BasePerson;
import com.g_manager.enums.GenderType;
import com.g_manager.exception.ClientCategoryException;
import com.g_manager.service.ClientCategoryService;
import com.g_manager.service.ClientService;
import com.g_manager.utils.SimpleDialogManager;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.ChoiceBox;
import javafx.stage.Stage;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import java.net.URL;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import static org.slf4j.LoggerFactory.getLogger;

/**
 * Created by Nikolenko Oleh on 15.12.2017.
 */
@Controller
public class ClientDialogController extends BasePersonDialogController implements Initializable {

    private static final Logger LOGGER = getLogger(ClientDialogController.class);

    @FXML
    protected ChoiceBox<ClientCategory> choboxCategory;
    private ObservableList<ClientCategory> categories = FXCollections.observableArrayList();
    private List<String> errorValidationMessages = new ArrayList<>();

    @Autowired
    private ClientService clientService;
    @Autowired
    private ClientCategoryService clientCategoryService;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        resourceBundle = resources;
        registerValidators(resources);

        fillClientCategoryBox();
    }

    private void fillClientCategoryBox() {
        try {
            categories.addAll(clientCategoryService.findAllNotEmpty());
        } catch (ClientCategoryException e) {
            LOGGER.error("Client Category must not be empty",e,e.getCause());
            SimpleDialogManager.showErrorDialog(resourceBundle.getString("fatal.error"),
                    resourceBundle.getString("client.category.not.found.error"));
            Platform.exit();
        }
        choboxCategory.setItems(categories);
    }

    @FXML
    void actionClose(ActionEvent event) {
        Node source = (Node) event.getSource();
        Stage stage = (Stage) source.getScene().getWindow();
        stage.hide();
    }

    @FXML
    void savePerson(ActionEvent event) {
        fillValidationErrors();
        if(errorValidationMessages.isEmpty()){
            if(isEdit){
                Client editPersone = editClient(clientService.findOne(personId));
                checkAndSave(event,editPersone);
                SimpleDialogManager.showInfoDialog(resourceBundle.getString("client.editClient"),
                        resourceBundle.getString("client.successful.edit"));
            }else {
                Client newClient = createClient();
                checkAndSave(event, newClient);
                SimpleDialogManager.showInfoDialog(resourceBundle.getString("client.addNewClient"),
                        resourceBundle.getString("client.successful.added"));
            }
        }else{
            SimpleDialogManager.showErrorDialog(resourceBundle.getString("validation.error"),errorValidationMessages);
        }
    }

    @Override
    public void setPersonInfo(BasePerson person) {

        setBasePersonInfo(person);
        Client client = (Client)person;
        ClientCategory currentClientCategory = client.getClientCategory();
        choboxCategory.getSelectionModel().select((currentClientCategory!=null)? currentClientCategory : categories.get(0));
    }

    private Client editClient(Client editClient){
        if(editClient!=null){
            fillClient(editClient);
        }
        return editClient;
    }

    private void checkAndSave(ActionEvent event, Client client) {
        Client existClient = clientService.findByPhone(client.getPhone());

        if((existClient != null && client.getId() == null) || (existClient != null && existClient.getId().equals(client.getId()))){
            SimpleDialogManager.showErrorDialog(resourceBundle.getString("save.error"),
                    resourceBundle.getString("client.exist.phone.error")+ ": "+ existClient.getFullName());
        }else {
            existClient = clientService.findByFullName(client.getFullName());
            if ((existClient != null && client.getId() == null) || (existClient != null && existClient.getId().equals(client.getId()))) {
                SimpleDialogManager.showErrorDialog(resourceBundle.getString("save.error"),
                        resourceBundle.getString("client.exist.name.error"));

            } else {
                clientService.save(client);
                actionClose(event);
            }
        }
    }

    private Client createClient() {
        Client newClient = new Client();
        fillClient(newClient);
        newClient.setTitleFotoPath(isMale()? MALE_DEFAULT_CLIENT_TITLE_FOTO_PATH : FEMALE_DEFAULT_CLIENT_TITLE_FOTO_PATH);
        return newClient;
    }

    private void fillClient(Client client) {
        client.setFullName(getFullName());
        client.setAddress(getAddress());
        client.setBirthday(getBirthday());
        client.setClientCategory(getCategory());
        client.setEmail(getEmale());
        client.setPhone(getPhone());
        client.setGender(isMale()? GenderType.MALE : GenderType.FEMALE);
        client.setLastChangeDate(LocalDateTime.now());
    }
    private ClientCategory getCategory(){
        return choboxCategory.getSelectionModel().getSelectedItem();
    }
}
