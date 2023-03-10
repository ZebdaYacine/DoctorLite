/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package doctorlite.BackEnd.Mapper;

import doctorlite.BackEnd.uiPresenter.UiStudentPaiement;
import doctorlite.BackEnd.uiPresenter.UiSection;
import doctorlite.BackEnd.uiPresenter.UiModule;
import doctorlite.BackEnd.uiPresenter.UiTeacher;
import doctorlite.BackEnd.uiPresenter.UiStudent;
import doctorlite.BackEnd.uiPresenter.UiLevel;
import doctorlite.BackEnd.uiPresenter.UiOffre;
import doctorlite.BackEnd.uiPresenter.UiRoom;
import doctorlite.BackEnd.uiPresenter.UiType;
import doctorlite.BackEnd.uiPresenter.UiSeance;
import doctorlite.BackEnd.uiPresenter.UiGroupe;
import doctorlite.BackEnd.Model.Section;
import doctorlite.BackEnd.Model.Level;
import doctorlite.BackEnd.Model.Module;
import doctorlite.BackEnd.Model.Teacher;
import doctorlite.BackEnd.Model.Seance;
import doctorlite.BackEnd.Model.Offer;
import doctorlite.BackEnd.Model.Room;
import doctorlite.BackEnd.Model.Paiement;
import doctorlite.BackEnd.Model.Group;
import doctorlite.BackEnd.Model.Student;
import doctorlite.BackEnd.Model.Type;
import doctorlite.BackEnd.Controller.CommunController;

/**
 *
 * @author Zed Yacine
 */
public abstract class Mapping {

    public static Student getObjectStudentFromUiStudent(UiStudent uistd) {
        boolean a = UiStudent.UiStudentInputIsValid(uistd);
        if (a) {
            return new Student(
                    CommunController.checkString(uistd.getPhone1().getText()),
                    CommunController.checkString(uistd.getPhone2().getText()),
                    uistd.getSectionName().getSelectionModel().getSelectedItem().toString(),
                    uistd.getFirstName().getText(), uistd.getLastName().getText());
        } else {
            return null;
        }
    }

    public static Seance getObjectSeanceFromUiSeance(UiSeance uiseance) {
        boolean a = UiSeance.UiSeanceInputIsValid(uiseance);
        int pT = 0;
        if (a) {

            String dt = uiseance.getDate().getValue().toString() + " " + uiseance.getTime().getValue().toString();
            return new Seance(
                    uiseance.getTeacherCmb().getSelectionModel().getSelectedItem().getId(),
                    uiseance.getRoomCmb().getSelectionModel().getSelectedItem().getId(),
                    uiseance.getGroupCmb().getSelectionModel().getSelectedItem().getId(),
                    dt
            );
        } else {
            return null;
        }
    }

    public static Teacher getObjectTeacherFromUiTeacher(UiTeacher uitech) {
        boolean a = UiTeacher.UiTeacherInputIsValid(uitech);
        if (a) {
            return new Teacher(uitech.getFirstName().getText(), uitech.getLastName().getText(),
                    CommunController.checkString(uitech.getPhone().getText())
                    , uitech.getWorkeSpace().getText());
        } else {
            return null;
        }
    }

    public static Level getObjectLevelFromUiLevl(UiLevel uiLevl) {
        boolean a = UiLevel.UiInputIsValid(uiLevl);
        if (a) {
            return new Level(uiLevl.getName().getText());
        } else {
            return null;
        }
    }

    public static Module getObjectModuleFromUiModule(UiModule uiModule) {
        boolean a = UiModule.UiInputIsValid(uiModule);
        if (a) {
            return new Module(uiModule.getName().getText());
        } else {
            return null;
        }
    }

    public static Type getObjectTypeFromUiType(UiType uiType) {
        boolean a = UiType.UiInputIsValid(uiType);
        if (a) {
            return new Type(uiType.getName().getText());
        } else {
            return null;
        }
    }

    public static Section getObjectSectionFromUiSection(UiSection uiSection) {
        boolean a = UiSection.UiInputIsValid(uiSection);
        if (a) {
            return new Section(uiSection.getName().getText());
        } else {
            return null;
        }
    }

    public static Room getObjectRoomFromUiRoom(UiRoom uiRoom) {
        boolean a = UiRoom.UiRoomInputIsValid(uiRoom);
        if (a) {
            int nbr = Integer.parseInt(uiRoom.getNbrChair().getText());
            return new Room(uiRoom.getName().getText(), nbr);
        } else {
            return null;
        }
    }

    public static Offer getOffreObjectFromOffreUi(UiOffre uiOffre) {
        boolean a = UiOffre.UiOffreInputIsValid(uiOffre);
        if (a) {
            return new Offer(uiOffre.getName().getText(),
                    uiOffre.getTypeCmb().getSelectionModel().getSelectedItem().toString(),
                    uiOffre.getModuleCmb().getSelectionModel().getSelectedItem().toString(),
                    uiOffre.getLevelCmb().getSelectionModel().getSelectedItem().toString(),
                    Integer.parseInt(uiOffre.getPrice().getText()));
        } else {
            return null;
        }
    }

    public static Group getObjectGroupeFromUiGroupe(UiGroupe uigrp) {
        boolean a = UiGroupe.UiGroupeInputIsValid(uigrp);
        if (a) {
            return new Group(uigrp.getOfferCmb().getSelectionModel().getSelectedItem().toString(),
                    uigrp.getName().getText(), Integer.parseInt(uigrp.getNbrPlace().getText()),
                    uigrp.getTeacherCmb().getSelectionModel().getSelectedItem());
        } else {
            return null;
        }
    }

    public static Paiement getObjectAccountFromUiStudentPaiementHistory(UiStudentPaiement uistd) {
        boolean a = UiStudentPaiement.UiStudentInputIsValid(uistd);
        if (a) {
            Group grp =  uistd.getGroupCB().getSelectionModel().getSelectedItem();
          return new Paiement(grp, Float.parseFloat(uistd.getAmount().getText())
          ,Float.parseFloat(uistd.getAmountP().getText()),uistd.getAroundCB().getSelectionModel().getSelectedItem());
        } else {
            return null;
        }
    }
}
