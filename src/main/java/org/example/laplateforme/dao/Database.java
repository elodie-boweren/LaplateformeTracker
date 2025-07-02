package org.example.laplateforme.dao;

import java.sql.*;

public class Database {
//    private static final String DB_NAME = "plateformetracker";
//    private static final String HOST = "dpg-d1ierler433s73agd6tg-a.frankfurt-postgres.render.com";
//    private static final String PORT = "5432";
    private static final String USER = "plateformetracker_user";
    private static final String PASSWORD = "5zhAoSpXbD9mTQT3MrggX3pd4YCGiT7Z";
    private static final String DB_URL  = "jdbc:postgresql://dpg-d1ierler433s73agd6tg-a.frankfurt-postgres.render.com:5432/plateformetracker";

    private Connection connection;

    // Connect to postgres and database
    public boolean connectDb() {
        try {
            connection = DriverManager.getConnection(DB_URL, USER, PASSWORD);
            System.out.println("✅ Connected to the database plateformetracker.");
            return true;
        } catch (SQLException e) {
            System.err.println("❌ Error in connecting to plateformetracker: " + e.getMessage());
            e.printStackTrace();
            return false;
        }
    }

    // Close connection to the database
    public void closeConnection() {
        if (connection != null) {
            try {
                connection.close();
                System.out.println("✅ Connection to database closed.");
            } catch (SQLException e) {
                System.err.println("❌ Failure in disconnecting from the database: " + e.getMessage());
            }
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

        String createTableSql = """
            CREATE TABLE IF NOT EXISTS Users (
                id SERIAL PRIMARY KEY,
                username VARCHAR(50) UNIQUE NOT NULL,
                email VARCHAR(100) UNIQUE NOT NULL,
                password_hash VARCHAR(255) NOT NULL,
                role VARCHAR(20) DEFAULT 'USER',
                created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
                last_login TIMESTAMP,
                is_active BOOLEAN DEFAULT TRUE
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
