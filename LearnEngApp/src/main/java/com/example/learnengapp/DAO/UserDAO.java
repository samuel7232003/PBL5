package com.example.learnengapp.DAO;

import com.example.learnengapp.model.User;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;

public class UserDAO extends connectMySQL{
    public static ArrayList<User> getUser(){
        ArrayList<User> users = new ArrayList<User>();
        try{
            Connection conn = connectSQL();
            var sql = "select * FROM user";
            var statement = conn.prepareStatement(sql);
            var resultSet = statement.executeQuery();

            while (resultSet.next()){
                String idUser = resultSet.getString("idUser");
                String username = resultSet.getString("username");
                User user = new User(idUser, username);
                users.add(user);
            }
        }catch(SQLException e) {
            throw new RuntimeException(e);
        }
        return users;
    }

    public static User getUser(String idUser){
        User user;
        try{
            Connection conn = connectSQL();
            var sql = "select * FROM user WHERE idUser =  '" + idUser + "'";
            var statement = conn.prepareStatement(sql);
            var resultSet = statement.executeQuery();
            String id = "", username = "";

            while (resultSet.next()){
                id = resultSet.getString("idUser");
                username = resultSet.getString("username");
            }
            user = new User(id, username);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return user;
    }
    public static User getUserByName(String usernameCheck){
        User user;
        try{
            Connection conn = connectSQL();
            var sql = "select * FROM user WHERE username =  '" + usernameCheck + "'";
            var statement = conn.prepareStatement(sql);
            var resultSet = statement.executeQuery();
            String id = "", username = "";

            while (resultSet.next()){
                id = resultSet.getString("idUser");
                username = resultSet.getString("username");
            }
            user = new User(id, username);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return user;
    }
}
