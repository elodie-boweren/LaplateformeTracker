package org.example.laplateforme.dao;

import java.sql.*;

public class UserDAO {
    private Database database;

    public UserDAO(Database database) {
        this.database = database;
    }

    // Create new user
    public boolean insertUser(String username, String email, String passwordHash, String role) {
        Connection conn = database.getConnection();
        if (conn == null) {
            return false;
        }

        String insertSql = """
            
                INSERT INTO Users (username, email, password_hash, role) 
            VALUES (?, ?, ?, ?)
            """;

        try (PreparedStatement pstmt = conn.prepareStatement(insertSql)) {
            pstmt.setString(1, username);
            pstmt.setString(2, email);
            pstmt.setString(3, passwordHash);
            pstmt.setString(4, role);

            return pstmt.executeUpdate() > 0;

        } catch (SQLException e) {
            System.err.println("❌ SQL error when inserting new user: " + e.getMessage());
            return false;
        }
    }

    public ResultSet getUserByUsername(String username) {
        Connection conn = database.getConnection();
        if (conn == null) {
            return null;
        }

        String selectSql =
                """
            SELECT id, username, email, password_hash, role, created_at,
                last_login, is_active
            FROM 
                      WHERE username = ?
            """;

        try {
            PreparedStatement pstmt = conn.prepareStatement(selectSql);
            pstmt.setString(1, username);
            return pstmt.executeQuery();

        } catch (SQLException e) {
            System.err.println("❌ SQL error when getting user: " + e.getMessage());
            return null;
        }
    }

    public ResultSet getUserByEmail(String email) {
        Connection conn = database.getConnection();
        if (conn == null) {
            return null;
        }

        String selectSql = """
            SELECT id, username, email, password_hash, role,
                created_at,
                last_login, 
                         FROM Users 
            WHERE email = ?
            """;

        try {
            PreparedStatement pstmt = conn.prepareStatement(selectSql);
            pstmt.setString(1, email);
            return pstmt.executeQuery();

        } catch (SQLException e) {
            System.err.println("❌ SQL error when searching user by email: " + e.
                    getMessage());
            return null;
        }
    }

    public boolean existsByUsername(String username) {
        Connection conn = database.getConnection();
        if (conn == null) {
            return false;
        }

        String selectSql = "SELECT COUNT(*) FROM Users WHERE username = ?";

        try (PreparedStatement pstmt = conn.prepareStatement(selectSql)) {
            pstmt.setString(1, username);

            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt(1) > 0;
                }
            }
        } catch (SQLException e) {
            System.err.println("❌ SQL error when checking existence of username: " + e.getMessage());
        }

        return false;
    }

    public boolean existsByEmail(String email) {
        Connection conn = database.getConnection();
        if (conn == null) {
            return false;
        }

        String selectSql = "SELECT COUNT(*) FROM Users WHERE email = ?";

        try (PreparedStatement pstmt = conn.prepareStatement(selectSql)) {
            pstmt.setString(1, email);

            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt(1) > 0;
                }
            }
        } catch (SQLException e) {
            System.err.println("❌ SQL error when checking if email exists: " + e.getMessage());
        }

        return false;
    }

    public boolean updatePassword(String username, String newPasswordHash) {
        Connection conn = database.getConnection();
        if (conn == null) {
            return false;
        }

        String updateSql = "UPDATE Users SET password_hash = ? WHERE username = ?";

        try (PreparedStatement pstmt = conn.prepareStatement(updateSql)) {
            pstmt.setString(1, newPasswordHash);
            pstmt.setString(2, username);
            return pstmt.executeUpdate() > 0;

        } catch (SQLException e) {
            System.err.println("❌ SQL error when updating password: " + e.getMessage());
            return false;
        }
    }

    public boolean deleteUser(String username) {
        Connection conn = database.getConnection();
        if (conn == null) {
            return false;
        }

        String deleteSql = "DELETE FROM Users WHERE username = ?";

        try (PreparedStatement pstmt = conn.prepareStatement(deleteSql)) {
            pstmt.setString(1, username);
            return pstmt.executeUpdate() > 0;

        } catch (SQLException e) {
            System.err.println("❌ SQL error when deleting user: " + e.getMessage());
            return false;
        }
    }

    public ResultSet getAllUsers() {
        Connection conn = database.getConnection();
        if (conn == null) {
            return null;
        }

        String selectSql = """
            SELECT id, username, email, role, created_at, last_login, is_active
            FROM Users 
            ORDER BY created_at DESC
            """;

        try {
            PreparedStatement pstmt = conn.prepareStatement(selectSql);
            return pstmt.executeQuery();

        } catch (SQLException e) {
            System.err.println("❌ SQL error when getting all users: " + e.getMessage());
            return null;
        }
    }

}