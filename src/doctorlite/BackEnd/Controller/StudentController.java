/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package doctorlite.BackEnd.Controller;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;

import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;

import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import static doctorlite.BackEnd.Controller.LoginController.loginUser;
import doctorlite.BackEnd.Mapper.Mapping;
import doctorlite.BackEnd.Model.Student;
import doctorlite.BackEnd.Results;
import doctorlite.BackEnd.Service.SectionService;
import doctorlite.BackEnd.Service.StudentService;
import doctorlite.BackEnd.uiPresenter.UiStudent;
import static doctorlite.DoctorLite.alertUpdate;

/**
 * FXML Controller class
 *
 * @author kadri
 */
public class StudentController implements Initializable {

    @FXML
    private TableView<?> studentTable;
    @FXML
    private TextField firstName;
    @FXML
    private TextField lastName;
    @FXML
    private TextField phone2;
    @FXML
    private TextField phone1;
    @FXML
    private JFXComboBox sectionName;
    @FXML
    private TableColumn<?, ?> firstNameC;
    @FXML
    private TableColumn<?, ?> lastNameC;
    @FXML
    private TableColumn<?, ?> phone1C;
    @FXML
    private TableColumn<?, ?> phone2C;
    @FXML
    private TableColumn<?, ?> sectionNameC;
    @FXML
    private Label firstName_err;
    @FXML
    private Label lastName_err;
    @FXML
    private Label phone1_err;
    @FXML
    private Label phone2_err;

    private Student std = new Student();

    private UiStudent uistd = new UiStudent();

    @FXML
    private JFXButton delete;
    @FXML
    private JFXButton update;


    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        ObservableList<String> sectionsName = SectionService.getAllObjectName("section");
        this.sectionName.setItems(sectionsName);
        /*enableSearch.setOnAction(((event) -> {
            if (enableSearch.isSelected()) {
                if (!firstName.getText().equals("")) {
                    std.setFirstName(firstName.getText());
                    refrechStudent(studentTable, firstNameC, lastNameC, phone1C, phone2C, sectionNameC,
                            std, "searche");
                } else {
                    enableSearch.setSelected(false);
                }
            } else {
                uistd.clearInputs();
                refrechStudent(studentTable, firstNameC, lastNameC, phone1C, phone2C, sectionNameC,
                        std, "");
            }
        }));*/
        firstName.setOnKeyTyped(((event) -> {
            if (firstName.getText().equals("")) {
                std.setFirstName(firstName.getText());
                refrechStudent(studentTable, firstNameC, lastNameC, phone1C, phone2C, sectionNameC,
                        std, "searche");
                //enableSearch.setSelected(false);
            }else{
                refrechStudent(studentTable, firstNameC, lastNameC, phone1C, phone2C, sectionNameC, new Student(), "");
            }
        }));
        uistd = new UiStudent(firstName, lastName, phone2, phone1, sectionName,
                firstName_err, lastName_err, phone1_err, phone2_err);
        if (loginUser.getRole().equals("simple")) {
            update.setVisible(false);
            delete.setVisible(false);
        } else {
            update.setVisible(true);
            delete.setVisible(true);
        }
        refrechStudent(studentTable, firstNameC, lastNameC, phone1C, phone2C, sectionNameC, new Student(), "");

    }

    public static void refrechStudent(TableView table, TableColumn Column1, TableColumn Column2,
            TableColumn Column3, TableColumn Column4, TableColumn Column5, Student std, String type) {
        ObservableList<Student> pr;
        if (type.equals("search")) {
            pr = StudentService.searchStudentByName(std);
        } else {
            pr = StudentService.getAllStudents("", new Student());
        }
        pr.get(0).PresentObject();
        Column1.setCellValueFactory(
                new PropertyValueFactory<>("firstName")
        );
        Column2.setCellValueFactory(
                new PropertyValueFactory<>("lastName")
        );
        Column3.setCellValueFactory(
                new PropertyValueFactory<>("phone1")
        );
        Column4.setCellValueFactory(
                new PropertyValueFactory<>("phone2")
        );
        Column5.setCellValueFactory(
                new PropertyValueFactory<>("sectionName")
        );
        table.setItems(pr);
    }

    @FXML
    private void add( ) {
        std = Mapping.getObjectStudentFromUiStudent(uistd);
        std.PresentObject();
        if (std != null) {
            Results.Rstls r = StudentService.addStudent(std);
            if (r == Results.Rstls.OBJECT_NOT_INSERTED) {
                CommunController.alert(r.toString());
            } else {
                uistd.clearInputs();
            }
            refrechStudent(studentTable, firstNameC, lastNameC, phone1C, phone2C, sectionNameC, new Student(), "");
        }
    }

    @FXML
    private void update(ActionEvent event) {
        if (std.getId() != 0) {
            Student newStd = Mapping.getObjectStudentFromUiStudent(uistd);
            newStd.setId(std.getId());
            Optional<ButtonType> option = alertUpdate.showAndWait();
            if (option.get() == ButtonType.OK) {
                Results.Rstls r = StudentService.updateStudent(newStd);
                if (r == Results.Rstls.OBJECT_NOT_UPDATED) {
                    CommunController.alert(r.toString());
                } else {
                    uistd.clearInputs();
                    refrechStudent(studentTable, firstNameC, lastNameC, phone1C, phone2C, sectionNameC, new Student(), "");
                }
                std = new Student();
                uistd.clearInputs();
            }
        }
    }

    @FXML
    private void delete( ) {
        if (std.getId() != 0) {
            Optional<ButtonType> option = alertUpdate.showAndWait();
            if (option.get() == ButtonType.OK) {
                Results.Rstls r = StudentService.deleteStudent(std);
                if (r == Results.Rstls.OBJECT_NOT_DELETED) {
                    CommunController.alert(r.toString());
                } else {
                    uistd.clearInputs();
                    refrechStudent(studentTable, firstNameC, lastNameC, phone1C, phone2C, sectionNameC, new Student(), "");
                }
                std = new Student();
                uistd.clearInputs();
            }
        }
    }

    @FXML
    private void selectStudent(MouseEvent event) {
        uistd.clearInputs();
        std = (Student) studentTable.getSelectionModel().getSelectedItem();
        if (std != null) {
            firstName.setText(std.getFirstName());
            lastName.setText(std.getLastName());
            phone1.setText(std.getPhone1());
            phone2.setText(std.getPhone2());
            sectionName.getSelectionModel().select(std.getSectionName());
            std.PresentObject();
            uistd = new UiStudent(firstName, lastName, phone2, phone1, sectionName,
                    firstName_err, lastName_err, phone1_err, phone2_err);
        }
    }

    @FXML
    private void searchByName(KeyEvent event) {
        Student std= new Student();
        String name = firstName.getText();
        System.out.println(name);
       if (!name.isEmpty()) {
            std.setFirstName(name);
            refrechStudent(studentTable, firstNameC, lastNameC, phone1C,
                    phone2C, sectionNameC, std, "search");
        }
    }

    @FXML
    private void hotkey(KeyEvent event) {
        switch (event.getCode()) {
            case ENTER:
                add();
                break;
            case DELETE:
                delete();
                break;
            default:
                break;
        }
    }

}
