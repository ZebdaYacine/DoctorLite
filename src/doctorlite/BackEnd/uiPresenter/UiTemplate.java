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
public class UiTemplate  extends TestingMethods{
      private TextField name;
    private Label name_err;


    public UiTemplate() {
    }

    public UiTemplate(TextField name , Label name_err) {
        this.name = name;
        this.name_err = name_err;
    }

    public TextField getName() {
        return name;
    }

    public void setName(TextField name) {
        this.name = name;
    }

    public Label getName_err() {
        return name_err;
    }

    public void setName_err(Label name_err) {
        this.name_err = name_err;
    }

   
    public static boolean UiInputIsValid(UiTemplate uitemplate) {
        boolean bname = false;
        if (uitemplate.getName().getText().isEmpty()) {
            uitemplate.getName_err().setText("الإسم فارغ");
            uitemplate.getName_err().setVisible(true);
        } else {
           if (uitemplate.getName().getText().length() > 20) {
                uitemplate.getName_err().setText("مكان العمل يتكون من 20 حرف او أقل");
                uitemplate.getName_err().setVisible(true);
            } else {
                uitemplate.getName_err().setVisible(false);
                bname = true;
            }
        }
        return bname ;
    }

    public  void clearInputs() {
        this.name.setText("");
        this.name_err.setText("");
    }

}
