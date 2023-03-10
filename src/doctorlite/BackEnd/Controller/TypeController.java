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
import doctorlite.BackEnd.Model.Template;
import doctorlite.BackEnd.Model.Type;
import doctorlite.BackEnd.Results;
import doctorlite.BackEnd.Service.ModuleService;
import doctorlite.BackEnd.Service.TypeService;
import doctorlite.BackEnd.uiPresenter.UiType;
import static doctorlite.DoctorLite.alertUpdate;

/**
 * FXML Controller class
 *
 * @author kadri
 */
public class TypeController implements Initializable {

    @FXML
    private TableView<?> typeTable;
    @FXML
    private TextField name;
    @FXML
    private TableColumn<?, ?> nameC;
    @FXML
    private Label name_err;

    private Type type = new Type();

    private UiType uiType = new UiType();

    @FXML
    private JFXButton delete;
    @FXML
    private JFXButton update;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        uiType = new UiType(name, name_err);
        if (loginUser.getRole().equals("simple")) {
            update.setVisible(false);
            delete.setVisible(false);
        } else {
            update.setVisible(true);
            delete.setVisible(true);
        }
        refrech(typeTable, nameC, "", new Template());
    }

    public static void refrech(TableView table, TableColumn Column1, String type, Template template) {
        ObservableList<Object> pr;
        switch (type) {
            case "search": {
                pr = ModuleService.searchObjectByName(template, "type");
                break;
            }
            default: {
                pr = ModuleService.getAllObjects("type");
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
        Type type = Mapping.getObjectTypeFromUiType(uiType);
        if (type != null) {
            Results.Rstls r = TypeService.addObject(type, "type");
            if (r == Results.Rstls.OBJECT_NOT_INSERTED) {
                CommunController.alert(r.toString());
            }
            refrech(typeTable, nameC, "", new Template());
        }
    }

    @FXML
    private void update(ActionEvent event) {
        long id = type.getId();
        if (id != 0) {
            type = Mapping.getObjectTypeFromUiType(uiType);
            type.setId(id);
            Optional<ButtonType> option = alertUpdate.showAndWait();
            if (option.get() == ButtonType.OK) {
                Results.Rstls r = TypeService.updateObject(type, "type");
                if (r == Results.Rstls.OBJECT_NOT_UPDATED) {
                    CommunController.alert(r.toString());
                } else {
                    refrech(typeTable, nameC, "", new Template());
                }
                type = new Type();
                uiType.clearInputs();
            }
        }
    }

    @FXML
    private void delete( ) {
        if (type.getId() != 0) {
            Optional<ButtonType> option = alertUpdate.showAndWait();
            if (option.get() == ButtonType.OK) {
                Results.Rstls r = TypeService.deleteObject(type, "type");
                if (r == Results.Rstls.OBJECT_NOT_UPDATED) {
                    CommunController.alert(r.toString());
                } else {
                    refrech(typeTable, nameC, "", new Template());
                }
            }
            type = new Type();
            uiType.clearInputs();
        }
    }

    @FXML
    private void select(MouseEvent event) {
        Template tmp = (Template) typeTable.getSelectionModel().getSelectedItem();
        if (tmp != null) {
            type.setName(tmp.getName());
            type.setId(tmp.getId());
            name.setText(type.getName());
        }
    }

    @FXML
    private void search(KeyEvent event) {
        Template g = new Template();
        if (!name.getText().isEmpty()) {
            g.setName(name.getText());
            refrech(typeTable, nameC, "search", g);
        } else {
            refrech(typeTable, nameC, "", new Template());
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
