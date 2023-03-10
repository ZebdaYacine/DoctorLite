/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package doctorlite.BackEnd.Service;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import doctorlite.BackEnd.Model.Room;
import doctorlite.BackEnd.Model.Student;
import doctorlite.BackEnd.Results;

import java.sql.PreparedStatement;
import java.sql.ResultSet;

import static doctorlite.BackEnd.DataBaseConnection.con;

/**
 *
 * @author Zed Yacine
 */
public final class RoomService {

    private static final Room room = new Room();

    public static  Results.Rstls addRoom(Room rm) {
        if (rm == null) {
            return Results.Rstls.OBJECT_NOT_INSERTED;
        }
        try {
            PreparedStatement stm = con.prepareStatement(""
                    + "insert into room (name,nbrchair)"
                    + " values (?,?)");
            stm.setString(1, rm.getName());
            stm.setInt(2, rm.getNbrchair());
            stm.executeUpdate();
            stm.close();
            return Results.Rstls.OBJECT_INSERTED;
        } catch (Exception ex) {
            ex.printStackTrace();
            return Results.Rstls.OBJECT_NOT_INSERTED;
        }
    }

    public static Results.Rstls updateRoom(Room rm) {
        if (rm == null) {
            return Results.Rstls.OBJECT_NOT_INSERTED;
        }
        try {
            PreparedStatement stm = con.prepareStatement("UPDATE "
                    + " room SET name = ?"
                    + ", nbrchair = ? WHERE id = ? ");
            stm.setString(1, rm.getName());
            stm.setInt(2, rm.getNbrchair());
            stm.setLong(3, rm.getId());
            stm.executeUpdate();
            stm.close();

            return Results.Rstls.OBJECT_UPDATED;
        } catch (Exception ex) {
            ex.printStackTrace();
            return Results.Rstls.OBJECT_NOT_UPDATED;
        }
    }

    public static Results.Rstls deleteRoom(Room rm) {
        if (rm == null) {
            return Results.Rstls.OBJECT_NOT_INSERTED;
        }
        try {
            PreparedStatement stm = con.prepareStatement("DELETE FROM "
                    + " room WHERE id = ?");
            stm.setLong(1, rm.getId());
            stm.executeUpdate();
            stm.close();
            return Results.Rstls.OBJECT_DELETED;
        } catch (Exception ex) {
            ex.printStackTrace();
            return Results.Rstls.OBJECT_NOT_DELETED;
        }
    }

    public static ObservableList<Room> getAllRoom() {
        String query;
        query = "SELECT * FROM room order by id desc ";
        ObservableList<Room> listRooms = FXCollections.observableArrayList(new Room());
        listRooms.remove(0);
        try {
            PreparedStatement ps = con.prepareStatement(query);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Room rm = new Room();
                rm.setId(rs.getLong("id"));
                rm.setName(rs.getString("name"));
                rm.setNbrchair(rs.getInt("nbrchair"));
                rm.setId(rs.getLong("id"));
                listRooms.add(rm);
            }
            rs.close();
            ps.close();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return listRooms;
    }

    public static ObservableList<Room> searchRoomByName(Room room) {
        String query;
        query = "SELECT * FROM room where name LIKE'" + room.getName() + "%'";
        ObservableList<Room> listRooms = FXCollections.observableArrayList(new Room());
        listRooms.remove(0);
        try {
            PreparedStatement ps = con.prepareStatement(query);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Room rm = new Room();
                rm.setName(rs.getString("name"));
                rm.setNbrchair(rs.getInt("nbrchair"));
                rm.setId(rs.getLong("id"));
                listRooms.add(rm);
            }
            rs.close();
            ps.close();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return listRooms;
    }
    
    public static ObservableList<Room> searchRoomById(Room room) {
        String query;
        query = "SELECT * FROM room where id = " + room.getId()+ "";
        ObservableList<Room> listRooms = FXCollections.observableArrayList(new Room());
        listRooms.remove(0);
        try {
            PreparedStatement ps = con.prepareStatement(query);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Room rm = new Room();
                rm.setName(rs.getString("name"));
                rm.setNbrchair(rs.getInt("nbrchair"));
                rm.setId(rs.getLong("id"));
                listRooms.add(rm);
            }
            rs.close();
            ps.close();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return listRooms;
    }

}
