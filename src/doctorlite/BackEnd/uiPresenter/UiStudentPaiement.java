/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package doctorlite.BackEnd.uiPresenter;

import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXDatePicker;
import javafx.scene.control.TextField;
import doctorlite.BackEnd.Model.Group;

/**
 *
 * @author Zed Yacine
 */
public class UiStudentPaiement extends TestingMethods {

    private TextField amount;
    private TextField type;
    private TextField amountP;
    private JFXDatePicker dateP;
    private JFXComboBox<Group> groupCB;
    private JFXComboBox<String> aroundCB;

    public TextField getType() {
        return type;
    }

    public void setType(TextField type) {
        this.type = type;
    }

    public JFXComboBox<String> getAroundCB() {
        return aroundCB;
    }

    public void setAroundCB(JFXComboBox<String> aroundCB) {
        this.aroundCB = aroundCB;
    }

    public UiStudentPaiement() {
    }

    public UiStudentPaiement(TextField amount, TextField amountP, JFXComboBox<Group> groupCB, JFXComboBox<String> aroundCB) {
        this.amount = amount;
        this.amountP = amountP;
        this.groupCB = groupCB;
        this.aroundCB = aroundCB;
    }

    public TextField getAmount() {
        return amount;
    }

    public void setAmount(TextField amount) {
        this.amount = amount;
    }

    public JFXDatePicker getDateP() {
        return dateP;
    }

    public void setDateP(JFXDatePicker dateP) {
        this.dateP = dateP;
    }

    public TextField getAmountP() {
        return amountP;
    }

    public void setAmountP(TextField amountP) {
        this.amountP = amountP;
    }

    public JFXComboBox<Group> getGroupCB() {
        return groupCB;
    }

    public void setGroupCB(JFXComboBox<Group> groupCB) {
        this.groupCB = groupCB;
    }

    public static boolean UiStudentInputIsValid(UiStudentPaiement uistd) {
        boolean bamountP = false, groupeB = false, aroundB = false;
        String amountP = uistd.getAmountP().getText();
        String amount = uistd.getAmount().getText();
        Group group = uistd.groupCB.getSelectionModel().getSelectedItem();
        String around = uistd.aroundCB.getSelectionModel().getSelectedItem();
        if (!amountP.isEmpty()) {
            uistd.amountP.setStyle("-fx-background-color : transparent");
            bamountP = true;
        } else {
            uistd.amountP.setStyle("-fx-background-color : #f70d1b");
        }

        if (group != null) {
            uistd.groupCB.setStyle("-fx-background-color : transparent");
            groupeB = true;
        } else {
            uistd.groupCB.setStyle("-fx-background-color : #f70d1b");
        }
        if (around != null) {
            aroundB = true;
            uistd.aroundCB.setStyle("-fx-background-color : transparent");
        } else {
            uistd.aroundCB.setStyle("-fx-background-color : #f70d1b");
            aroundB = false;
        }

        return bamountP && groupeB && aroundB;
    }

    public void clearInputs() {
        this.amount.setText("");
        this.amountP.setText("");
    }

}
