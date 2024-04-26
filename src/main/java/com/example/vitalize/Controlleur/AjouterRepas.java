package tn.esprit.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import tn.esprit.entities.Meal;
import tn.esprit.entities.TypeRepas;
import tn.esprit.services.MealService;

import java.io.File;
import java.io.IOException;

public class AjouterRepas {

    @FXML
    private TextField addMealtf;

    @FXML
    private TextField durationtf;

    @FXML
    private TextField ingredientstf;

    @FXML
    private TextField mealTypetf;

    @FXML
    private TextField nombrePtf;

    @FXML
    private TextField phototf;

    @FXML
    private TextField pricetf;

    @FXML
    private TextField quantitytf;

    @FXML
    private TextField recipetf;

    private final MealService ms = new MealService();

    @FXML
    void Ajouter(ActionEvent event) {
        if (isValidInput()) {
            String phototfText = phototf.getText().replace("%20", " ");
            phototfText = phototfText.replace("/", "\\").replace("file:\\", "");

            Meal NouveauRepas = new Meal(
                    addMealtf.getText(),
                    ingredientstf.getText(),
                    recipetf.getText(),
                    TypeRepas.valueOf(mealTypetf.getText()),
                    durationtf.getText(),
                    phototfText, // corrected variable name
                    Integer.parseInt(nombrePtf.getText()),
                    Integer.parseInt(quantitytf.getText()),
                    Double.parseDouble(pricetf.getText())
            );
            ms.add(NouveauRepas);
        } else {
            showErrorAlert("Veuillez remplir tous les champs avec des données valides.");
        }
    }


   /* @FXML
    void naviguer(ActionEvent event) {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("/AjouterCommande.fxml"));
            Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
            window.setScene(new Scene(root));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }*/

    @FXML
    void naviguer2(ActionEvent event) {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("/listMeal.fxml"));
            Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
            window.setScene(new Scene(root));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    public void Browse(javafx.event.ActionEvent event) {
        Stage primaryStage = new Stage();
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Choose a File");

        File selectedFile = fileChooser.showOpenDialog(primaryStage);

        if (selectedFile != null) {
            String fileUrl = selectedFile.toURI().toString();
            phototf.setText(fileUrl);
        }
    }
    /*void browseImage(ActionEvent event) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Choisir une image");
        fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("Image Files", "*.png", "*.jpg", "*.gif"));
        File selectedFile = fileChooser.showOpenDialog(((Node) event.getSource()).getScene().getWindow());
        if (selectedFile != null) {
            phototf.setText(selectedFile.getAbsolutePath()); // Stocker le chemin complet de l'image
        }
    }*/

    private boolean isValidInput() {
        return isValidTextField(addMealtf) &&
                isValidTextField(durationtf) &&
                isValidTextField(ingredientstf) &&
                isValidTextField(mealTypetf) &&
                isValidTextField(nombrePtf) &&
                isValidTextField(phototf) &&
                isValidTextField(pricetf) &&
                isValidTextField(quantitytf) &&
                isValidTextField(recipetf) ;
               /* isValidIntegerField(nombrePtf) &&*/
               /* isValidIntegerField(quantitytf) &&*/
                /*isValidDoubleField(pricetf) &&*/
               /* isValidNameField(addMealtf) &&
                isValidNameField(ingredientstf) &&
                isValidNameField(recipetf) ;*/
               /* isValidMealType(mealTypetf);*/
    }

    private boolean isValidTextField(TextField textField) {
        return textField.getText() != null && !textField.getText().isEmpty();
    }

    /*private boolean isValidIntegerField(TextField textField) {
        return isValidTextField(textField) && textField.getText().matches("\\d+");
    }*/

    /*private boolean isValidDoubleField(TextField textField) {
        return isValidTextField(textField) && textField.getText().matches("\\d+(\\.\\d+)?");
    }*/

    /*private boolean isValidNameField(TextField textField) {
        return isValidTextField(textField) && textField.getText().matches("[a-zA-Z]+");
    }*/

    /*private boolean isValidMealType(TextField textField) {
        String text = textField.getText();
        return isValidTextField(textField) && (text.equalsIgnoreCase("entree") || text.equalsIgnoreCase("plat") || text.equalsIgnoreCase("dessert"));
    }*/

    private void showErrorAlert(String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Erreur de saisie");
        alert.setContentText(message);
        alert.showAndWait();
    }
}
