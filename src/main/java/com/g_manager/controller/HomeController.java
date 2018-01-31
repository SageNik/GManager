package com.g_manager.controller;

import com.g_manager.config.StageManager;
import com.g_manager.entity.Client;
import com.g_manager.entity.Employee;
import com.g_manager.enums.GenderType;
import com.g_manager.service.ClientService;
import com.g_manager.service.EmployeeService;
import com.g_manager.utils.SimpleDialogManager;
import com.g_manager.view.FxmlView;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.util.Callback;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Controller;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.time.LocalDate;
import java.time.Period;
import java.util.ResourceBundle;
import java.util.stream.Collectors;

import static com.g_manager.constants.Constants.*;
import static org.slf4j.LoggerFactory.getLogger;

/**
 * Created by Nikolenko Oleh on 06.12.2017.
 */
@Controller
public class HomeController implements Initializable{

    // ----------- Client -----------  //
    @FXML
    private Label addressClient;
    @FXML
    private Label emailClient;
    @FXML
    private Label categoryClient;
    @FXML
    private Label phoneClient;
    @FXML
    private TextField textClientSearch;
    @FXML
    private Label surnameClient;
    @FXML
    private Label birthDayClient;
    @FXML
    private ImageView fotoClient;
    @FXML
    private Label nameClient;
    @FXML
    private Label secondNameClient;
    @FXML
    private Label ageClient;
    @FXML
    private Label genderClient;
    @FXML
    private TableColumn<Client, String> colClientCategory;
    @FXML
    private TableColumn<Client, String> colFullName;
    @FXML
    private TableColumn<Client, Long> colClientId;
    @FXML
    private TableView<Client> tblClients;
    @FXML
    private Label lblNotChooseClient;
    @FXML
    private HBox paneClientInfo;

    private ClientDialogController clientDialogController;
    private Stage stageClientDialog;
    private Client selectedClient = null ;
    private ObservableList<Client> clients = FXCollections.observableArrayList();

    public void addNewClient(ActionEvent actionEvent) {
        LOGGER.info("Adding a new client");
        Client newClient = new Client();
        stageClientDialog = getStageClientDialog(actionEvent);
        clientDialogController.setPersonInfo(newClient);
        clientDialogController.setEditPerson(false, null);
        stageClientDialog.setTitle(resourceBundle.getString("client.addNewClient"));
        stageClientDialog.showAndWait();
        loadClientDetails();
    }

    public void editClient(ActionEvent actionEvent) {

        if(isSelectedClient(selectedClient)){
            LOGGER.info("Editing client with name "+selectedClient.getFullName());
            Long clientId = selectedClient.getId();

            stageClientDialog = getStageClientDialog(actionEvent);
            clientDialogController.setPersonInfo(selectedClient);
            clientDialogController.setEditPerson(true, clientId);
            stageClientDialog.setTitle(resourceBundle.getString("client.editClient"));
            stageClientDialog.showAndWait();
            loadClientDetails();

            selectedClient = clientService.findOne(clientId);
            tableClientSelect();
        }
    }

    private void searchClient() {
        String serchString = textClientSearch.getText();
        if(serchString!=null){
            ObservableList<Client> foundClients = clients.stream()
                    .filter(x -> x.getFullName().toLowerCase().contains(serchString.toLowerCase()))
                    .collect(Collectors.toCollection(FXCollections::observableArrayList));
            tblClients.setItems(foundClients);
        }else{
            loadClientDetails();
        }
    }

    private void tableClientSelect() {
        if(selectedClient != null){

            fillClientInfo();

            lblNotChooseClient.setVisible(false);
            paneClientInfo.setVisible(true);
        }else{
            lblNotChooseClient.setVisible(true);
            paneClientInfo.setVisible(false);
        }
    }

    private void fillClientInfo() {
        String[] fullName = ((selectedClient.getFullName()!=null)? selectedClient.getFullName().split(" "): new String[0]);
        surnameClient.setText((fullName.length>=1)? fullName[0] : "");
        nameClient.setText((fullName.length>=2)? fullName[1] : "");
        secondNameClient.setText((fullName.length>=3)? fullName[2]: "");
        addressClient.setText((selectedClient.getAddress()!=null)? selectedClient.getAddress() : "");
        emailClient.setText((selectedClient.getEmail()!=null)? selectedClient.getEmail() : "");
        phoneClient.setText((selectedClient.getPhone()!=null)? selectedClient.getPhone() : "");
        genderClient.setText((selectedClient.getGender()!=null)? getClientGender() : "");
        categoryClient.setText((selectedClient.getClientCategory()!=null)? selectedClient.getClientCategory().toString() : "");
        birthDayClient.setText((selectedClient.getBirthday()!=null)? selectedClient.getBirthday().toString() : "");
        ageClient.setText(getClientAge(selectedClient.getBirthday()));
        Image image = new Image(selectedClient.getTitleFotoPath());
        fotoClient.setImage(image);
    }

    private String getClientAge(LocalDate birthday){
        Integer age = 0;
        if(selectedClient.getBirthday()!=null){
            age = Period.between(birthday, LocalDate.now()).getYears();
        }
        return age.toString();
    }

    private String getClientGender(){
        return (selectedClient.getGender()== GenderType.MALE)? resourceBundle.getString("male")
                : resourceBundle.getString("female");
    }

    /*
    *  Add All clients to observable list and update table
    */
    private void loadClientDetails() {
        clients.clear();
        clients.addAll(clientService.findAll());
        tblClients.setItems(clients);
    }

    private Stage getStageClientDialog(ActionEvent actionEvent) {
        if(stageClientDialog == null) {
            stageClientDialog = stageManager.getStageDialog(FxmlView.CLIENT_DIALOG, actionEvent);
            clientDialogController = (ClientDialogController) stageManager.getDialogController();
        }
        return stageClientDialog;
    }

    private boolean isSelectedClient(Client selectedClient){
        if (selectedClient == null) {
            SimpleDialogManager.showInfoDialog(resourceBundle.getString("alert.titleAttention"),
                    resourceBundle.getString("client.chooseMessage"));
            return false;
        }
        return  true;
    }

    public void changeFotoClient(ActionEvent actionEvent) {
        LOGGER.info("Start to change client foto");
        String folderPath = "/src/main/resources/images/clients/titleFoto/";
        changeFoto(folderPath);
    }

    //  ----------- Employee -----------  //

    @FXML
    private TextField textEmployeeSearch;
    @FXML
    private Label lblNotChooseEmployee;
    @FXML
    private HBox paneEmployeeInfo;
    @FXML
    private ImageView fotoEmployee;
    @FXML
    private Button btnChangeFotoEmployee;
    @FXML
    private Label surnameEmployee;
    @FXML
    private Label nameEmployee;
    @FXML
    private Label secondNameEmployee;
    @FXML
    private Label addressEmployee;
    @FXML
    private Label emailEmployee;
    @FXML
    private Label categoryEmployee;
    @FXML
    private Label phoneEmployee;
    @FXML
    private Label ageEmployee;
    @FXML
    private Label genderEmployee;
    @FXML
    private Label birthDayEmployee;
    @FXML
    private TableColumn<Employee, Long> colEmployeeId;
    @FXML
    private TableView<Employee> tblEmployees;
    @FXML
    private TableColumn<Employee, String> colFullNameEmployee;
    @FXML
    private TableColumn<Employee, String> colEmployeeCategory;

    private EmployeeDialogController employeeDialogController;
    private Stage stageEmployeeDialog;
    private Employee selectedEmployee = null ;
    private ObservableList<Employee> employees = FXCollections.observableArrayList();


   public void addNewEmployee(ActionEvent event) {

        LOGGER.info("Adding a new employee");
        Employee newEmployee = new Employee();
        stageEmployeeDialog = getStageEmployeeDialog(event);
        employeeDialogController.setPersonInfo(newEmployee);
        employeeDialogController.setEditPerson(false, null);
        stageEmployeeDialog.setTitle(resourceBundle.getString("employee.addNewEmployee"));
        stageEmployeeDialog.showAndWait();
        loadEmployeeDetails();
    }

   public void editEmployee(ActionEvent event) {

        if(isSelectedEmployee(selectedEmployee)){
            LOGGER.info("Editing employee with name "+selectedEmployee.getFullName());
            Long employeeId = selectedEmployee.getId();

            stageEmployeeDialog = getStageEmployeeDialog(event);
            employeeDialogController.setPersonInfo(selectedEmployee);
            employeeDialogController.setEditPerson(true, employeeId);
            stageEmployeeDialog.setTitle(resourceBundle.getString("employee.editEmployee"));
            stageEmployeeDialog.showAndWait();
            loadEmployeeDetails();

            selectedEmployee = employeeService.findOne(employeeId);
            tableEmployeeSelect();
        }
    }

    @FXML
    void changeFotoEmployee(ActionEvent event) {
        LOGGER.info("Start to change employee foto");
        String folderPath = "/src/main/resources/images/employees/titleFoto/";
        changeFoto(folderPath);
    }

    private void searchEmployee() {
        String serchString = textEmployeeSearch.getText();
        if(serchString!=null){
            ObservableList<Employee> foundEmployees = employees.stream()
                    .filter(x -> x.getFullName().toLowerCase().contains(serchString.toLowerCase()))
                    .collect(Collectors.toCollection(FXCollections::observableArrayList));
            tblEmployees.setItems(foundEmployees);
        }else{
            loadClientDetails();
        }
    }

    private void tableEmployeeSelect() {
        if(selectedEmployee != null){

            fillEmployeeInfo();

            lblNotChooseEmployee.setVisible(false);
            paneEmployeeInfo.setVisible(true);
        }else{
            lblNotChooseEmployee.setVisible(true);
            paneEmployeeInfo.setVisible(false);
        }
    }

    private void fillEmployeeInfo() {
        String[] fullName = ((selectedEmployee.getFullName()!=null)? selectedEmployee.getFullName().split(" "): new String[0]);
        surnameEmployee.setText((fullName.length>=1)? fullName[0] : "");
        nameEmployee.setText((fullName.length>=2)? fullName[1] : "");
        secondNameEmployee.setText((fullName.length>=3)? fullName[2]: "");
        addressEmployee.setText((selectedEmployee.getAddress()!=null)? selectedEmployee.getAddress() : "");
        emailEmployee.setText((selectedEmployee.getEmail()!=null)? selectedEmployee.getEmail() : "");
        phoneEmployee.setText((selectedEmployee.getPhone()!=null)? selectedEmployee.getPhone() : "");
        genderEmployee.setText((selectedEmployee.getGender()!=null)? getEmployeeGender() : "");
        categoryEmployee.setText((selectedEmployee.getStaffCategory()!=null)? selectedEmployee.getStaffCategory().toString() : "");
        birthDayEmployee.setText((selectedEmployee.getBirthday()!=null)? selectedEmployee.getBirthday().toString() : "");
        ageEmployee.setText(getEmployeeAge(selectedEmployee.getBirthday()));
        Image image = new Image(selectedEmployee.getTitleFotoPath());
        fotoEmployee.setImage(image);
    }

    private String getEmployeeAge(LocalDate birthday){
        Integer age = 0;
        if(selectedEmployee.getBirthday()!=null){
            age = Period.between(birthday, LocalDate.now()).getYears();
        }
        return age.toString();
    }

    private String getEmployeeGender(){
        return (selectedEmployee.getGender()== GenderType.MALE)? resourceBundle.getString("male")
                : resourceBundle.getString("female");
    }

   // Add All employee to observable list and update table
    private void loadEmployeeDetails() {
        employees.clear();
        employees.addAll(employeeService.findAll());
        tblEmployees.setItems(employees);
    }

    private Stage getStageEmployeeDialog(ActionEvent actionEvent) {
        if(stageEmployeeDialog == null) {
            stageEmployeeDialog = stageManager.getStageDialog(FxmlView.EMPLOYEE_DIALOG, actionEvent);
            employeeDialogController = (EmployeeDialogController) stageManager.getDialogController();
        }
        return stageEmployeeDialog;
    }

    private boolean isSelectedEmployee(Employee selectedEmployee){
        if (selectedEmployee == null) {
            SimpleDialogManager.showInfoDialog(resourceBundle.getString("alert.titleAttention"),
                    resourceBundle.getString("employee.chooseMessage"));
            return false;
        }
        return  true;
    }


    //  ----------- Home  -----------  //

    private ResourceBundle resourceBundle;

    @Lazy
    @Autowired
    private StageManager stageManager;
    @Autowired
    private ClientService clientService;
    @Autowired
    private EmployeeService employeeService;

    private static final Logger LOGGER = getLogger(HomeController.class);

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        resourceBundle = resources;
        setColumnProperties();
        loadClientDetails();
        loadEmployeeDetails();

        addListeners();

    }

    private void addListeners() {
        tblClients.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if(newValue !=null) {
                selectedClient = tblClients.getSelectionModel().getSelectedItem();
                tableClientSelect();
            }
        });
        tblEmployees.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if(newValue !=null) {
                selectedEmployee = tblEmployees.getSelectionModel().getSelectedItem();
                tableEmployeeSelect();
            }
        });
        textClientSearch.textProperty().addListener((observable, oldValue, newValue) -> {
            if(newValue!=null){
                searchClient();
            }
        });
        textEmployeeSearch.textProperty().addListener((observable, oldValue, newValue) -> {
            if(newValue!=null){
                searchEmployee();
            }
        });
    }

    private void setColumnProperties() {
        // --- Client ---
        colClientId.setCellValueFactory(new PropertyValueFactory<>("id"));
        colFullName.setCellValueFactory(new PropertyValueFactory<>("fullName"));
        colClientCategory.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<Client, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TableColumn.CellDataFeatures<Client, String> param) {
                return  new SimpleStringProperty(param.getValue().getClientCategory().getCategory());
            }
        });
        // --- Employee ---
        colEmployeeId.setCellValueFactory(new PropertyValueFactory<>("id"));
        colFullNameEmployee.setCellValueFactory(new PropertyValueFactory<>("fullName"));
        colEmployeeCategory.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<Employee, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TableColumn.CellDataFeatures<Employee, String> param) {
                return  new SimpleStringProperty(param.getValue().getStaffCategory().getCategory());
            }
        });
    }

    private void changeTitleFoto(File newFotoFile) {

        LOGGER.debug("Changing user`s title foto");
        String filePath = null;
        try {
            filePath = newFotoFile.toURI().toURL().toString();
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
            if(filePath!= null) {

            String oldFotoPath = selectedClient.getTitleFotoPath();
            selectedClient.setTitleFotoPath(newFotoFile.getPath());
                Image image = new Image(filePath);
                fotoClient.setImage(image);
                if(SimpleDialogManager.showConfirmDialog(resourceBundle.getString("changeFoto"),
                        resourceBundle.getString("client.change.foto.confirm"))){
                        clientService.save(selectedClient);
                    deleteOldFoto(oldFotoPath);
                    LOGGER.debug("Foto changed. New foto path: "+filePath);
                }else {
                    selectedClient.setTitleFotoPath(oldFotoPath);
                    tableClientSelect();
                    LOGGER.debug("Foto didn't change");
                }
            }
    }

    private void deleteOldFoto(String oldFotoPath) {
        if(!oldFotoPath.equals(MALE_DEFAULT_CLIENT_TITLE_FOTO_PATH) && !oldFotoPath.equals(FEMALE_DEFAULT_CLIENT_TITLE_FOTO_PATH) &&
                !oldFotoPath.equals(MALE_DEFAULT_EMPLOYEE_TITLE_FOTO_PATH) && !oldFotoPath.equals(FEMALE_DEFAULT_EMPLOYEE_TITLE_FOTO_PATH)) {
            try {
               Files.deleteIfExists(Paths.get(oldFotoPath));
                LOGGER.info("The old foto with path: " + oldFotoPath + "deleted");
            } catch (IOException e) {
                LOGGER.info("Fail delete old foto with path: " + oldFotoPath);
            }
        }
    }

    private void changeFoto(String folderPath) {
        File newFotoFile = choiceNewFoto();
        if(newFotoFile != null) {
            File savedFoto = downloadFoto(newFotoFile,folderPath);
            if(savedFoto !=null){
                changeTitleFoto(savedFoto);
            }
        }
    }

    private File choiceNewFoto() {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle(resourceBundle.getString("downloadFoto.title"));
        FileChooser.ExtensionFilter imageFilter = new FileChooser.ExtensionFilter("Image files (*.jpg, *.png, *.bmp,*.gif)",
                "*.jpg", "*.png", "*.bmp", "*.gif");
        fileChooser.getExtensionFilters().add(imageFilter);

        return fileChooser.showOpenDialog(stageManager.getPrimaryStage());
    }

    private File downloadFoto(File file,String folderPath) {

        String currentDir = System.getProperty("user.dir");
        File dest = new File(currentDir + folderPath + file.getName());

        try {
            LOGGER.info("Try to copy foto to "+ dest.getPath()+ " from "+ file.getPath());
            Files.copy(file.toPath(), dest.toPath(), StandardCopyOption.REPLACE_EXISTING);
        } catch (IOException ex) {
            LOGGER.error("Copy foto to "+ dest.getPath()+ " from "+ file.getPath()+ " failed");
        }
        return dest;
    }
}
