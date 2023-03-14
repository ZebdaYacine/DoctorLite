/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package doctorlite.BackEnd.Service;

//import static BackEnd.BackEndMilk.con;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import doctorlite.BackEnd.Controller.CommunController;
import static doctorlite.BackEnd.DataBaseConnection.con;
import doctorlite.BackEnd.Model.User;
import doctorlite.BackEnd.Results;

/**
 *
 * @author RD°INFO
 */
public class UserService {

    public static Results.Rstls addUser(User user) {
        try {
            PreparedStatement stm = con.prepareStatement("insert into "
                    + " user(userName,password,role) "
                    + "values (?,?,?)");
            stm.setString(1, user.getUserName());
            stm.setString(2, user.getPassword());
            stm.setString(3, user.getRole());
            stm.executeUpdate();
            stm.close();
            return Results.Rstls.OBJECT_INSERTED;
        } catch (SQLException ex) {
            return Results.Rstls.OBJECT_NOT_INSERTED;
        }
    }

    public static Results.Rstls deleteUser(User user) {
        try {
            PreparedStatement stm = con.prepareStatement("DELETE FROM "
                    + " user WHERE id = ?");
            stm.setInt(1, user.getId());
            stm.executeUpdate();
            stm.close();
            return Results.Rstls.OBJECT_DELETED;
        } catch (SQLException ex) {
            return Results.Rstls.OBJECT_NOT_DELETED;
        }
    }

    public static Results.Rstls updateUser(User user) {
        try {
            PreparedStatement stm = con.prepareStatement("update  user set userName=?,password=?"
                    + " ,role=? where id =?");
            stm.setString(1, user.getUserName());
            stm.setString(2, user.getPassword());
            stm.setString(3, user.getRole());
            stm.setInt(4, user.getId());
            stm.executeUpdate();
            stm.close();
            return Results.Rstls.OBJECT_UPDATED;
        } catch (SQLException ex) {
            return Results.Rstls.OBJECT_NOT_UPDATED;
        }
    }

    private static String selctQuery(User user, String ArgSearch) {
        String query = "";
        switch (ArgSearch) {
            case "id": {
                query = " SELECT * FROM user where id = " + user.getId();
                break;
            }
            case "userName": {
                query = " SELECT * FROM user where userName = '" + user.getUserName() + "'"
                        + " and password='"+user.getPassword()+"'";
                break;
            }
            default: {
                query = "SELECT * FROM user order by id desc";
            }
        }
        return query;
    }

    public static Object getUsers(User user, String ArgSearch) {
        String query = selctQuery(user, ArgSearch);
        ObservableList<User> ListUsers = FXCollections.observableArrayList(new User());
        ListUsers.remove(0);
        try {
            PreparedStatement ps = con.prepareStatement(query);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                User usr = new User();
                usr.setId(rs.getInt("id"));
                usr.setUserName(rs.getString("userName"));
                usr.setRole(rs.getString("role"));
                ListUsers.add(usr);
            }
            rs.close();
            ps.close();
        } catch (SQLException ex) {
            System.err.println(ex);
        }
        return ListUsers;
    }

    public static User isauthentificated(User user) {
        String query = selctQuery(user, "userName");
        System.err.println(query);
        User usr = new User();
        ObservableList<User> ListUsers = FXCollections.observableArrayList(new User());
        ListUsers.remove(0);
        try {
            PreparedStatement ps = con.prepareStatement(query);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                if (user.getUserName().equals(rs.getString("userName"))) {
                    if (user.getPassword().equals(rs.getString("password"))) {
                        usr.setId(rs.getInt("id"));
                        usr.setRole(rs.getString("role"));
                    }else {
                        usr.setId(0);
                    }
                }

                ListUsers.add(usr);
            }
            rs.close();
            ps.close();
        } catch (SQLException ex) {
             usr.setId(0);
            System.err.println(ex);
            CommunController.alert("خادم قواعد البيانات غير متصل");
        }
        return usr;
    }

}
