package controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import models.FichePatient;
import services.FichePatientService;

public class EditerFiche {

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
    @FXML
    void updatefiche(ActionEvent event) {
        try {
            if (selectedFichePatient != null) {
                int weight = Integer.parseInt(weightTF.getText());
                int muscleMass = Integer.parseInt(muscleTF.getText());
                int height = Integer.parseInt(heightTF.getText());
                String allergies = allergiesTF.getText();
                String illnesses = illnessesTF.getText();
                String breakfast = breakfastTF.getText();
                String midday = middayTF.getText();
                String dinner = dinnerTF.getText();
                String snacks = snacksTF.getText();
                int calories = Integer.parseInt(caloriesTF.getText());
                String other = otherTA.getText();

                selectedFichePatient.setWeight(weight);
                selectedFichePatient.setMuscleMass(muscleMass);
                selectedFichePatient.setHeight(height);
                selectedFichePatient.setAllergies(allergies);
                selectedFichePatient.setIllnesses(illnesses);
                selectedFichePatient.setBreakfast(breakfast);
                selectedFichePatient.setMidday(midday);
                selectedFichePatient.setDinner(dinner);
                selectedFichePatient.setSnacks(snacks);
                selectedFichePatient.setCalories(calories);
                selectedFichePatient.setOther(other);

                fps.update(selectedFichePatient);

                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Update Successful");
                alert.setContentText("FichePatient updated successfully.");
                alert.showAndWait();
            }
        } catch (NumberFormatException e) {
            showErrorAlert("Please enter valid numbers for weight, muscle, height, and calories.");
        }
    }
    @FXML
    void deletefiche(ActionEvent event) {
        try {
            if (selectedFichePatient != null) {
                Alert confirmationAlert = new Alert(Alert.AlertType.CONFIRMATION);
                confirmationAlert.setTitle("Confirm Deletion");
                confirmationAlert.setHeaderText("Are you sure you want to delete this fiche patient?");
                confirmationAlert.setContentText("This action cannot be undone.");

                confirmationAlert.showAndWait().ifPresent(response -> {
                    if (response == ButtonType.OK) {
                        try {
                            fps.delete(selectedFichePatient);
                            Alert successAlert = new Alert(Alert.AlertType.INFORMATION);
                            successAlert.setTitle("Delete Successful");
                            successAlert.setContentText("Fiche patient deleted successfully.");
                            successAlert.showAndWait();
                        } catch (Exception e) {
                            showErrorAlert("An error occurred while deleting the fiche patient.");
                        }
                    }
                });
            }
        } catch (Exception e) {
            showErrorAlert("An error occurred while deleting the fiche patient.");
        }
    }


    private final FichePatientService fps = new FichePatientService();

    private FichePatient selectedFichePatient;

    public void initData(FichePatient fichePatient) {
        this.selectedFichePatient = fichePatient;
        displayFichePatientData();
    }

    private void displayFichePatientData() {
        if (selectedFichePatient != null) {
            weightTF.setText(String.valueOf(selectedFichePatient.getWeight()));
            muscleTF.setText(String.valueOf(selectedFichePatient.getMuscleMass()));
            heightTF.setText(String.valueOf(selectedFichePatient.getHeight()));
            allergiesTF.setText(selectedFichePatient.getAllergies());
            illnessesTF.setText(selectedFichePatient.getIllnesses());
            breakfastTF.setText(selectedFichePatient.getBreakfast());
            middayTF.setText(selectedFichePatient.getMidday());
            dinnerTF.setText(selectedFichePatient.getDinner());
            snacksTF.setText(selectedFichePatient.getSnacks());
            caloriesTF.setText(String.valueOf(selectedFichePatient.getCalories()));
            otherTA.setText(selectedFichePatient.getOther());
        }
    }

    @FXML
    void updateFiche(ActionEvent event) {
        try {
            if (selectedFichePatient != null) {
                selectedFichePatient.setWeight(Integer.parseInt(weightTF.getText()));
                selectedFichePatient.setMuscleMass(Integer.parseInt(muscleTF.getText()));
                selectedFichePatient.setHeight(Integer.parseInt(heightTF.getText()));
                selectedFichePatient.setAllergies(allergiesTF.getText());
                selectedFichePatient.setIllnesses(illnessesTF.getText());
                selectedFichePatient.setBreakfast(breakfastTF.getText());
                selectedFichePatient.setMidday(middayTF.getText());
                selectedFichePatient.setDinner(dinnerTF.getText());
                selectedFichePatient.setSnacks(snacksTF.getText());
                selectedFichePatient.setCalories(Integer.parseInt(caloriesTF.getText()));
                selectedFichePatient.setOther(otherTA.getText());

                fps.update(selectedFichePatient);

                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Update Successful");
                alert.setContentText("FichePatient updated successfully.");
                alert.showAndWait();
            }
        } catch (NumberFormatException e) {
            showErrorAlert("Please enter valid numbers for weight, muscle, height, and calories.");
        }
    }

    @FXML
    void naviguerAdd(ActionEvent event) {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("/AjouterFiche.fxml"));
            Scene scene = new Scene(root);
            Stage window = (Stage) ((javafx.scene.Node) event.getSource()).getScene().getWindow();
            window.setScene(scene);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    void naviguerList(ActionEvent event7) {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("/AfficherFiche.fxml"));
            Scene scene = new Scene(root);
            Stage window = (Stage) ((javafx.scene.Node) event7.getSource()).getScene().getWindow();
            window.setScene(scene);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void showErrorAlert(String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Input Error");
        alert.setContentText(message);
        alert.showAndWait();
    }
}
