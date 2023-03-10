/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package doctorlite.BackEnd.Controller;

import java.util.Optional;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import doctorlite.BackEnd.Model.Paiement;

/**
 *
 * @author Zed Yacine
 */
public class CommunController {
    
    public static String checkString(String str) {
        String[] arraString = {"", ""};
        String phone1 = "";
        if (str.contains(",") || str.contains(";") || str.length() > 10) {
            boolean a1 = str.contains(",");
            boolean a2 = str.contains(";");
            if (a1) {
                arraString = str.split(",");
                phone1 = getNbr(arraString);
                a2 = phone1.contains(";");
            }else{
                phone1=str;
            }
            if (a2) {
                arraString = phone1.split(";");
                phone1 = getNbr(arraString);
            }
        } else {
            phone1 = str;
        }
        return phone1;
    }

    public static String getNbr(String splitePhone[]) {
        String phone = "";
        for (String str : splitePhone) {
            phone += str;
        }
        return phone;
    }

    public static void alert(String header) {
        Alert a = new Alert(Alert.AlertType.INFORMATION);
        a.setTitle("Information Dialog");
        a.setHeaderText(header);
        a.showAndWait();
    }

    public static boolean confirm(String header) {
        Alert a = new Alert(Alert.AlertType.CONFIRMATION);
        a.setTitle("Information Dialog");
        a.setContentText(header);
        final Optional<ButtonType> result = a.showAndWait();
        return result.get() == ButtonType.OK;
    }

    public static float getAmountSeance(Paiement p) {
        float amountSeance = 0f;
        switch (p.getTypeOfOffer().toLowerCase()) {
            case "simple": {
                amountSeance = p.getAmount() / 4;
                break;
            }
            case "double": {
                amountSeance = p.getAmount() / 8;
                break;
            }
            case "vip": {
                amountSeance = p.getAmount() / 2;
                break;
            }
        }
        return amountSeance;
    }

    public static int getnbrSeanceInOffer(String type) {
        int nbr = 0;
        switch (type.toLowerCase()) {
            case "simple": {
                nbr = 4;
                break;
            }
            case "double": {
                nbr =  8;
                break;
            }
            case "vip": {
                nbr = 2;
                break;
            }
        }
        return nbr;
    }
}
