/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package doctorlite.BackEnd.Controller;

import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import doctorlite.BackEnd.Model.Group;
import doctorlite.BackEnd.Model.Paiement;
import doctorlite.BackEnd.Model.Seance;
import doctorlite.BackEnd.Model.Student;
import doctorlite.BackEnd.Service.SeanceService;

import java.net.URL;
import java.util.ResourceBundle;

/*import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.design.JasperDesign;
import net.sf.jasperreports.engine.xml.JRXmlLoader;
import net.sf.jasperreports.view.JasperViewer;*/

/**
 * FXML Controller class
 *
 * @author kadri
 */
public class SeanceNotPaidController implements Initializable {

    @FXML
    private TableView<?> seanceTable;
    @FXML
    private Label idL, idL1;


    public static ObservableList<Seance> listSeance;

    @FXML
    private TableColumn<?, ?> roomC, dateTimeC, teacherC, pTeacherC, pStudentC;

    private static Group group = new Group();
    private static Student st = new Student();
    private static Paiement p = new Paiement();


    /**
     * Initializes the controller class.adminضa
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {

    }


    public static void refrechSeance(TableView table, TableColumn Column1, TableColumn Column2,
                                     TableColumn Column3, TableColumn Column4,
                                     TableColumn Column5, Student std, Group grp) {
        p.setStd(std);
        p.setGrp(grp);
        p.PresentObject();
        listSeance = SeanceService.getAllSeancesNoPaid(p);
        Column1.setCellValueFactory(
                new PropertyValueFactory<>("nameRoom")
        );
        Column3.setCellValueFactory(
                new PropertyValueFactory<>("date")
        );
        Column2.setCellValueFactory(
                new PropertyValueFactory<>("nameTeacher")
        );
        Column4.setCellValueFactory(
                new PropertyValueFactory<>("test")
        );
        Column5.setCellValueFactory(
                new PropertyValueFactory<>("test1")
        );
        table.setItems(listSeance);
    }


    @FXML
    private void selectItem(MouseEvent event) {

    }

    public void setInputs(Student std) {
        st = std;
        group = std.getGroup();
        idL.setText(idL.getText() + " " + std.getFirstName() + " " + std.getLastName());
        idL1.setText(idL1.getText() + " " + std.getGroup().getNameGroup());
        showSeancsNoPaid(st, group);
    }

    private void showSeancsNoPaid(Student std, Group grp) {
        if (std != null && grp != null) {
            refrechSeance(seanceTable, roomC, teacherC,dateTimeC, pTeacherC, pStudentC,
                    st, group);
        } else {
            System.out.println(std + " is null");
        }
    }


}
