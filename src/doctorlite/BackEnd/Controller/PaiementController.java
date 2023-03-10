/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package doctorlite.BackEnd.Controller;

import doctorlite.BackEnd.Service.BelongsService;
import doctorlite.BackEnd.Service.StudentService;
import doctorlite.BackEnd.Service.GroupService;
import doctorlite.BackEnd.Service.PaiementService;
import doctorlite.BackEnd.Service.SeanceService;
import doctorlite.BackEnd.Service.FollowService;
import doctorlite.BackEnd.Model.Follow;
import doctorlite.BackEnd.Model.Paiement;
import doctorlite.BackEnd.Model.Group;
import doctorlite.BackEnd.Model.Student;
import com.jfoenix.controls.JFXComboBox;
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
import javafx.stage.Stage;
import javafx.util.Callback;
import net.sf.jasperreports.engine.JRException;
import doctorlite.BackEnd.ReceiptPrinter.MainPrinter;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;

import static doctorlite.DoctorLite.SecondStage;


/**
 * FXML Controller class
 *
 * @author kadri
 */
public class PaiementController implements Initializable {

    @FXML
    private TableView<Student> studentTable;
    static TableView<Student> studentTable1;
    @FXML
    private TableView<?> PaiementTable;
    public static TableView<?> PaiementTable1;
    @FXML
    private TextField firstName, phone;
    @FXML
    private JFXComboBox<Group> GroupCmb;
    static JFXComboBox<Group> GroupCmb1;
    @FXML
    private TableColumn<?, ?> firstNameC, lastNameC, phone1C, phone2C, sectionNameC;
    static TableColumn<?, ?> firstNameC1;
    @FXML
    static TableColumn<?, ?> lastNameC1, phone1C1, phone2C1, sectionNameC1;
    @FXML
    private Label stdLbl;
    @FXML
    private TableColumn<?, ?> offerC, datePC, groupC, amountC, amountRC, nbrseanceC;
    public static TableColumn<?, ?> offerC1;
    @FXML
    public static TableColumn<?, ?> datePC1, groupC1, amountC1, amountRC1, nbrseanceC1;

    public static Student std = new Student();
    private static final Group group = new Group();
    private static Paiement paiement = new Paiement();

    private final ContextMenu contextMenu = new ContextMenu();
    private final MenuItem showGroups = new MenuItem("الحصص الغير مدفوعة ");

    private final ContextMenu contextMenu1 = new ContextMenu();
    private final MenuItem PrinteP = new MenuItem("طباعة ");
    private final MenuItem showP = new MenuItem("عرض  ");
    private final MenuItem deleteP = new MenuItem("حذف  ");
    private static boolean b1 = false;
    @FXML
    private Label firstName_err;
    @FXML
    private Label phone1_err;
    @FXML
    private Label lastName_err;
    @FXML
    private Label phone2_err;

    /**
     * Initializes the controller class.adminضa
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        PaiementTable1 = PaiementTable;
        studentTable1 = studentTable;
        offerC1 = offerC;
        datePC1 = datePC;
        groupC1 = groupC;
        amountC1 = amountC;
        amountRC1 = amountRC;
        nbrseanceC1 = nbrseanceC;
        firstNameC1 = firstNameC;
        lastNameC1 = lastNameC;
        phone1C1 = phone1C;
        phone2C1 = phone2C;
        GroupCmb1 = GroupCmb;
        sectionNameC1 = sectionNameC;
        std = new Student();
        GroupCmb.getSelectionModel().select(null);
        ObservableList<Group> grouplist = GroupService.getAllGroups(null, "all");
        GroupCmb.setItems(grouplist);
        refrechStudent(studentTable, firstNameC, lastNameC, phone1C, phone2C,
                sectionNameC, new Student(), "student");
        contextMenu.getItems().addAll(showGroups);
        contextMenu1.getItems().addAll(PrinteP, showP, deleteP);
        b1 = false;
        studentTable.setOnMouseClicked(event -> {
            std = (Student) studentTable.getSelectionModel().getSelectedItem();
            if (std != null) {
                if (event.getButton() == MouseButton.PRIMARY) {
                    if (GroupCmb.getSelectionModel().getSelectedItem() == null) {
                        refrechPaiement(PaiementTable, groupC, offerC, datePC, amountC, amountRC, nbrseanceC, std);
                    } else {
                        std.setGroup(GroupCmb.getSelectionModel().getSelectedItem());
                        refrechPaiement(PaiementTable, groupC, offerC, datePC, amountC, amountRC, nbrseanceC, std);
                    }
                    stdLbl.setText("التلميذ : " + std.getFirstName() + " " + std.getLastName());
                } else if (event.getButton() == MouseButton.SECONDARY) {
                    studentTable.setContextMenu(contextMenu);
                    showGroups.setOnAction(event1 -> {
                        if (GroupCmb.getSelectionModel().getSelectedItem().getId() != 0) {
                            std.setGroup(GroupCmb.getSelectionModel().getSelectedItem());
                            showPaiementLayout(std, "/schoolmanager/FrontEnd/layout/SeanceNotPaid.fxml",
                                    "سجل الدفع", "SeanceNotPaidController");
                        } else {
                            CommunController.alert("اختر فوج");
                        }
                    });
                }
            }
        });
        studentTable.setRowFactory(new Callback<TableView<Student>, TableRow<Student>>() {
            @Override
            public TableRow<Student> call(TableView param) {
                return new TableRow<Student>() {
                    protected void updateItem(Student s, boolean b) {
                        super.updateItem(s, b);
                        if (s != null) {
                            if (b1) {
                                if (s.isPaid()) {
                                    setStyle("-fx-background-color: #081018;");
                                } else {
                                    setStyle("-fx-background-color: #FF0000;");
                                }
                                /*if (!s.isHasSeanceNoPaid()) {
                                    setStyle("-fx-background-color: #081018;");
                                } else {
                                    setStyle("-fx-background-color: #eaf816;");
                                }*/
                            } else {
                                setStyle("-fx-background-color: #081018;");
                            }
                        } else {
                            setStyle("-fx-background-color: #081018;");
                        }

                    }
                };
            }
        });
        PaiementTable.setOnMouseClicked(event -> {
            paiement = (Paiement) PaiementTable.getSelectionModel().getSelectedItem();
            if (paiement != null) {
                if (event.getButton() == MouseButton.PRIMARY) {
                    if (event.getClickCount() == 2) {
                        paiement.setNbrSeance(SeanceService.countPaidSeances(paiement.getId()));
                        if (CommunController.getnbrSeanceInOffer(paiement.getTypeOfOffer()) == paiement.getNbrSeance()) {
                            CommunController.alert("عملية الدفع مقفلة");
                        } else {
                            showPaiementLayout(paiement, "/schoolmanager/FrontEnd/layout/UpdatePaiement.fxml",
                                    " عملية دفع جديدة ", "UpdatePaiementController");
                        }
                    }
                } else if (event.getButton() == MouseButton.SECONDARY) {
                    PaiementTable.setContextMenu(contextMenu1);
                    PrinteP.setOnAction(event1 -> {
                        try {
                            MainPrinter.Print(paiement);
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    });
                    showP.setOnAction(event1 -> {
                        showPaiementLayout(paiement, "/schoolmanager/FrontEnd/layout/ShowPaiement.fxml", " جدول الحصص ",
                                "ShowPaiementController");
                    });
                    deleteP.setOnAction(event1 -> {
                        boolean b = CommunController.confirm("هل أنت متاكد من حف عملية الدفع");
                        if (b) {
                            /*Seance s = PaiementService.PaiementHasAseans(paiement);
                            if (s.getId() == 0) {*/
                            Follow flw = new Follow();
                            flw.setIdPaiement(paiement.getId());
                            FollowService.updateFollow(flw, "deletepaiement");
                            PaiementService.deletePaiement(paiement);
                            refrechPaiement(PaiementTable, groupC, offerC, datePC, amountC, amountRC, nbrseanceC, std);
                            refrechStudent(studentTable, firstNameC, lastNameC, phone1C,
                                    phone2C, sectionNameC, std, "group");
                            /*} else {
                                CommunController.alert("لايمكن حذف هذه العميلة");
                            }*/
                        }
                    });
                }
            }
        });
    }

    @FXML
    private void onchangecontent(ActionEvent event) {
        if (GroupCmb.getSelectionModel().getSelectedItem().getId() != 0) {
            Group group = GroupCmb.getSelectionModel().getSelectedItem();
            std.setGroup(group);
            b1 = true;
            refrechStudent(studentTable, firstNameC, lastNameC, phone1C,
                    phone2C, sectionNameC, std, "group");
        } else {
            b1 = false;
            refrechStudent(studentTable, firstNameC, lastNameC, phone1C, phone2C,
                    sectionNameC, new Student(), "student");
        }
    }

    private void showPaiementLayout(Object obj, String url, String titleLayout, String object) {
        try {
            FXMLLoader loader = new FXMLLoader(this.getClass().getResource(url));
            Parent uigrp = loader.load();
            Stage stage = SecondStage;
            switch (object) {
                case "SeanceNotPaidController": {
                    SeanceNotPaidController seanceNotPaidController = loader.getController();
                    seanceNotPaidController.setInputs((Student) obj);
                    break;
                }
                case "PaiementSeancesController": {
                    PaiementSeancesController paiementSeancesController = loader.getController();
                    paiementSeancesController.setInput((Paiement) obj);
                    //stage = thirdStage;
                    break;
                }
                case "ShowPaiementController": {
                    ShowPaiementController showPaiementController = loader.getController();
                    showPaiementController.setInput((Paiement) obj);
                    //stage = thirdStage;
                    break;
                }
                case "NewPaiementController": {
                    NewPaiementController newPaiementController = loader.getController();
                    newPaiementController.setInputsNewPaiement((Student) obj);
                    break;
                }
                case "UpdatePaiementController": {
                    UpdatePaiementController updatePaiementController = loader.getController();
                    updatePaiementController.setInputsUpdatePaiement((Paiement) obj);
                    break;
                }
            }
            Scene scene = new Scene(uigrp);
            if (!stage.isShowing()) {
                stage.setScene(scene);
                stage.setTitle(titleLayout);
                stage.showAndWait();
            } else {
                stage.setAlwaysOnTop(true);
                stage.setAlwaysOnTop(false);
            }
        } catch (IOException ex) {
            Logger.getLogger(MainController.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    public static void refrechPaiement(TableView table, TableColumn Column1, TableColumn Column2,
                                       TableColumn Column3, TableColumn Column4,
                                       TableColumn Column5, TableColumn Column6,
                                       Student std) {

        paiement.setStd(std);
        ObservableList<Paiement> pr = PaiementService.getPaiementOfStudent(paiement);
        Column1.setCellValueFactory(
                new PropertyValueFactory<>("groupName")
        );
        Column2.setCellValueFactory(
                new PropertyValueFactory<>("around")
        );
        Column3.setCellValueFactory(
                new PropertyValueFactory<>("date")
        );
        Column4.setCellValueFactory(
                new PropertyValueFactory<>("amount")
        );
        Column5.setCellValueFactory(
                new PropertyValueFactory<>("amountC")
        );
        Column6.setCellValueFactory(
                new PropertyValueFactory<>("typeOfOffer")
        );
        table.setItems(pr);
    }

    public static void refrechStudent(TableView table, TableColumn Column1, TableColumn Column2,
                                      TableColumn Column3, TableColumn Column4, TableColumn Column5, Student std, String type) {
        ObservableList<Student> pr = null;
        if (type.equals("student")) {
            b1 = false;
            pr = StudentService.getAllStudents("", new Student());
        } else if (type.equals("belongs")) {
            pr = BelongsService.getStudentsOfGroup(group);
        } else if (type.equals("group")) {
            pr = BelongsService.getStudentsOfGroup(std.getGroup());
        } else {
            pr = BelongsService.searchStudentByName(std);
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
    private void addPaiement(ActionEvent event) throws JRException {
        if (std.getId() != 0) {
            showPaiementLayout(std, "/schoolmanager/FrontEnd/layout/NewPaiement.fxml",
                    " عملية دفع جديدة ", "NewPaiementController");
        } else {
            CommunController.alert("عذرا. اختر تلميذ");
        }
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
        b1 = false;
        GroupCmb.getSelectionModel().select(0);
        refrechStudent(studentTable, firstNameC, lastNameC, phone1C,
                phone2C, sectionNameC, std, "");
    }


}
