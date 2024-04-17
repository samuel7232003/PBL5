package com.example.learnengapp.DAO;

import com.example.learnengapp.model.User;
import com.example.learnengapp.model.Vocab;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Objects;

public class VocabDAO extends connectMySQL{
    public static ArrayList<Vocab> getVocab(){
        ArrayList<Vocab> vocabs = new ArrayList<>();
        try {
            Connection conn = connectSQL();
            var sql = "select * FROM vocab";
            var statement = conn.prepareStatement(sql);
            var resultSet = statement.executeQuery();
            while (resultSet.next()){
                String idVocab = resultSet.getString("idVocab");
                String word = resultSet.getString("word");
                String mean = resultSet.getString("mean");
                String phonetic = resultSet.getString("phonetic");
                String example = resultSet.getString("example");
                String image = resultSet.getString("image");
                Vocab vocab = new Vocab(idVocab, word, mean, phonetic, example, image);
                vocabs.add(vocab);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return vocabs;
    }

    public static Vocab getVocabById(String idVocab){
        Vocab vocab;
        try{
            Connection conn = connectSQL();
            var sql = "select * FROM vocab WHERE idVocab =  '" + idVocab + "'";
            var statement = conn.prepareStatement(sql);
            var resulutSet = statement.executeQuery();
            String id = "", word = "", mean = "", phonetic = "", example = "", image = "";
            while (resulutSet.next()){
                id = resulutSet.getString("idVocab");
                word = resulutSet.getString("word");
                mean = resulutSet.getString("mean");
                phonetic = resulutSet.getString("phonetic");
                example = resulutSet.getString("example");
                image = resulutSet.getString("image");
            }
            vocab = new Vocab(id, word, mean, phonetic, example, image);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return vocab;
    }

    public static Vocab getVocabByWord(String word){
        Vocab vocab;
        try{
            Connection conn = connectSQL();
            var sql = "select * FROM vocab WHERE word =  '" + word + "'";
            var statement = conn.prepareStatement(sql);
            var resulutSet = statement.executeQuery();
            String id = "", wordd = "", mean = "", phonetic = "", example = "", image = "";
            while (resulutSet.next()){
                id = resulutSet.getString("idVocab");
                wordd = resulutSet.getString("word");
                mean = resulutSet.getString("mean");
                phonetic = resulutSet.getString("phonetic");
                example = resulutSet.getString("example");
                image = resulutSet.getString("image");
            }
            vocab = new Vocab(id, wordd, mean, phonetic, example, image);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return vocab;
    }

    public static void setVocabForUser(String idUser, String idVocab){
        try{
            Connection conn = connectSQL();
            var sql = "INSERT INTO `uservocabulary`(`user_id`, `vocabulary_id`) VALUES ('" + idUser + "','" + idVocab + "')";
            Statement statement = conn.createStatement();
            statement.executeUpdate(sql);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public static void deleteVocabForUser(String idUser, String idVocab){
        try{
            Connection conn = connectSQL();
            var sql = "DELETE FROM `uservocabulary` WHERE vocabulary_id = '" + idVocab + "' AND user_id = '" + idUser + "'";
            Statement statement = conn.createStatement();
            statement.executeUpdate(sql);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public static boolean checkIdVocabFromIdUser(String idUser, String idVocab){
        String result;
        try{
            Connection conn = connectSQL();
            var sql = "select vocabulary_id FROM uservocabulary WHERE user_id = '" + idUser + "'";
            var statement = conn.prepareStatement(sql);
            var resultSet = statement.executeQuery();
            while (resultSet.next()){
                result = resultSet.getString("vocabulary_id");
                if (Objects.equals(result, idVocab)) return true;
            }
            return false;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
