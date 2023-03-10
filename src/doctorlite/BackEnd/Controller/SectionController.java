/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package doctorlite.BackEnd.Controller;

import com.jfoenix.controls.JFXButton;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import doctorlite.BackEnd.Mapper.Mapping;
import doctorlite.BackEnd.Model.Section;
import doctorlite.BackEnd.Model.Template;
import doctorlite.BackEnd.Results;
import doctorlite.BackEnd.Service.LevelService;
import doctorlite.BackEnd.uiPresenter.UiSection;

import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;

import static doctorlite.BackEnd.Controller.LoginController.loginUser;
import static doctorlite.DoctorLite.alertUpdate;

/**
 * FXML Controller class
 *
 * @author kadri
 */
public class SectionController implements Initializable {

    @FXML
    private TableView<?> SectionTable;
    @FXML
    private TextField name;
    @FXML
    private TableColumn<?, ?> nameC;
    @FXML
    private Label name_err;

    private Section section = new Section();

    private UiSection uiSection = new UiSection();

    @FXML
    private JFXButton delete;
    @FXML
    private JFXButton update;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        uiSection = new UiSection(name, name_err);
        if (loginUser.getRole().equals("simple")) {
            update.setVisible(false);
            delete.setVisible(false);
        } else {
            update.setVisible(true);
            delete.setVisible(true);
        }
        refrech(SectionTable, nameC, "", new Template());

    }

    public static void refrech(TableView table, TableColumn Column1, String type, Template template) {
        ObservableList<Object> pr;
        switch (type) {
            case "search": {
                pr = LevelService.searchObjectByName(template, "section");
                break;
            }
            default: {
                pr = LevelService.getAllObjects("section");
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
        Section section1 = Mapping.getObjectSectionFromUiSection(uiSection);
        if (section1 != null) {
            Results.Rstls r = LevelService.addObject(section1, "section");
            if (r == Results.Rstls.OBJECT_NOT_INSERTED) {
                CommunController.alert(r.toString());
            }
            refrech(SectionTable, nameC, "", new Template());
        }
    }

    @FXML
    private void update(ActionEvent event) {
        if (section.getId() != 0) {
            Section section1 = Mapping.getObjectSectionFromUiSection(uiSection);
            section1.setId(section.getId());
            Optional<ButtonType> option = alertUpdate.showAndWait();
            if (option.get() == ButtonType.OK) {
                Results.Rstls r = LevelService.updateObject(section1, "section");
                if (r == Results.Rstls.OBJECT_NOT_UPDATED) {
                    CommunController.alert(r.toString());
                } else {
                    refrech(SectionTable, nameC, "", new Template());
                }
                section = new Section();
                uiSection.clearInputs();
            }
        }
    }

    @FXML
    private void delete( ) {
        if (section.getId() != 0) {
            Section section1 = Mapping.getObjectSectionFromUiSection(uiSection);
            section1.setId(section.getId());
            Optional<ButtonType> option = alertUpdate.showAndWait();
            if (option.get() == ButtonType.OK) {
                Results.Rstls r = LevelService.deleteObject(section1, "section");
                if (r == Results.Rstls.OBJECT_NOT_UPDATED) {
                    CommunController.alert(r.toString());
                } else {
                    refrech(SectionTable, nameC, "", new Template());
                }
                section = new Section();
                uiSection.clearInputs();
            }
        }
    }

    @FXML
    private void select(MouseEvent event) {
        uiSection = new UiSection(name, name_err);
        uiSection.clearInputs();
        Template tmp = (Template) SectionTable.getSelectionModel().getSelectedItem();
        section.setName(tmp.getName());
        section.setId(tmp.getId());
        if (section != null) {
            name.setText(section.getName());
        }
    }

    @FXML
    private void search(KeyEvent event) {
        Template g = new Template();
        String s = "";
        if (!name.getText().isEmpty()) {
            g.setName(name.getText());
            s = "name";
            refrech(SectionTable, nameC, "search", g);
        } else {
            refrech(SectionTable, nameC, "", new Template());
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
