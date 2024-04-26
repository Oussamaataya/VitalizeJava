package controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TextArea;
import modals.Reponse;
import services.ReponseService;

import java.util.Date;

public class AjoutReponse {

    @FXML
    private TextArea messageId;

    private final ReponseService rps = new ReponseService();
    private int reclamationId;

    public void setReclamationId(int reclamationId) {
        this.reclamationId = reclamationId;
    }



    @FXML
    void AjouterReponse(ActionEvent event) {
        if (isValidReponse()) {
            try {
                String message = messageId.getText();

                Date currentDate = new Date();

                Reponse reponse = new Reponse();
                reponse.setMessage(message);
                reponse.setDate(currentDate);
                reponse.setReclamation_id(reclamationId);
                reponse.setPatient(1);
                // Set other attributes like reclamation_id, patient, date
                rps.add(reponse);
            } catch (Exception e) {
                showAlert("Error", "Failed to add response.");
            }
        } else {
            showAlert("Validation Error", "Please fill in all fields.");
        }
    }

    private boolean isValidReponse() {
        // Implement validation logic here
        return !messageId.getText().isEmpty();
    }

    private void showAlert(String title, String content) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(content);
        alert.showAndWait();
    }


}
