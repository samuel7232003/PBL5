package com.example.learnengapp.DAO;

import com.example.learnengapp.model.User;
import com.example.learnengapp.model.Vocab;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
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

    public static ArrayList<String> getFullIdVocabByIdUser(String idUser){
        ArrayList<String> listIdVocab = new ArrayList<String>();
        Connection conn = connectSQL();
        try {
            var sql = "select * FROM uservocabulary WHERE user_id = '" + idUser + "'";
            var statement = conn.prepareStatement(sql);
            var resultSet = statement.executeQuery();
            String idVocab;

            while(resultSet.next()){
                idVocab = resultSet.getString("vocabulary_id");
                listIdVocab.add(idVocab);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return listIdVocab;
    }

//    public static ArrayList<Vocab> getFullVocabByIdUser(String idUser){
//        ArrayList<Vocab> listVocab = new ArrayList<>();
//        Connection conn = connectSQL();
//        try{
//            var sql = "select * FROM uservocabulary WHERE user_id = '" + idUser + "'";
//            var statement = conn.prepareStatement(sql);
//            var resultSet = statement.executeQuery();
//
//            String id_vocab, id_user;
//
//            while(resultSet.next()){
//                id_vocab = resultSet.getString("vocabulary_id");
//                id_user = resultSet.getString("user_id");
//            }
//        } catch (SQLException e) {
//            throw new RuntimeException(e);
//        }
//    }

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

    public static void SignUp(String userId, String userName){
        try{
            Connection conn = connectSQL();
            String sql = "INSERT INTO `user`(`idUser`, `username`) VALUES ('" + userId + "','"+ userName +"')";
            Statement statement = conn.createStatement();
            statement.executeUpdate(sql);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
