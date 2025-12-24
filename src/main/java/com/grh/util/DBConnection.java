package com.grh.util;
import java.sql.* ;

// Ouvrir une connexion avec la base , Envoyer des requetes , Recevoir des résultas
import java.sql.Connection ;

// Fabriquer l'objet de connexion à partir d'un URL, du nom de l'utilisateur et du mot de passe
import java.sql.DriverManager ;

// Gérer les erreurs : échec de connexion, erreurs SQL, mauvaise table, syntaxe incorrect...
import java.sql.SQLException  ;

public class DBConnection {

    private static final String URL =
            "jdbc:mysql://localhost:3306/grh_db?useSSL=false&serverTimezone=UTC";

    private static final String User = "root" ;

    private static final String PASSWORD = "root" ;

    private static Connection con = null ;

    public static Connection getConnection(){
        try{
            if(con == null || con.isClosed()) {
                con = DriverManager.getConnection(URL, User, PASSWORD);
                System.out.println("Connexion réussie ! ");
            }
        }catch(SQLException ex){
             System.out.println(ex.getMessage());
        }
        return con ;
    }

    public static void closeConnection(){
        if(con != null  ){
            try{
                con.close();
                System.out.println("Connexion fermée ! ");
            }catch(SQLException ex){
                System.out.println(ex.getMessage()) ;
            }
        }
    }
}
