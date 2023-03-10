/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package doctorlite.BackEnd.Service;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import doctorlite.BackEnd.Model.Follow;
import doctorlite.BackEnd.Model.Section;
import doctorlite.BackEnd.Model.Student;
import doctorlite.BackEnd.Results;

import java.sql.PreparedStatement;
import java.sql.ResultSet;

import static doctorlite.BackEnd.DataBaseConnection.con;

/**
 * @author Zed Yacine
 */
public class FollowService {

    public static Results.Rstls addFollow(Follow follow, String type) {
        if (follow == null) {
            return Results.Rstls.OBJECT_NOT_INSERTED;
        }
        try {
            PreparedStatement stm;
            if (type.equals("withP")) {
                stm = con.prepareStatement(""
                        + "insert into follow (idStudent,idSeance,presenceStudent,status,idPaiement)"
                        + " values (?,?,?,?,?)");
            } else {
                stm = con.prepareStatement(""
                        + "insert into follow (idStudent,idSeance,presenceStudent,status)"
                        + " values (?,?,?,?)");
            }
            stm.setLong(1, follow.getIdStudent());
            stm.setLong(2, follow.getIdSeance());
            stm.setLong(3, follow.getPresenceStudent());
            stm.setLong(4, follow.getStatus());
            if (type.equals("withP")) {
                stm.setLong(5, follow.getIdPaiement());
            }
            stm.executeUpdate();
            stm.close();
            return Results.Rstls.OBJECT_INSERTED;
        } catch (Exception ex) {
            ex.printStackTrace();
            return Results.Rstls.OBJECT_NOT_INSERTED;
        }
    }

    public static Results.Rstls updateFollow(Follow follow, String att) {
        if (follow == null) {
            return Results.Rstls.OBJECT_NOT_UPDATED;
        }
        try {
            String query = "";
            if (att.equals("presenceStudent")) {
                query = "update follow  set presenceStudent = " + follow.getPresenceStudent()
                        + " where idSeance =" + follow.getIdSeance() + " and idStudent = " + follow.getIdStudent();
            } else if (att.equals("status")) {
                query = "update follow  set status = " + follow.getStatus()
                        + " where idSeance =" + follow.getIdSeance() + " and idStudent = " + follow.getIdStudent();
            } else if (att.equals("statusWithP")) {
                query = "update follow  set status = " + follow.getStatus() + " , presenceStudent=" + follow.getPresenceStudent()
                        + " where idSeance =" + follow.getIdSeance() + " and idStudent = " + follow.getIdStudent();
            }
            else if (att.equals("paiement")) {
                query = "update follow set idPaiement = null where " +
                        " idStudent ="+follow.getIdStudent()+" and idSeance="+follow.getIdSeance();
            }
            else if (att.equals("deletepaiement")) {
                query = "update follow set idPaiement = null , status=0 where " +
                        " idPaiement ="+follow.getIdPaiement();
            }
            System.out.println(query);
            PreparedStatement stm = con.prepareStatement(query);
            stm.executeUpdate();
            stm.close();
            return Results.Rstls.OBJECT_UPDATED;
        } catch (Exception ex) {
            ex.printStackTrace();
            return Results.Rstls.OBJECT_NOT_UPDATED;
        }
    }


    public static Results.Rstls deleteFollow(Follow follow) {
        if (follow == null) {
            return Results.Rstls.OBJECT_NOT_DELETED;
        }
        try {
            String query = "delete from  follow  where idStudent=" + follow.getIdStudent()
                    + " and idSeance =" + follow.getIdSeance();
            PreparedStatement stm = con.prepareStatement(query);
            stm.executeUpdate();
            stm.close();
            return Results.Rstls.OBJECT_DELETED;
        } catch (Exception ex) {
            ex.printStackTrace();
            return Results.Rstls.OBJECT_NOT_DELETED;
        }
    }

    public static ObservableList<Student> getStudentsPresence(long id) {
        String query;
        query = "SELECT * FROM student S , follow F , seance SN "
                + " where S.id=F.idStudent and SN.id=F.idSeance and SN.id =" + id + " order by S.id desc ";
        ObservableList<Student> listStudents = FXCollections.observableArrayList(new Student());
        listStudents.remove(0);
        try {
            PreparedStatement ps = con.prepareStatement(query);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Student student = new Student();
                student.setId(rs.getLong("id"));
                student.setFirstName(rs.getString("firstName"));
                student.setLastName(rs.getString("lastName"));
                student.setPhone1(rs.getString("phone1"));
                student.setPhone2(rs.getString("phone2"));
                student.setSectionName(
                        ObjectService.getNameFromIdObject(new Section(rs.getLong("idSection")), "section"));
                listStudents.add(student);
            }
            rs.close();
            ps.close();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return listStudents;
    }

    public static int getCountSeancePaid(long idStudent, long idSeance) {
        String query = "select idStudent,count(idStudent) as 'nbrseance'  from " +
                "follow where idStudent=" + idStudent + " and idSeance=" + idSeance + " " +
                "and status=1 group by idStudent ";
        int nbr = 0;
        try {
            PreparedStatement ps = con.prepareStatement(query);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                nbr = rs.getInt("nbrseance");
            }
            rs.close();
            ps.close();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return nbr;
    }

}
