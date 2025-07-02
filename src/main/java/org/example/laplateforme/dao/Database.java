package org.example.laplateforme.dao;

import java.sql.*;

public class Database {
//    private static final String DB_NAME = "plateformetracker";
//    private static final String HOST = "dpg-d1ierler433s73agd6tg-a.frankfurt-postgres.render.com";
//    private static final String PORT = "5432";
    private static final String USER = "plateformetracker_user";
    private static final String PASSWORD = "5zhAoSpXbD9mTQT3MrggX3pd4YCGiT7Z";

    public static void main(String[] args) {
        String dbUrl = "jdbc:postgresql://dpg-d1ierler433s73agd6tg-a.frankfurt-postgres.render.com:5432/plateformetracker";

        // Connection to postgres
        try (Connection conn = DriverManager.getConnection(dbUrl, USER, PASSWORD);
             Statement createTableStmt = conn.createStatement()) {

            String createTableSql = """
                CREATE TABLE IF NOT EXISTS Student (
                    id SERIAL PRIMARY KEY,
                    firstName VARCHAR(100),
                    lastName VARCHAR(100),
                    age INTEGER,
                    grade INTEGER
                );
                """;

            createTableStmt.executeUpdate(createTableSql);
            System.out.println("✅ Student table ready.");

        } catch (SQLException e) {
              System.err.println("❌ Database error: " + e.getMessage());
              e.printStackTrace();
            }
        }

}
