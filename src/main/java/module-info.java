module org.example.laplateforme {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;
    requires jBCrypt;


    opens org.example.laplateforme to javafx.fxml;
    exports org.example.laplateforme;
    exports org.example.laplateforme.dao;
    opens org.example.laplateforme.dao to javafx.fxml;
}