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

/**
 *
 * @author Zed Yacine
 */
public class UiRoom extends TestingMethods {

    private TextField name;
    private TextField nbrChair;

    private Label name_err;
    private Label nbrChair_err;

    public UiRoom() {
    }

    public UiRoom(TextField name, TextField nbrChair) {
        this.name = name;
        this.nbrChair = nbrChair;
    }

    public UiRoom(TextField name, TextField nbrChair, Label name_err, Label nbrChair_err) {
        this.name = name;
        this.nbrChair = nbrChair;
        this.name_err = name_err;
        this.nbrChair_err = nbrChair_err;
    }

    public TextField getName() {
        return name;
    }

    public void setName(TextField name) {
        this.name = name;
    }

    public TextField getNbrChair() {
        return nbrChair;
    }

    public void setNbrChair(TextField nbrChair) {
        this.nbrChair = nbrChair;
    }

    public Label getName_err() {
        return name_err;
    }

    public void setName_err(Label name_err) {
        this.name_err = name_err;
    }

    public Label getNbrChair_err() {
        return nbrChair_err;
    }

    public void setNbrChair_err(Label nbrChair_err) {
        this.nbrChair_err = nbrChair_err;
    }

    public static boolean UiRoomInputIsValid(UiRoom uiroom) {
        boolean bName = false, bNbrChair = false;
        if (uiroom.getName().getText().isEmpty()) {
            uiroom.getName_err().setText("الإسم فارغ");
            uiroom.getName_err().setVisible(true);
        } else {
            uiroom.getName_err().setVisible(false);
            bName = true;
        }

        if (uiroom.getNbrChair().getText().isEmpty()) {
            uiroom.getNbrChair_err().setText("عدد المقاعد فارغ");
            uiroom.getNbrChair_err().setVisible(true);
        } else if (!testInt(uiroom.getNbrChair())) {
            uiroom.getNbrChair_err().setText("ادخل ارقام");
            uiroom.getNbrChair_err().setVisible(true);
        } else {
            uiroom.getNbrChair_err().setVisible(false);
            bNbrChair = true;
        }
        return bName && bNbrChair;
    }

    public void clearInputs() {
        this.name.setText("");
        this.name_err.setText("");
        this.nbrChair.setText("");
        this.nbrChair_err.setText("");
    }

}
