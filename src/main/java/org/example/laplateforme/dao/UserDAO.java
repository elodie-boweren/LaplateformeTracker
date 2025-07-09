package org.example.laplateforme.dao;
import org.example.laplateforme.model.User;

import java.sql.*;

public class UserDAO {
    private Database database;

    public UserDAO() {
        this.database = new Database();
        this.database.connectDb(); // Connexion à la base
    }

    // Create new user
    public boolean insertUser(String email, String passwordHash, String role) {
        Connection conn = database.getConnection();
        if (conn == null) {
            return false;
        }

        String insertSql = "INSERT INTO Users (email, password_hash, role) VALUES (?, ?, ?)";

        try (PreparedStatement pstmt = conn.prepareStatement(insertSql)) {
            pstmt.setString(1, email);
            pstmt.setString(2, passwordHash);
            pstmt.setString(3, role);

            return pstmt.executeUpdate() > 0;

        } catch (SQLException e) {
            System.err.println("❌ SQL error when inserting new user: " + e.getMessage());
            return false;
        }
    }

    public ResultSet getUserByEmail(String email) {
        Connection conn = database.getConnection();
        if (conn == null) {
            return null;
        }

        String selectSql = """
            SELECT id, email, password_hash, role
            FROM Users 
            WHERE email = ?
            """;

        try {
            PreparedStatement pstmt = conn.prepareStatement(selectSql);
            pstmt.setString(1, email);
            return pstmt.executeQuery();

        } catch (SQLException e) {
            System.err.println("❌ SQL error when searching user by email: " + e.getMessage());
            return null;
        }
    }

    public User findByEmail(String email) {
        ResultSet rs = getUserByEmail(email);

        if (rs == null) {
            return null;
        }

        try {
            if (rs.next()) {
                // Créer un objet User avec les propriétés de base
                String userEmail = rs.getString("email");
                String passwordHash = rs.getString("password_hash");
                String role = rs.getString("role");

                // Utiliser le constructeur de User (adaptez selon votre constructeur)
                User user = new User(userEmail, passwordHash, role);

                // Si votre User a un setId, décommentez cette ligne
                // user.setId(rs.getInt("id"));

                return user;
            }
        } catch (SQLException e) {
            System.err.println("❌ SQL error when converting ResultSet to User: " + e.getMessage());
        } finally {
            try {
                rs.close();
            } catch (SQLException e) {
                System.err.println("❌ Error closing ResultSet: " + e.getMessage());
            }
        }

        return null;
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

    public boolean save(User user) {
        Connection conn = database.getConnection();
        if (conn == null) {
            return false;
        }

        String insertSql = "INSERT INTO Users (email, password_hash, role) VALUES (?, ?, ?)";

        try (PreparedStatement pstmt = conn.prepareStatement(insertSql)) {
            pstmt.setString(1, user.getEmail());
            pstmt.setString(2, user.getPassword());
            pstmt.setString(3, user.getRole());
            pstmt.executeUpdate();
            return true;
        } catch (SQLException e) {
            System.err.println("Erreur lors de l'enregistrement de l'utilisateur : " + e.getMessage());
            return false;
        }
    }

    public boolean updatePassword(String email, String newPasswordHash) {
        Connection conn = database.getConnection();
        if (conn == null) {
            return false;
        }

        String updateSql = "UPDATE Users SET password_hash = ? WHERE email = ?";

        try (PreparedStatement pstmt = conn.prepareStatement(updateSql)) {
            pstmt.setString(1, newPasswordHash);
            pstmt.setString(2, email);
            return pstmt.executeUpdate() > 0;

        } catch (SQLException e) {
            System.err.println("❌ SQL error when updating password: " + e.getMessage());
            return false;
        }
    }

    public boolean deleteUser(String email) {
        Connection conn = database.getConnection();
        if (conn == null) {
            return false;
        }

        String deleteSql = "DELETE FROM Users WHERE email = ?";

        try (PreparedStatement pstmt = conn.prepareStatement(deleteSql)) {
            pstmt.setString(1, email);
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
            SELECT id, email, role
            FROM Users 
            ORDER BY email DESC
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