package com.g_manager.controller;

import com.g_manager.entity.Employee;
import com.g_manager.entity.Salary;
import com.g_manager.entity.StaffCategory;
import com.g_manager.entity.base.BasePerson;
import com.g_manager.enums.EmployeeStatus;
import com.g_manager.enums.GenderType;
import com.g_manager.exception.StaffCategoryException;
import com.g_manager.service.EmployeeService;
import com.g_manager.service.StaffCategoryService;
import com.g_manager.utils.SimpleDialogManager;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ChoiceBox;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import java.math.BigDecimal;
import java.net.URL;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.ResourceBundle;

import static org.slf4j.LoggerFactory.getLogger;

/**
 * Created by Nikolenko Oleh on 23.01.2018.
 */
@Controller
public class EmployeeDialogController extends BasePersonDialogController implements Initializable{

    private static final Logger LOGGER = getLogger(EmployeeDialogController.class);

    @FXML
    protected ChoiceBox<StaffCategory> choboxCategory;
    private ObservableList<StaffCategory> categories = FXCollections.observableArrayList();

    @Autowired
    private EmployeeService employeeService;
    @Autowired
    private StaffCategoryService staffCategoryService;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        resourceBundle = resources;
        registerValidators(resourceBundle);

        fillStaffCategoryBox();
    }

    private void fillStaffCategoryBox() {
        try {
            categories.addAll(staffCategoryService.findAllNotEmpty());
        } catch (StaffCategoryException e) {
            LOGGER.error("Staff Category must not be empty",e,e.getCause());
            SimpleDialogManager.showErrorDialog(resourceBundle.getString("fatal.error"),
                    resourceBundle.getString("employee.category.not.found.error"));
            Platform.exit();
        }
        choboxCategory.setItems(categories);
    }

    @Override
    void savePerson(ActionEvent event) {

        fillValidationErrors();
        if(errorValidationMessages.isEmpty()){
            if(isEdit){
                Employee editPersone = editEmployee(employeeService.findOne(personId));
               if(checkAndSavePersone(event, editPersone)) {
                   SimpleDialogManager.showInfoDialog(resourceBundle.getString("employee.editEmployee"),
                           resourceBundle.getString("employee.successful.edit"));
               }
            }else {
                Employee newEmployee = createEmployee();
                if(checkAndSavePersone(event, newEmployee)) {
                    SimpleDialogManager.showInfoDialog(resourceBundle.getString("employee.addNewEmployee"),
                            resourceBundle.getString("employee.successful.add"));
                }
            }
        }else{
            SimpleDialogManager.showErrorDialog(resourceBundle.getString("validation.error"), errorValidationMessages);
        }
    }

    private boolean checkAndSavePersone(ActionEvent event, Employee persone) {
        boolean isSaved = employeeService.checkAndSave(event,persone);
        actionClose(event);
        return isSaved;
    }


    private Employee createEmployee() {
        Employee newEmployee = new Employee();
        fillEmployee(newEmployee);
        newEmployee.setStatus(EmployeeStatus.EMPLOYED);
        newEmployee.setWorkStartDate(LocalDate.now());
        newEmployee.setTitleFotoPath(isMale()? MALE_DEFAULT_EMPLOYEE_TITLE_FOTO_PATH : FEMALE_DEFAULT_EMPLOYEE_TITLE_FOTO_PATH);
        newEmployee.setSalaries(new ArrayList<>());
        newEmployee.getSalaries().add(Salary.newInstance(newEmployee));

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
        employee.setStaffCategory(getCategory());
        employee.setEmail(getEmale());
        employee.setPhone(getPhone());
        employee.setGender(isMale()? GenderType.MALE : GenderType.FEMALE);
        employee.setLastChangeDate(LocalDateTime.now());
        employee.setRate(getRate());
        employee.setWorkHoursPerWeek(getWorkHoursPerWeek());
    }

    @Override
    public void setPersonInfo(BasePerson person) {

        setBasePersonInfo(person);
        Employee employee = (Employee) person;
        StaffCategory currentStaffCategory = employee.getStaffCategory();
        choboxCategory.getSelectionModel().select((currentStaffCategory!=null)? currentStaffCategory : categories.get(0));
        tfldSalaryRate.setText((employee.getRate()!=null)? employee.getRate().toString() : "");
        tfldWorkHoursPerWeek.setText((employee.getWorkHoursPerWeek()!=null)? employee.getWorkHoursPerWeek().toString() : "");
    }

    private StaffCategory getCategory(){
        return choboxCategory.getSelectionModel().getSelectedItem();
    }
    private BigDecimal getRate() {
        if (tfldSalaryRate.getText().isEmpty()) {
            return null;
        } else {
            return new BigDecimal(tfldSalaryRate.getText());
        }
    }

    private BigDecimal getWorkHoursPerWeek() {
        if(tfldWorkHoursPerWeek.getText().isEmpty()){
            return  null;
        }else{
            return new BigDecimal(tfldWorkHoursPerWeek.getText());
        }
    }
}
