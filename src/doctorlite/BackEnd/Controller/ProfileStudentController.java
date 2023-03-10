/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package doctorlite.BackEnd.Controller;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import doctorlite.BackEnd.Model.Group;
import doctorlite.BackEnd.Model.Teacher;
import doctorlite.BackEnd.Service.GroupService;

/**
 * FXML Controller class
 *
 * @author Administrator
 */
public class ProfileStudentController implements Initializable {

    @FXML
    private TableView<?> GroupeTable;
    @FXML
    private TableColumn<?, ?> groupeC;
    @FXML
    private TableColumn<?, ?> nameC;
    @FXML
    private TableColumn<?, ?> placeC;
    @FXML
    private TableColumn<?, ?> moduleC;
    @FXML
    private TableColumn<?, ?> levelC;
    @FXML
    private Label fistname;
    @FXML
    private Label section;
    @FXML
    private Label phone1;
    @FXML
    private Label phone2;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        fistname.setText(BelongsController.std.getFirstName() + "  " + BelongsController.std.getLastName());
        section.setText(BelongsController.std.getSectionName());
        phone1.setText(BelongsController.std.getPhone1());
        phone2.setText(BelongsController.std.getPhone2());
        refrechGroup(GroupeTable, groupeC, placeC, nameC, moduleC, levelC, new Group(), "");
    }

    public void refrechGroup(TableView table, TableColumn Column1, TableColumn Column2,
            TableColumn Column3, TableColumn Column4, TableColumn Column5,
            Group group, String type) {
        Teacher tech = new Teacher(BelongsController.std.getId());
        ObservableList<Group> pr = GroupService.getAllGroups(tech, "std");
        Column1.setCellValueFactory(
                new PropertyValueFactory<>("nameGroup")
        );
        Column2.setCellValueFactory(
                new PropertyValueFactory<>("nbrPlace")
        );
        Column3.setCellValueFactory(
                new PropertyValueFactory<>("name")
        );
        Column4.setCellValueFactory(
                new PropertyValueFactory<>("module")
        );
        Column5.setCellValueFactory(
                new PropertyValueFactory<>("level")
        );
        table.setItems(pr);
    }

    @FXML
    private void selectGroup(MouseEvent event) {
    }

}
