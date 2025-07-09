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
import javafx.scene.control.Hyperlink;
import javafx.stage.Stage;
import org.example.laplateforme.model.User;
import org.mindrot.jbcrypt.BCrypt;
import java.io.IOException;
import java.util.regex.Pattern;

public class AuthController {

    @FXML private TextField emailField;

    @FXML private PasswordField passwordField;

    @FXML private Label errorLabel;

    @FXML private Label errorMessage;

    @FXML private Button backButton;

    @FXML private Button loginButton;

    @FXML private Hyperlink registerLink;

    private final UserDAO userDAO = new UserDAO();
    private final Database database = new Database();

    // Variable pour gérer le mode (login ou register)
    private boolean isRegisterMode = false;

    @FXML
    public void initialize() {
        // Initialiser la base de données et créer les tables
        if (database.connectDb()) {
            database.createUserTable();
            database.createStudentTable();
        }

        if (backButton != null) {
            backButton.setOnAction(e -> goBackToMainMenu());
        }

        // Gérer le lien d'inscription
        if (registerLink != null) {
            registerLink.setOnAction(e -> toggleRegisterMode());
        }
    }

    @FXML
    private void handleLogin(ActionEvent event) {
        if (isRegisterMode) {
            handleRegister();
        } else {
            performLogin();
        }
    }

    private void performLogin() {
        String email = emailField.getText().trim();
        String password = passwordField.getText();

        if (email.isEmpty() || password.isEmpty()) {
            showLoginError("Veuillez remplir tous les champs");
            return;
        }

        User user = userDAO.findByEmail(email);

        if (user != null && BCrypt.checkpw(password, user.getPassword())) {
            hideLoginError();
            System.out.println("Connexion réussie pour: " + email);

            // Rediriger vers le dashboard
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/org/example/laplateforme/view/dashboard.fxml"));
                Parent root = loader.load();

                Stage stage = (Stage) loginButton.getScene().getWindow();
                stage.setScene(new Scene(root));
                stage.setTitle("La Plateforme Tracker - Dashboard");

            } catch (IOException e) {
                System.err.println("Erreur lors de la redirection: " + e.getMessage());
                showLoginError("Erreur lors de la connexion");
            }
        } else {
            showLoginError("Adresse email ou mot de passe incorrect");
        }
    }

    private void toggleRegisterMode() {
        isRegisterMode = !isRegisterMode;
        if (isRegisterMode) {
            loginButton.setText("S'inscrire");
            registerLink.setText("Se connecter");
            hideLoginError();
        } else {
            loginButton.setText("Se connecter");
            registerLink.setText("S'inscrire");
            hideLoginError();
        }
    }

    private void showLoginError(String message) {
        if (errorMessage != null) {
            errorMessage.setText(message);
            errorMessage.setVisible(true);
        } else if (errorLabel != null) {
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
            showLoginError("Email déjà utilisé.");
            return;
        }

        String hashedPassword = BCrypt.hashpw(password, BCrypt.gensalt());
        User user = new User(email, hashedPassword, "user");

        if (userDAO.save(user)) {
            showLoginError("Inscription réussie ! Vous pouvez maintenant vous connecter.");
            // Revenir au mode connexion
            toggleRegisterMode();
            clearFields();
        } else {
            showLoginError("Erreur lors de l'inscription.");
        }
    }

    private boolean validateInputs(String email, String password) {
        if (email.isEmpty() || password.isEmpty()) {
            showLoginError("Tous les champs sont obligatoires.");
            return false;
        }

        if (!isValidEmail(email)) {
            showLoginError("Email invalide.");
            return false;
        }

        if (!isStrongPassword(password)) {
            showLoginError("Mot de passe trop faible (min 10 caractères, majuscule, minuscule, chiffre, spécial).");
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
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/org/example/laplateforme/view/dashboard.fxml"));
            Parent root = loader.load();

            Stage stage = (Stage) backButton.getScene().getWindow();
            stage.setScene(new Scene(root));
            stage.setTitle("La Plateforme Tracker - Dashboard");

        } catch (IOException e) {
            System.err.println("Erreur lors du retour au menu principal: " + e.getMessage());
            e.printStackTrace();
        }
    }
}