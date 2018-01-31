package com.g_manager.controller;

import com.g_manager.entity.Employee;
import com.g_manager.entity.StaffCategory;
import com.g_manager.entity.base.BaseCategory;
import com.g_manager.entity.base.BasePerson;
import com.g_manager.enums.GenderType;
import com.g_manager.service.EmployeeService;
import com.g_manager.service.StaffCategoryService;
import com.g_manager.utils.SimpleDialogManager;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ChoiceBox;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import java.net.URL;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

/**
 * Created by Nikolenko Oleh on 23.01.2018.
 */
@Controller
public class EmployeeDialogController extends BasePersonDialogController implements Initializable{

    @FXML
    protected ChoiceBox<StaffCategory> choboxCategory;
    private ObservableList<StaffCategory> categories = FXCollections.observableArrayList();
    private List<String> errorValidationMessages = new ArrayList<>();

    @Autowired
    private EmployeeService employeeService;
    @Autowired
    private StaffCategoryService staffCategoryService;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        resourceBundle = resources;
        registerValidators(resources);

        categories.addAll(staffCategoryService.findAll());
        choboxCategory.setItems(categories);
    }
    @Override
    void savePerson(ActionEvent event) {

        fillValidationErrors();
        if(errorValidationMessages.isEmpty()){
            if(isEdit){
                Employee editPersone = editEmployee(employeeService.findOne(personId));
                checkAndSave(event,editPersone);
                SimpleDialogManager.showInfoDialog(resourceBundle.getString("employee.editEmployee"),
                        resourceBundle.getString("employee.successful.edit"));
            }else {
                Employee newEmployee = createEmployee();
                checkAndSave(event, newEmployee);
                SimpleDialogManager.showInfoDialog(resourceBundle.getString("employee.addNewEmployee"),
                        resourceBundle.getString("employee.successful.add"));
            }
        }else{
            SimpleDialogManager.showErrorDialog(resourceBundle.getString("validation.error"),errorValidationMessages);
        }
    }

    private void checkAndSave(ActionEvent event, Employee employee) {

        Employee existEmployee = employeeService.findByPhone(employee.getPhone());

        if((existEmployee != null && employee.getId() == null) || (existEmployee != null && existEmployee.getId() != employee.getId())){
            SimpleDialogManager.showErrorDialog(resourceBundle.getString("save.error"),
                    resourceBundle.getString("employee.exist.phone.error")+ ": "+ existEmployee.getFullName());
        }else {
            existEmployee = employeeService.findByFullName(employee.getFullName());
            if ((existEmployee != null && employee.getId() == null) || (existEmployee != null && existEmployee.getId() != employee.getId())) {
                SimpleDialogManager.showErrorDialog(resourceBundle.getString("save.error"),
                        resourceBundle.getString("employee.exist.name.error"));

            } else {
                employeeService.save(employee);
                actionClose(event);
            }
        }

    }

    private Employee createEmployee() {
        Employee newEmployee = new Employee();
        fillEmployee(newEmployee);
        newEmployee.setTitleFotoPath(isMale()? MALE_DEFAULT_EMPLOYEE_TITLE_FOTO_PATH : FEMALE_DEFAULT_EMPLOYEE_TITLE_FOTO_PATH);

        return newEmployee;
    }

    private Employee editEmployee(Employee employee) {
        if(employee != null){
            fillEmployee(employee);
        }
        return employee;
    }

    private void fillEmployee(Employee employee) {
        employee.setFullName(getFullName());
        employee.setAddress(getAddress());
        employee.setBirthday(getBirthday());
        employee.setStaffCategory((StaffCategory)getCategory());
        employee.setEmail(getEmale());
        employee.setPhone(getPhone());
        employee.setGender(isMale()? GenderType.MALE : GenderType.FEMALE);
        employee.setLastChangeDate(LocalDateTime.now());
    }

    @Override
    public void setPersonInfo(BasePerson person) {

        setBasePersonInfo(person);
        Employee employee = (Employee) person;
        StaffCategory currentStaffCategory = employee.getStaffCategory();
        choboxCategory.getSelectionModel().select((currentStaffCategory!=null)? currentStaffCategory : categories.get(0));
    }

    private StaffCategory getCategory(){
        return choboxCategory.getSelectionModel().getSelectedItem();
    }
}