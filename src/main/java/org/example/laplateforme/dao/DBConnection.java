package org.example.laplateforme.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBConnection {

    // Informations de connexion à la base de données
    private static final String DB_NAME = "projetdb";
    private static final String HOST = "10.10.194.244";
    private static final String PORT = "5432";
    private static final String USER = "groupe";
    private static final String PASSWORD = "MotDePasseComplexe123!";
    private static final String URL = "jdbc:postgresql://dpg-d1ierler433s73agd6tg-a.frankfurt-postgres.render.com:5432/plateformetracker" + HOST + ":" + PORT + "/" + DB_NAME;

    // Méthode pour établir une connexion
    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(URL, USER, PASSWORD);
    }
}
