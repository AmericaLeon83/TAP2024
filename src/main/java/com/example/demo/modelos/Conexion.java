
package com.example.demo.modelos;

import java.sql.Connection;
import java.sql.DriverManager;

public class Conexion {
    static private String DB = "taqueria2";
    static private String USER = "adminTacos2";
    static private String PWD = "123";
    static public Connection connection;
   public static void crearConexion(){
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            connection = DriverManager.
                    getConnection("jdbc:mysql://127.0.0.1:3306/"+
                    DB+"?allowPublicKeyRetrieval=true&useSSL=false",
                            USER , PWD);
            System.out.println("Conexion establecida con exito!! ;");
        } catch (Exception e){
         e.printStackTrace();
        }
    }
}
