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
import doctorlite.BackEnd.Model.Room;
import doctorlite.BackEnd.Results;
import doctorlite.BackEnd.Service.RoomService;
import doctorlite.BackEnd.uiPresenter.UiRoom;

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
public class RoomController implements Initializable {

    @FXML
    private TableView<?> RoomTable;
    @FXML
    private TextField name;
    @FXML
    private TextField nbrChair;
    @FXML
    private TableColumn<?, ?> nameC;
    @FXML
    private TableColumn<?, ?> nbrChairC;
    @FXML
    private Label name_err;
    @FXML
    private Label nbrChair_err;

    private Room rm = new Room();

    private UiRoom uirm = new UiRoom();

    @FXML
    private JFXButton delete;
    @FXML
    private JFXButton update;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        uirm = new UiRoom(name, nbrChair, name_err, nbrChair_err);
        if (loginUser.getRole().equals("simple")) {
            update.setVisible(false);
            delete.setVisible(false);
        } else {
            update.setVisible(true);
            delete.setVisible(true);
        }
        refrechRoom(RoomTable, nameC, nbrChairC, rm, "");
    }

    public static void refrechRoom(TableView table, TableColumn Column1, TableColumn Column2,
            Room rm, String type) {
        ObservableList<Room> pr;
        if (type.equals("search")) {
            pr = RoomService.searchRoomByName(rm);
        } else {
            pr = RoomService.getAllRoom();
        }
        Column1.setCellValueFactory(
                new PropertyValueFactory<>("name")
        );
        Column2.setCellValueFactory(
                new PropertyValueFactory<>("nbrchair")
        );
        table.setItems(pr);
    }

    @FXML
    private void add( ) {
        rm = Mapping.getObjectRoomFromUiRoom(uirm);
        if (rm != null) {
            Results.Rstls r = RoomService.addRoom(rm);
            if (r == Results.Rstls.OBJECT_NOT_INSERTED) {
                CommunController.alert(r.toString());
            } else {
                uirm.clearInputs();
            }
            refrechRoom(RoomTable, nameC, nbrChairC, new Room(), "");
        }
    }

    @FXML
    private void update(ActionEvent event) {
        if (rm.getId() != 0) {
            Room newRm = Mapping.getObjectRoomFromUiRoom(uirm);
            newRm.setId(rm.getId());
            Optional<ButtonType> option = alertUpdate.showAndWait();
            if (option.get() == ButtonType.OK) {
                Results.Rstls r = RoomService.updateRoom(newRm);
                if (r == Results.Rstls.OBJECT_NOT_UPDATED) {
                    CommunController.alert(r.toString());
                } else {
                    uirm.clearInputs();
                    refrechRoom(RoomTable, nameC, nbrChairC, rm, "");
                }
                rm = new Room();
                uirm.clearInputs();
            }
        }
    }

    @FXML
    private void delete() {
        if (rm.getId() != 0) {
            Optional<ButtonType> option = alertUpdate.showAndWait();
            if (option.get() == ButtonType.OK) {
                Results.Rstls r = RoomService.deleteRoom(rm);
                if (r == Results.Rstls.OBJECT_NOT_DELETED) {
                    CommunController.alert(r.toString());
                } else {
                    uirm.clearInputs();
                    refrechRoom(RoomTable, nameC, nbrChairC, rm, "");
                }
                rm = new Room();
                uirm.clearInputs();
            }
        }
    }

    @FXML
    private void selectStudent(MouseEvent event) {
        uirm.clearInputs();
        rm = (Room) RoomTable.getSelectionModel().getSelectedItem();
        if (rm != null) {
            name.setText(rm.getName());
            nbrChair.setText(rm.getNbrchair() + "");
            rm.PresentTemplate();
            uirm = new UiRoom(name, nbrChair, name_err, nbrChair_err);
        }
    }

    @FXML
    private void search(KeyEvent event) {
        if (!name.getText().isEmpty()) {
            rm.setName(name.getText());
            refrechRoom(RoomTable, nameC, nbrChairC, rm, "search");
        } else {
            refrechRoom(RoomTable, nameC, nbrChairC, rm, "");
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
