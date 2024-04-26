package controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import modals.Reclamation;
import services.ReclamationService;
import util.MaConnexion;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class AjoutReclamation {

    @FXML
    private TextArea descriptionTF;

    @FXML
    private TextField fileTF;

    @FXML
    private TextField sujetTF;

    @FXML
    private TextField typeTF;

    @FXML
    private ComboBox<String> medecinComboBox;

    private final ReclamationService reclamationService = new ReclamationService();
    private final Map<String, Integer> medecinComboBoxMap = new HashMap<>();

    @FXML
    public void initialize() {
        populateMedecinComboBox();
    }

    private void populateMedecinComboBox() {
        try (Connection connection = MaConnexion.getInstance().getCnx();
             PreparedStatement statement = connection.prepareStatement("SELECT id, nom FROM users WHERE prenom = ?");
        ) {
            statement.setString(1, "Chebaane");
            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {
                int id = resultSet.getInt("id");
                String nom = resultSet.getString("nom");
                System.out.println("Medecin Name: " + nom); // Debug print statement
                medecinComboBox.getItems().add(nom);
                medecinComboBoxMap.put(nom, id);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            showAlert("Error", "Failed to populate medecin list.");
        }
    }

    @FXML
    void AjouterReclamation(ActionEvent event) {
        if (isValidReclamation()) {
            String sujet = sujetTF.getText();
            String type = typeTF.getText();
            String description = descriptionTF.getText();
            String file = fileTF.getText();
            String medecinName = medecinComboBox.getSelectionModel().getSelectedItem();
            int medecinId = medecinComboBoxMap.getOrDefault(medecinName, -1);

            if (medecinId != -1) {
                Date currentDate = new Date();
                Reclamation reclamation = new Reclamation(sujet, type, description, file, currentDate, 1, medecinId);
                reclamationService.add(reclamation);
                showAlert("Success", "Reclamation added successfully.");
            } else {
                showAlert("Error", "Please select a medecin.");
            }
        } else {
            showAlert("Error", "Please fill in all fields.");
        }
    }

    private boolean isValidReclamation() {
        return !sujetTF.getText().isEmpty() &&
                !typeTF.getText().isEmpty() &&
                !descriptionTF.getText().isEmpty() &&
                !fileTF.getText().isEmpty() &&
                medecinComboBox.getSelectionModel().getSelectedItem() != null;
    }

    private void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    @FXML
    void AfficheReclamation(ActionEvent event) {
        try {
            sujetTF.getScene().setRoot(FXMLLoader.load(getClass().getResource("/AfficherReclamation.fxml")));
        } catch (IOException e) {
            showAlert("Error", "Failed to load reclamations.");
            e.printStackTrace();
        }
    }
}
