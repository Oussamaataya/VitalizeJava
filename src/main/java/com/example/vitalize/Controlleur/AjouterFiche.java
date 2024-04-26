package controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import models.FichePatient;
import services.FichePatientService;

import java.util.List;

public class AjouterFiche {
    @FXML
    private TextField allergiesTF;

    @FXML
    private TextField breakfastTF;

    @FXML
    private TextField caloriesTF;

    @FXML
    private TextField dinnerTF;

    @FXML
    private TextField heightTF;

    @FXML
    private TextField illnessesTF;

    @FXML
    private TextField middayTF;

    @FXML
    private TextField muscleTF;

    @FXML
    private TextArea otherTA;

    @FXML
    private TextField snacksTF;

    @FXML
    private TextField weightTF;

    private final FichePatientService fps = new FichePatientService();

    @FXML
    void AddFiche(ActionEvent event) {
        if (isValidInput()) {
            try {
                fps.add(new FichePatient(
                        Integer.parseInt(weightTF.getText()),
                        Integer.parseInt(muscleTF.getText()),
                        Integer.parseInt(heightTF.getText()),
                        allergiesTF.getText(),
                        illnessesTF.getText(),
                        breakfastTF.getText(),
                        middayTF.getText(),
                        dinnerTF.getText(),
                        snacksTF.getText(),
                        Integer.parseInt(caloriesTF.getText()),
                        otherTA.getText()
                ));
            } catch (NumberFormatException e) {
                showErrorAlert("Please enter valid numbers for weight, muscle, height, and calories.");
            }
        } else {
            showErrorAlert("Please enter valid data for all fields.");
        }
    }
    @FXML
    void naviguer(ActionEvent event) {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("/AfficherFiche.fxml"));
            Scene scene = new Scene(root);
            Stage window = (Stage) ((javafx.scene.Node) event.getSource()).getScene().getWindow();
            window.setScene(scene);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    private boolean isValidInput() {
        return isValidTextField(weightTF) &&
                isValidTextField(muscleTF) &&
                isValidTextField(heightTF) &&
                isValidTextField(caloriesTF) &&
                isValidStringField(allergiesTF) &&
                isValidStringField(illnessesTF) &&
                isValidStringField(breakfastTF) &&
                isValidStringField(middayTF) &&
                isValidStringField(dinnerTF) &&
                isValidCalories() &&
                isValidWeight() &&
                isValidHeight() &&
                isValidCalories()&&
                isValidMuscleMass()&&
                isValidStringField(snacksTF);
    }

    private boolean isValidTextField(TextField textField) {
        return textField.getText() != null && !textField.getText().isEmpty();
    }
    private boolean isValidCalories() {
        if (!isValidTextField(caloriesTF)) {
            return false;
        } else {
            String calories = caloriesTF.getText().trim();
            if (!calories.matches("\\d{1,4}")) {
                showErrorAlert("Calories should be a 4-digit number.");
                return false;
            }
        }
        return true;
    }
    private boolean isValidHeight() {
        if (!isValidTextField(heightTF)) {
            return false;
        } else {
            String height = heightTF.getText().trim();
            if (!height.matches("\\d{1,3}")) {
                showErrorAlert("Height should be a 3-digit number.");
                return false;
            }
        }
        return true;
    }
    private boolean isValidMuscleMass() {
        String muscleMassText = muscleTF.getText();
        if (!muscleMassText.matches("\\d{1,3}")) {
            showErrorAlert("Muscle mass should be a positive integer with maximum 3 digits.");
            return false;
        }
        return true;
    }
    private boolean isValidWeight() {
        if (!isValidTextField(weightTF)) {
            return false;
        } else {
            String weight = weightTF.getText().trim();
            if (!weight.matches("\\d{1,3}")) {
                showErrorAlert("Weight should be a 3-digit number.");
                return false;
            }
        }
        return true;
    }

    private boolean isValidStringField(TextField textField) {
        return isValidTextField(textField) && textField.getText().matches("[a-zA-Z\\s-]+");
    }

    private void showErrorAlert(String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Input Error");
        alert.setContentText(message);
        alert.showAndWait();

}
}
