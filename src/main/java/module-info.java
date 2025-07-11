module org.example.laplateforme {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;
    requires jBCrypt;
    requires org.apache.poi.poi;
    requires org.apache.poi.ooxml;

    opens org.example.laplateforme to javafx.fxml;
    opens org.example.laplateforme.controller to javafx.fxml;
    opens org.example.laplateforme.dao to javafx.fxml;
    opens org.example.laplateforme.model to javafx.base;

    exports org.example.laplateforme;
    exports org.example.laplateforme.controller;
    exports org.example.laplateforme.dao;
    exports org.example.laplateforme.model;
}
