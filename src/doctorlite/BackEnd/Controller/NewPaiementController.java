/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package doctorlite.BackEnd.Controller;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ProgressIndicator;
import javafx.scene.control.TextField;
import doctorlite.BackEnd.Mapper.Mapping;
import doctorlite.BackEnd.Model.Group;
import doctorlite.BackEnd.Model.Offer;
import doctorlite.BackEnd.Model.Paiement;
import doctorlite.BackEnd.Model.Student;
import doctorlite.BackEnd.Results;
import doctorlite.BackEnd.Service.BelongsService;
import doctorlite.BackEnd.Service.OfferService;
import doctorlite.BackEnd.Service.PaiementService;
import doctorlite.BackEnd.uiPresenter.UiStudentPaiement;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.scene.input.KeyEvent;

import static doctorlite.BackEnd.Service.OfferService.getOfferAttFromIdOffer;
import static doctorlite.DoctorLite.SecondStage;

/**
 * FXML Controller class
 *
 * @author kadri
 */
public class NewPaiementController extends PaiementController implements Initializable {

    public static int t = 0;
    private static Paiement paiement = new Paiement();
    private static Student std1 = new Student();
    @FXML
    private TextField fullName;
    @FXML
    private TextField amount;
    @FXML
    private TextField amountP;
    @FXML
    private TextField OfferN;
    @FXML
    private JFXComboBox<Group> GroupCmb;
    @FXML
    private JFXComboBox<String> aroundCmb1;
    private UiStudentPaiement uistd = new UiStudentPaiement();

    @FXML
    private JFXButton add;
    @FXML
    private ProgressIndicator prg;

    ObservableList<String> around
            = FXCollections.observableArrayList(
                    "1", "2", "3", "4", "5", "6", "7", "8", "9", "10", "11", "12"
            );

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        prg.setProgress(0);
        aroundCmb1.getItems().clear();
        aroundCmb1.setItems(around);
        uistd = new UiStudentPaiement(amount, amountP, GroupCmb, aroundCmb1);
        GroupCmb.setOnAction(event -> {
            Group grp = GroupCmb.getSelectionModel().getSelectedItem();
            if (grp != null) {
                ObservableList<Offer> offer = OfferService.getOfferbyid(new Offer(grp.getIdOffer()));
                OfferN.setText(offer.get(0).getName());
                amount.setText(offer.get(0).getPrice() + "");
            }
        });
    }

    @FXML
    private void onchangecontent(ActionEvent event) {

    }

    public void setInputsNewPaiement(Student std) {
        fullName.setText(std.getFirstName() + " " + std.getLastName());
        fullName.setEditable(false);
        ObservableList<Group> grouplist = BelongsService.getGroupOfStudent(std);
        GroupCmb.getItems().addAll(grouplist);
        std1 = std;
    }

    @FXML
    private void add() throws InterruptedException, IOException {
        paiement = Mapping.getObjectAccountFromUiStudentPaiementHistory(uistd);
        paiement.setTypeOfOffer(OfferService.getOfferAttFromIdOffer(new Offer(paiement.getGrp().getIdOffer()), "nameType"));
        paiement.setNbrSeance(0);
        paiement.setStd(std1);
        paiement.setAround(aroundCmb1.getSelectionModel().getSelectedItem());
        long lastID = PaiementService.addPaiement(paiement);
        paiement.setId(lastID);
        paiement.setTypeOfOffer(getOfferAttFromIdOffer(new Offer(paiement.getGrp().getIdOffer()), "nameType"));
        paiement.setOfferName(getOfferAttFromIdOffer(new Offer(paiement.getGrp().getIdOffer()), "offerName"));
        if (lastID == 0) {
            CommunController.alert(Results.Rstls.OBJECT_NOT_INSERTED.toString());
        } else {
            //editProgressBar();
            CommunController.alert("تم اضافة عملية دفع جديدة");
            refrechPaiement(PaiementTable1, groupC1, offerC1, datePC1, amountC1, amountRC1, nbrseanceC1, std1);
            FXMLLoader loader = new FXMLLoader(this.getClass().getResource("/schoolmanager/FrontEnd/layout/PaiementSeances.fxml"));
            Parent ui = loader.load();
            PaiementSeancesController paiementSeancesController = loader.getController();
            paiementSeancesController.setInput(paiement);
            Scene scene = new Scene(ui);
            if (!SecondStage.isShowing()) {
                SecondStage.setScene(scene);
                SecondStage.setTitle("جدول الحصص ");
                SecondStage.showAndWait();
            } else {
                SecondStage.setScene(scene);
                SecondStage.setTitle("جدول الحصص ");
                SecondStage.setAlwaysOnTop(true);
                SecondStage.setAlwaysOnTop(false);
            }
        }
    }

    @FXML
    private void hotkey(KeyEvent event) throws IOException, InterruptedException {
        switch (event.getCode()) {
            case ENTER:
                add();
                break;
            default:
                break;
        }
    }

}
