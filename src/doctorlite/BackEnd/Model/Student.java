/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package doctorlite.BackEnd.Model;

/**
 *
 * @author Zed Yacine
 */
public class Student extends Person {

    private String phone1;
    private String phone2;
    private String sectionName;
    private float currentAmount;
    private String status;
    private Group group;
    private int  nbr;
    private boolean  paid;
    private boolean hasSeanceNoPaid;

    public boolean isHasSeanceNoPaid() {
        return hasSeanceNoPaid;
    }

    public void setHasSeanceNoPaid(boolean hasSeanceNoPaid) {
        this.hasSeanceNoPaid = hasSeanceNoPaid;
    }

    public boolean isPaid() {
        return paid;
    }

    public void setPaid(boolean paid) {
        this.paid = paid;
    }

    public int getNbr() {
        return nbr;
    }

    public void setNbr(int nbr) {
        this.nbr = nbr;
    }

    public Group getGroup() {
        return group;
    }

    public void setGroup(Group group) {
        this.group = group;
    }

    public Student() {
    }

    public Student(long id) {
        super(id);
    }

    public Student(long id, float amount) {
        super(id);
        this.currentAmount = amount;
    }

    public Student(String firstName, String lastName) {
        super(firstName, lastName);
    }

    public Student(String phone1, String phone2, String sectionName, String firstName, String lastName) {
        super(firstName, lastName);
        this.phone1 = phone1;
        this.phone2 = phone2;
        this.sectionName = sectionName;
    }

    
    
    public Student(String phone1, String phone2, String sectionName, String status, String firstName, String lastName) {
        super(firstName, lastName);
        this.phone1 = phone1;
        this.phone2 = phone2;
        this.sectionName = sectionName;
        this.status = status;
    }

    public Student(String phone1, String phone2, String sectionName, String status, long id, String firstName, String lastName) {
        super(id, firstName, lastName);
        this.phone1 = phone1;
        this.phone2 = phone2;
        this.sectionName = sectionName;
        this.status = status;
    }
    
    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getPhone1() {
        return phone1;
    }

    public void setPhone1(String phone1) {
        this.phone1 = phone1;
    }

    public String getPhone2() {
        return phone2;
    }

    public void setPhone2(String phone2) {
        this.phone2 = phone2;
    }

    public String getSectionName() {
        return sectionName;
    }

    public void setSectionName(String sectionName) {
        this.sectionName = sectionName;
    }

    public float getCurrentAmount() {return currentAmount;}

    public void setCurrentAmount(float currentAmount) {this.currentAmount = currentAmount;}
}
