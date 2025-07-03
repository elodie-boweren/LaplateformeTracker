import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.stage.Stage;

public class TestFX extends Application {
    public void start(Stage stage) {
        Label label = new Label("Bonjour, JavaFX !");
        Scene scene = new Scene(label, 300, 200);
        stage.setScene(scene);
        stage.setTitle("Test JavaFX");
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}
