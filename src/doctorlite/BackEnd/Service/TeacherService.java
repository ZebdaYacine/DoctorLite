/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package doctorlite.BackEnd.Service;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import static doctorlite.BackEnd.DataBaseConnection.con;
import doctorlite.BackEnd.Model.Teacher;
import doctorlite.BackEnd.Results;

/**
 *
 * @author Zed Yacine
 */
public class TeacherService {

    public static Results.Rstls addTeacher(Teacher teacher) {
        if (teacher == null) {
            return Results.Rstls.OBJECT_NOT_INSERTED;
        }
        try {
            PreparedStatement stm = con.prepareStatement(""
                    + "insert into teacher (firstName,lastName,phone,workePlace)"
                    + " values (?,?,?,?)");
            stm.setString(1, teacher.getFirstName());
            stm.setString(2, teacher.getLastName());
            stm.setString(3, teacher.getPhone());
            stm.setString(4, teacher.getWorkePlace());
            stm.executeUpdate();
            stm.close();
            return Results.Rstls.OBJECT_INSERTED;
        } catch (Exception ex) {
            ex.printStackTrace();
            return Results.Rstls.OBJECT_NOT_INSERTED;
        }
    }

    public static Results.Rstls updateTeacher(Teacher teacher) {
        try {
            PreparedStatement stm = con.prepareStatement("UPDATE "
                    + " teacher SET firstName = ?"
                    + ", lastName = ? ,phone = ? ,workePlace = ? "
                    + " WHERE id = ? ");
            stm.setString(1, teacher.getFirstName());
            stm.setString(2, teacher.getLastName());
            stm.setString(3, teacher.getPhone());
            stm.setString(4, teacher.getWorkePlace());
            stm.setLong(5, teacher.getId());
            stm.executeUpdate();
            stm.close();
            return Results.Rstls.OBJECT_UPDATED;
        } catch (Exception ex) {
            ex.printStackTrace();
            return Results.Rstls.OBJECT_NOT_UPDATED;
        }
    }

    public static Results.Rstls deleteTeacher(Teacher teacher) {
        try {
            PreparedStatement stm = con.prepareStatement("DELETE FROM "
                    + " teacher WHERE id = ?");
            stm.setLong(1, teacher.getId());
            stm.executeUpdate();
            stm.close();
            return Results.Rstls.OBJECT_DELETED;
        } catch (Exception ex) {
            ex.printStackTrace();
            return Results.Rstls.OBJECT_NOT_DELETED;
        }
    }

    public static ObservableList<Teacher> getAllTeachers() {
        String query;
        query = "SELECT * FROM teacher order by id desc ";
        ObservableList<Teacher> listTeachers = FXCollections.observableArrayList(new Teacher());
        listTeachers.remove(0);
        try {
            PreparedStatement ps = con.prepareStatement(query);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Teacher student = new Teacher();
                student.setId(rs.getLong("id"));
                student.setFirstName(rs.getString("firstName"));
                student.setLastName(rs.getString("lastName"));
                student.setPhone(rs.getString("phone"));
                student.setWorkePlace(rs.getString("workePlace"));
                listTeachers.add(student);
            }
            rs.close();
            ps.close();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return listTeachers;
    }

    public static ObservableList<Teacher> searchTeacherByName(Teacher teacher) {
        String query;
        query = "SELECT * FROM teacher where firstName LIKE '" + teacher.getFirstName() + "%'";
        Teacher tchr = new Teacher();
        ObservableList<Teacher> listTeachers = FXCollections.observableArrayList(new Teacher());
        listTeachers.remove(0);
        try {
            PreparedStatement ps = con.prepareStatement(query);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                tchr.setId(rs.getLong("id"));
                tchr.setFirstName(rs.getString("firstName"));
                tchr.setLastName(rs.getString("lastName"));
                tchr.setPhone(rs.getString("phone"));
                tchr.setWorkePlace(rs.getString("workePlace"));
                listTeachers.add(tchr);
            }
            rs.close();
            ps.close();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return listTeachers;
    }
    
    public static ObservableList<Teacher> searchTeacherById(Teacher teacher) {
        String query;
        query = "SELECT * FROM teacher where id = " + teacher.getId()+ "";
        Teacher tchr = new Teacher();
        ObservableList<Teacher> listTeachers = FXCollections.observableArrayList(new Teacher());
        listTeachers.remove(0);
        try {
            PreparedStatement ps = con.prepareStatement(query);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                tchr.setId(rs.getLong("id"));
                tchr.setFirstName(rs.getString("firstName"));
                tchr.setLastName(rs.getString("lastName"));
                tchr.setPhone(rs.getString("phone"));
                tchr.setWorkePlace(rs.getString("workePlace"));
                listTeachers.add(tchr);
            }
            rs.close();
            ps.close();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return listTeachers;
    }

}
