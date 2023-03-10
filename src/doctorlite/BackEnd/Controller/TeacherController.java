/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package doctorlite.BackEnd.Controller;

import com.jfoenix.controls.JFXButton;

import java.net.URL;
import java.sql.SQLException;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
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
import doctorlite.BackEnd.Model.Teacher;
import doctorlite.BackEnd.Results;
import doctorlite.BackEnd.Service.TeacherService;
import doctorlite.BackEnd.uiPresenter.UiTeacher;
import static doctorlite.DoctorLite.alertUpdate;

/**
 * FXML Controller class
 *
 * @author kadri
 */
public class TeacherController implements Initializable {

    @FXML
    private TableView<?> teacherTable;
    @FXML
    private TextField firstName;
    @FXML
    private TextField lastName;
    @FXML
    private TextField phone;
    @FXML
    private TextField workePlace;
    @FXML
    private TableColumn<?, ?> firstNameC;
    @FXML
    private TableColumn<?, ?> lastNameC;
    @FXML
    private TableColumn<?, ?> phoneC;
    @FXML
    private TableColumn<?, ?> workPlaceC;
    @FXML
    private Label firstName_err;
    @FXML
    private Label lastName_err;
    @FXML
    private Label phone_err;
    @FXML
    private Label workeSpace_err;

    private Teacher tech = new Teacher();

    private UiTeacher uitech = new UiTeacher();

    @FXML
    private JFXButton delete;
    @FXML
    private JFXButton update;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        uitech = new UiTeacher(firstName, lastName, phone, workePlace,
                firstName_err, lastName_err, phone_err, workeSpace_err);
        if (loginUser.getRole().equals("simple")) {
            update.setVisible(false);
            delete.setVisible(false);
        } else {
            update.setVisible(true);
            delete.setVisible(true);
        }
        try {
            refrechTeacher(teacherTable, firstNameC, lastNameC,
                    phoneC, workPlaceC, new Teacher(), "");
        } catch (SQLException ex) {
            Logger.getLogger(TeacherController.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    public void refrechTeacher(TableView table, TableColumn Column1, TableColumn Column2,
            TableColumn Column3, TableColumn Column4, Teacher tech, String type)
            throws SQLException {
        ObservableList<Teacher> pr;
        if (type.equals("searche")) {
            pr = TeacherService.searchTeacherByName(tech);
        } else {
            pr = TeacherService.getAllTeachers();
        }
        Column1.setCellValueFactory(
                new PropertyValueFactory<>("firstName")
        );
        Column2.setCellValueFactory(
                new PropertyValueFactory<>("lastName")
        );
        Column3.setCellValueFactory(
                new PropertyValueFactory<>("phone")
        );
        Column4.setCellValueFactory(
                new PropertyValueFactory<>("workePlace")
        );
        table.setItems(pr);

    }

    @FXML
    private void add( ) {
        tech = Mapping.getObjectTeacherFromUiTeacher(uitech);
        if (tech != null) {
            Results.Rstls r = TeacherService.addTeacher(tech);
            if (r == Results.Rstls.OBJECT_NOT_INSERTED) {
                CommunController.alert(r.toString());
            } else {
                uitech.clearInputs();
                try {
                    refrechTeacher(teacherTable, firstNameC, lastNameC, phoneC,
                            workPlaceC, new Teacher(), "");
                } catch (SQLException ex) {
                    Logger.getLogger(TeacherController.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
    }

    @FXML
    private void update(ActionEvent event) {
        if (tech.getId() != 0) {
            Teacher newtech = Mapping.getObjectTeacherFromUiTeacher(uitech);
            newtech.setId(tech.getId());
            Optional<ButtonType> option = alertUpdate.showAndWait();
            if (option.get() == ButtonType.OK) {
                Results.Rstls r = TeacherService.updateTeacher(newtech);
                if (r == Results.Rstls.OBJECT_NOT_UPDATED) {
                    CommunController.alert(r.toString());
                } else {
                    try {
                        refrechTeacher(teacherTable, firstNameC, lastNameC,
                                phoneC, workPlaceC, new Teacher(), "");
                    } catch (SQLException ex) {
                        Logger.getLogger(TeacherController.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
                tech = new Teacher();
                uitech.clearInputs();
            }
        }
    }

    @FXML
    private void delete( ) {
        if (tech.getId() != 0) {
            Teacher newtech = Mapping.getObjectTeacherFromUiTeacher(uitech);
            newtech.setId(tech.getId());
            Optional<ButtonType> option = alertUpdate.showAndWait();
            if (option.get() == ButtonType.OK) {
                Results.Rstls r = TeacherService.deleteTeacher(newtech);
                if (r == Results.Rstls.OBJECT_NOT_UPDATED) {
                    CommunController.alert(r.toString());
                } else {
                    try {
                        refrechTeacher(teacherTable, firstNameC, lastNameC, phoneC,
                                workPlaceC, new Teacher(), "");
                    } catch (SQLException ex) {
                        Logger.getLogger(TeacherController.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
                tech = new Teacher();
                uitech.clearInputs();
            }
        }
    }

    @FXML
    private void selectTeacher(MouseEvent event) {
        uitech.clearInputs();
        tech = (Teacher) teacherTable.getSelectionModel().getSelectedItem();
        if (tech != null) {
            firstName.setText(tech.getFirstName());
            lastName.setText(tech.getLastName());
            phone.setText(tech.getPhone());
            workePlace.setText(tech.getWorkePlace());
            uitech = new UiTeacher(firstName, lastName, phone, workePlace,
                    firstName_err, lastName_err, phone_err, workeSpace_err);
        }
    }

    @FXML
    private void searchTeacher(KeyEvent event) throws SQLException {
        Teacher t = new Teacher();
        if (firstName.getText().isEmpty()) {
            refrechTeacher(teacherTable, firstNameC,
                    lastNameC, phoneC, workPlaceC,
                    t, "");
        } else {
            t.setFirstName(firstName.getText());
            refrechTeacher(teacherTable, firstNameC,
                    lastNameC, phoneC, workPlaceC,
                    t, "searche");
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
