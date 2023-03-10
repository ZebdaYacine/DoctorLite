/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package doctorlite.BackEnd.uiPresenter;


import javafx.scene.control.Label;
import javafx.scene.control.TextField;

/**
 *
 * @author Zed Yacine
 */
public class UiTeacher extends TestingMethods {

    private TextField firstName;
    private TextField lastName;
    private TextField phone;
    private TextField workeSpace;

    private Label firstName_err;
    private Label lastName_err;
    private Label phone_err;
    private Label workeSpace_err;

    public UiTeacher() {
    }

    public UiTeacher(TextField firstName, TextField lastName, TextField phone, TextField workeSpace) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.phone = phone;
        this.workeSpace = workeSpace;
    }

    public UiTeacher(TextField firstName, TextField lastName, TextField phone, TextField workeSpace
            , Label firstName_err, Label lastName_err, Label phone_err, Label workeSpace_err) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.phone = phone;
        this.workeSpace = workeSpace;
        this.firstName_err = firstName_err;
        this.lastName_err = lastName_err;
        this.phone_err = phone_err;
        this.workeSpace_err = workeSpace_err;
    }

    public TextField getFirstName() {
        return firstName;
    }

    public void setFirstName(TextField firstName) {
        this.firstName = firstName;
    }

    public TextField getLastName() {
        return lastName;
    }

    public void setLastName(TextField lastName) {
        this.lastName = lastName;
    }

    public TextField getPhone() {
        return phone;
    }

    public void setPhone(TextField phone) {
        this.phone = phone;
    }

    public TextField getWorkeSpace() {
        return workeSpace;
    }

    public void setWorkeSpace(TextField workeSpace) {
        this.workeSpace = workeSpace;
    }

    public Label getFirstName_err() {
        return firstName_err;
    }

    public void setFirstName_err(Label firstName_err) {
        this.firstName_err = firstName_err;
    }

    public Label getLastName_err() {
        return lastName_err;
    }

    public void setLastName_err(Label lastName_err) {
        this.lastName_err = lastName_err;
    }

    public Label getPhone_err() {
        return phone_err;
    }

    public void setPhone_err(Label phone_err) {
        this.phone_err = phone_err;
    }

    public Label getWorkeSpace_err() {
        return workeSpace_err;
    }

    public void setWorkeSpace_err(Label workeSpace_err) {
        this.workeSpace_err = workeSpace_err;
    }

    public static boolean UiTeacherInputIsValid(UiTeacher uistd) {
        boolean bFirstName = false, blastName = false, bphone = false, bWorkeSpace = false;
        if (uistd.getFirstName().getText().isEmpty()) {
            uistd.getFirstName_err().setText("اللقب فارغ");
            uistd.getFirstName_err().setVisible(true);
        } else {
            uistd.getFirstName_err().setVisible(false);
            bFirstName = true;
        }
        if (uistd.getLastName().getText().isEmpty()) {
            uistd.getLastName_err().setText("الإسم فارغ");
            uistd.getLastName_err().setVisible(true);
        } else {
            uistd.getLastName_err().setVisible(false);
            blastName = true;
        }
        if (uistd.getWorkeSpace().getText().isEmpty()) {
            uistd.getWorkeSpace_err().setText("أدخل مكان العمل");
            uistd.getWorkeSpace_err().setVisible(true);
        } else {
            if (uistd.getWorkeSpace().getText().length() > 20) {
                uistd.getWorkeSpace_err().setText("مكان العمل متكون من 20 حرف أو أقل");
                uistd.getWorkeSpace_err().setVisible(true);
            } else {
                uistd.getWorkeSpace_err().setVisible(false);
                bWorkeSpace = true;
            }
        }
        if (uistd.getPhone().getText().isEmpty()) {
            uistd.getPhone_err().setText("رقم الهاتف فارغ");
            uistd.getPhone_err().setVisible(true);
        } else {
            /*if (testInt(uistd.getPhone())) {
                if (uistd.getPhone().getText().length() != 10) {
                    uistd.getPhone_err().setText("رقم الهاتف متكون من 10 ارقام");
                    uistd.getPhone_err().setVisible(true);
                } else {
                    uistd.getWorkeSpace_err().setVisible(false);
                    bWorkeSpace = true;
                }
            } else {
                uistd.getPhone_err().setText("أدخل ارقام");
                uistd.getPhone_err().setVisible(true);
            }*/
            bphone=true;
        }

        return bFirstName && bphone && bWorkeSpace;
    }

    public  void clearInputs() {
        this.firstName.setText("");
        this.firstName_err.setText("");
        this.lastName.setText("");
        this.lastName_err.setText("");
        this.workeSpace.setText("");
        this.phone_err.setText("");
        this.phone.setText("");
        this.workeSpace_err.setText("");
    }

}
