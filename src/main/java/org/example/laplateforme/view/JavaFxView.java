package org.example.laplateforme.view;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

/**
 * Classe principale de l'application JavaFX pour la gestion des étudiants
 * La Plateforme Tracker - Version interface simple sans base de données
 */
public class JavaFxView extends Application {

    @Override
    public void start(Stage primaryStage) {
        try {
            BorderPane root = new BorderPane();

            // Créer la vue du menu principal
            MainMenuView mainMenu = new MainMenuView();
            root.setCenter(mainMenu);

            // Configuration de la scène
            Scene scene = new Scene(root, 1000, 700);

            // Configuration de la fenêtre principale
            primaryStage.setTitle("La Plateforme_ Tracker - Gestion des Étudiants");
            primaryStage.setScene(scene);
            primaryStage.setResizable(true);
            primaryStage.setMinWidth(800);
            primaryStage.setMinHeight(600);

            // Gestionnaire de fermeture
            primaryStage.setOnCloseRequest(e -> {
                System.out.println("Fermeture de l'application...");
                System.exit(0);
            });

            // Afficher la fenêtre
            primaryStage.show();

        } catch (Exception e) {
            System.err.println("Erreur lors du démarrage de l'application : " + e.getMessage());
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        System.out.println("Démarrage de La Plateforme_ Tracker...");
        launch(args);
    }
}