/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package doctorlite.BackEnd.Controller;

import com.jfoenix.controls.JFXRadioButton;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.DirectoryChooser;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.ResourceBundle;
import doctorlite.LocalSettings;
import static doctorlite.DoctorLite.SecondStage;

/**
 * FXML Controller class
 *
 * @author kadri
 */
public class BackupDbController implements Initializable {

    @FXML
    private TextField path;

    @FXML
    private JFXRadioButton data, all;

    @FXML
    private Label path_err;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        path_err.setVisible(false);
        path_err.setText("");
        String userNameSys = System.getProperty("user.name");
        String pt = "\"C:\\Users\\" + userNameSys + "\\Desktop\\schoolManager\\dumps\\" + FileDb() + "\"";
        data.setSelected(true);
        data.setOnAction(event -> {
            if (data.isSelected()) {
                all.setSelected(false);
            }
        });
        all.setOnAction(event -> {
            if (all.isSelected()) {
                data.setSelected(false);
            }
        });
        path.setText(pt);
    }

    private String FileDb() {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        Date date = new Date();
        String file = formatter.format(date);
        return file + ".sql";
    }

    private String getPath() {
        String str = "";
        DirectoryChooser directoryChooser = new DirectoryChooser();
        File selectedDirectory = directoryChooser.showDialog(SecondStage);
        if (selectedDirectory == null) {
            str = null;
        } else {
            str = selectedDirectory.getAbsolutePath();
        }
        return str;
    }

    @FXML
    private void clone(ActionEvent event) {
        String dump = "C:\\\"Program Files\"\\MySQL\\\"MySQL Server 8.0\"\\bin\\mysqldump.exe -u" + LocalSettings.user + " -p" + LocalSettings.Password + " --no-create-info SchoolManager > ";
        String str = path.getText();
        if (str.isEmpty()) {
            path_err.setText("اختر مسار المسار ");
            path_err.setVisible(true);
            return;
        }
        if (data.isSelected()) {
            dump = dump + str;
        } else if (all.isSelected()) {
            dump = "C:\\\"Program Files\"\\MySQL\\\"MySQL Server 8.0\"\\bin\\mysqldump.exe -uroot -proot  SchoolManager > ";
            dump = dump + str;
        }
        createDumpFile(dump);
    }

    private void createDumpFile(String dump) {
        String[] cmdarray = {"cmd.exe", "/c", dump};
        Process p = null;
        try {
            p = Runtime.getRuntime().exec(cmdarray);
        } catch (IOException e) {
            CommunController.alert("حدث خطأ في نسخ قاعدة البيانات ");
            System.err.println("try 1");
        }
        try {
            if (p.waitFor() == 0) {
                CommunController.alert("تم نسخ قاعدة البيانات ");
                SecondStage.close();
            } else {
                CommunController.alert("حدث خطأ في نسخ قاعدة البيانات ");
                System.err.println("try 2");
            }
        } catch (InterruptedException e) {
            CommunController.alert("حدث خطأ في نسخ قاعدة البيانات ");
            System.err.println("try 3");
        }
    }

    @FXML
    private void browse(ActionEvent event) {
        String p = getPath();
        if (p != null) {
            path_err.setVisible(false);
            path_err.setText("");
            path.setText("\"" + p + "\\" + FileDb() + "\"");
        } else {
            path.setText("");
        }
    }

}
