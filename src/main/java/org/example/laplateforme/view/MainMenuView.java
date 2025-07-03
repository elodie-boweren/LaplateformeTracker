package org.example.laplateforme.view;

import javafx.application.Platform;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;

/**
 * Vue du menu principal de l'application La Plateforme Tracker
 * Version simplifiée sans base de données
 */
public class MainMenuView extends VBox {

    public MainMenuView() {
        initializeUI();
        setupEventHandlers();
    }

    /**
     * Initialise l'interface utilisateur du menu principal
     */
    private void initializeUI() {
        // Configuration du conteneur principal
        setPadding(new Insets(30));
        setSpacing(20);
        setAlignment(Pos.CENTER);
        setStyle("-fx-background-color: #f8f9fa;");

        // Titre principal
        Label titleLabel = new Label("La Plateforme_ Tracker");
        titleLabel.setFont(Font.font("Arial", FontWeight.BOLD, 28));
        titleLabel.setStyle("-fx-text-fill: #2c3e50;");

        Label subtitleLabel = new Label("Système de Gestion des Étudiants");
        subtitleLabel.setFont(Font.font("Arial", FontWeight.NORMAL, 16));
        subtitleLabel.setStyle("-fx-text-fill: #7f8c8d;");

        // Section des fonctionnalités principales
        Label mainFeaturesLabel = new Label("Fonctionnalités Principales");
        mainFeaturesLabel.setFont(Font.font("Arial", FontWeight.BOLD, 18));
        mainFeaturesLabel.setStyle("-fx-text-fill: #34495e;");

        // Boutons pour les fonctionnalités principales
        Button addBtn = createStyledButton("➕ Ajouter un étudiant", "#27ae60");
        Button editBtn = createStyledButton("✏️ Modifier un étudiant", "#f39c12");
        Button deleteBtn = createStyledButton("🗑️ Supprimer un étudiant", "#e74c3c");
        Button searchBtn = createStyledButton("🔍 Rechercher un étudiant", "#3498db");
        Button listBtn = createStyledButton("📋 Afficher tous les étudiants", "#9b59b6");

        // Organiser les boutons principaux en grille
        GridPane mainButtonsGrid = new GridPane();
        mainButtonsGrid.setHgap(15);
        mainButtonsGrid.setVgap(15);
        mainButtonsGrid.setAlignment(Pos.CENTER);

        mainButtonsGrid.add(addBtn, 0, 0);
        mainButtonsGrid.add(editBtn, 1, 0);
        mainButtonsGrid.add(deleteBtn, 0, 1);
        mainButtonsGrid.add(searchBtn, 1, 1);
        mainButtonsGrid.add(listBtn, 0, 2, 2, 1); // S'étend sur 2 colonnes

        // Section des fonctionnalités avancées
        Label advancedFeaturesLabel = new Label("Fonctionnalités Avancées");
        advancedFeaturesLabel.setFont(Font.font("Arial", FontWeight.BOLD, 18));
        advancedFeaturesLabel.setStyle("-fx-text-fill: #34495e;");

        // Boutons pour les fonctionnalités avancées
        Button sortBtn = createStyledButton("📊 Tri des étudiants", "#16a085");
        Button advancedSearchBtn = createStyledButton("🔎 Recherche avancée", "#2980b9");
        Button statsBtn = createStyledButton("📈 Statistiques", "#8e44ad");
        Button importExportBtn = createStyledButton("💾 Import/Export", "#d35400");

        // Organiser les boutons avancés en ligne
        HBox advancedButtonsBox = new HBox(15);
        advancedButtonsBox.setAlignment(Pos.CENTER);
        advancedButtonsBox.getChildren().addAll(sortBtn, advancedSearchBtn, statsBtn, importExportBtn);

        // Section utilitaires
        Label utilityFeaturesLabel = new Label("Utilitaires");
        utilityFeaturesLabel.setFont(Font.font("Arial", FontWeight.BOLD, 18));
        utilityFeaturesLabel.setStyle("-fx-text-fill: #34495e;");

        // Boutons utilitaires
        Button aboutBtn = createStyledButton("ℹ️ À propos", "#7f8c8d");
        Button helpBtn = createStyledButton("❓ Aide", "#95a5a6");
        Button exitBtn = createStyledButton("🚪 Quitter", "#c0392b");

        HBox utilityButtonsBox = new HBox(15);
        utilityButtonsBox.setAlignment(Pos.CENTER);
        utilityButtonsBox.getChildren().addAll(aboutBtn, helpBtn, exitBtn);

        // Ajouter tous les éléments au conteneur principal
        getChildren().addAll(
                titleLabel,
                subtitleLabel,
                new Label(), // Espacement
                mainFeaturesLabel,
                mainButtonsGrid,
                new Label(), // Espacement
                advancedFeaturesLabel,
                advancedButtonsBox,
                new Label(), // Espacement
                utilityFeaturesLabel,
                utilityButtonsBox
        );
    }

    /**
     * Crée un bouton avec un style personnalisé
     */
    private Button createStyledButton(String text, String color) {
        Button button = new Button(text);
        button.setPrefWidth(200);
        button.setPrefHeight(50);
        button.setFont(Font.font("Arial", FontWeight.NORMAL, 14));
        button.setStyle(
                "-fx-background-color: " + color + ";" +
                        "-fx-text-fill: white;" +
                        "-fx-background-radius: 8;" +
                        "-fx-border-radius: 8;" +
                        "-fx-effect: dropshadow(three-pass-box, rgba(0,0,0,0.2), 5, 0, 0, 2);"
        );

        // Effet hover
        button.setOnMouseEntered(e -> button.setStyle(
                "-fx-background-color: derive(" + color + ", -10%);" +
                        "-fx-text-fill: white;" +
                        "-fx-background-radius: 8;" +
                        "-fx-border-radius: 8;" +
                        "-fx-effect: dropshadow(three-pass-box, rgba(0,0,0,0.3), 8, 0, 0, 3);" +
                        "-fx-scale-x: 1.05;" +
                        "-fx-scale-y: 1.05;"
        ));

        button.setOnMouseExited(e -> button.setStyle(
                "-fx-background-color: " + color + ";" +
                        "-fx-text-fill: white;" +
                        "-fx-background-radius: 8;" +
                        "-fx-border-radius: 8;" +
                        "-fx-effect: dropshadow(three-pass-box, rgba(0,0,0,0.2), 5, 0, 0, 2);" +
                        "-fx-scale-x: 1.0;" +
                        "-fx-scale-y: 1.0;"
        ));

        return button;
    }

    /**
     * Configure les gestionnaires d'événements pour les boutons
     */
    private void setupEventHandlers() {
        // Parcourir tous les boutons et leur assigner des actions
        assignButtonActions();
    }

    private void assignButtonActions() {
        getChildren().forEach(node -> {
            if (node instanceof GridPane) {
                GridPane grid = (GridPane) node;
                grid.getChildren().forEach(child -> {
                    if (child instanceof Button) {
                        Button btn = (Button) child;
                        setButtonAction(btn);
                    }
                });
            } else if (node instanceof HBox) {
                HBox hbox = (HBox) node;
                hbox.getChildren().forEach(child -> {
                    if (child instanceof Button) {
                        Button btn = (Button) child;
                        setButtonAction(btn);
                    }
                });
            }
        });
    }

    private void setButtonAction(Button btn) {
        String buttonText = btn.getText();

        btn.setOnAction(e -> {
            if (buttonText.contains("Quitter")) {
                showExitConfirmation();
            } else if (buttonText.contains("À propos")) {
                showAboutDialog();
            } else if (buttonText.contains("Aide")) {
                showHelpDialog();
            } else {
                showFeatureDialog(buttonText);
            }
        });
    }

    /**
     * Affiche une boîte de dialogue pour les fonctionnalités
     */
    private void showFeatureDialog(String featureName) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Fonctionnalité");
        alert.setHeaderText(featureName);
        alert.setContentText("Cette fonctionnalité sera implémentée prochainement !\n\n" +
                "Fonctionnalités à développer :\n" +
                "• Connexion à la base de données PostgreSQL\n" +
                "• Interface de gestion des étudiants\n" +
                "• Système de recherche et de tri\n" +
                "• Import/Export de données\n" +
                "• Statistiques et rapports");
        alert.showAndWait();
    }

    /**
     * Affiche la boîte de dialogue "À propos"
     */
    private void showAboutDialog() {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("À propos");
        alert.setHeaderText("La Plateforme_ Tracker");
        alert.setContentText("Système de Gestion des Étudiants\n\n" +
                "Version : 1.0-SNAPSHOT\n" +
                "Développé avec JavaFX\n" +
                "Base de données : PostgreSQL\n\n" +
                "Fonctionnalités principales :\n" +
                "• Gestion complète des étudiants\n" +
                "• Recherche et tri avancés\n" +
                "• Import/Export de données\n" +
                "• Statistiques détaillées\n" +
                "• Interface utilisateur moderne");
        alert.showAndWait();
    }

    /**
     * Affiche la boîte de dialogue d'aide
     */
    private void showHelpDialog() {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Aide");
        alert.setHeaderText("Comment utiliser La Plateforme_ Tracker");
        alert.setContentText("Guide d'utilisation :\n\n" +
                "1. Fonctionnalités principales :\n" +
                "   • Ajouter : Créer un nouvel étudiant\n" +
                "   • Modifier : Éditer les informations\n" +
                "   • Supprimer : Retirer un étudiant\n" +
                "   • Rechercher : Trouver un étudiant\n" +
                "   • Afficher : Voir tous les étudiants\n\n" +
                "2. Fonctionnalités avancées :\n" +
                "   • Tri par nom, âge, notes\n" +
                "   • Recherche multicritères\n" +
                "   • Statistiques de classe\n" +
                "   • Import/Export CSV, JSON\n\n" +
                "Note : Interface en développement");
        alert.showAndWait();
    }

    /**
     * Affiche la confirmation de sortie
     */
    private void showExitConfirmation() {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Quitter l'application");
        alert.setHeaderText("Voulez-vous vraiment quitter ?");
        alert.setContentText("Toutes les données non sauvegardées seront perdues.");

        alert.showAndWait().ifPresent(response -> {
            if (response.getButtonData().isDefaultButton()) {
                System.out.println("Fermeture de l'application...");
                Platform.exit();
            }
        });
    }
}