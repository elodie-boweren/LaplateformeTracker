module org.example.laplateforme {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;
    requires jBCrypt;

    opens org.example.laplateforme to javafx.fxml;
    opens org.example.laplateforme.controller to javafx.fxml;
    opens org.example.laplateforme.dao to javafx.fxml;
    opens org.example.laplateforme.model to javafx.base;

    exports org.example.laplateforme;
    exports org.example.laplateforme.controller;
    exports org.example.laplateforme.dao;
    exports org.example.laplateforme.model;
    exports org.example.laplateforme.service;
    opens org.example.laplateforme.service to javafx.base;
}
