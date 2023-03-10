/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package doctorlite.BackEnd.Controller;

import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import doctorlite.BackEnd.Model.Account;
import doctorlite.BackEnd.Model.Student;
import doctorlite.BackEnd.Service.PaiementService;

import java.net.URL;
import java.util.ResourceBundle;

/**
 * FXML Controller class
 *
 * @author kadri
 */
public class StudentPaiementHistoryController implements Initializable {

    @FXML
    private TableView<?> studentTable;
    @FXML
    private Label stdLbl;
    @FXML
    private TableColumn<?, ?> offerC;
    @FXML
    private TableColumn<?, ?> datePC;
    @FXML
    private TableColumn<?, ?> groupC;
    @FXML
    private TableColumn<?, ?> amountC;
    @FXML
    private TableColumn<?, ?> amountRC;
    private static final Account account = new Account();


    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        refrechStudent(studentTable, groupC, offerC, datePC, amountC, amountRC, account, "");
    }

    public static void refrechStudent(TableView table, TableColumn Column1, TableColumn Column2,
                                      TableColumn Column3, TableColumn Column4,
                                      TableColumn Column5
            , Account acnt, String type) {
     /*   ObservableList<Account> pr= PaiementService.getAccountOfStudent(acnt);
        Column1.setCellValueFactory(
                new PropertyValueFactory<>("groupName")
        );
        Column2.setCellValueFactory(
                new PropertyValueFactory<>("offerName")
        );
        Column3.setCellValueFactory(
                new PropertyValueFactory<>("day")
        );
        Column4.setCellValueFactory(
                new PropertyValueFactory<>("amount")
        );
        Column5.setCellValueFactory(
                new PropertyValueFactory<>("amountC")
        );

        table.setItems(pr);*/
    }

    public void setInputs(Student std) {
        account.setIdStudent(std.getId());
        account.setFirstName(std.getFirstName());
        account.setLastName(std.getLastName());
        stdLbl.setText(" التلميذ : "+account.getFirstName()+" "+account.getLastName());
        account.PresentAccount();
        refrechStudent(studentTable, groupC, offerC, datePC, amountC, amountRC, account, "");
    }

}
