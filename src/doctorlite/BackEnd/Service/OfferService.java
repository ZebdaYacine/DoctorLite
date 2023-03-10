/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package doctorlite.BackEnd.Service;

import doctorlite.BackEnd.Model.Level;
import doctorlite.BackEnd.Model.Template;
import doctorlite.BackEnd.Model.Module;
import doctorlite.BackEnd.Model.Offer;
import doctorlite.BackEnd.Model.Type;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import doctorlite.BackEnd.Results;

import java.sql.PreparedStatement;
import java.sql.ResultSet;

import static doctorlite.BackEnd.DataBaseConnection.con;

/**
 *
 * @author Zed Yacine
 */
public class OfferService {

    private static final Offer offer = new Offer();

    public static Results.Rstls addOffer(Offer offer) {
        if (offer == null) {
            return Results.Rstls.OBJECT_NOT_INSERTED;
        }
        try {
            PreparedStatement stm = con.prepareStatement(""
                    + "insert into offer (offerName,nameModule,nameType,nameLevel,price,idModule,idType,idLevel)"
                    + " values (?,?,?,?,?,?,?,?)");
            stm.setString(1, offer.getName());
            stm.setString(2, offer.getModule());
            stm.setString(3, offer.getType());
            stm.setString(4, offer.getLevel());
            stm.setInt(5, offer.getPrice());
            System.out.println(offer.getLevel() + " " + offer.getType() + " " + offer.getModule());
            Template template = offer;
            template.setName(offer.getModule());
            stm.setLong(6, ObjectService.getIdObject(template, "module"));
            template.setName(offer.getType());
            stm.setLong(7, ObjectService.getIdObject(template, "type"));
            template.setName(offer.getLevel());
            stm.setLong(8, ObjectService.getIdObject(template, "level"));
            stm.executeUpdate();
            stm.close();
            return Results.Rstls.OBJECT_INSERTED;
        } catch (Exception ex) {
            ex.printStackTrace();
            return Results.Rstls.OBJECT_NOT_INSERTED;
        }
    }

    public static Results.Rstls updateOffer(Offer offer) {
        if (offer == null) {
            return Results.Rstls.OBJECT_NOT_INSERTED;
        }
        try {
            PreparedStatement stm = con.prepareStatement("UPDATE "
                    + " offer SET offerName = ?"
                    + ", nameModule = ? , nameType = ? ,nameLevel=? , idModule = ? ,idType = ? , idLevel=? ,price = ? "
                    + " WHERE id = ? ");
            stm.setString(1, offer.getName());
            stm.setString(2, offer.getModule());
            stm.setString(3, offer.getType());
            stm.setString(4, offer.getLevel());
            stm.setLong(5, ModuleService.getIdObject(new Module(offer.getModule()), "module"));
            stm.setLong(6, TypeService.getIdObject(new Type(offer.getType()), "type"));
            stm.setLong(7, TypeService.getIdObject(new Level(offer.getLevel()), "level"));
            stm.setInt(8, offer.getPrice());
            stm.setLong(9, offer.getId());
            stm.executeUpdate();
            stm.close();
            return Results.Rstls.OBJECT_UPDATED;
        } catch (Exception ex) {
            ex.printStackTrace();
            return Results.Rstls.OBJECT_NOT_UPDATED;
        }
    }

    public static Results.Rstls deleteOffer(Offer offer) {
        if (offer == null) {
            return Results.Rstls.OBJECT_NOT_INSERTED;
        }
        try {
            PreparedStatement stm = con.prepareStatement("DELETE FROM "
                    + " offer WHERE id = ?");
            stm.setLong(1, offer.getId());
            stm.executeUpdate();
            stm.close();
            return Results.Rstls.OBJECT_DELETED;
        } catch (Exception ex) {
            ex.printStackTrace();
            return Results.Rstls.OBJECT_NOT_DELETED;
        }
    }

    public static ObservableList<Offer> getAllOffers() {
        String query;
        query = "SELECT * FROM Offer O , level L where O.idLevel=L.id order by L.id ASC ";
        ObservableList<Offer> listOffers = FXCollections.observableArrayList(new Offer());
        listOffers.remove(0);
        try {
            PreparedStatement ps = con.prepareStatement(query);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Offer offer = new Offer();
                offer.setId(rs.getLong("id"));
                offer.setName(rs.getString("OfferName"));
                offer.setModule(rs.getString("nameModule"));
                offer.setLevel(rs.getString("nameLevel"));
                offer.setType(rs.getString("nameType"));
                offer.setIdLevel(
                        ObjectService.getIdObject(new Offer(rs.getLong("idLevel")), "level"));
                offer.setIdModule(
                        ObjectService.getIdObject(new Offer(rs.getLong("idModule")), "module"));
                offer.setIdLevel(
                        ObjectService.getIdObject(new Offer(rs.getLong("idType")), "type"));
                offer.setPrice(rs.getInt("price"));
                listOffers.add(offer);
            }
            rs.close();
            ps.close();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return listOffers;
    }

    public static ObservableList<Offer> getOfferbyid(Offer o) {
        String query;
        query = "SELECT * FROM Offer O , level L where O.idLevel=L.id and O.id =" + o.getId() + " order by L.id ASC ";
        ObservableList<Offer> listOffers = FXCollections.observableArrayList(new Offer());
        listOffers.remove(0);
        try {
            PreparedStatement ps = con.prepareStatement(query);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Offer offer = new Offer();
                offer.setId(rs.getLong("id"));
                offer.setName(rs.getString("OfferName"));
                offer.setModule(rs.getString("nameModule"));
                offer.setLevel(rs.getString("nameLevel"));
                offer.setType(rs.getString("nameType"));
                offer.setIdLevel(
                        ObjectService.getIdObject(new Offer(rs.getLong("idLevel")), "level"));
                offer.setIdModule(
                        ObjectService.getIdObject(new Offer(rs.getLong("idModule")), "module"));
                offer.setIdLevel(
                        ObjectService.getIdObject(new Offer(rs.getLong("idType")), "type"));
                offer.setPrice(rs.getInt("price"));
                listOffers.add(offer);
            }
            rs.close();
            ps.close();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return listOffers;
    }

    public static ObservableList<Offer> searchStudentByName(Offer offer) {
        String query;
        query = "SELECT * FROM offer where name LIKE'" + offer.getName() + "%'";
        ObservableList<Offer> listOffers = FXCollections.observableArrayList(new Offer());
        listOffers.remove(0);
        try {
            PreparedStatement ps = con.prepareStatement(query);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Offer ofr = new Offer();
                ofr.setId(rs.getLong("id"));
                ofr.setName(rs.getString("name"));
                ofr.setModule(rs.getString("nameModule"));
                ofr.setLevel(rs.getString("nameLevel"));
                ofr.setType(rs.getString("nameType"));
                ofr.setIdLevel(
                        ObjectService.getIdObject(new Offer(rs.getLong("idLevel")), "level"));
                ofr.setIdModule(
                        ObjectService.getIdObject(new Offer(rs.getLong("idModule")), "module"));
                ofr.setIdLevel(
                        ObjectService.getIdObject(new Offer(rs.getLong("idType")), "type"));
                listOffers.add(ofr);
            }
            rs.close();
            ps.close();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return listOffers;
    }

    public static String getOfferAttFromIdOffer(Offer offer, String att) {
        String query;
        query = "SELECT "+att+" FROM Offer where id =" + offer.getId();
        String value = "";
        try {
            PreparedStatement ps = con.prepareStatement(query);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                if(att.equals("nameType")){
                    value = rs.getString("nameType");
                }else if(att.equals("offerName")){
                    value = rs.getString("offerName");
                }
            }
            rs.close();
            ps.close();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return value;
    }

}
