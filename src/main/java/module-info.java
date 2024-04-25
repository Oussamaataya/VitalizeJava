module com.example.vitalize {
    requires javafx.controls;
    requires javafx.fxml;
        requires javafx.web;
            
        requires org.controlsfx.controls;
    requires java.sql;
        requires org.json;
    requires jbcrypt;
    requires MaterialFX;


    opens com.example.vitalize to javafx.fxml;
    exports com.example.vitalize;
    exports com.example.vitalize.Controlleur;
    opens com.example.vitalize.Controlleur to javafx.fxml;
}