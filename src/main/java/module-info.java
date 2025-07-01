module org.example.laplateforme {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;


    opens org.example.laplateforme to javafx.fxml;
    exports org.example.laplateforme;
}