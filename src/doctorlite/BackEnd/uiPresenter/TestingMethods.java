/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package doctorlite.BackEnd.uiPresenter;

import javafx.scene.control.TextField;

/**
 *
 * @author Zed Yacine
 */
public class TestingMethods {

    public static boolean testDouble(TextField t) {
        boolean var = t.getText().matches("\\d+\\.\\d+") || t.getText().matches("\\d+") || t.getText().matches("\\d+\\.");
        return var;
    }

    public static boolean testInt(TextField t) {
        boolean var = t.getText().matches("\\d+");
        return var;
    }

}
