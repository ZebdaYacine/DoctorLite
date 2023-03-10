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
import doctorlite.BackEnd.Model.Seance;
import doctorlite.BackEnd.Model.Section;
import doctorlite.BackEnd.Model.Student;
import doctorlite.BackEnd.Results;

/**
 * @author Zed Yacine
 */
public class StudentService {

    private static final Section section = new Section();

    public static Results.Rstls addStudent(Student student) {
        if (student == null) {
            return Results.Rstls.OBJECT_NOT_INSERTED;
        }
        try {
            PreparedStatement stm = con.prepareStatement(""
                    + "insert into student (firstName,lastName,phone1,phone2,idSection)"
                    + " values (?,?,?,?,?)");
            stm.setString(1, student.getFirstName());
            stm.setString(2, student.getLastName());
            stm.setString(3, student.getPhone1());
            stm.setString(4, student.getPhone2());
            section.setName(student.getSectionName());
            section.PresentTemplate();
            stm.setLong(5, ObjectService.getIdObject(section, "section"));
            stm.executeUpdate();
            stm.close();
            return Results.Rstls.OBJECT_INSERTED;
        } catch (Exception ex) {
            ex.printStackTrace();
            return Results.Rstls.OBJECT_NOT_INSERTED;
        }
    }

    public static long addStudentWithGetLastId(Student student) {
        if (student == null) {
            return 0;
        }
        long last_inserted_id = 0;
        try {
            PreparedStatement stm = con.prepareStatement(""
                    + "insert into student (firstName,lastName,phone1,phone2,idSection)"
                    + " values (?,?,?,?,?)", PreparedStatement.RETURN_GENERATED_KEYS);
            stm.setString(1, student.getFirstName());
            stm.setString(2, student.getLastName());
            stm.setString(3, student.getPhone1());
            stm.setString(4, student.getPhone2());
            section.setName(student.getSectionName());
            section.PresentTemplate();
            stm.setLong(5, ObjectService.getIdObject(section, "section"));
            stm.executeUpdate();
            ResultSet rs = stm.getGeneratedKeys();
            if (rs.next()) {
                last_inserted_id = rs.getLong(1);
            }
            stm.close();
            return last_inserted_id;
        } catch (Exception ex) {
            ex.printStackTrace();
            return 0;
        }
    }

    public static Results.Rstls updateStudent(Student student) {
        if (student == null) {
            return Results.Rstls.OBJECT_NOT_INSERTED;
        }
        try {
            PreparedStatement stm = con.prepareStatement("UPDATE "
                    + " student SET firstName = ?"
                    + ", lastName = ? ,phone1 = ? ,phone2 = ? , idSection=? "
                    + " WHERE id = ? ");
            stm.setString(1, student.getFirstName());
            stm.setString(2, student.getLastName());
            stm.setString(3, student.getPhone1());
            stm.setString(4, student.getPhone2());
            section.setName(student.getSectionName());
            stm.setLong(5, ObjectService.getIdObject(section, "section"));
            stm.setLong(6, student.getId());
            stm.executeUpdate();
            stm.close();
            return Results.Rstls.OBJECT_UPDATED;
        } catch (Exception ex) {
            ex.printStackTrace();
            return Results.Rstls.OBJECT_NOT_UPDATED;
        }
    }

    public static Results.Rstls deleteStudent(Student student) {
        if (student == null) {
            return Results.Rstls.OBJECT_NOT_INSERTED;
        }
        try {
            PreparedStatement stm = con.prepareStatement("DELETE FROM "
                    + " student WHERE id = ?");
            stm.setLong(1, student.getId());
            stm.executeUpdate();
            stm.close();
            return Results.Rstls.OBJECT_DELETED;
        } catch (Exception ex) {
            ex.printStackTrace();
            return Results.Rstls.OBJECT_NOT_DELETED;
        }
    }

    public static ObservableList<Student> getAllStudents(String type, Student std) {
        String query;
        System.out.println(std.getFirstName());
        if (type.equals("search")) {
            query = "SELECT * FROM student  where id = " + std.getId() + " order by id desc";
        } else {
            query = "SELECT * FROM student order by id desc ";
        }
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

    public static ObservableList<Student> getAllStudentsFollow(Seance s, String type) {
        String query = "";
        boolean st = false;
        switch (type) {
            case "apsent":
                st = true;
                query = "SELECT S.id,S.idSection,S.firstName,S.lastName,S.phone1,S.phone2,F.idSeance,F.status "
                        + "FROM follow F , student S , belongs B where S.id = F.idStudent "
                        + " and F.idStudent=B.idStudnet and F.presenceStudent=0  and B.idGroupe = " + s.getIdGroupe()
                        + " and F.idSeance=" + s.getId() + " group by S.id order by S.id desc";
                /*query="select * from student where id in (select idStudent from follow where  presenceStudent=0 and  idStudent \n" +
                "in   (select idStudnet from belongs where idGroupe= "+s.getIdGroupe()+" ))";*/
                //query = "SELECT * FROM student where id not in(select idStudent from follow where idSeance = " + s.getId() + ") order by id desc";
                break;
            case "apsentFirstName":
                st = true;
                query = "SELECT S.id,S.idSection,S.firstName,S.lastName,S.phone1,S.phone2,F.idSeance,F.status "
                        + "FROM follow F , student S , belongs B where S.id = F.idStudent "
                        + " and F.idStudent=B.idStudnet and F.presenceStudent=0  and B.idGroupe = " + s.getIdGroupe()
                        + " and F.idSeance=" + s.getId() + " and S.firstName like '" + s.getpStudent()+ "%' group by S.id order by S.id desc";
                System.err.println(query);
                break;
            case "present":
                st = true;
                query = "SELECT S.id,S.idSection,S.firstName,S.lastName,S.phone1,S.phone2,F.idSeance,F.status FROM follow F , student S  where S.id = F.idStudent "
                        + "and F.presenceStudent=1  and F.idSeance = " + s.getId() + " order by S.id desc";
                //query = "SELECT * FROM student where id in(select idStudent from follow where idSeance = " + s.getId() + ") order by id desc";
                break;
            case "presentFirstName":
                st = true;
                query = "SELECT S.id,S.idSection,S.firstName,S.lastName,S.phone1,S.phone2,F.idSeance,F.status "
                        + "FROM follow F , student S , belongs B where S.id = F.idStudent "
                        + " and F.idStudent=B.idStudnet and F.presenceStudent=1  and B.idGroupe = " + s.getIdGroupe()
                        + " and F.idSeance=" + s.getId() + " and S.firstName like '" + s.getpStudent() + "%' group by S.id order by S.id desc";
                break;
            case "empty":
                query = "SELECT * FROM student where id  in(select idStudnet from belongs where idGroupe = " + s.getIdGroupe() + ") order by id desc";
                break;
            default:
                break;
        }
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
                if (st) {
                    if (rs.getInt("status") == 1) {
                        student.setStatus("دفع");
                    } else {
                        student.setStatus("لم يدفع");
                    }
                }
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

    public static ObservableList<Student> searchStudentByName(Student student) {
        String query;
        query = "SELECT * FROM student where firstName LIKE'" + student.getFirstName() + "%'";
        System.out.println(query);
        ObservableList<Student> listStudents = FXCollections.observableArrayList(new Student());
        listStudents.remove(0);
        try {
            PreparedStatement ps = con.prepareStatement(query);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Student std = new Student();

                std.setId(rs.getLong("id"));
                std.setFirstName(rs.getString("firstName"));
                std.setLastName(rs.getString("lastName"));
                std.setPhone1(rs.getString("phone1"));
                std.setPhone2(rs.getString("phone2"));
                std.setSectionName(
                        ObjectService.getNameFromIdObject(new Section(rs.getLong("idSection")), "section"));
                listStudents.add(std);
            }
            rs.close();
            ps.close();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return listStudents;
    }

    public static Results.Rstls updateAmountOfStudent(Student student) {
        if (student == null) {
            return Results.Rstls.OBJECT_NOT_INSERTED;
        }
        try {
            PreparedStatement stm = con.prepareStatement("UPDATE "
                    + " student SET currentAmount = ?"
                    + " WHERE id = ? ");
            stm.setFloat(1, student.getCurrentAmount());
            stm.setLong(2, student.getId());
            stm.executeUpdate();
            stm.close();
            return Results.Rstls.OBJECT_UPDATED;
        } catch (Exception ex) {
            ex.printStackTrace();
            return Results.Rstls.OBJECT_NOT_UPDATED;
        }
    }

}
