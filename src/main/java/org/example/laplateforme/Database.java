package org.example.laplateforme;

import java.sql.*;

public class Database {
    // Group database created for the project
    private static final String DB_NAME = "projetdb";
    private static final String HOST = "10.10.194.244";
    private static final String PORT = "5432";
    private static final String USER = "groupe";
    private static final String PASSWORD = "MotDePasseComplexe123!";

    public static void main(String[] args) {
        String adminUrl = "jdbc:postgresql://" + HOST + ":" + PORT + "/postgres";

        // Connection to postgres
        try (Connection adminConn = DriverManager.getConnection(adminUrl, USER, PASSWORD)) {
            // Create database if it doesn't exist
            if (!databaseExists(adminConn)) {
                try (Statement createDbStmt = adminConn.createStatement()) {
                    createDbStmt.executeUpdate("CREATE DATABASE \"" + DB_NAME + "\"");
                    System.out.println("✅ Database created.");
                }
            } else {
                System.out.println("ℹ️ The database already exists.");
            }

        } catch (SQLException e) {
            e.printStackTrace();
            return;
        }

        // Connection to the database to create the Student table
        String trackerDbUrl = "jdbc:postgresql://" + HOST + ":" + PORT + "/" + DB_NAME;

        try (Connection trackerConn = DriverManager.getConnection(trackerDbUrl, USER, PASSWORD);
             Statement createTableStmt = trackerConn.createStatement()) {

            String createTableSql = """
                CREATE TABLE IF NOT EXISTS Student (
                    id SERIAL PRIMARY KEY,
                    firstName VARCHAR(100),
                    lastName VARCHAR(100),
                    age INTEGER,
                    grade VARCHAR(10)
                );
                """;

            createTableStmt.executeUpdate(createTableSql);
            System.out.println("✅ Student table ready.");

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private static boolean databaseExists(Connection conn) throws SQLException {
        // Search through postgres databases
        String checkQuery = "SELECT 1 FROM pg_database WHERE datname = ?";
        // Prepared Statement to prevent SQL injections
        try (PreparedStatement ps = conn.prepareStatement(checkQuery)) {
            ps.setString(1, DB_NAME);
            try (ResultSet rs = ps.executeQuery()) {
                return rs.next();
            }
        }
    }
}
