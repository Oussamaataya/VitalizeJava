package tn.esprit.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import tn.esprit.entities.Commande;
import tn.esprit.services.CommandeService;

import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

public class AjouterCommande {
    @FXML
    private TextField adressetf;

    @FXML
    private TextField client_nametf;

    @FXML
    private TextField datetf;

    @FXML
    private TextField etat_commandetf;

    @FXML
    private TextField family_nametf;

    @FXML
    private TextField instructiontf;

    @FXML
    private TextField methode_paiementtf;

    @FXML
    private TextField phonetf;

    @FXML
    private TextField prixtf;

    private final CommandeService cs = new CommandeService();

    @FXML
    void AjouterCommande(ActionEvent event) {
        if (isValidInput()) {
            try {
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
                LocalDate date = LocalDate.parse(datetf.getText(), formatter); // Modifier le type de date en LocalDate
                Commande nouvelleCommande = new Commande(
                        client_nametf.getText(),
                        family_nametf.getText(),
                        adressetf.getText(),
                        phonetf.getText(),
                        methode_paiementtf.getText(),
                        etat_commandetf.getText(),
                        date.atStartOfDay(), // Convertir LocalDate en LocalDateTime
                        instructiontf.getText(),
                        Float.parseFloat(prixtf.getText())
                );
                cs.add(nouvelleCommande);
            } catch (DateTimeParseException e) {
                showErrorAlert("Format de date invalide. Utilisez le format dd/MM/yyyy.");
            } catch (NumberFormatException e) {
                showErrorAlert("Le prix doit être un nombre valide.");
            }
        } else {
            showErrorAlert("Veuillez remplir tous les champs avec des données valides.");
        }
    }

    @FXML
    void navigateTOListCommand(ActionEvent event) {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("/ListCommande.fxml"));
            Stage window = (Stage) ((javafx.scene.Node) event.getSource()).getScene().getWindow();
            window.setScene(new Scene(root));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private boolean isValidTextField(TextField textField) {
        return textField.getText() != null && !textField.getText().isEmpty();
    }

    private boolean isValidInput() {
        return isValidTextField(client_nametf) &&
                isValidTextField(family_nametf) &&
                isValidTextField(adressetf) &&
                isValidTextField(phonetf) &&
                isValidTextField(methode_paiementtf) &&
                isValidTextField(etat_commandetf) &&
                isValidTextField(datetf) &&
                isValidTextField(instructiontf) &&
                isValidTextField(prixtf) &&
                isValidDate(datetf);
    }

    private boolean isValidDate(TextField textField) {
        try {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
            LocalDate.parse(textField.getText(), formatter);
            return true;
        } catch (DateTimeParseException e) {
            return false;
        }
    }

    private void showErrorAlert(String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Erreur de saisie");
        alert.setContentText(message);
        alert.showAndWait();
    }
}
