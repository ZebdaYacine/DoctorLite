/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package doctorlite.BackEnd.Controller;

import doctorlite.BackEnd.Model.Seance;
import doctorlite.BackEnd.Model.Offer;
import doctorlite.BackEnd.Model.Paiement;
import doctorlite.BackEnd.Model.Group;
import doctorlite.BackEnd.Model.Student;
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
import javafx.scene.control.TextField;
import doctorlite.BackEnd.Mapper.Mapping;
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
public class UpdatePaiementController extends PaiementController implements Initializable {

    public static int t = 0;
    private static Paiement paiement = new Paiement();
    private static Student std1 = new Student();
    private static Group grp1 = new Group();
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
    private JFXComboBox<String> aroundCmb;

    private UiStudentPaiement uistd = new UiStudentPaiement();
    ObservableList<Group> grouplist;


    ObservableList<String> around =
            FXCollections.observableArrayList(
                    "1","2","3","4","5","6","7","8","9","10","11","12"
            );
    @FXML
    private JFXButton update1;



    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        aroundCmb.getItems().clear();
        aroundCmb.setItems(around);
        uistd = new UiStudentPaiement(amount, amountP, GroupCmb,aroundCmb);
        GroupCmb.setOnAction(event -> {
            Group grp = GroupCmb.getSelectionModel().getSelectedItem();
            if (grp != null) {
                ObservableList<Offer> offer = OfferService.getOfferbyid(new Offer(grp.getIdOffer()));
                OfferN.setText(offer.get(0).getName());
                amount.setText(offer.get(0).getPrice() + "");
            }
        });
    }

    private int getIndexgroop(int id) {
        int i = 0;
        while (id != grouplist.get(i).getId()) {
            i++;
        }
        return i;
    }

    @FXML
    private void onchangecontent(ActionEvent event) {

    }

    public void setInputsUpdatePaiement(Paiement pa) {
        std1 = pa.getStd();
        grp1 = pa.getGrp();
        paiement = pa;
        fullName.setText(std1.getFirstName() + " " + std1.getLastName());
        fullName.setEditable(false);
        aroundCmb.getSelectionModel().select(pa.getAround());
        grouplist = BelongsService.getGroupOfStudent(std1);
        GroupCmb.setItems(grouplist);
        GroupCmb.getSelectionModel().select(getIndexgroop((int) grp1.getId()));
        OfferN.setText(grp1.getNameOffer());
        amount.setText(pa.getAmount() + " ");
        amountP.setText(pa.getAmountC() + "");
    }

    @FXML
    private void update( ) throws InterruptedException, IOException {
        Paiement paiementUpdated = Mapping.getObjectAccountFromUiStudentPaiementHistory(uistd);
        paiementUpdated.setStd(std1);
        paiementUpdated.setId(paiement.getId());
        Seance s = PaiementService.PaiementHasAseans(paiementUpdated);
        Results.Rstls resulat;
        if (s.getId() == 0) {
            resulat = PaiementService.updatePaiement(paiementUpdated);
        } else {
            if (s.getIdGroupe() == paiementUpdated.getGrp().getId()) {
                resulat = PaiementService.updatePaiement(paiementUpdated);
            } else {
                resulat = Results.Rstls.OBJECT_NOT_UPDATED;
            }
        }
        paiementUpdated.setTypeOfOffer(getOfferAttFromIdOffer(new Offer(paiementUpdated.getGrp().getIdOffer()), "nameType"));
        paiementUpdated.setOfferName(getOfferAttFromIdOffer(new Offer(paiementUpdated.getGrp().getIdOffer()), "offerName"));
        if (resulat.toString().equals("OBJECT_NOT_UPDATED")) {
            CommunController.alert("تعذر تعديل على عملية الدفع");
        } else {
            refrechPaiement(PaiementTable1, groupC1, offerC1, datePC1, amountC1, amountRC1, nbrseanceC1, std1);
            FXMLLoader loader = new FXMLLoader(this.getClass().getResource("/schoolmanager/FrontEnd/layout/PaiementSeances.fxml"));
            Parent ui = loader.load();
            PaiementSeancesController paiementSeancesController = loader.getController();
            paiementSeancesController.setInput(paiementUpdated);
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
    private void hotkey(KeyEvent event) throws InterruptedException, IOException {
        switch (event.getCode()) {
            case ENTER:
                update();
                break;
            default:
                break;
        }

    }

}
