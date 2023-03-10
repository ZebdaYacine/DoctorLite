package doctorlite.BackEnd.Model;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.logging.Level;
import java.util.logging.Logger;

public class Account extends  Student{
    private long  id,idStudent,idGroupe;
    private String day,groupName,offerName;
    private float amount,amountC;

    public Account() {
    }

    public Account(long id) {
        this.id = id;
    }

    public Account(long idStudent, long idGroupe, float amount, float amountC) {
        this.idStudent = idStudent;
        this.idGroupe = idGroupe;
        this.amount = amount;
        this.amountC = amountC;
    }

    public Account(long id,long idStudent, long idGroupe, float amount, float amountC) {
        this.id = id;
        this.idStudent = idStudent;
        this.idGroupe = idGroupe;
        this.amount = amount;
        this.amountC = amountC;
    }

    public Account(long id, long idStudent, long idGroupe, String day, String groupName, String offerName, float amount, float amountC) {
        this.id = id;
        this.idStudent = idStudent;
        this.idGroupe = idGroupe;
        this.day = day;
        this.groupName = groupName;
        this.offerName = offerName;
        this.amount = amount;
        this.amountC = amountC;
    }

    @Override
    public long getId() {
        return id;
    }

    @Override
    public void setId(long id) {
        this.id = id;
    }

    public long getIdStudent() {
        return idStudent;
    }

    public void setIdStudent(long idStudent) {
        this.idStudent = idStudent;
    }

    public String getDay() {
        return day;
    }

    public void setDay(String day) {
        this.day = day;
    }

    public float getAmount() {
        return amount;
    }

    public void setAmount(float amount) {
        this.amount = amount;
    }

    public long getIdGroupe() {
        return idGroupe;
    }

    public void setIdGroupe(long idGroupe) {
        this.idGroupe = idGroupe;
    }

    public String getGroupName() {
        return groupName;
    }

    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }

    public String getOfferName() {
        return offerName;
    }

    public void setOfferName(String offerName) {
        this.offerName = offerName;
    }

    public float getAmountC() {
        return amountC;
    }

    public void setAmountC(float amountC) {
        this.amountC = amountC;
    }

    public void PresentAccount() {
        try {
            ObjectMapper mapper = new ObjectMapper();
            String jsonString = mapper.writeValueAsString(this);
            System.out.println(jsonString);
        } catch (JsonProcessingException ex) {
            Logger.getLogger(Person.class.getName()).log(Level.SEVERE, null, ex);
        }
    }


}
