/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package doctorlite.BackEnd.Controller;

import com.jfoenix.controls.JFXButton;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.MenuItem;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;

import static doctorlite.DoctorLite.SecondStage;

/**
 * FXML Controller class
 *
 * @author Administrator
 */
public class MainController implements Initializable {

    @FXML
    private BorderPane main;
    @FXML
    private JFXButton presence;

    private double offset_x1;
    private double offset_y1;
    @FXML
    private JFXButton seance;
    @FXML
    private JFXButton offres;
    @FXML
    private JFXButton groupes;
    @FXML
    private MenuItem user;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        offres.setStyle("-fx-background-color : linear-gradient( #182532 80%, #0077cc 100%);");
        Parent root;
        try {
            root = FXMLLoader.load(getClass().getResource("/doctorlite/FrontEnd/layout/Offer.fxml"));
            main.setCenter(null);
            if (root != null) {
                main.setCenter(root);
            }
        } catch (IOException ex) {
            Logger.getLogger(MainController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void changePane(JFXButton btn1, JFXButton btn2, JFXButton btn3,
                           JFXButton btn4, String rsc) throws IOException {

        btn1.setStyle("-fx-background-color :  linear-gradient( #182532 80%, #0077cc 100%);");
        btn2.setStyle("-fx-background-color : transparent;");
        btn3.setStyle("-fx-background-color : transparent;");
        btn4.setStyle("-fx-background-color : transparent;");
        Parent root = FXMLLoader.load(getClass().getResource("/doctorlite/FrontEnd/layout/" + rsc + ".fxml"));
        main.setCenter(null);
        if (root != null) {
            main.setCenter(root);
        } else {
            System.out.println(root);
        }
    }


    private void btn4(ActionEvent event) throws IOException {

    }

    @FXML
    private void goToStudent(ActionEvent event) throws IOException {
        String url = "/doctorlite/FrontEnd/layout/Student.fxml";
        swithchLayout(url, SecondStage, "إدارة التلاميذ");
    }

    @FXML
    private void goToTeacher(ActionEvent event) throws IOException {
        String url = "/doctorlite/FrontEnd/layout/Teacher.fxml";
        swithchLayout(url, SecondStage, "إدارة الأساتذة");
    }

    @FXML
    private void goToLevel(ActionEvent event) throws IOException {
        String url = "/doctorlite/FrontEnd/layout/level.fxml";
        swithchLayout(url, SecondStage, "إدارة المستويات");
    }

    @FXML
    private void goToModule(ActionEvent event) throws IOException {
        String url = "/doctorlite/FrontEnd/layout/module.fxml";
        swithchLayout(url, SecondStage, "إدارة المواد");
    }

    @FXML
    private void goToType(ActionEvent event) throws IOException {
        String url = "/doctorlite/FrontEnd/layout/Type.fxml";
        swithchLayout(url, SecondStage, "إدارة الانواع");

    }

    @FXML
    private void goToSections(ActionEvent event) throws IOException {
        String url = "/doctorlite/FrontEnd/layout/Section.fxml";
        swithchLayout(url, SecondStage, "إدارة الأقسام");

    }

    @FXML
    private void goToRooms(ActionEvent event) throws IOException {
        String url = "/doctorlite/FrontEnd/layout/Room.fxml";
        swithchLayout(url, SecondStage, "إدارة القاعات");

    }

    @FXML
    private void goToSetting(ActionEvent event) throws IOException {
        String url = "/doctorlite/FrontEnd/layout/BackupDb.fxml";
        swithchLayout(url, SecondStage, "حفظ  قاعدة البيانات ");
    }


    @FXML
    private void About(ActionEvent event) {

    }


    @FXML
    private void drag(MouseEvent event) {

    }

    @FXML
    private void press(MouseEvent event) {

    }

    private void swithchLayout(String url, Stage stage, String title) {
        try {
            Parent uistd = FXMLLoader.load(this.getClass().getResource(url));
            Scene scene = new Scene(uistd);
            if (!stage.isShowing()) {
                stage.setScene(scene);
                stage.setTitle(title);
                stage.showAndWait();
            } else {
                stage.setScene(scene);
                stage.setTitle(title);
                stage.setAlwaysOnTop(true);
                stage.setAlwaysOnTop(false);
            }
        } catch (IOException ex) {
            Logger.getLogger(MainController.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    /*private void stat(ActionEvent event) throws IOException {
        String rsc="stat";
        changePane(stat,offres,presence,seance,groupes,rsc);
    }*/


    @FXML
    private void presence(ActionEvent event) throws IOException {
        String rsc = "Paiement";
        changePane(presence, seance, offres, groupes, rsc);
    }

    @FXML
    private void seance(ActionEvent event) throws IOException {
        String rsc = "seance";
        changePane(seance, presence, offres, groupes, rsc);
    }

    @FXML
    private void offer(ActionEvent event) throws IOException {
        String rsc = "Offer";
        changePane(offres, groupes, presence, seance, rsc);
    }

    @FXML
    private void groupes(ActionEvent event) throws IOException {
        String rsc = "Groupes";
        changePane(groupes, offres, presence, seance, rsc);
    }

}
