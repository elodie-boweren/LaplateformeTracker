package org.example.laplateforme;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {
    @Override
    public void start(Stage stage) throws Exception {
        // Chargement du FXML
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/org/example/laplateforme/view/auth.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 500, 600);
        scene.getStylesheets().add(getClass().getResource("/org/example/laplateforme/view/auth-styles.css").toExternalForm());
        stage.setTitle("LaPlateforme - Login");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}