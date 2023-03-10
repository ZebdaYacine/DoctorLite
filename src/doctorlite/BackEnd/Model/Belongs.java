/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package doctorlite.BackEnd.Model;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DatabindException;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.logging.Logger;

/**
 *
 * @author RD°INFO
 */
public class Belongs extends Student {

    private long id;
    private long idStudent;
    private long idGroup;


    public Belongs() {
    }

    public Belongs(int id) {
        this.id = id;
    }

    public Belongs(long id, long idStudent, long idGroup) {
        this.id = id;
        this.idStudent = idStudent;
        this.idGroup = idGroup;
    }

    public Belongs(long idStudent, long idGroup) {
        this.idStudent = idStudent;
        this.idGroup = idGroup;
    }

    public Belongs(String firstName, String lastName, String phone1, String phone2, String sectionName, long id) {
        super(firstName, lastName, phone1, phone2, sectionName);
        this.id = id;
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

    public long getIdGroup() {
        return idGroup;
    }

    public void setIdGroup(long idGroup) {
        this.idGroup = idGroup;
    }

    public void PresentGroupe() {
        try {
            ObjectMapper mapper = new ObjectMapper();
            String jsonString = mapper.writeValueAsString(this);
            System.out.println(jsonString);
        } catch (JsonProcessingException ex) {
            Logger.getLogger(Person.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
    }
}
