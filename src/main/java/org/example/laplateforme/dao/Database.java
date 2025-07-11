package org.example.laplateforme.dao;

import java.sql.*;

public class Database {
    private static final String USER = "plateformetracker_user";
    private static final String PASSWORD = "5zhAoSpXbD9mTQT3MrggX3pd4YCGiT7Z";
    private static final String DB_URL = "jdbc:postgresql://dpg-d1ierler433s73agd6tg-a.frankfurt-postgres.render.com:5432/plateformetracker";

    private static Database instance;
    private Connection connection;

    private Database() {}

    public static Database getInstance() {
        if (instance == null) {
            instance = new Database();
        }
        return instance;
    }

    public boolean connectDb() {
        try {
            if (connection != null && !connection.isClosed()) {
                connection.close();
            }
            connection = DriverManager.getConnection(DB_URL, USER, PASSWORD);
            System.out.println("✅ Connexion à la base de données établie");
            return true;
        } catch (SQLException e) {
            System.err.println("❌ Erreur de connexion à la base de données: " + e.getMessage());
            return false;
        }
    }

    public Connection getConnection() {
        try {
            if (connection == null || connection.isClosed()) {
                connectDb();
            }
        } catch (SQLException e) {
            System.err.println("❌ Erreur lors de la vérification de la connexion: " + e.getMessage());
        }
        return connection;
    }

    public boolean isConnected() {
        try {
            return connection != null && !connection.isClosed();
        } catch (SQLException e) {
            return false;
        }
    }

    public void closeConnection() {
        try {
            if (connection != null && !connection.isClosed()) {
                connection.close();
                System.out.println("✅ Connexion à la base de données fermée");
            }
        } catch (SQLException e) {
            System.err.println("❌ Erreur lors de la fermeture de la connexion: " + e.getMessage());
        }
    }

    public boolean createStudentTable() {
        if (connection == null) {
            System.err.println("NOT connected to the database.");
            return false;
        }

        String createTableSql = """
            CREATE TABLE IF NOT EXISTS Student (
                id SERIAL PRIMARY KEY,
                firstName VARCHAR(100),
                lastName VARCHAR(100),
                age INTEGER,
                grade INTEGER
            );
            """;

        try (Statement stmt = connection.createStatement()) {
            stmt.executeUpdate(createTableSql);
            System.out.println("✅ Student table created/checked.");
            return true;
        }
        catch (SQLException e) {
            System.err.println("❌ Error in creating the table Student: " + e.getMessage());
            e.printStackTrace();
            return false;
        }
    }

    public boolean createUserTable() {
        if (connection == null) {
            System.err.println("NOT connected to the database.");
            return false;
        }

        // CORRECTION: Suppression de la virgule après 'USER'
        String createTableSql = """
            CREATE TABLE IF NOT EXISTS Users (
                id SERIAL PRIMARY KEY,
                email VARCHAR(100) UNIQUE NOT NULL,
                password_hash TEXT NOT NULL,
                role VARCHAR(20) DEFAULT 'USER'
            );
            """;

        try (Statement stmt = connection.createStatement()) {
            stmt.executeUpdate(createTableSql);
            System.out.println("✅ User table created/checked.");
            return true;
        } catch (SQLException e) {
            System.err.println("❌ Error in creating the table User: " + e.getMessage());
            e.printStackTrace();
            return false;
        }
    }
}
