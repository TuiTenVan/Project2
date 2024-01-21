package com.javaweb.Utils;

import java.sql.Connection;
import java.sql.DriverManager;

public class ConnectionJDBC {
    static String URL = "jdbc:mysql://localhost:3306/estatebasic";
    static String USER = "root";
    static String PASS = "Nvv@02022003";
    public static Connection getConnection(){
        Connection conn = null;
        try{
            conn = DriverManager.getConnection(URL, USER, PASS);
        }
        catch (Exception e){
            e.printStackTrace();
        }
        return conn;
    }
}
