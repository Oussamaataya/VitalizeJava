package com.example.vitalize.Controlleur;

import io.github.palexdev.materialfx.controls.MFXButton;
import io.github.palexdev.materialfx.controls.MFXTextField;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.fxml.FXMLLoader;
import javafx.stage.Stage;
import com.example.vitalize.Entity.Commentaire;
import com.example.vitalize.Service.Servicecommentaire;

import java.io.IOException;
import java.net.URL;
import java.util.Arrays;
import java.util.List;
import java.util.ResourceBundle;
import javafx.scene.control.Alert;

public class AddCommentaire implements Initializable {

    @FXML
    private MFXButton AjoutComment;

    @FXML
    private MFXTextField id_user;

    @FXML
    private MFXTextField id_pub;

    @FXML
    private MFXTextField Contenu;
    private static final List<String> badWords = Arrays.asList("badword1", "badword2", "badword3");
    private final Servicecommentaire serviceCommentaire = new Servicecommentaire();
    private int publicationId;

    public AddCommentaire() {
        // Default constructor
    }
    private String filterBadWords(String content) {
        for (String badWord : badWords) {
            content = content.replaceAll("(?i)" + badWord, "*".repeat(badWord.length())); // Replace bad words with asterisks
        }
        return content;
    }

    public AddCommentaire(int publicationId) {
        this.publicationId = publicationId;
    }

    public void populateFields(int publicationId) {
        id_pub.setText(String.valueOf(publicationId));
        id_pub.setDisable(true);
    }

    @FXML
    public void AjoutComment(ActionEvent event) {
        try {
            String content = filterBadWords(Contenu.getText());
            String userIdText = id_user.getText();
            String pubIdText = id_pub.getText();

            if (isValidInput(content) && isValidUserId(userIdText) && isValidPubId(pubIdText)) {
                int userID = Integer.parseInt(userIdText);
                int pubId = Integer.parseInt(pubIdText);
                Commentaire commentaire = new Commentaire(1, userID, pubId, content);
                serviceCommentaire.add(commentaire);

                FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/vitalize/ShowPublication.fxml"));
                Parent root = loader.load();

                Scene currentScene = ((Node) event.getSource()).getScene();
                currentScene.setRoot(root);
            } else {
                showErrorAlert("Please enter valid content for the comment.");
            }
        } catch (IOException | NumberFormatException e) {
            e.printStackTrace();
            // Handle the exception here
        }
    }

    private boolean isValidUserId(String userIdText) {
        return userIdText != null && !userIdText.isEmpty() && userIdText.matches("\\d+");
    }

    private boolean isValidPubId(String pubIdText) {
        return pubIdText != null && !pubIdText.isEmpty() && pubIdText.matches("\\d+");
    }

    private boolean isValidInput(String content) {
        return content != null && !content.isEmpty();
    }

    private void showErrorAlert(String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Input Error");
        alert.setContentText(message);
        alert.showAndWait();
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }
}
