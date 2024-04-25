package com.example.vitalize.Controlleur;

import io.github.palexdev.materialfx.controls.MFXButton;
import io.github.palexdev.materialfx.controls.MFXTextField;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.stage.Stage;
import com.example.vitalize.Entity.Commentaire;
import com.example.vitalize.Service.Servicecommentaire;

import java.net.URL;
import java.util.ResourceBundle;

public class EditCommentaire implements Initializable {
    private int ID;
    private Stage primaryStage;

    @FXML
    private MFXButton editButton;

    @FXML
    private MFXTextField idField;

    @FXML
    private MFXTextField idUserField;

    @FXML
    private MFXTextField idPubField;

    @FXML
    private MFXTextField contenuField;

    Servicecommentaire commentaireService = new Servicecommentaire();

    public void editCommentaire(ActionEvent event) {
        // Retrieve the user ID, publication ID, and content from the fields
        int idUser = Integer.parseInt(idUserField.getText());
        int idPub = Integer.parseInt(idPubField.getText());
        String contenu = contenuField.getText();

        // Create a new Commentaire object with the retrieved values
        Commentaire commentaire = new Commentaire(ID, idUser, idPub, contenu);

        // Update the Commentaire using the service
        commentaireService.edit(commentaire);

        // Close the stage
        Stage stage = (Stage) editButton.getScene().getWindow();
        stage.close();
    }


    public void setPassedId(int ID) {
        this.ID = ID;
    }

    public void setPrimaryStage(Stage primaryStage) {
        this.primaryStage = primaryStage;
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        // Populate the fields with the data of the selected Commentaire object
        Commentaire commentaire = commentaireService.getById(ID);
        idField.setText(String.valueOf(commentaire.getId()));
        idField.setDisable(true);
        idUserField.setText(String.valueOf(commentaire.getId_user()));
        idUserField.setDisable(true);
        idPubField.setText(String.valueOf(commentaire.getId_pub()));
        idPubField.setDisable(true);
        contenuField.setText(commentaire.getContenu());
    }
}
