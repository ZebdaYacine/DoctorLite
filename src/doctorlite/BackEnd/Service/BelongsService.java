/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package doctorlite.BackEnd.Service;

import doctorlite.BackEnd.Model.Section;
import doctorlite.BackEnd.Model.Seance;
import doctorlite.BackEnd.Model.Belongs;
import doctorlite.BackEnd.Model.Paiement;
import doctorlite.BackEnd.Model.Group;
import doctorlite.BackEnd.Model.Student;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import doctorlite.BackEnd.Controller.CommunController;
import doctorlite.BackEnd.Results;

import java.sql.PreparedStatement;
import java.sql.ResultSet;

import static doctorlite.BackEnd.DataBaseConnection.con;

/**
 * @author Zed Yacine
 */
public class BelongsService {

    public static Results.Rstls addBelongs(Belongs blgns) {
        if (blgns == null) {
            return Results.Rstls.OBJECT_NOT_INSERTED;
        }
        try {
            PreparedStatement stm = con.prepareStatement(""
                    + "insert into belongs (idGroupe,idStudnet)"
                    + " values (?,?)");
            stm.setLong(1, blgns.getIdGroup());
            stm.setLong(2, blgns.getIdStudent());
            stm.executeUpdate();
            stm.close();
            return Results.Rstls.OBJECT_INSERTED;
        } catch (Exception ex) {
            ex.printStackTrace();
            return Results.Rstls.OBJECT_NOT_INSERTED;
        }
    }

    public static Results.Rstls deleteBelongs(Belongs blgns) {
        if (blgns == null) {
            return Results.Rstls.OBJECT_NOT_DELETED;
        }
        try {
            String query = "delete from  belongs  where idStudnet=" + blgns.getIdStudent()
                    + " and idGroupe =" + blgns.getIdGroup();
            PreparedStatement stm = con.prepareStatement(query);
            stm.executeUpdate();
            stm.close();
            return Results.Rstls.OBJECT_DELETED;
        } catch (Exception ex) {
            ex.printStackTrace();
            return Results.Rstls.OBJECT_NOT_DELETED;
        }
    }

    public static ObservableList<Student> getStudentsOfGroup(Group grp) {
        String query;
        query = "SELECT S.id,S.firstName,S.lastName,S.phone1,S.phone2,S.idSection" +
                " FROM   student S , belongs B" +
                " where  B.idStudnet=S.id and B.idGroupe=" + grp.getId();
        ObservableList<Student> listStudents = FXCollections.observableArrayList(new Student());
        listStudents.remove(0);
        ObservableList<Seance> SeancesOfGroup = SeanceService.getSeancesOfGroup(grp.getId());
        String type = ObjectService.getTypeFromOffer(grp.getIdOffer());
        int nbrSeanceInOffer = CommunController.getnbrSeanceInOffer(type);
        try {
            PreparedStatement ps = con.prepareStatement(query);
            ResultSet rs = ps.executeQuery();
            Paiement pamnt= new Paiement();
            while (rs.next()) {
                Student student = new Student();
                student.setId(rs.getLong("id"));
                student.setFirstName(rs.getString("firstName"));
                student.setLastName(rs.getString("lastName"));
                student.setPhone1(rs.getString("phone1"));
                student.setPhone2(rs.getString("phone2"));
                student.setSectionName(
                        ObjectService.getNameFromIdObject(new Section(rs.getLong("idSection")), "section"));
               /* for (Seance snc : SeancesOfGroup) {
                    if (!SeanceService.isPaid(student.getId(), snc.getId())) {
                        student.setNbr(student.getNbr() + 1);
                    }
                }
                student.setPaid(student.getNbr() < 1);*/
                pamnt.setGrp(grp);
                pamnt.setStd(student);
                int size =SeanceService.getAllSeancesNoPaid(pamnt).size();
                student.setPaid(size<=0);
                //student.setHasSeanceNoPaid(student.getNbr()>0);
                listStudents.add(student);
                student.PresentObject();
            }
            rs.close();
            ps.close();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return listStudents;
    }

    public static ObservableList<Student> getStudentsNotInGroup(long id, Student std) {
        String query;
        query = "SELECT * FROM student "
                + " where firstName LIKE '" + std.getFirstName() + "%' "
                + " and ( phone1 LIKE '" + std.getPhone1() + "%' or "
                + "phone2 LIKE '" + std.getPhone2() + "%') "
                + " and id not in (select idStudnet from  belongs where idGroupe =" + id + " ) order by id desc ";
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

    public static ObservableList<Group> getGroupOfStudent(Student std) {
        String query = "SELECT G.id ,G.name,G.nbrPlace , O.offerName ,O.id as 'idO' ,O.nameModule,O.nameLevel"
                + " FROM  belongs B , Groupe G , Offer O where"
                + " G.id=B.idGroupe and O.id= G.idOffer"
                + " and B.idStudnet=" + std.getId() + " group by G.id  order by B.idStudnet";
        ObservableList<Group> listGroups = FXCollections.observableArrayList(new Group());
        listGroups.remove(0);
        try {
            PreparedStatement ps = con.prepareStatement(query);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Group grp = new Group();
                grp.setId(rs.getLong("id"));
                grp.setIdOffer(rs.getLong("idO"));
                grp.setNameGroup(rs.getString("name"));
                grp.setNbrPlace(rs.getInt("nbrPlace"));
                grp.setNameOffer(rs.getString("offerName"));
                grp.setModule(rs.getString("nameModule"));
                grp.setLevel(rs.getString("nameLevel"));
                listGroups.add(grp);
            }
            rs.close();
            ps.close();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return listGroups;
    }

    public static ObservableList<Student> searchStudentByName(Student student) {
        String query;
        query = "SELECT * FROM student where (firstName LIKE '" + student.getFirstName() + "%' " +
                "or lastName LIKE '" + student.getFirstName() + "%')"
                + " and ( phone1 LIKE'" + student.getPhone1() + "%' or "
                + "phone2 LIKE'" + student.getPhone2() + "%')";
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


}
