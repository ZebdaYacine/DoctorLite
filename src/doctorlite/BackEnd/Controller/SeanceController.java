/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package doctorlite.BackEnd.Controller;

import doctorlite.BackEnd.Service.ObjectService;
import doctorlite.BackEnd.Service.StudentService;
import doctorlite.BackEnd.Service.GroupService;
import doctorlite.BackEnd.Service.PaiementService;
import doctorlite.BackEnd.Service.SeanceService;
import doctorlite.BackEnd.Service.RoomService;
import doctorlite.BackEnd.Service.FollowService;
import doctorlite.BackEnd.Service.TeacherService;
import doctorlite.BackEnd.Model.Seance;
import doctorlite.BackEnd.Model.Teacher;
import doctorlite.BackEnd.Model.Room;
import doctorlite.BackEnd.Model.Follow;
import doctorlite.BackEnd.Model.Paiement;
import doctorlite.BackEnd.Model.Student;
import doctorlite.BackEnd.Model.Group;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXCheckBox;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXDatePicker;
import com.jfoenix.controls.JFXTextField;
import com.jfoenix.controls.JFXTimePicker;
import java.net.URL;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.ResourceBundle;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.Label;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import doctorlite.BackEnd.Mapper.Mapping;
import doctorlite.BackEnd.Results;
import doctorlite.BackEnd.uiPresenter.UiSeance;

/**
 * FXML Controller class
 *
 * @author kadri
 */
public class SeanceController implements Initializable {

    @FXML
    private JFXButton update;
    @FXML
    private JFXButton delete;
    @FXML
    private TableView<?> SeanceTable;
    @FXML
    private JFXComboBox<Teacher> teacherCmb;
    @FXML
    private JFXComboBox<Room> RoomCmb;
    @FXML
    private JFXDatePicker dateSeance;
    @FXML
    private Label OfferErr;
    @FXML
    private Label teacherErr;
    @FXML
    private Label roomErr;
    @FXML
    private Label dateErr;
    @FXML
    private Label timeErr;
    @FXML
    private TableView<?> studentPTable;
    @FXML
    private TableView<?> studentATable;
    @FXML
    private JFXComboBox<Group> GroupCmb;
    @FXML
    private Label groupErr;
    @FXML
    private TableColumn<?, ?> GroupC;
    @FXML
    private JFXTimePicker time;

    private UiSeance uiseance = new UiSeance();
    private Seance seanceSelect = new Seance();
    private final ContextMenu ApsentMenu = new ContextMenu();
    private final ContextMenu PresentMenu = new ContextMenu();
    private final MenuItem delItem = new MenuItem("غائب");
    private final MenuItem addPayItem = new MenuItem("حاضر");

    @FXML
    private TableColumn<?, ?> TeacherC;
    @FXML
    private TableColumn<?, ?> RoomC;
    @FXML
    private TableColumn<?, ?> dateC;
    @FXML
    private TableColumn<?, ?> firstNamePC;
    @FXML
    private TableColumn<?, ?> lastNamePC;
    @FXML
    private TableColumn<?, ?> phone1PC;
    @FXML
    private TableColumn<?, ?> phone2PC;
    @FXML
    private TableColumn<?, ?> sectionNamePC;
    @FXML
    private TableColumn<?, ?> firstNameAC;
    @FXML
    private TableColumn<?, ?> lastNameAC;
    @FXML
    private TableColumn<?, ?> phone1AC;
    @FXML
    private TableColumn<?, ?> phone2AC;
    @FXML
    private TableColumn<?, ?> sectionNameAC;

    private ObservableList<Room> roomlist;
    private ObservableList<Teacher> teacherlist;
    ObservableList<Group> grouplist;
    @FXML
    private TableColumn<?, ?> paymentPC;
    @FXML
    private TableColumn<?, ?> paymentAC;
    @FXML
    private JFXTextField firstName;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        PresentMenu.getItems().addAll(addPayItem);
        ApsentMenu.getItems().addAll(delItem);
        uiseance = new UiSeance(teacherErr, roomErr, groupErr, GroupCmb, teacherCmb, RoomCmb, dateSeance, dateErr, time, timeErr);
        roomlist = RoomService.getAllRoom();
        RoomCmb.getItems().addAll(roomlist);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
        LocalDateTime datTime = LocalDateTime.parse(ObjectService.getCurrentDateTime(), formatter);
        dateSeance.setValue(datTime.toLocalDate());
        time.setValue(datTime.toLocalTime());
        teacherlist = TeacherService.getAllTeachers();
        teacherCmb.getItems().addAll(teacherlist);
        refrechSeance(SeanceTable, TeacherC, RoomC, GroupC, dateC, new Seance(), "", 0);
    }

    public void refrechSeance(TableView table, TableColumn Column2,
            TableColumn Column3, TableColumn Column4, TableColumn Column5,
            Seance seance, String type, int a) {
        ObservableList<Seance> pr = SeanceService.getAllSeances(null, 0);
        if (pr.size() > 0) {
            seanceSelect = pr.get(0);
            if (a == 1) {
                refrechStudents(studentATable, firstNameAC, lastNameAC, phone1AC, phone2AC, sectionNameAC, paymentAC, pr.get(0), "apsent");
                refrechStudents(studentPTable, firstNamePC, lastNamePC, phone1PC, phone2PC, sectionNamePC, paymentPC, pr.get(0), "present");
            }
            Column2.setCellValueFactory(
                    new PropertyValueFactory<>("nameTeacher")
            );
            Column3.setCellValueFactory(
                    new PropertyValueFactory<>("nameRoom")
            );
            Column4.setCellValueFactory(
                    new PropertyValueFactory<>("nameGroup")
            );
            Column5.setCellValueFactory(
                    new PropertyValueFactory<>("date")
            );

            table.setItems(pr);
        }
    }

    public static void refrechStudents(TableView table, TableColumn Column1, TableColumn Column2,
            TableColumn Column3, TableColumn Column4, TableColumn Column5, TableColumn Column6,
            Seance seance, String type) {
        ObservableList<Student> pr = StudentService.getAllStudentsFollow(seance, type);
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
        Column6.setCellValueFactory(
                new PropertyValueFactory<>("status")
        );
        table.setItems(pr);
    }

    @FXML
    private void add() {
        seanceSelect = Mapping.getObjectSeanceFromUiSeance(uiseance);
        if (seanceSelect != null) {
            Results.Rstls r = SeanceService.addSeance(seanceSelect);
            if (r == Results.Rstls.OBJECT_NOT_INSERTED) {
                CommunController.alert(r.toString());
            } else {
                uiseance.clearInputs();
            }
            refrechSeance(SeanceTable, TeacherC, RoomC, GroupC, dateC, new Seance(), "", 1);
        }
    }

    @FXML
    private void update(ActionEvent event) {
        long id = seanceSelect.getId();
        if (id != 0) {
            if (CommunController.confirm("sure de modifier  le seance")) {
                Seance newSnc = Mapping.getObjectSeanceFromUiSeance(uiseance);
                newSnc.setId(seanceSelect.getId());
                Seance snc = SeanceService.getSeances(id);
                if (snc.getIdGroupe() != newSnc.getIdGroupe()) {
                    SeanceService.checkPaiement(snc);
                }
                newSnc.PresentSeance();
                Results.Rstls r = SeanceService.updateSeance(newSnc);
                if (r == Results.Rstls.OBJECT_NOT_UPDATED) {
                    CommunController.alert(r.toString());
                } else {
                    uiseance.clearInputs();
                }
                refrechSeance(SeanceTable, TeacherC, RoomC, GroupC, dateC, new Seance(), "", 1);
            }
        }
    }

    @FXML
    private void delete() {
        long idSeance = seanceSelect.getId();
        if (idSeance != 0) {
            if (CommunController.confirm("sure de supprimer  le seance")) {
                ArrayList<Long> idPaiemetArray = SeanceService.getPaiementInFollow(idSeance);
                long nbrSeanceInPaiement = 0;
                for (Long idPaiement : idPaiemetArray) {
                    nbrSeanceInPaiement = SeanceService.countPaidSeances(idPaiement);
                    Paiement p = new Paiement();
                    p.setNbrSeance((int) nbrSeanceInPaiement - 1);
                    p.setId(idPaiement);
                    SeanceService.updateNbrSeanceInPaiement(p);
                }
                Results.Rstls r = SeanceService.deleteSeance(seanceSelect);
                if (r == Results.Rstls.OBJECT_NOT_DELETED) {
                    CommunController.alert(r.toString());
                } else {
                    uiseance.clearInputs();
                }
                refrechSeance(SeanceTable, TeacherC, RoomC, GroupC, dateC, new Seance(), "", 1);
            }
        }
        refrechSeance(SeanceTable, TeacherC, RoomC, GroupC, dateC, new Seance(), "", 1);
    }

    @FXML
    private void onchangecontent(ActionEvent event) {
        Teacher tech = teacherCmb.getSelectionModel().getSelectedItem();
        if (tech != null) {
            GroupCmb.getSelectionModel().select(null);
            grouplist = GroupService.getAllGroups(tech, "tech");
            GroupCmb.setItems(grouplist);
        }

    }

    private int getIndexTeacher(int id) {
        int i = 0;
        while (id != teacherlist.get(i).getId()) {
            i++;
        }
        return i;
    }

    private int getIndexRoom(int id) {
        int i = 0;
        while (id != roomlist.get(i).getId()) {
            i++;
        }
        return i;
    }

    private int getIndexgroop(int id) {
        int i = 0;
        while (id != grouplist.get(i).getId()) {
            i++;
        }
        return i;
    }

    @FXML
    private void selectSeance(MouseEvent event) {
        seanceSelect = (Seance) SeanceTable.getSelectionModel().getSelectedItem();
        if (seanceSelect != null) {
            refrechStudents(studentATable, firstNameAC, lastNameAC, phone1AC, phone2AC, sectionNameAC, paymentAC, seanceSelect, "apsent");
            refrechStudents(studentPTable, firstNamePC, lastNamePC, phone1PC, phone2PC, sectionNamePC, paymentPC, seanceSelect, "present");
            teacherCmb.getSelectionModel().select(getIndexTeacher((int) seanceSelect.getIdTeacher()));
            RoomCmb.getSelectionModel().select(getIndexRoom((int) seanceSelect.getIdRoom()));
            GroupCmb.getSelectionModel().select(getIndexgroop((int) seanceSelect.getIdGroupe()));
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
            LocalDateTime dattime = LocalDateTime.parse(seanceSelect.getDate(), formatter);
            dateSeance.setValue(dattime.toLocalDate());
            time.setValue(dattime.toLocalTime());
            uiseance = new UiSeance(teacherErr, roomErr, groupErr, GroupCmb, teacherCmb, RoomCmb, dateSeance, dateErr, time, timeErr);
        }
    }

    @FXML
    private void selectStudentPrsent(MouseEvent event) {
        Student std = (Student) studentPTable.getSelectionModel().getSelectedItem();
        if (std != null) {
            if (event.getButton() == MouseButton.PRIMARY) {

            } else if (event.getButton() == MouseButton.SECONDARY) {
                studentPTable.setContextMenu(ApsentMenu);
                delItem.setOnAction(event1 -> {
                    Follow f = new Follow();
                    f.setIdSeance(seanceSelect.getId());
                    f.setIdStudent(std.getId());
                    f.setPresenceStudent(0);
                    FollowService.updateFollow(f, "presenceStudent");
                    refrechStudents(studentATable, firstNameAC, lastNameAC, phone1AC, phone2AC, sectionNameAC, paymentAC, seanceSelect, "apsent");
                    refrechStudents(studentPTable, firstNamePC, lastNamePC, phone1PC, phone2PC, sectionNamePC, paymentPC, seanceSelect, "present");
                    studentPTable.setContextMenu(null);
                });
            }
        }

    }

    private boolean isPaiementFull(Paiement p) {
        return p.getNbrSeance() == CommunController.getnbrSeanceInOffer(PaiementService
                .getPaiementForThisGroupIfExist(p).getTypeOfOffer());
    }

    private boolean isPaiementEnough(Paiement p) {
        float seancePrice = CommunController.getAmountSeance(p);
        float amountRest = p.getAmountC() - seancePrice * p.getNbrSeance();
        return amountRest >= seancePrice;
    }

    private boolean isPaiementAvailable(Paiement p) {
        if (p.getId() != 0) {
            return isPaiementEnough(p) && !isPaiementFull(p);
        } else {
            return false;
        }
    }

    @FXML
    private void selectStudentApsent(MouseEvent event) {
        Student std = (Student) studentATable.getSelectionModel().getSelectedItem();
        std.PresentObject();
        if (std != null) {
            if (event.getButton() == MouseButton.PRIMARY) {
            } else if (event.getButton() == MouseButton.SECONDARY) {
                studentATable.setContextMenu(PresentMenu);
                addPayItem.setOnAction(event1 -> {
                    Follow f = new Follow();
                    f.setIdSeance(seanceSelect.getId());
                    f.setIdStudent(std.getId());
                    f.setPresenceStudent(1);
                    Paiement p = new Paiement(std, new Group(seanceSelect.getIdGroupe()));
                    p = PaiementService.getPaiementForThisGroupIfExist(p);
                    p.setStd(std);
                    p.setGrp(new Group(seanceSelect.getIdGroupe()));
                    if (isPaiementAvailable(p)) {
                        p.setNbrSeance(p.getNbrSeance() + 1);
                        SeanceService.updateNbrSeanceInPaiement(p);
                        f.setIdStudent(p.getStd().getId());
                        f.setIdSeance(seanceSelect.getId());
                        f.setIdPaiement(p.getId());
                        f.setStatus(1);
                        SeanceService.updatePaiementInFollow(f);
                        FollowService.updateFollow(f, "statusWithP");
                    } else {
                        FollowService.updateFollow(f, "presenceStudent");
                    }
                    refrechStudents(studentATable, firstNameAC, lastNameAC, phone1AC, phone2AC, sectionNameAC, paymentAC, seanceSelect, "apsent");
                    refrechStudents(studentPTable, firstNamePC, lastNamePC, phone1PC, phone2PC, sectionNamePC, paymentPC, seanceSelect, "present");
                    studentATable.setContextMenu(null);
                });
            }
        }

    }

    @FXML
    private void searchByName(KeyEvent event) {
        Student std = new Student();
        Seance snc = seanceSelect;
        String name = firstName.getText();
        System.out.println(name);
        snc.setpStudent(name);
        refrechStudents(studentATable, firstNameAC, lastNameAC, phone1AC, phone2AC, sectionNameAC, paymentAC, snc, "apsentFirstName");
        refrechStudents(studentPTable, firstNamePC, lastNamePC, phone1PC, phone2PC, sectionNamePC, paymentPC, snc, "presentFirstName");
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
