/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package doctorlite.BackEnd.Controller;

import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.util.Callback;
import doctorlite.BackEnd.Mapper.Mapping;
import doctorlite.BackEnd.Model.Offer;
import doctorlite.BackEnd.Results;
import doctorlite.BackEnd.Service.OfferService;
import doctorlite.BackEnd.Service.TypeService;
import doctorlite.BackEnd.uiPresenter.UiOffre;

import static doctorlite.BackEnd.Controller.LoginController.loginUser;
import static doctorlite.DoctorLite.alertUpdate;

/**
 * FXML Controller class
 *
 * @author kadri
 */
public class OfferController implements Initializable {

    @FXML
    private TableView<Offer> offerTable;
    @FXML
    private TextField name, price;
    @FXML
    private Label nameErr, priceErr, typeErr, moduleErr, levelErr;
    @FXML
    private JFXComboBox typeCmb, moduleCmb, levelCmb;
    @FXML
    private TableColumn<?, ?> nameC,TypeC,ModuleC,LevelC,PriceC;
    @FXML
    private JFXButton update,delete;

    private Offer offer = new Offer();

    private UiOffre uiOffre = new UiOffre();


    @Override
    public void initialize(URL url, ResourceBundle rb) {
        refrechOffre(offerTable,nameC,TypeC,ModuleC,LevelC,PriceC, offer,"");
        ObservableList<String> typesNameList = TypeService.getAllObjectName("type");
        ObservableList<String> ModulesNameList = TypeService.getAllObjectName("module");
        ObservableList<String> LevelsNameList = TypeService.getAllObjectName("level");
        this.typeCmb.setItems(typesNameList);
        this.moduleCmb.setItems(ModulesNameList);
        this.levelCmb.setItems(LevelsNameList);
        uiOffre = new UiOffre( name, price, nameErr, priceErr, typeErr, moduleErr, levelErr, typeCmb
                ,  moduleCmb,  levelCmb);
        if (loginUser.getRole().equals("simple")) {
            update.setVisible(false);
            delete.setVisible(false);
        } else {
            update.setVisible(true);
            delete.setVisible(true);
        }
        offerTable.setRowFactory(new Callback<TableView<Offer>, TableRow<Offer>>() {
            @Override
            public TableRow<Offer> call(TableView param) {
                return new TableRow<Offer>() {
                    protected void updateItem(Offer offer, boolean b) {
                        super.updateItem(offer, b);
                        if (offer != null) {
                            switch (offer.getLevel().toLowerCase()){
                                case "الثانوي" :{
                                    setStyle("-fx-background-color: #29A952;");
                                    break;
                                }
                                case "المتوسط" :{
                                    setStyle("-fx-background-color: #ef910e;");
                                    break;
                                }
                                case "الابتدائي" :{
                                    setStyle("-fx-background-color: #d48eaf;");
                                    break;
                                }
                            }
                        } else {
                            setStyle("-fx-background-color: #081018;");
                        }
                    }
                };
            }
        });

    }

    public static void refrechOffre(TableView table, TableColumn Column1, TableColumn Column2,
                                    TableColumn Column3, TableColumn Column4,
                                    TableColumn Column5, Offer offer, String type) {
        ObservableList<Offer> pr;
        if (type.equals("searche")) {
            pr = OfferService.searchStudentByName(offer);
        } else {
            pr = OfferService.getAllOffers();
        }
        Column1.setCellValueFactory(
                new PropertyValueFactory<>("name")
        );
        Column2.setCellValueFactory(
                new PropertyValueFactory<>("type")
        );
        Column3.setCellValueFactory(
                new PropertyValueFactory<>("module")
        );
        Column4.setCellValueFactory(
                new PropertyValueFactory<>("level")
        );
        Column5.setCellValueFactory(
                new PropertyValueFactory<>("price")
        );
        table.setItems(pr);
    }

    @FXML
    private void add() {
        offer = Mapping.getOffreObjectFromOffreUi(uiOffre);
        if(offer!= null){
            Results.Rstls r = OfferService.addOffer(offer);
            if (r == Results.Rstls.OBJECT_NOT_INSERTED) {
                CommunController.alert(r.toString());
            } else {
                uiOffre.clearInputs();
            }
            refrechOffre(offerTable,nameC,TypeC,ModuleC,LevelC,PriceC, offer,"");
        }
    }

    @FXML
    private void update(ActionEvent event) {
        if (offer.getId() != 0) {
            Offer newOffer = Mapping.getOffreObjectFromOffreUi(uiOffre);
            newOffer.setId(offer.getId());
            Optional<ButtonType> option = alertUpdate.showAndWait();
            if (option.get() == ButtonType.OK) {
                Results.Rstls r = OfferService.updateOffer(newOffer);
                if (r == Results.Rstls.OBJECT_NOT_UPDATED) {
                    CommunController.alert(r.toString());
                } else {
                    uiOffre.clearInputs();
                    refrechOffre(offerTable,nameC,TypeC,ModuleC,LevelC,PriceC, offer,"");
                }
                offer = new Offer();
                uiOffre.clearInputs();
            }
        }
    }

    @FXML
    private void delete() {
        if (offer.getId() != 0) {
            Offer newOffer = Mapping.getOffreObjectFromOffreUi(uiOffre);
            newOffer.setId(offer.getId());
            Optional<ButtonType> option = alertUpdate.showAndWait();
            if (option.get() == ButtonType.OK) {
                Results.Rstls r = OfferService.deleteOffer(newOffer);
                if (r == Results.Rstls.OBJECT_NOT_UPDATED) {
                    CommunController.alert(r.toString());
                } else {
                    uiOffre.clearInputs();
                    refrechOffre(offerTable,nameC,TypeC,ModuleC,LevelC,PriceC, offer,"");
                }
                offer = new Offer();
                uiOffre.clearInputs();
            }
        }
    }


    @FXML
    private void selectOffer(MouseEvent event) {
        uiOffre.clearInputs();
        offer = offerTable.getSelectionModel().getSelectedItem();
        if (offer != null) {
            name.setText(offer.getName());
            price.setText(offer.getPrice()+"");
            moduleCmb.getSelectionModel().select(offer.getModule());
            levelCmb.getSelectionModel().select(offer.getLevel());
            typeCmb.getSelectionModel().select(offer.getType());
            uiOffre = new UiOffre( name, price, nameErr, priceErr, typeErr, moduleErr, levelErr, typeCmb
                    ,  moduleCmb,  levelCmb);
        }
    }

    @FXML
    private void search(KeyEvent event) {

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
