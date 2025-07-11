package org.example.laplateforme.dao;

import org.example.laplateforme.model.User;
import org.mindrot.jbcrypt.BCrypt;
import java.sql.*;

public class UserDAO {
    private final Database database;

    public UserDAO() {
        this.database = Database.getInstance();
    }

    public boolean authenticate(String email, String password) {
        String sql = "SELECT password_hash FROM Users WHERE email = ?";

        try (Connection conn = database.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, email);

            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    String storedHash = rs.getString("password_hash");
                    // Utilisation de BCrypt pour vérifier le mot de passe
                    return BCrypt.checkpw(password, storedHash);
                }
                return false;
            }
        } catch (SQLException e) {
            System.err.println("❌ Erreur d'authentification: " + e.getMessage());
            return false;
        }
    }

    public boolean addUser(User user) {
        // Hashage du mot de passe avant stockage
        String hashedPassword = BCrypt.hashpw(user.getPassword(), BCrypt.gensalt());
        String sql = "INSERT INTO Users (email, password_hash) VALUES (?, ?)";

        try (Connection conn = database.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, user.getEmail());
            stmt.setString(2, hashedPassword);

            int rowsAffected = stmt.executeUpdate();
            return rowsAffected > 0;
        } catch (SQLException e) {
            System.err.println("❌ Erreur lors de l'ajout de l'utilisateur: " + e.getMessage());
            return false;
        }
    }

    public boolean userExists(String email) {
        String sql = "SELECT 1 FROM Users WHERE email = ?";

        try (Connection conn = database.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, email);

            try (ResultSet rs = stmt.executeQuery()) {
                return rs.next();
            }
        } catch (SQLException e) {
            System.err.println("❌ Erreur lors de la vérification de l'utilisateur: " + e.getMessage());
            return false;
        }
    }
}
