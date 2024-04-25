package com.example.vitalize.Controlleur;

import io.github.palexdev.materialfx.controls.MFXButton;
import io.github.palexdev.materialfx.controls.MFXComboBox;
import io.github.palexdev.materialfx.controls.MFXTextField;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import com.example.vitalize.Entity.Publication;
import com.example.vitalize.Service.Servicepublication;

import java.awt.event.ActionEvent;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;


public class AddPublication implements Initializable {
    @FXML
    private MFXButton AjoutPub;

    @FXML
    private MFXButton Browse;

    @FXML
    private MFXTextField Description;

    @FXML
    private MFXTextField Image;

    @FXML
    private MFXTextField Titre;

    @FXML
    private MFXComboBox<String> Type;

Servicepublication exp=new Servicepublication();

    public void AjoutEx(javafx.event.ActionEvent event) {
        String type = Type.getValue(); // Use getValue() instead of getText() for MFXComboBox
        String titre = Titre.getText();
        String description = Description.getText();
        String imageUrl = Image.getText();

        if (isValidInput(type, titre, description, imageUrl)) {
            // Perform addition if input is valid
            String t = imageUrl.replace("%20", " ");
            t = t.replace("/", "\\").replace("file:\\", "");
            // FONCTION L'AJOUT YA MEEEEEHDI
            exp.add(new Publication(75, type, titre, description, t));
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/vitalize/ShowPublication.fxml"));
                Parent root = loader.load();

                Scene currentScene = ((Node) event.getSource()).getScene();

                currentScene.setRoot(root);
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            showErrorAlert("Please enter valid data for all fields.");
        }
    }

    // Validation methods
    private boolean isValidInput(String type, String titre, String description, String imageUrl) {
        return isValidTextField(Titre, titre) &&
                isValidTextField(Description, description) &&
                isValidComboBox(Type, type) &&
                isValidImage(imageUrl);
        // Add more validations if needed
    }

    private boolean isValidTextField(MFXTextField textField, String value) {
        return value != null && !value.isEmpty();
    }

    private boolean isValidComboBox(MFXComboBox<String> comboBox, String value) {
        return value != null && !value.isEmpty();
    }

    private boolean isValidImage(String imageUrl) {
        return imageUrl != null && !imageUrl.isEmpty() && imageUrl.startsWith("file:");
    }

    private void showErrorAlert(String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Input Error");
        alert.setContentText(message);
        alert.showAndWait();
    }

    public void Browse(javafx.event.ActionEvent event) {
        Stage primaryStage = new Stage();
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Choose a File");

        File selectedFile = fileChooser.showOpenDialog(primaryStage);

        if (selectedFile != null) {
            String fileUrl = selectedFile.toURI().toString();
            Image.setText(fileUrl);
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        String[] t2 = {"Nutrition", "Progrés"};
        Type.getItems().addAll(t2);
    }
}