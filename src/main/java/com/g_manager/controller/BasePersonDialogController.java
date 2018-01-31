package com.g_manager.controller;

import com.g_manager.constants.Constants;
import com.g_manager.entity.base.BaseCategory;
import com.g_manager.entity.base.BasePerson;
import com.g_manager.enums.GenderType;
import com.g_manager.utils.ValidationManager;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.stage.Stage;
import org.controlsfx.validation.ValidationSupport;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

/**
 * Created by Nikolenko Oleh on 23.01.2018.
 */
public abstract class BasePersonDialogController implements Constants{

    @FXML
    protected TextField tfldName;

    @FXML
    protected TextArea tfldAddress;

    @FXML
    protected ToggleGroup gender;

    @FXML
    protected RadioButton rbtnFemale;

    @FXML
    protected Button btnSaveClient;

    @FXML
    protected TextField tfldSecondName;

    @FXML
    protected DatePicker datepicBirthday;

    @FXML
    protected TextField tfldEmail;

    @FXML
    protected TextField tfldSurname;

    @FXML
    protected Button btnCancel;

    @FXML
    protected RadioButton rbtnMale;

    @FXML
    protected TextField tfldPhone;

    protected Boolean isEdit;
    protected Long personId = null;

    protected ResourceBundle resourceBundle;
    private ValidationSupport validation;
    private ValidationManager validationManager;
    private List<String> errorValidationMessages = new ArrayList<>();

    @FXML
    void actionClose(ActionEvent event) {
        Node source = (Node) event.getSource();
        Stage stage = (Stage) source.getScene().getWindow();
        stage.hide();
    }

    @FXML
   abstract void savePerson(ActionEvent event) ;

   public abstract void setPersonInfo(BasePerson person) ;


    protected void setBasePersonInfo(BasePerson person) {
        String[] fullName;
        fullName = ((person.getFullName()!=null)? person.getFullName().split(" "): new String[0]);
        tfldName.setText((fullName.length>=1)? fullName[0] : "");
        tfldSurname.setText((fullName.length>=2)? fullName[1] : "");
        tfldSecondName.setText((fullName.length>=3)? fullName[2]: "");
        tfldAddress.setText((person.getAddress()!=null)? person.getAddress() : "");
        tfldEmail.setText((person.getEmail()!=null)? person.getEmail() : "");
        tfldPhone.setText((person.getPhone()!=null)? person.getPhone() : "");
        datepicBirthday.setValue(person.getBirthday());
        if(person.getGender() == null){
            rbtnMale.setSelected(true);
        }else {
            rbtnMale.setSelected(person.getGender() == GenderType.MALE);
            rbtnFemale.setSelected(person.getGender() == GenderType.FEMALE);
        }
    }

    public void setEditPerson(Boolean isEdit, Long editPersonId ){
        this.isEdit = isEdit;
        personId = editPersonId;
    }

    protected void fillValidationErrors() {
        errorValidationMessages.clear();
        if(validation.getHighestMessage(tfldName).isPresent()){
            errorValidationMessages.add( validation.getHighestMessage(tfldName).get().getText());}
        if(validation.getHighestMessage(tfldSurname).isPresent()){
            errorValidationMessages.add( validation.getHighestMessage(tfldSurname).get().getText());}
        if(validation.getHighestMessage(tfldPhone).isPresent()){
            errorValidationMessages.add( validation.getHighestMessage(tfldPhone).get().getText());}
        if(validation.getHighestMessage(tfldEmail).isPresent()){
            errorValidationMessages.add( validation.getHighestMessage(tfldEmail).get().getText());}
    }

    protected void registerValidators(ResourceBundle resources) {
        validationManager = new ValidationManager(resources);
        validation = new ValidationSupport();

        validation.registerValidator(tfldName,true, validationManager::checkName);
        validation.registerValidator(tfldSurname,true, validationManager::checkSurname);
        validation.registerValidator(tfldPhone,true, validationManager::checkPhone);
        validation.registerValidator(tfldEmail,true, validationManager::checkEmail);
    }

    protected String getFullName (){
        return tfldSurname.getText() +" "+ tfldName.getText() +" "+ tfldSecondName.getText();
    }
    protected String getPhone(){
        return tfldPhone.getText();
    }
    protected String getEmale(){
        return tfldEmail.getText();
    }
    protected String getAddress(){
        return tfldAddress.getText();
    }
    protected Boolean isMale(){
        return gender.getToggles().get(0).isSelected();
    }
    protected LocalDate getBirthday(){
        return datepicBirthday.getValue();
    }
}
