/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package doctorlite.BackEnd.Controller;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXDatePicker;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import doctorlite.BackEnd.Model.Account;
import doctorlite.BackEnd.Model.Student;
import doctorlite.BackEnd.Results;
import doctorlite.BackEnd.Service.StudentService;
import doctorlite.BackEnd.uiPresenter.UiStudentPaiement;

import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;
import javafx.scene.input.KeyEvent;

import static doctorlite.BackEnd.Controller.LoginController.loginUser;
import static doctorlite.DoctorLite.alertUpdate;

/**
 * FXML Controller class
 *
 * @author kadri
 */
public class PaiementCouresController implements Initializable {

    @FXML
    private TableView<?> studentTable;
    @FXML
    private TextField firstName;
    @FXML
    private TextField idStudent;
    @FXML
    private TextField lastName;
    @FXML
    private JFXDatePicker dateP;
    @FXML
    private TextField amountP;
    @FXML
    private TextField amountR;
    @FXML
    private TableColumn<?, ?> firstNameC;
    @FXML
    private TableColumn<?, ?> lastNameC;
    @FXML
    private TableColumn<?, ?> datePC;
    @FXML
    private TableColumn<?, ?> idC;
    @FXML
    private TableColumn<?, ?> amountC;
    @FXML
    private TableColumn<?, ?> amountRC;

    private Account account = new Account();

    private final UiStudentPaiement uistd = new UiStudentPaiement();

    @FXML
    private JFXButton delete;
    @FXML
    private JFXButton update;
    @FXML
    private JFXButton add;

    public static void refrechStudent(TableView table, TableColumn Column1, TableColumn Column2,
                                      TableColumn Column3, TableColumn Column4,
                                      TableColumn Column5, TableColumn Column6
            , Account acnt, String type) {
       /* ObservableList<Account> pr = PaiementService.getAccountOfStudent(acnt);
        Column1.setCellValueFactory(
                new PropertyValueFactory<>("idStudent")
        );
        Column2.setCellValueFactory(
                new PropertyValueFactory<>("firstName")
        );
        Column3.setCellValueFactory(
                new PropertyValueFactory<>("lastName")
        );
        Column4.setCellValueFactory(
                new PropertyValueFactory<>("day")
        );
        Column5.setCellValueFactory(
                new PropertyValueFactory<>("amount")
        );
        Column6.setCellValueFactory(
                new PropertyValueFactory<>("amount")
        );
        table.setItems(pr);*/
    }

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        if (loginUser.getRole().equals("simple")) {
            update.setVisible(false);
            delete.setVisible(false);
        } else {
            update.setVisible(true);
            delete.setVisible(true);
        }
        refrechStudent(studentTable, idC, firstNameC, lastNameC, datePC, amountC, amountRC, new Account(), "");
    }

    public void setInputs(Student std) {
        std.PresentObject();
        account.setIdStudent(std.getId());
        account.setFirstName(std.getFirstName());
        account.setLastName(std.getLastName());
        fillInputes(account);
        account.PresentAccount();
        refrechStudent(studentTable, idC, firstNameC, lastNameC, idC, amountC, amountRC, new Account(), "");
    }

    public void fillInputes(Account account) {
        firstName.setText(account.getFirstName());
        lastName.setText(account.getLastName());
        idStudent.setText(account.getIdStudent() + "");
    }

    @FXML
    private void add() {
      /*  account = Mapping.getObjectAccountFromUiStudentPaiementHistory(uistd);
        Results.Rstls r = PaiementService.addPaiement(account);
        if (r == Results.Rstls.OBJECT_NOT_INSERTED) {
            CommunController.alert(r.toString());
        } else {
            uistd.clearInputs();
        }*/
        refrechStudent(studentTable, idC, firstNameC, lastNameC, idC, amountC, amountRC, new Account(), "");
    }

    @FXML
    private void update(ActionEvent event) {
        /*if (account.getId() != 0) {
            Account newAccount = Mapping.getObjectAccountFromUiStudentPaiementHistory(uistd);
            newAccount.setId(account.getId());
            Optional<ButtonType> option = alertUpdate.showAndWait();
            if (option.get() == ButtonType.OK) {
                Results.Rstls r = StudentService.updateStudent(newAccount);
                if (r == Results.Rstls.OBJECT_NOT_UPDATED) {
                    CommunController.alert(r.toString());
                } else {
                    uistd.clearInputs();
                    refrechStudent(studentTable, idC, firstNameC, lastNameC, idC, amountC, amountRC, new Account(), "");
                }
                account = new Account();
                uistd.clearInputs();
            }
        }*/
    }

    @FXML
    private void delete() {
        if (account.getId() != 0) {
            Optional<ButtonType> option = alertUpdate.showAndWait();
            if (option.get() == ButtonType.OK) {
                Results.Rstls r = StudentService.deleteStudent(account);
                if (r == Results.Rstls.OBJECT_NOT_DELETED) {
                    CommunController.alert(r.toString());
                } else {
                    uistd.clearInputs();
                    refrechStudent(studentTable, idC, firstNameC, lastNameC, idC, amountC, amountRC, new Account(), "");
                }
                account = new Account();
                uistd.clearInputs();
            }
        }
    }

    @FXML
    private void selectStudent(MouseEvent event) {

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
