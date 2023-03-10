/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package doctorlite.BackEnd.Model;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import javafx.scene.control.CheckBox;

import java.util.logging.Logger;

/**
 *
 * @author kadri
 */
public class Seance {
    private long id,idOffer,idTeacher,idStudent,idRoom,idGroupe,idPaiement;
    private String date,nameOffer,nameGroup,nameTeacher,nameRoom,pTeacher,pStudent, test,test1;
    private int presenceTeacher,presenceStudent,status;
    private CheckBox pstatus ;


    public Seance() {
    }

    public Seance(long id, long idPaiement) {
        this.id = id;
        this.idPaiement = idPaiement;
    }

    public Seance(long id, int pStudent, int pTeacher) {
        this.id = id;
        this.presenceStudent = pStudent;
        this.presenceStudent=pTeacher;
    }

    public Seance(long idTeacher, long idRoom, long idGroupe,String date) {
        this.idTeacher = idTeacher;
        this.idRoom = idRoom;
        this.idGroupe = idGroupe;
        this.date=date;
    }

    public Seance(long id) {
        this.id = id;
    }

    public Seance(long idTeacher, long idRoom, String nameTeacher, String nameRoom, String pTeacher,
                  int presenceTeacher, int presenceStudent, int status) {
        this.idTeacher = idTeacher;
        this.idRoom = idRoom;
        this.nameTeacher = nameTeacher;
        this.nameRoom = nameRoom;
        this.pTeacher = pTeacher;
        this.presenceTeacher = presenceTeacher;
        this.presenceStudent = presenceStudent;
        this.status = status;
/*
        this.pstatus=new CheckBox();
*/
    }

    public Seance(long idOffer, long idTeacher, long idRoom, long idGroupe, String date, int presenceTeacher) {
        this.idOffer = idOffer;
        this.idTeacher = idTeacher;
        this.idRoom = idRoom;
        this.idGroupe = idGroupe;
        this.date = date;
        this.presenceTeacher = presenceTeacher;
    }

    public Seance(long id, long idOffer, long idTeacher, long idRoom, long idGroupe, String date, String nameOffer, String nameGroup, String nameTeacher, String nameRoom, String pTeacher, int presenceTeacher) {
        this.id = id;
        this.idOffer = idOffer;
        this.idTeacher = idTeacher;
        this.idRoom = idRoom;
        this.idGroupe = idGroupe;
        this.date = date;
        this.nameOffer = nameOffer;
        this.nameGroup = nameGroup;
        this.nameTeacher = nameTeacher;
        this.nameRoom = nameRoom;
        this.pTeacher = pTeacher;
        this.presenceTeacher = presenceTeacher;
    }



    public String getpTeacher() {
        return pTeacher;
    }

    public void setpTeacher(String pTeacher) {
        this.pTeacher = pTeacher;
    }

    public String getNameRoom() {
        return nameRoom;
    }

    public void setNameRoom(String nameRoom) {
        this.nameRoom = nameRoom;
    }

    
    public String getNameOffer() {
        return nameOffer;
    }

    public void setNameOffer(String nameOffer) {
        this.nameOffer = nameOffer;
    }

    public String getNameGroup() {
        return nameGroup;
    }

    public void setNameGroup(String nameGroup) {
        this.nameGroup = nameGroup;
    }

    public String getNameTeacher() {
        return nameTeacher;
    }

    public void setNameTeacher(String nameTeacher) {
        this.nameTeacher = nameTeacher;
    }
    
    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getIdOffer() {
        return idOffer;
    }

    public void setIdOffer(long idOffer) {
        this.idOffer = idOffer;
    }

    public long getIdTeacher() {
        return idTeacher;
    }

    public void setIdTeacher(long idTeacher) {
        this.idTeacher = idTeacher;
    }

    public long getIdRoom() {
        return idRoom;
    }

    public void setIdRoom(long idRoom) {
        this.idRoom = idRoom;
    }

    public long getIdGroupe() {
        return idGroupe;
    }

    public void setIdGroupe(long idGroupe) {
        this.idGroupe = idGroupe;
    }

    public String getDate() {
        return date;
    }

    public String getTest1() {
        return test1;
    }

    public void setTest1(String test1) {
        this.test1 = test1;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public long getIdStudent() {
        return idStudent;
    }

    public void setIdStudent(long idStudent) {
        this.idStudent = idStudent;
    }

    public int getPresenceStudent() {
        return presenceStudent;
    }

    public void setPresenceStudent(int presenceStudent) {
        this.presenceStudent = presenceStudent;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public CheckBox getPstatus() {
        return pstatus;
    }

    public void setPstatus(CheckBox pstatus) {
        this.pstatus = pstatus;
    }

    public String getpStudent() {
        return pStudent;
    }

    public void setpStudent(String pStudent) {
        this.pStudent = pStudent;
    }

    public String getTest() {
        return test;
    }

    public void setTest(String test) {
        this.test = test;
    }

    public int getPresenceTeacher() {
        return presenceTeacher;
    }

    public void setPresenceTeacher(int presenceTeacher) {
        this.presenceTeacher = presenceTeacher;
    }

    public long getIdPaiement() {
        return idPaiement;
    }

    public void setIdPaiement(long idPaiement) {
        this.idPaiement = idPaiement;
    }

    public void PresentSeance() {
        try {
            ObjectMapper mapper = new ObjectMapper();
            String jsonString = mapper.writeValueAsString(this);
            System.out.println(jsonString);
        } catch (JsonProcessingException ex) {
            Logger.getLogger(Person.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
    }
}
