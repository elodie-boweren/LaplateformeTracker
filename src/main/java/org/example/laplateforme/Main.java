package org.example.laplateforme;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.example.laplateforme.dao.Database;
import org.example.laplateforme.service.BackupService;

import java.io.IOException;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) {
        try {
            // Charger le FXML
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/org/example/laplateforme/view/auth.fxml"));
            Parent root = loader.load();

            // Créer la scène
            Scene scene = new Scene(root, 500, 600);

            // Charger le CSS depuis le même package que le FXML
            String cssPath = getClass().getResource("/org/example/laplateforme/view/auth-styles.css").toExternalForm();

            if (cssPath == null) {
                System.err.println("❌ Fichier CSS introuvable à l'emplacement spécifié!");
                // Essayez avec un chemin relatif comme solution de secours
                cssPath = getClass().getResource("view/auth-styles.css").toExternalForm();
                if (cssPath != null) {
                    scene.getStylesheets().add(cssPath);
                    System.out.println("✅ CSS chargé avec chemin relatif");
                }
            } else {
                scene.getStylesheets().add(cssPath);
                System.out.println("✅ CSS chargé avec chemin absolu");
            }

            primaryStage.setTitle("Connexion - Gestion des étudiants");
            primaryStage.setScene(scene);
            primaryStage.show();
        } catch (Exception e) {
            e.printStackTrace();
            System.err.println("Erreur: " + e.getMessage());
        }
    }



    @Override
    public void stop() throws Exception {
        // Arrêter le service de sauvegarde et fermer la connexion à la base de données
        Database.getInstance().closeConnection();
        super.stop();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
