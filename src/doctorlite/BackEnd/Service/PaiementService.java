/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package doctorlite.BackEnd.Service;

import doctorlite.BackEnd.Model.Section;
import doctorlite.BackEnd.Model.Seance;
import doctorlite.BackEnd.Model.Offer;
import doctorlite.BackEnd.Model.Paiement;
import doctorlite.BackEnd.Model.Group;
import doctorlite.BackEnd.Model.Student;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import doctorlite.BackEnd.Controller.CommunController;
import doctorlite.BackEnd.Results;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;

import static doctorlite.BackEnd.DataBaseConnection.con;

/**
 * @author Zed Yacine
 */
public class PaiementService {

    private static final Section section = new Section();

    public static long addPaiement(Paiement paiement) {
        long id = 0;
        if (paiement == null) {
            return id;
        }
        try {
            PreparedStatement stm = con.prepareStatement(""
                    + "insert into paiement (idStudent,idGroupe,day,amount,amountC,type,nbrSeance,around)"
                    + " values (?,?,?,?,?,?,?,?)", Statement.RETURN_GENERATED_KEYS);
            stm.setLong(1, paiement.getStd().getId());
            stm.setLong(2, paiement.getGrp().getId());
            stm.setString(3, ObjectService.getCurrentDateTime());
            stm.setFloat(4, paiement.getAmount());
            stm.setFloat(5, paiement.getAmountC());
            stm.setString(6, paiement.getTypeOfOffer());
            stm.setInt(7, paiement.getNbrSeance());
            stm.setString(8, paiement.getAround());
            stm.executeUpdate();
            ResultSet rs = stm.getGeneratedKeys();
            if (rs.next()) {
                id = rs.getInt(1);
            }
            stm.close();
            return id;
        } catch (Exception ex) {
            ex.printStackTrace();
            return id;
        }
    }

    public static Results.Rstls updatePaiement(Paiement paiement) {
        if (paiement == null) {
            return Results.Rstls.OBJECT_NOT_UPDATED;
        }
        try {
            PreparedStatement stm = con.prepareStatement("UPDATE "
                    + " paiement SET idStudent = ? , idGroupe = ?"
                    + ", day = ? ,amount = ? , amountC=? , around = ? "
                    + " WHERE id = ? ");
            stm.setLong(1, paiement.getStd().getId());
            stm.setLong(2, paiement.getGrp().getId());
            stm.setString(3, ObjectService.getCurrentDateTime());
            stm.setFloat(4, paiement.getAmount());
            stm.setFloat(5, paiement.getAmountC());
            stm.setString(6, paiement.getAround());
            stm.setLong(7, paiement.getId());
            stm.executeUpdate();
            stm.close();
            return Results.Rstls.OBJECT_UPDATED;
        } catch (Exception ex) {
            ex.printStackTrace();
            return Results.Rstls.OBJECT_NOT_UPDATED;
        }
    }


    public static Results.Rstls deletePaiement(Paiement paiement) {
        if (paiement == null) {
            return Results.Rstls.OBJECT_NOT_INSERTED;
        }
        try {
            PreparedStatement stm = con.prepareStatement("DELETE FROM "
                    + " paiement WHERE id = ?");
            stm.setLong(1, paiement.getId());
            stm.executeUpdate();
            stm.close();
            return Results.Rstls.OBJECT_DELETED;
        } catch (Exception ex) {
            ex.printStackTrace();
            return Results.Rstls.OBJECT_NOT_DELETED;
        }
    }

    public static ObservableList<Paiement> getPaiementOfStudent(Paiement paiement) {
        String query;
        if (paiement.getStd().getGroup()==null) {
            query = " SELECT A.id,A.idStudent,A.day,A.amount,A.amountC,A.idGroupe ,  G.idOffer  , A.around" +
                    " FROM paiement A ,  Groupe G " +
                    " where A.idGroupe=G.id  and " +
                    " A.idStudent=" + paiement.getStd().getId() + " order by A.idStudent desc ";
        } else {
            query = " SELECT A.id,A.idStudent,A.day,A.amount,A.amountC,A.idGroupe ,  G.idOffer  , A.around" +
                    " FROM paiement A ,  Groupe G " +
                    " where A.idGroupe=G.id  and " +
                    " A.idStudent=" + paiement.getStd().getId() + " and G.id="+paiement.getStd().getGroup().getId()+" order by A.around asc ";
        }

        ObservableList<Paiement> listPaiementsOfStudents = FXCollections.observableArrayList(new Paiement());
        listPaiementsOfStudents.remove(0);
        try {
            PreparedStatement ps = con.prepareStatement(query);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Paiement paiementOfStd = new Paiement();
                paiementOfStd.setId(rs.getLong("id"));
                paiementOfStd.setStd(StudentService.getAllStudents("search",
                                new Student(rs.getLong("idStudent")))
                        .get(0));
                Group grp1 = new Group();
                grp1 = GroupService.getGroupbyId
                        (new Group(rs.getLong("idGroupe"))).get(0);
                paiementOfStd.setGrp(grp1);
                paiementOfStd.setGroupName(grp1.getNameGroup());
                paiementOfStd.setOfferName(grp1.getNameOffer());
                paiementOfStd.setTypeOfOffer(OfferService.getOfferbyid(
                        new Offer(rs.getLong("idOffer"))).get(0).getType());
                paiementOfStd.setAmount(rs.getFloat("amount"));
                paiementOfStd.setAmountC(rs.getFloat("amountC"));
                paiementOfStd.setDate(rs.getString("day"));
                paiementOfStd.setAround(rs.getString("around"));
                listPaiementsOfStudents.add(paiementOfStd);
            }
            rs.close();
            ps.close();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return listPaiementsOfStudents;
    }

    public static Paiement getPaiementForThisGroupIfExist(Paiement paiement) {
        String query = "SELECT * from paiement where idGroupe=" + paiement.getGrp().getId()
                + " and idStudent=" + paiement.getStd().getId();
        Paiement p = new Paiement();
        try {
            PreparedStatement ps = con.prepareStatement(query);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                p.setId(rs.getLong("id"));
                p.setAmountC(rs.getFloat("amountC"));
                p.setAmount(rs.getFloat("amount"));
                p.setNbrSeance(rs.getInt("nbrSeance"));
                p.setTypeOfOffer(rs.getString("type"));
                p.setAround(rs.getString("around"));
            }
            rs.close();
            ps.close();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return p;
    }

    public static Seance PaiementHasAseans(Paiement paiement) {
        String query = "SELECT S.id,S.idGroupe,S.idOffer,F.id from seance S , " +
                "follow F where S.id=F.idSeance and F.idStudent=" + paiement.getStd().getId() +
                " and F.idPaiement=" + paiement.getId();
        Seance p = new Seance();
        try {
            PreparedStatement ps = con.prepareStatement(query);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                p.setId(rs.getLong("id"));
                p.setIdGroupe(rs.getLong("idGroupe"));
                p.setIdOffer(rs.getLong("idOffer"));
            }
            rs.close();
            ps.close();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return p;
    }

}
