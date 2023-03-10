/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package doctorlite.BackEnd.Controller;

import com.jfoenix.controls.JFXButton;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import static doctorlite.BackEnd.Controller.LoginController.loginUser;
import doctorlite.BackEnd.Mapper.Mapping;
import doctorlite.BackEnd.Model.Level;
import doctorlite.BackEnd.Model.Template;
import doctorlite.BackEnd.Results;
import doctorlite.BackEnd.Service.LevelService;
import doctorlite.BackEnd.uiPresenter.UiLevel;

import static doctorlite.DoctorLite.alertUpdate;

/**
 * FXML Controller class
 *
 * @author kadri
 */
public class LevelController implements Initializable {

    @FXML
    private TableView<?> levelTable;
    @FXML
    private TextField name;
    @FXML
    private TableColumn<?, ?> nameC;
    @FXML
    private Label name_err;

    private Level levl = new Level();

    private UiLevel uilevl = new UiLevel();

    @FXML
    private JFXButton delete;
    @FXML
    private JFXButton update;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        uilevl = new UiLevel(name, name_err);
        if (loginUser.getRole().equals("simple")) {
            update.setVisible(false);
            delete.setVisible(false);
        } else {
            update.setVisible(true);
            delete.setVisible(true);
        }
        refrech(levelTable, nameC, "", new Template());

    }

    public static void refrech(TableView table, TableColumn Column1, String type, Template template) {
        ObservableList<Object> pr;
        switch (type) {
            case "search": {
                pr = LevelService.searchObjectByName(template, "level");
                break;
            }
            default: {
                pr = LevelService.getAllObjects("level");
                break;
            }
        }
        Column1.setCellValueFactory(
                new PropertyValueFactory<>("name")
        );
        table.setItems(pr);
    }

    @FXML
    private void add( ) {
        Level lvel = Mapping.getObjectLevelFromUiLevl(uilevl);
        if (lvel != null) {
            Results.Rstls r = LevelService.addObject(lvel, "level");
            if (r == Results.Rstls.OBJECT_NOT_INSERTED) {
                CommunController.alert(r.toString());
            }
            refrech(levelTable, nameC, "", new Template());
        }
    }

    @FXML
    private void update(ActionEvent event) {
        if (levl.getId() != 0) {
            Level lvel = Mapping.getObjectLevelFromUiLevl(uilevl);
            lvel.setId(levl.getId());
            Optional<ButtonType> option = alertUpdate.showAndWait();
            if (option.get() == ButtonType.OK) {
                Results.Rstls r = LevelService.updateObject(lvel, "level");
                if (r == Results.Rstls.OBJECT_NOT_UPDATED) {
                    CommunController.alert(r.toString());
                } else {
                    refrech(levelTable, nameC, "", new Template());
                }
                levl = new Level();
                uilevl.clearInputs();
            }
        }
    }

    @FXML
    private void delete( ) {
        if (levl.getId() != 0) {
            Level lvel = Mapping.getObjectLevelFromUiLevl(uilevl);
            lvel.setId(levl.getId());
            Optional<ButtonType> option = alertUpdate.showAndWait();
            if (option.get() == ButtonType.OK) {
                Results.Rstls r = LevelService.deleteObject(lvel, "level");
                if (r == Results.Rstls.OBJECT_NOT_UPDATED) {
                    CommunController.alert(r.toString());
                } else {
                    refrech(levelTable, nameC, "", new Template());
                }
                levl = new Level();
                uilevl.clearInputs();
            }
        }
    }

    @FXML
    private void selectLevels(MouseEvent event) {
        UiLevel uilevl = new UiLevel(name, name_err);
        uilevl.clearInputs();
        Template tmp = (Template) levelTable.getSelectionModel().getSelectedItem();
        levl.setName(tmp.getName());
        levl.setId(tmp.getId());
        if (levl != null) {
            name.setText(levl.getName());
        }
    }

    @FXML
    private void search(KeyEvent event) {
        Template g = new Template();
        String s = "";
        if (!name.getText().isEmpty()) {
            g.setName(name.getText());
            s = "name";
            refrech(levelTable, nameC, "search", g);
        } else {
            refrech(levelTable, nameC, "", new Template());
        }
    }

    @FXML
    private void hotkey(KeyEvent event) {
        switch (event.getCode()) {
            case ENTER:
                add();
                break;
            case DELETE:
                delete();
                break;
            default:
                break;
        }
    }
}
