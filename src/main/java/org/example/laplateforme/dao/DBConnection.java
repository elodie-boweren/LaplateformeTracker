package org.example.laplateforme.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBConnection {

    private static final String URL = "jdbc:postgresql://dpg-d1ierler433s73agd6tg-a.frankfurt-postgres.render.com:5432/plateformetracker";
    private static final String USER = "plateformetracker_user";
    private static final String PASSWORD = "5zhAoSpXbD9mTQT3MrggX3pd4YCGiT7Z";

    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(URL, USER, PASSWORD);
    }
}
