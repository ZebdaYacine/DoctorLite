/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package doctorlite.BackEnd.Controller;

import doctorlite.BackEnd.Model.Seance;
import doctorlite.BackEnd.Model.Follow;
import doctorlite.BackEnd.Model.Paiement;
import doctorlite.BackEnd.Model.Student;
import doctorlite.BackEnd.Model.Group;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import doctorlite.BackEnd.Service.FollowService;
import doctorlite.BackEnd.Service.SeanceService;

import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;
import javafx.scene.input.KeyEvent;

import static doctorlite.BackEnd.Controller.PaiementController.*;
import static doctorlite.DoctorLite.SecondStage;



/**
 * FXML Controller class
 *
 * @author kadri
 */
public class PaiementSeancesController implements Initializable {


    @FXML
    private TableView<?> seanceTable;
    @FXML
    private Label amuntCL, idL,idL1;

    public static Label amuntCLStatic;

    public static ObservableList<Seance> listSeance;

    @FXML
    private TableColumn<?, ?> roomC, dateTimeC, teacherC, pTeacherC, pStudentC, paiementC;


    private Group group = new Group();
    private Student std = new Student();
    private Paiement p = new Paiement();

    public static ArrayList<Seance> list;
    public static float amountRound;
    public static float priceSeance;
    public static String source;


    /**
     * Initializes the controller class.adminضa
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        amuntCLStatic = amuntCL;
        list = new ArrayList<>();
    }


    private void showSeancsOfGroup(Student std, Group grp) {
        if (std != null && grp != null) {
            refrechSeance(seanceTable, roomC, dateTimeC, teacherC, pTeacherC, pStudentC, paiementC, std, grp);
        } else {
            System.out.println(std + " is null");
        }
    }


    public static void refrechSeance(TableView table, TableColumn Column1, TableColumn Column2,
                                     TableColumn Column3, TableColumn Column4, TableColumn Column5,
                                     TableColumn Column6, Student std, Group grp) {
        listSeance = SeanceService.getAllSeances(new Paiement(std, grp),0);
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
        table.setItems(listSeance);
    }

    private void updateIdPaiementofFollow(){
        for (Seance seance : list) {
            Follow f = new Follow();
            f.setIdStudent(p.getStd().getId());
            f.setIdSeance(seance.getId());
            f.setIdPaiement(p.getId());
            f.setStatus(1);
            f.PresentFollow();
            SeanceService.updatePaiementInFollow(f);
            FollowService.updateFollow(f,"status");
        }
    }

    @FXML
    private void pay() {
        int nbr= SeanceService.countPaidSeances(p.getId());
        nbr=nbr+list.size();
        p.setNbrSeance(nbr);
        switch (p.getTypeOfOffer().toLowerCase()){
            case "vip":{
                if(nbr<=2){
                    updateIdPaiementofFollow();
                    SeanceService.updateNbrSeanceInPaiement(p);
                }else{
                    CommunController.alert("عملية الدفع مقفلة ");
                }
                break;
            }
            case "simple":{
                    if(nbr<=4){
                        updateIdPaiementofFollow();
                        SeanceService.updateNbrSeanceInPaiement(p);
                    }else{
                        CommunController.alert("عملية الدفع مقفلة ");
                    }
                break;
            }
            case "double":{
                if(nbr<=8){
                    updateIdPaiementofFollow();
                    SeanceService.updateNbrSeanceInPaiement(p);
                }else{
                    CommunController.alert("عملية الدفع مقفلة ");
                }
                break;
            }
        }
        SecondStage.close();
        Group grp = GroupCmb1.getSelectionModel().getSelectedItem();
        if(grp.getId()==group.getId()){
            std.setGroup(grp);
            refrechStudent(studentTable1, firstNameC1, lastNameC1, phone1C1,
                    phone2C1, sectionNameC1, std, "group");
        }
        refrechSeance(seanceTable, roomC, dateTimeC, teacherC, pTeacherC, pStudentC, paiementC, p.getStd(),p.getGrp());
    }

    @FXML
    private void selectItem(MouseEvent event) {

    }




    public void setInput(Paiement paiement) {
        p = paiement;
        paiement.PresentObject();
        std = paiement.getStd();
        group = paiement.getGrp();
        idL.setText(idL.getText() + " " + p.getId());
        idL1.setText(idL1.getText() + " " + p.getAround());
        amountRound = paiement.getAmount();
        priceSeance = CommunController.getAmountSeance(paiement);
        long nbrSeancePaid= SeanceService.countPaidSeances(paiement.getId());
        if(nbrSeancePaid!=0){
            float amountC=paiement.getAmountC()-priceSeance*nbrSeancePaid;
            amuntCL.setText(amountC + " Da");
        }else{
            amuntCL.setText(paiement.getAmountC() + " Da");
        }
        showSeancsOfGroup(std, group);
    }

    @FXML
    private void hotkey(KeyEvent event) {
        switch (event.getCode()) {
            case ENTER:
                pay();
                break;
            default:
                break;
        }
    }




}
