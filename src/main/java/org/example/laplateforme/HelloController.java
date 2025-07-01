package org.example.laplateforme;

import javafx.fxml.FXML;
import javafx.scene.control.Label;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class HelloController {
    @FXML
    private Label welcomeText;

    @FXML
    protected void onHelloButtonClick() {
        welcomeText.setText("Welcome to JavaFX Application!");
    }

    public static class TestPostgres {
        public static void main(String[] args) {
            String url = "jdbc:postgresql://localhost:5432/postgres";
            String user = "postgres";
            String password = "motdepasse";

            try (Connection conn = DriverManager.getConnection(url, user, password)) {
                System.out.println("✅ Connexion réussie !");
            } catch (SQLException e) {
                System.out.println("❌ Échec de la connexion.");
                e.printStackTrace();
            }
        }
    }
}