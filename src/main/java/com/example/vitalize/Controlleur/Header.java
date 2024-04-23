package com.example.vitalize.Controlleur;

import com.example.vitalize.Entity.UserSession;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Button;

import java.io.IOException;

public class Header {
    @FXML
    private Button acceuilbtn;
    public void acceuil(ActionEvent actionEvent) throws IOException {
        FXMLLoader loader=new FXMLLoader(getClass().getResource("/com/example/vitalize/Acceuil.fxml"));
        Parent root=loader.load();
        acceuilbtn.getScene().setRoot(root);
    }

    public void profile(ActionEvent actionEvent) throws IOException {
        FXMLLoader loader=new FXMLLoader(getClass().getResource("/com/example/vitalize/Profile.fxml"));
        Parent root=loader.load();
        acceuilbtn.getScene().setRoot(root);
    }

    public void commentaire(ActionEvent actionEvent) {
    }

    public void logout(ActionEvent actionEvent) throws IOException {
        UserSession userSession =UserSession.getInstance();
        userSession.logout();
        FXMLLoader loader=new FXMLLoader(getClass().getResource("/com/example/vitalize/Login.fxml"));
        Parent root=loader.load();
        acceuilbtn.getScene().setRoot(root);
    }
}
