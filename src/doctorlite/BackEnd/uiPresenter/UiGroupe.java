/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package doctorlite.BackEnd.uiPresenter;

import com.jfoenix.controls.JFXComboBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import doctorlite.BackEnd.Model.Group;
import doctorlite.BackEnd.Model.Teacher;

/**
 *
 * @author Zed Yacine
 */
public class UiGroupe extends TestingMethods {

    private TextField name, nbrPlace;
    private Label name_err, nbrPlace_err, OfferCmb_err,tech_err;
    private ComboBox OfferCmb;
    private JFXComboBox<Teacher> teacherCmb;

    public Label getOfferCmb_err() {
        return OfferCmb_err;
    }

    public void setOfferCmb_err(Label offerCmb_err) {
        OfferCmb_err = offerCmb_err;
    }

    public Label getTech_err() {
        return tech_err;
    }

    public void setTech_err(Label tech_err) {
        this.tech_err = tech_err;
    }

    public JFXComboBox<Teacher> getTeacherCmb() {
        return teacherCmb;
    }

    public void setTeacherCmb(JFXComboBox<Teacher> teacherCmb) {
        this.teacherCmb = teacherCmb;
    }

    public UiGroupe() {
    }

    public UiGroupe(TextField name, TextField price, ComboBox OfferCmb) {
        this.name = name;
        this.nbrPlace = price;
        this.OfferCmb = OfferCmb;
    }

    public UiGroupe(TextField name, TextField nbrPlace, Label name_err, Label price_err, Label OfferCmb_err
            , ComboBox OfferCmb, ComboBox<Teacher> teacherCmb,Label tech_err) {
        this.name = name;
        this.nbrPlace = nbrPlace;
        this.name_err = name_err;
        this.OfferCmb_err = OfferCmb_err;
        this.nbrPlace_err = price_err;
        this.OfferCmb = OfferCmb;
        this.tech_err=tech_err;
        this.teacherCmb = (JFXComboBox<Teacher>) teacherCmb;
    }

    public TextField getName() {
        return name;
    }

    public void setName(TextField name) {
        this.name = name;
    }

    public TextField getNbrPlace() {
        return nbrPlace;
    }

    public void setNbrPlace(TextField nbrPlace) {
        this.nbrPlace = nbrPlace;
    }

    public Label getName_err() {
        return name_err;
    }

    public void setName_err(Label name_err) {
        this.name_err = name_err;
    }

    public Label getNbrPlace_err() {
        return nbrPlace_err;
    }

    public void setNbrPlace_err(Label nbrPlace_err) {
        this.nbrPlace_err = nbrPlace_err;
    }

    public ComboBox getOfferCmb() {
        return OfferCmb;
    }

    public void setOfferCmb(ComboBox offerCmb) {
        OfferCmb = offerCmb;
    }

    public static boolean UiGroupeInputIsValid(UiGroupe uiGroupe) {
        boolean bName = false, bnbrPlace = false, bOffer = false;
        if (uiGroupe.getName().getText().isEmpty()) {
            uiGroupe.getName_err().setText("إسم الفوج فارغ");
            uiGroupe.getName_err().setVisible(true);
        } else {
            uiGroupe.getName_err().setVisible(false);
            bName = true;
        }
        if (uiGroupe.getNbrPlace().getText().isEmpty()) {
            uiGroupe.getNbrPlace_err().setText("عدد الأماكن فارغ");
            uiGroupe.getNbrPlace_err().setVisible(true);
        } else {
            if (!testInt(uiGroupe.getNbrPlace())) {
                uiGroupe.getNbrPlace_err().setText("أدخل ارقام فقط");
                uiGroupe.getNbrPlace_err().setVisible(true);
            } else {
                uiGroupe.getNbrPlace_err().setVisible(false);
                bnbrPlace = true;
            }
        }
        if (uiGroupe.getOfferCmb().getSelectionModel().getSelectedItem() == null) {
            uiGroupe.OfferCmb_err.setText("العرض فارغ");
            uiGroupe.OfferCmb_err.setVisible(true);
        } else {
            uiGroupe.OfferCmb_err.setVisible(false);
            bOffer = true;
        }
        if (uiGroupe.getTeacherCmb().getSelectionModel().getSelectedItem() == null) {
            uiGroupe.tech_err.setText("اسم الأستاذ فارغ");
            uiGroupe.tech_err.setVisible(true);
        } else {
            uiGroupe.tech_err.setVisible(false);
            bOffer = true;
        }
        return bName && bnbrPlace && bOffer;
    }

    public void clearInputs() {
        this.OfferCmb.getSelectionModel().clearSelection();
        this.name.setText("");
        this.name_err.setText("");
        this.nbrPlace.setText("");
    }

}
