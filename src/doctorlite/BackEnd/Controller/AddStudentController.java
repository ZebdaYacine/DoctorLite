/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package doctorlite.BackEnd.Controller;

import com.jfoenix.controls.JFXComboBox;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import doctorlite.BackEnd.Mapper.Mapping;
/*<<<<<<< HEAD
import schoolmanager.BackEnd.Model.Student;
import schoolmanager.BackEnd.Results;
=======*/
import doctorlite.BackEnd.Model.Belongs;
import doctorlite.BackEnd.Model.Group;
import doctorlite.BackEnd.Model.Student;
import doctorlite.BackEnd.Results;
import doctorlite.BackEnd.Service.BelongsService;
import doctorlite.BackEnd.Service.SectionService;
import doctorlite.BackEnd.Service.StudentService;
import doctorlite.BackEnd.uiPresenter.UiStudent;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.scene.input.KeyEvent;

import static doctorlite.BackEnd.Controller.BelongsController.*;

/**
 * FXML Controller class
 *
 * @author kadri
 */
public class AddStudentController implements Initializable {


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
    private Label firstName_err;
    @FXML
    private Label lastName_err;
    @FXML
    private Label phone1_err;
    @FXML
    private Label phone2_err;

    private Student std = new Student();

    private UiStudent uistd = new UiStudent();

/*<<<<<<< HEAD
=======*/
    public static Group group = new Group();

/*
>>>>>>> e3bc2b46c7698858d23de104e75bf0784d826522
*/


    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        ObservableList<String> sectionsName = SectionService.getAllObjectName("section");
        this.sectionName.setItems(sectionsName);
        uistd = new UiStudent(firstName, lastName, phone2, phone1, sectionName,
                firstName_err, lastName_err, phone1_err, phone2_err);
    }

    public static void refrechStudent(TableView table, TableColumn Column1, TableColumn Column2,
            TableColumn Column3, TableColumn Column4, TableColumn Column5, Student std, String type) {
/*<<<<<<< HEAD
        ObservableList<Student> pr;
        if (type.equals("searche")) {
            pr = StudentService.searchStudentByName(std);
=======*/
        ObservableList<Student> pr = null;
        if (type.equals("searche")) {
            pr = StudentService.searchStudentByName(std);
        }else if(type.equals("belongs")){
            pr = BelongsService.getStudentsOfGroup(group);
/*
>>>>>>> e3bc2b46c7698858d23de104e75bf0784d826522
*/
        } else {
            pr = StudentService.getAllStudents("", new Student());
        }
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

/*<<<<<<< HEAD*/
/*    @FXML
    private void add(ActionEvent event) {
        std = Mapping.getObjectStudentFromUiStudent(uistd);
        if (std != null) {
            Results.Rstls r = StudentService.addStudent(std);
            if (r == Results.Rstls.OBJECT_NOT_INSERTED) {
                CommunController.alert(r.toString());
            } else {
                uistd.clearInputs();
                refrechStudent(belongsTable1, firstNameC11, lastNameC11, phone1C11,
                        phone2C11, sectionNameC11, new Student(), "belongs");
                refrechStudent(studentTable1, firstNameC10, lastNameC10, phone1C10,
                        phone2C10, sectionNameC10, new Student(), "");
            }
        }
    }*/
/*
=======*/
    public void setInputs(Group grp) {
        group = grp;
    }

    @FXML
    private void add() {
        std = Mapping.getObjectStudentFromUiStudent(uistd);
        long lastId=0;
        if (std != null) {
            //Results.Rstls r = StudentService.addStudent(std);
            lastId =StudentService.addStudentWithGetLastId(std);
            std.setId(lastId);
            if (lastId==0) {
                CommunController.alert("حدث خطأ في عملية الإدخال");
            } else {
                uistd.clearInputs();
                refrechStudent(studentTable1, firstNameC10, lastNameC10, phone1C10,
                        phone2C10, sectionNameC10, new Student(), "");
                if (group.getNbrRest() > 0) {
                    Belongs blg=  new Belongs(std.getId(), group.getId());
                    blg.PresentGroupe();
                    Results.Rstls r =BelongsService.addBelongs(blg);
                    if(r== Results.Rstls.OBJECT_INSERTED){
                        refrechStudent(belongsTable1, firstNameC11, lastNameC11, phone1C11,
                                phone2C11, sectionNameC11, new Student(), "belongs");
                    }else {
                        CommunController.alert("حدث خطأ في عملية الإدخال 1");
                    }
                    group.setNbrRest(group.getNbrPlace() - (BelongsService.getStudentsOfGroup(group).size()));
                    nbrPlace1.setText(Integer.toString(group.getNbrRest()));
                } else {
                    CommunController.alert("فوج ممتلى ");
                }
            }
        }
    }
/*>>>>>>> e3bc2b46c7698858d23de104e75bf0784d826522*/

    @FXML
    private void hotkey(KeyEvent event) {
        switch (event.getCode()) {
            case ENTER:
                add();
                break;
            default:
                break;
        }
    }
}
