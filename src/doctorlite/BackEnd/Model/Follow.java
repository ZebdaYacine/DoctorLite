/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package doctorlite.BackEnd.Model;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.logging.Logger;

/**
 *
 * @author RD°INFO
 */
public class Follow  {

    private long id,idStudent,idSeance,idPaiement;
    private int presenceStudent,status;

    public Follow() {
    }

    public Follow(long id) {
        this.id = id;
    }

    public Follow(long idStudent, long idSeance) {
        this.idSeance = idSeance;
        this.idStudent=idStudent;
    }

    public Follow(long idStudent, long idSeance, int presenceStudent, int status) {
        this.idStudent = idStudent;
        this.idSeance = idSeance;
        this.presenceStudent = presenceStudent;
        this.status = status;
    }

    public Follow(long id, long idStudent, long idSeance, int presenceStudent, int status) {
        this.id = id;
        this.idStudent = idStudent;
        this.idSeance = idSeance;
        this.presenceStudent = presenceStudent;
        this.status = status;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getIdStudent() {
        return idStudent;
    }

    public void setIdStudent(long idStudent) {
        this.idStudent = idStudent;
    }

    public long getIdSeance() {
        return idSeance;
    }

    public void setIdSeance(long idSeance) {
        this.idSeance = idSeance;
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

    public long getIdPaiement() {return idPaiement;}

    public void setIdPaiement(long idPaiement) {this.idPaiement = idPaiement;}

    public void PresentFollow() {
        try {
            ObjectMapper mapper = new ObjectMapper();
            String jsonString = mapper.writeValueAsString(this);
            System.out.println(jsonString);
        } catch (JsonProcessingException ex) {
            Logger.getLogger(Person.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
    }
}
