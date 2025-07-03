package org.example.laplateforme.controller;

import org.example.laplateforme.dao.UserDAO;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.scene.control.PasswordField;
import javafx.scene.control.Label;
import org.example.laplateforme.model.User;
import org.mindrot.jbcrypt.BCrypt;

import java.util.regex.Pattern;

public class AuthController {

    @FXML
    private TextField usernameField;

    @FXML
    private TextField emailField;

    @FXML
    private PasswordField passwordField;

    @FXML
    private Label errorLabel;

    private final UserDAO userDAO = new UserDAO();

    @FXML
    private void handleRegister() {
        String username = usernameField.getText().trim();
        String email = emailField.getText().trim();
        String password = passwordField.getText();

        if (!validateInputs(username, email, password)) {
            return;
        }

        if (userDAO.existsByUsername(username)) {
            errorLabel.setText("Nom d'utilisateur déjà utilisé.");
            return;
        }

        if (userDAO.existsByEmail(email)) {
            errorLabel.setText("Email déjà utilisé.");
            return;
        }

        String hashedPassword = BCrypt.hashpw(password, BCrypt.gensalt());

        User user = new User(username, email, hashedPassword, "user");

        if (userDAO.save(user)) {
            errorLabel.setText("Inscription réussie !");
            // Rediriger vers la vue de connexion ou d'accueil
        } else {
            errorLabel.setText("Erreur lors de l'inscription.");
        }
    }

    private boolean validateInputs(String username, String email, String password) {
        if (username.isEmpty() || email.isEmpty() || password.isEmpty()) {
            errorLabel.setText("Tous les champs sont obligatoires.");
            return false;
        }

        if (!isValidEmail(email)) {
            errorLabel.setText("Email invalide.");
            return false;
        }

        if (!isStrongPassword(password)) {
            errorLabel.setText("Mot de passe trop faible (min 10 caractères, majuscule, minuscule, chiffre, spécial).");
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
}
