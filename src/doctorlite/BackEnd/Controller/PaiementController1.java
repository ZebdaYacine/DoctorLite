/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package doctorlite.BackEnd.Controller;

import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import net.sf.jasperreports.engine.JRException;
import doctorlite.BackEnd.Model.Group;
import doctorlite.BackEnd.Model.Seance;
import doctorlite.BackEnd.Model.Student;
import doctorlite.BackEnd.Printer.Print;
import doctorlite.BackEnd.Service.BelongsService;
import doctorlite.BackEnd.Service.StudentService;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;

import static doctorlite.DoctorLite.SecondStage;

/*import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.design.JasperDesign;
import net.sf.jasperreports.engine.xml.JRXmlLoader;
import net.sf.jasperreports.view.JasperViewer;*/

/**
 * FXML Controller class
 *
 * @author kadri
 */
public class PaiementController1 implements Initializable {

    @FXML
    private TableView<?> studentTable;
    @FXML
    private TableView<?> groupTable;
    @FXML
    private TableView<?> seanceTable;
    @FXML
    private TextField firstName;
    @FXML
    private TextField phone;
    @FXML
    private Label idG;

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
    private TableColumn<?, ?> grpC;
    @FXML
    private TableColumn<?, ?> moduleC;
    @FXML
    private TableColumn<?, ?> levelC;
    @FXML
    private TableColumn<?, ?> offerC;
    @FXML
    private TableColumn<?, ?> nbrPlaceC;
    @FXML
    private TableColumn<?, ?> roomC;
    @FXML
    private TableColumn<?, ?> dateTimeC;
    @FXML
    private TableColumn<?, ?> teacherC;
    @FXML
    private TableColumn<?, ?> pTeacherC;
    @FXML
    private TableColumn<?, ?> pStudentC;
    @FXML
    private TableColumn<?, ?> paiementC;
    @FXML
    private Label nbrPlace;
    @FXML
    private Label groupName;
    @FXML
    private Label offerName;

    private Student std = new Student();
    private static final Group group = new Group();
    private static final Seance seance = new Seance();

    private final ContextMenu contextMenu = new ContextMenu();
    private final MenuItem showGroups = new MenuItem("عرض المجموعات ");
    private final MenuItem showProfile = new MenuItem("عرض سجل الدفع ");

    private final ContextMenu contextMenu1 = new ContextMenu();
    private final MenuItem pay = new MenuItem("دفع المستحقات الـمالية   ");
    private URL url1 = null;


    /**
     * Initializes the controller class.adminضa
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        refrechStudent(studentTable, firstNameC, lastNameC, phone1C, phone2C,
                sectionNameC, new Student(), "student");
        contextMenu.getItems().addAll(showGroups, showProfile);
        contextMenu1.getItems().addAll(pay);
        studentTable.setOnMouseClicked(event -> {
            std = (Student) studentTable.getSelectionModel().getSelectedItem();
            if (std != null) {
                if (event.getButton() == MouseButton.PRIMARY) {
                    showGroupsOfStudent(std);
                } else if (event.getButton() == MouseButton.SECONDARY) {
                    studentTable.setContextMenu(contextMenu);
                    showGroups.setOnAction(event1 -> {
                        showGroupsOfStudent(std);
                        studentTable.setContextMenu(null);
                    });
                    showProfile.setOnAction(event1 -> {
                        //CommunController.alert("عرض الملف الشخصي للتلميذ");
                        try {
                            url1 = new File("src/schoolmanager/FrontEnd/layout/StudentPaiementHistory.fxml").toURI().toURL();
                        } catch (MalformedURLException e) {
                            e.printStackTrace();
                        }
                        showPaiementLayout(std,url1,"سجل الدفع","StudentPaiementHistoryController");
                        //studentTable.setContextMenu(null);
                    });
                }
            }
        });

    }

    private void showGroupsOfStudent(Student std) {
        if (std != null) {
            refrechGroup(groupTable, grpC, nbrPlaceC, offerC, moduleC, levelC, std);
        } else {
            System.out.println(std + " is null");
        }
    }

    private void showSeancsOfGroup(Student std, Group grp) {
        if (std != null && grp != null) {
            refrechSeance(seanceTable, roomC, dateTimeC, teacherC, pTeacherC, pStudentC, paiementC, std, grp);
        } else {
            System.out.println(std + " is null");
        }
    }

    private void showPaiementLayout(Student std,URL url,String titleLayout,String object) {
        try {
            FXMLLoader loader = new FXMLLoader(url);
            Parent uigrp = loader.load();
            switch (object){
                case  "PaiementCouresController" : {
                    PaiementCouresController paiementCouresController = loader.getController();
                    paiementCouresController.setInputs(std);
                    break;
                }
                case  "StudentPaiementHistoryController" : {
                    StudentPaiementHistoryController studentPaiementHistoryController = loader.getController();
                    studentPaiementHistoryController.setInputs(std);
                    break;
                }
            }
            Scene scene = new Scene(uigrp);
            if (!SecondStage.isShowing()) {
                SecondStage.setScene(scene);
                SecondStage.setTitle(titleLayout);
                SecondStage.showAndWait();
            } else {
                SecondStage.setAlwaysOnTop(true);
                SecondStage.setAlwaysOnTop(false);
            }
        } catch (IOException ex) {
            Logger.getLogger(MainController.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    public static void refrechSeance(TableView table, TableColumn Column1, TableColumn Column2,
                                     TableColumn Column3, TableColumn Column4, TableColumn Column5,
                                     TableColumn Column6, Student std, Group grp) {
       /* ObservableList<Seance> seance = SeanceService.getAllSeances(new Paiement(std, grp));
        Column1.setCellValueFactory(
                new PropertyValueFactory<>("nameRoom")
        );
        Column2.setCellValueFactory(
                new PropertyValueFactory<>("date")
        );
        Column3.setCellValueFactory(
                new PropertyValueFactory<>("nameTeacher")
        );
        Column4.setCellValueFactory(
                new PropertyValueFactory<>("test")
        );
        Column5.setCellValueFactory(
                new PropertyValueFactory<>("test1")
        );
        Column6.setCellValueFactory(
                new PropertyValueFactory<>("pstatus")
        );
        table.setItems(seance);*/
    }

    public static void refrechGroup(TableView table, TableColumn Column1, TableColumn Column2,
                                    TableColumn Column3, TableColumn Column4, TableColumn Column5,
                                    Student std) {
        ObservableList<Group> pr = BelongsService.getGroupOfStudent(std);
        Column1.setCellValueFactory(
                new PropertyValueFactory<>("nameGroup")
        );
        Column2.setCellValueFactory(
                new PropertyValueFactory<>("nbrPlace")
        );
        Column3.setCellValueFactory(
                new PropertyValueFactory<>("nameOffer")
        );
        Column4.setCellValueFactory(
                new PropertyValueFactory<>("module")
        );
        Column5.setCellValueFactory(
                new PropertyValueFactory<>("level")
        );
        table.setItems(pr);
    }

    public static void refrechStudent(TableView table, TableColumn Column1, TableColumn Column2,
                                      TableColumn Column3, TableColumn Column4, TableColumn Column5, Student std, String type) {
        ObservableList<Student> pr = null;
        if (type.equals("student")) {
            pr = StudentService.getAllStudents("",new Student());
        } else if (type.equals("belongs")) {
            pr = BelongsService.getStudentsOfGroup(group);
        } else {
            pr = BelongsService.searchStudentByName(std);
        }
        for (Student s : pr) {
            System.out.println(s.getSectionName());
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

    @FXML
    private void add(ActionEvent event) {
        /* std = Mapping.getObjectStudentFromUiStudent(uistd);
        Results.Rstls r = StudentService.addStudent(std);
        if (r == Results.Rstls.OBJECT_NOT_INSERTED) {
            CommunController.alert(r.toString());
        } else {
            uistd.clearInputs();
        }*/
    }

    @FXML
    private void printList(ActionEvent event) throws JRException {
        Print prt = new Print(group);
        new Thread(() -> {
            try {
                prt.PrintListStudent();
            } catch (JRException ex) {
                Logger.getLogger(PaiementController1.class.getName()).log(Level.SEVERE, null, ex);
            }
        }).start();
    }

    @FXML
    private void update(ActionEvent event) {
        /* if (std.getId() != 0) {
            Student newStd = Mapping.getObjectStudentFromUiStudent(uistd);
            newStd.setId(std.getId());
            Optional<ButtonType> option = alertUpdate.showAndWait();
            if (option.get() == ButtonType.OK) {
                Results.Rstls r = StudentService.updateStudent(newStd);
                if (r == Results.Rstls.OBJECT_NOT_UPDATED) {
                    CommunController.alert(r.toString());
                } else {
                    uistd.clearInputs();
                    refrechStudent(studentTable, firstNameC, lastNameC, phone1C, phone2C,sectionNameC, new Student(), "");
                }
                std = new Student();
                uistd.clearInputs();
            }
        }*/
    }

    @FXML
    private void delete(ActionEvent event) {
        /*if (std.getId() != 0) {
            Optional<ButtonType> option = alertUpdate.showAndWait();
            if (option.get() == ButtonType.OK) {
                Results.Rstls r = StudentService.deleteStudent(std);
                if (r == Results.Rstls.OBJECT_NOT_DELETED) {
                    CommunController.alert(r.toString());
                } else {
                    uistd.clearInputs();
                    refrechStudent(studentTable, firstNameC, lastNameC, phone1C, phone2C,sectionNameC, new Student(), "");
                }
                std = new Student();
                uistd.clearInputs();
            }
        }*/
    }

    @FXML
    private void selectStudent(MouseEvent event) {
       /* std = (Student) studentTable.getSelectionModel().getSelectedItem();
        if (std != null) {
            std.PresentObject();
            showGroupsOfStudent(std);
        }*/
    }

    public void setInputs(Group grp) {
        /*group = grp;
        group.PresentGroupe();
        group.setNbrRest(group.getNbrPlace() - (BelongsService.getStudentsOfGroup(group.getId()).size()));
        nbrPlace.setText(Integer.toString(group.getNbrRest()));
        groupName.setText("الفوج : " + group.getNameGroup());
        offerName.setText("العرض : " + group.getNameOffer());
        refrechStudent(belongsTable, firstNameC1, lastNameC1, phone1C1,
                phone2C1, sectionNameC1, new Student(), "belongs");*/
    }

    @FXML
    private void searchByNameAndPhone(KeyEvent event) {
        Student std = new Student();
        String name = firstName.getText();
        String phn = phone.getText();
        if (!name.isEmpty() && !phn.isEmpty()) {
            std.setFirstName(name);
            std.setPhone1(phn);
            std.setPhone2(phn);
        } else if (!name.isEmpty()) {
            std.setFirstName(name);
            std.setPhone1("");
            std.setPhone2("");
        } else {
            std.setFirstName("");
            std.setPhone1(phn);
            std.setPhone2(phn);
        }
        std.PresentObject();
        refrechStudent(studentTable, firstNameC, lastNameC, phone1C,
                phone2C, sectionNameC, std, "");
    }


}
