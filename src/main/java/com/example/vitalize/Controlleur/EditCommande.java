package tn.esprit.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import tn.esprit.entities.Commande;
import tn.esprit.services.CommandeService;

import java.io.IOException;
import java.time.LocalDateTime;

public class EditCommande {

    @FXML
    private TextField clientNameTF;

    @FXML
    private TextField clientFamilyNameTF;

    @FXML
    private TextField clientAdresseTF;

    @FXML
    private TextField clientPhoneTF;

    @FXML
    private TextField methodePaiementTF;

    @FXML
    private TextField etatCommandeTF;

    @FXML
    private TextField dateTF;

    @FXML
    private TextField instructionSpecialeTF;

    @FXML
    private TextField prixtotalTF;

    private CommandeService commandeService = new CommandeService();
    private Commande commandeToEdit;

    public void setCommandeToEdit(Commande commande) {
        this.commandeToEdit = commande;
        clientNameTF.setText(commande.getClient_name());
        clientFamilyNameTF.setText(commande.getClient_family_name());
        clientAdresseTF.setText(commande.getClient_adresse());
        clientPhoneTF.setText(commande.getClient_phone());
        methodePaiementTF.setText(commande.getMethodePaiement());
        etatCommandeTF.setText(commande.getEtatCommande());
        instructionSpecialeTF.setText(commande.getInstructionSpeciale());
        prixtotalTF.setText(String.valueOf(commande.getPrixtotal()));
        dateTF.setText(String.valueOf(commande.getDate()));
        // Set other fields accordingly
    }

    @FXML
    void editCommande(ActionEvent event) {
        if (isValidInput()) {
            try {
                commandeToEdit.setClient_name(clientNameTF.getText());
                commandeToEdit.setClient_family_name(clientFamilyNameTF.getText());
                commandeToEdit.setClient_adresse(clientAdresseTF.getText());
                commandeToEdit.setClient_phone(clientPhoneTF.getText());
                commandeToEdit.setMethodePaiement(methodePaiementTF.getText());
                commandeToEdit.setEtatCommande(etatCommandeTF.getText());
                commandeToEdit.setInstructionSpeciale(instructionSpecialeTF.getText());
                commandeToEdit.setPrixtotal(Float.parseFloat(prixtotalTF.getText()));
                LocalDateTime date = LocalDateTime.parse(dateTF.getText());
                commandeToEdit.setDate(date);
                // Set other fields accordingly
                commandeService.update(commandeToEdit);
                // Close window or take other actions
            } catch (NumberFormatException e) {
                showErrorAlert("Le prix doit être un nombre valide.");
            } catch (Exception e) {
                showErrorAlert("Format de date invalide.");
            }
        } else {
            showErrorAlert("Veuillez remplir tous les champs avec des données valides.");
        }
    }

    @FXML
    void navigateTOListCommand(ActionEvent event) {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("/ListCommande.fxml"));
            Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
            window.setScene(new Scene(root));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private boolean isValidInput() {
        return isValidTextField(clientNameTF) &&
                isValidTextField(clientFamilyNameTF) &&
                isValidTextField(clientAdresseTF) &&
                isValidTextField(clientPhoneTF) &&
                isValidTextField(methodePaiementTF) &&
                isValidTextField(etatCommandeTF) &&
                isValidTextField(dateTF) ;
               /* isValidFloatField(prixtotalTF);*/
        // Add more validation as needed
    }

   /* private boolean isValidStringField(TextField textField) {
        return textField.getText() != null && !textField.getText().isEmpty() && textField.getText().matches("[a-zA-Z]+");
    }*/
    private boolean isValidTextField(TextField textField) {
        return textField.getText() != null && !textField.getText().isEmpty();
    }

    private boolean isValidDate(TextField textField) {
        try {
            LocalDateTime.parse(textField.getText());
            return true;
        } catch (Exception e) {
            return false;
        }
    }

   /* private boolean isValidFloatField(TextField textField) {
        try {
            Float.parseFloat(textField.getText());
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }*/

    private void showErrorAlert(String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Erreur de saisie");
        alert.setContentText(message);
        alert.showAndWait();
    }
}
