/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package doctorlite.BackEnd.uiPresenter;

import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

/**
 *
 * @author Zed Yacine
 */
public class UiOffre extends TestingMethods {

    private TextField name, price;
    private Label name_err, price_err, type_err, module_err, level_err;
    private ComboBox TypeCmb, ModuleCmb, LevelCmb;

    public UiOffre() {
    }

    public UiOffre(TextField name, TextField price, ComboBox typeCmb, ComboBox moduleCmb, ComboBox levelCmb) {
        this.name = name;
        this.price = price;
        TypeCmb = typeCmb;
        ModuleCmb = moduleCmb;
        LevelCmb = levelCmb;
    }

    public UiOffre(TextField name, TextField price, Label name_err, Label price_err, Label type_err, Label module_err, Label level_err, ComboBox typeCmb, ComboBox moduleCmb, ComboBox levelCmb) {
        this.name = name;
        this.price = price;
        this.name_err = name_err;
        this.price_err = price_err;
        this.type_err = type_err;
        this.module_err = module_err;
        this.level_err = level_err;
        TypeCmb = typeCmb;
        ModuleCmb = moduleCmb;
        LevelCmb = levelCmb;
    }

    public TextField getName() {
        return name;
    }

    public void setName(TextField name) {
        this.name = name;
    }

    public TextField getPrice() {
        return price;
    }

    public void setPrice(TextField price) {
        this.price = price;
    }

    public Label getName_err() {
        return name_err;
    }

    public void setName_err(Label name_err) {
        this.name_err = name_err;
    }

    public Label getPrice_err() {
        return price_err;
    }

    public void setPrice_err(Label price_err) {
        this.price_err = price_err;
    }

    public Label getType_err() {
        return type_err;
    }

    public void setType_err(Label type_err) {
        this.type_err = type_err;
    }

    public Label getModule_err() {
        return module_err;
    }

    public void setModule_err(Label module_err) {
        this.module_err = module_err;
    }

    public Label getLevel_err() {
        return level_err;
    }

    public void setLevel_err(Label level_err) {
        this.level_err = level_err;
    }

    public ComboBox getTypeCmb() {
        return TypeCmb;
    }

    public void setTypeCmb(ComboBox typeCmb) {
        TypeCmb = typeCmb;
    }

    public ComboBox getModuleCmb() {
        return ModuleCmb;
    }

    public void setModuleCmb(ComboBox moduleCmb) {
        ModuleCmb = moduleCmb;
    }

    public ComboBox getLevelCmb() {
        return LevelCmb;
    }

    public void setLevelCmb(ComboBox levelCmb) {
        LevelCmb = levelCmb;
    }

    public static boolean UiOffreInputIsValid(UiOffre uiOffre) {
        boolean bName = false, bPrice = false, bLevel = false, bType = false, bModule = false;
        if (uiOffre.getName().getText().isEmpty()) {
            uiOffre.getName_err().setText("إسم العرض فارغ");
            uiOffre.getName_err().setVisible(true);
        } else {
            uiOffre.getName_err().setVisible(false);
            bName = true;
        }
        if (uiOffre.getPrice().getText().isEmpty()) {
            uiOffre.getPrice_err().setText("السعر فارغ");
            uiOffre.getPrice_err().setVisible(true);
        } else {
            if (!testInt(uiOffre.getPrice())) {
                uiOffre.getPrice_err().setText("أدخل ارقام");
                uiOffre.getPrice_err().setVisible(true);
            } else {
                uiOffre.getPrice_err().setVisible(false);
                bPrice = true;
            }
        }
        if (uiOffre.getLevelCmb().getSelectionModel().getSelectedItem() == null) {
            uiOffre.getLevel_err().setText("حدد المستوى");
            uiOffre.getLevel_err().setVisible(true);
        } else {
            uiOffre.getLevel_err().setVisible(false);
            bLevel = true;
        }
        if (uiOffre.getModuleCmb().getSelectionModel().getSelectedItem() == null) {
            uiOffre.getModule_err().setText("حدد المادة");
            uiOffre.getModule_err().setVisible(true);
        } else {
            uiOffre.getModule_err().setVisible(false);
            bModule = true;
        }
        if (uiOffre.getTypeCmb().getSelectionModel().getSelectedItem() == null) {
            uiOffre.getType_err().setText("حدد النوع");
            uiOffre.getType_err().setVisible(true);
        } else {
            uiOffre.getType_err().setVisible(false);
            bType = true;
        }
        return bName && bPrice && bLevel && bLevel && bType && bModule;
    }

    public void clearInputs() {
        this.ModuleCmb.getSelectionModel().clearSelection();
        this.LevelCmb.getSelectionModel().clearSelection();
        this.TypeCmb.getSelectionModel().clearSelection();
        this.name.setText("");
        this.name_err.setText("");
        this.price.setText("");
        this.price.setText("");
    }

}
