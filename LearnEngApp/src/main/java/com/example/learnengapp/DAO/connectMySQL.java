package com.example.learnengapp.DAO;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class connectMySQL {
    public static Connection connectSQL(){
        String hostName = "localhost:3306";
        String dbName = "learnengdb";
        String username = "root";
        String password = "";
        String connectionURL = "jdbc:mysql://"+hostName+"/"+dbName;
        Connection conn = null;
        try{
            Class.forName("com.mysql.cj.jdbc.Driver");
            conn = DriverManager.getConnection(connectionURL, username, password);
            System.out.println("Ket noi thanh cong");
        } catch (SQLException e){
            e.printStackTrace();
            System.out.println("Ket noi that bai");
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
        return conn;
    }
}
