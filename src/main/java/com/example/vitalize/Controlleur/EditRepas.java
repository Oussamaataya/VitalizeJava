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

public class EditRepas {
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

    private Meal mealToEdit;

    public void setMealToEdit(Meal meal) {
        this.mealToEdit = meal;
        // Afficher les données du repas dans les champs correspondants
        addMealtf.setText(meal.getNom_repas());
        ingredientstf.setText(meal.getIngredients());
        recipetf.setText(meal.getRecette());
        mealTypetf.setText(meal.getType_repas().toString());
        nombrePtf.setText(String.valueOf(meal.getNombre_personnes()));
        phototf.setText(meal.getImage());
        durationtf.setText(meal.getDuree_preparation());
        pricetf.setText(String.valueOf(meal.getPrix()));
        quantitytf.setText(String.valueOf(meal.getQuantity()));
    }

    @FXML
    void EditMeal(ActionEvent event) {
        if (isValidInput()) {
            // Mettre à jour les données du repas avec les nouvelles valeurs des champs
            mealToEdit.setNom_repas(addMealtf.getText());
            mealToEdit.setIngredients(ingredientstf.getText());
            mealToEdit.setRecette(recipetf.getText());
            mealToEdit.setType_repas(TypeRepas.valueOf(mealTypetf.getText()));
            mealToEdit.setNombre_personnes(Integer.parseInt(nombrePtf.getText()));
            mealToEdit.setImage(phototf.getText());
            mealToEdit.setDuree_preparation(durationtf.getText());
            mealToEdit.setPrix(Double.parseDouble(pricetf.getText()));
            mealToEdit.setQuantity(Integer.parseInt(quantitytf.getText()));

            // Appeler la méthode de mise à jour dans le service
            ms.update(mealToEdit);

            // Fermer la fenêtre ou effectuer une autre action après la mise à jour
            // Par exemple, fermer la fenêtre actuelle
            // ((Stage) addMealtf.getScene().getWindow()).close();
        } else {
            showErrorAlert("Veuillez remplir tous les champs avec des données valides.");
        }
    }

    @FXML
    void browseImage(ActionEvent event) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Choisir une image");
        fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("Image Files", "*.png", "*.jpg", "*.gif", "*jpeg"));
        File selectedFile = fileChooser.showOpenDialog(((Node) event.getSource()).getScene().getWindow());
        if (selectedFile != null) {
            phototf.setText(selectedFile.getAbsolutePath());
        }
    }

    @FXML
    void naviguer2(ActionEvent event) {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("/listMeal.fxml"));
            Stage window = (Stage) ((javafx.scene.Node) event.getSource()).getScene().getWindow();
            window.setScene(new Scene(root));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }

    private boolean isValidInput() {
        return isValidTextField(addMealtf) &&
                isValidTextField(durationtf) &&
                isValidTextField(ingredientstf) &&
                isValidTextField(mealTypetf) &&
                isValidTextField(nombrePtf) &&
                isValidTextField(phototf) &&
                isValidTextField(pricetf) &&
                isValidTextField(quantitytf) &&
                isValidTextField(recipetf);
                /*isValidIntegerField(nombrePtf) &&
                isValidIntegerField(quantitytf) &&
                isValidDoubleField(pricetf) &&
                isValidNameField(addMealtf) &&
                isValidNameField(ingredientstf) &&
                isValidNameField(recipetf) &&
                isValidMealType(mealTypetf);*/
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
    }*

   /* private boolean isValidMealType(TextField textField) {
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
