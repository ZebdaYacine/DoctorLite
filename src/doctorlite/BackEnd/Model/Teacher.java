/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package doctorlite.BackEnd.Model;

import com.fasterxml.jackson.databind.ObjectMapper;

/**
 *
 * @author Zed Yacine
 */
public class Teacher extends Person {

    private String phone;
    private String workePlace;
    private  String fullName;

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public Teacher() {
    }

    @Override
    public String toString() {
        return this.getFirstName() + " " + this.getLastName();
    }

    public Teacher(long id) {
        super(id);
    }

    public Teacher(String firstName, String lastName) {
        super(firstName, lastName);
    }


    public Teacher(String firstName, String lastName, String phoneTeacher, String workePlace) {
        super(firstName, lastName);
        this.phone = phoneTeacher;
        this.workePlace = workePlace;
    }

    public Teacher(long id, String firstName, String lastName, String phoneTeacher, String workePlace) {
        super(id, firstName, lastName);
        this.phone = phoneTeacher;
        this.workePlace = workePlace;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getWorkePlace() {
        return workePlace;
    }

    public void setWorkePlace(String workePlace) {
        this.workePlace = workePlace;
    }

}
