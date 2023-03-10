/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package doctorlite.BackEnd.Controller;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.stage.Stage;
import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;
import java.awt.Dimension;
import java.awt.Toolkit;

import javafx.scene.input.MouseEvent;
import doctorlite.BackEnd.Model.User;
import doctorlite.BackEnd.Service.UserService;
import static doctorlite.DoctorLite.loginStage;

/**
 * FXML Controller class
 *
 * @author Administrator
 */
public class LoginController implements Initializable {

    @FXML
    private Label error;
    @FXML
    private JFXTextField username;
    @FXML
    private JFXPasswordField password;

    public static User loginUser;
    private static final Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }

    @FXML
    private void login(ActionEvent event) throws IOException {

        User user = new User(username.getText(), password.getText());

        if (user.getUserName().isEmpty()) {
            error.setText("الرجاء ملأ اسم المستخدم");
            error.setVisible(true);
        } else if (user.getPassword().isEmpty()) {
            error.setText("الرجاء ملأ كلمة المرور");
            error.setVisible(true);
        } else {
            loginUser = UserService.isauthentificated(user);
            if (loginUser.getId() == 0) {
                error.setText("اسم المستخدم أو كلمة المرور خاطئة");
                error.setVisible(true);
            } else {
                try {
                    Parent home = FXMLLoader.load(this.getClass().getResource("/doctorlite/FrontEnd/layout/Main.fxml"));
                    if (screenSize.getWidth() <= 1366) {
                        screenSize.width = 1340;
                        screenSize.height = 700;
                    } else {
                        screenSize.width = 1570;
                        screenSize.height = 820;
                    }
                    Scene home_scene = new Scene(home, (screenSize.getWidth()), screenSize.getHeight());
                    loginStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
                    loginStage.hide();
                    loginStage.setScene(home_scene);
                    loginStage.setTitle("برنامج إدارة المدرسة الخاصة");
                    loginStage.setResizable(false);
                    loginStage.show();
                } catch (IOException ex) {
                    Logger.getLogger(LoginController.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
    }

    @FXML
    private void close(MouseEvent event) {
        loginStage.close();
    }
}
