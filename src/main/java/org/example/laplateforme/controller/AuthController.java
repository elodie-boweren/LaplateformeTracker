package org.example.laplateforme.controller;

import org.example.laplateforme.dao.UserDAO;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.event.ActionEvent;
import org.example.laplateforme.dao.Database;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.control.PasswordField;
import javafx.scene.control.Label;
import javafx.stage.Stage;
import org.example.laplateforme.model.User;
import org.mindrot.jbcrypt.BCrypt;
import java.io.IOException;
import java.util.regex.Pattern;

public class AuthController {

    @FXML
    private TextField emailField;

    @FXML
    private PasswordField passwordField;

    @FXML
    private Label errorLabel;

    // Nouveau champ pour le message d'erreur de la page de login
    @FXML
    private Label errorMessage;

    @FXML
    private Button backButton;

    @FXML
    private Button loginButton;

    private final UserDAO userDAO = new UserDAO();

    @FXML
    public void initialize() {
        if (backButton != null) {
            backButton.setOnAction(e -> goBackToMainMenu());
        }
    }

    @FXML
    private void handleLogin(ActionEvent event) {
        String email = emailField.getText().trim();
        String password = passwordField.getText();

        // Vérifiez que les champs ne sont pas vides
        if (email.isEmpty() || password.isEmpty()) {
            showLoginError("Veuillez remplir tous les champs");
            return;
        }

        // Récupérer l'utilisateur depuis la base de données
        User user = userDAO.findByEmail(email);

        if (user != null && BCrypt.checkpw(password, user.getPassword())) {
            // Connexion réussie
            hideLoginError();
            System.out.println("Connexion réussie pour: " + email);

            // Rediriger vers la page principale
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/src/main/resources/org/example/laplateforme/view/MainMenuView.fxml"));
                Parent root = loader.load();

                Stage stage = (Stage) loginButton.getScene().getWindow();
                stage.setScene(new Scene(root));
                stage.setTitle("La Plateforme Tracker - Menu Principal");

            } catch (IOException e) {
                System.err.println("Erreur lors de la redirection: " + e.getMessage());
                showLoginError("Erreur lors de la connexion");
            }
        } else {
            showLoginError("Adresse email ou mot de passe incorrect");
        }
    }

    // NOUVELLES MÉTHODES UTILITAIRES POUR LA CONNEXION
    private void showLoginError(String message) {
        if (errorMessage != null) {
            errorMessage.setText(message);
            errorMessage.setVisible(true);
        } else if (errorLabel != null) {
            // Fallback si errorMessage n'existe pas
            errorLabel.setText(message);
            errorLabel.setStyle("-fx-text-fill: red;");
        }
    }

    private void hideLoginError() {
        if (errorMessage != null) {
            errorMessage.setVisible(false);
        }
    }

    @FXML
    private void handleRegister() {
        String email = emailField.getText().trim();
        String password = passwordField.getText();

        if (!validateInputs(email, password)) {
            return;
        }

        if (userDAO.existsByEmail(email)) {
            errorLabel.setText("Email already in use.");
            errorLabel.setStyle("-fx-text-fill: red;");
            return;
        }

        String hashedPassword = BCrypt.hashpw(password, BCrypt.gensalt());

        User user = new User(email, hashedPassword, "user");

        if (userDAO.save(user)) {
            errorLabel.setText("Inscription réussie !");
            errorLabel.setStyle("-fx-text-fill: green;");
            clearFields();
        } else {
            errorLabel.setText("Erreur lors de l'inscription.");
            errorLabel.setStyle("-fx-text-fill: red;");
        }
    }

    private boolean validateInputs(String email, String password) {
        if (email.isEmpty() || password.isEmpty()) {
            errorLabel.setText("Tous les champs sont obligatoires.");
            errorLabel.setStyle("-fx-text-fill: red;");
            return false;
        }

        if (!isValidEmail(email)) {
            errorLabel.setText("Email invalide.");
            errorLabel.setStyle("-fx-text-fill: red;");
            return false;
        }

        if (!isStrongPassword(password)) {
            errorLabel.setText("Mot de passe trop faible (min 10 caractères, majuscule, minuscule, chiffre, spécial).");
            errorLabel.setStyle("-fx-text-fill: red;");
            return false;
        }

        return true;
    }

    private boolean isValidEmail(String email) {
        return email.matches("^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$");
    }

    private boolean isStrongPassword(String password) {
        String pattern = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[\\W_]).{10,}$";
        return Pattern.matches(pattern, password);
    }

    private void clearFields() {
        emailField.clear();
        passwordField.clear();
    }

    private void goBackToMainMenu() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/src/main/resources/org/example/laplateforme/view/MainMenuView.fxml"));
            Parent root = loader.load();

            Stage stage = (Stage) backButton.getScene().getWindow();
            stage.setScene(new Scene(root));
            stage.setTitle("La Plateforme Tracker - Menu Principal");

        } catch (IOException e) {
            System.err.println("Erreur lors du retour au menu principal: " + e.getMessage());
            e.printStackTrace();
        }
    }
}