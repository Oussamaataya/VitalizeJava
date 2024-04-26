package controllers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import models.FichePatient;
import services.FichePatientService;

import java.util.List;
import java.util.Objects;

public class AfficherFiche {
    @FXML
    private TableView<FichePatient> TableViewFiche;

    @FXML
    private TableColumn<FichePatient, String> allergiesCol;

    @FXML
    private TableColumn<FichePatient, String> breakfastCol;

    @FXML
    private TableColumn<FichePatient, Integer> caloriesCol;

    @FXML
    private TableColumn<FichePatient, String> dinnerCol;

    @FXML
    private TableColumn<FichePatient, Integer> heightCol;

    @FXML
    private TableColumn<FichePatient, String> illnessesCol;

    @FXML
    private TableColumn<FichePatient, String> middayCol;

    @FXML
    private TableColumn<FichePatient, Integer> musclCol;

    @FXML
    private TableColumn<FichePatient, String> otherCol;

    @FXML
    private TableColumn<FichePatient, String> snacksCol;
    @FXML
    private Button createnewformbtn;

    @FXML
    private TableColumn<FichePatient, Integer> weightCol;
    private final FichePatientService fps = new FichePatientService();
    @FXML
    void initialize() {
        try {
            List<FichePatient> fichePatients = fps.Afficher();
            ObservableList<FichePatient> observableList = FXCollections.observableList(fichePatients);
            fichePatients = fps.Afficher();
            TableViewFiche.setItems(observableList);
            weightCol.setCellValueFactory(new PropertyValueFactory<>("weight"));
            musclCol.setCellValueFactory(new PropertyValueFactory<>("muscleMass"));
            heightCol.setCellValueFactory(new PropertyValueFactory<>("height"));
            allergiesCol.setCellValueFactory(new PropertyValueFactory<>("allergies"));
            illnessesCol.setCellValueFactory(new PropertyValueFactory<>("illnesses"));
            breakfastCol.setCellValueFactory(new PropertyValueFactory<>("breakfast"));
            middayCol.setCellValueFactory(new PropertyValueFactory<>("midday"));
            dinnerCol.setCellValueFactory(new PropertyValueFactory<>("dinner"));
            snacksCol.setCellValueFactory(new PropertyValueFactory<>("snacks"));
            caloriesCol.setCellValueFactory(new PropertyValueFactory<>("calories"));
            otherCol.setCellValueFactory(new PropertyValueFactory<>("other"));

            TableViewFiche.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
                if (newSelection != null) {
                    try {
                        FXMLLoader loader = new FXMLLoader(getClass().getResource("/EditerFiche.fxml"));
                        Parent root = loader.load();
                        EditerFiche controller = loader.getController();
                        controller.initData(newSelection);

                        Scene scene = new Scene(root);
                        Stage window = (Stage) TableViewFiche.getScene().getWindow();
                        window.setScene(scene);
                    } catch (Exception e) {
                        Alert alert = new Alert(Alert.AlertType.ERROR);
                        alert.setTitle("Input Error");
                        alert.setContentText(e.getMessage());
                        alert.showAndWait();
                    }
                }
            });

        } catch (Exception e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Input Error");
            alert.setContentText(e.getMessage());
            alert.showAndWait();
        }


    }
    @FXML
    void updateSelectedFiche(ActionEvent event) {
        FichePatient selectedFichePatient = TableViewFiche.getSelectionModel().getSelectedItem();
        if (selectedFichePatient != null) {
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/EditerFiche.fxml"));
                Parent root = loader.load();
                EditerFiche controller = loader.getController();
                controller.initData(selectedFichePatient);

                Scene scene = new Scene(root);
                Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
                window.setScene(scene);
            } catch (Exception e) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Input Error");
                alert.setContentText(e.getMessage());
                alert.showAndWait();
            }
        }
    }


    @FXML
    void CreateNewFormBtn(ActionEvent event2) {
        try {
            Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/AjouterFiche.fxml")));
            Scene scene = new Scene(root);
            Stage window = (Stage) ((javafx.scene.Node) event2.getSource()).getScene().getWindow();
            window.setScene(scene);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
    @FXML
    void NavToRdv(ActionEvent event4) {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("/RendezVous.fxml"));
            Scene scene = new Scene(root);
            Stage window = (Stage) ((javafx.scene.Node) event4.getSource()).getScene().getWindow();
            window.setScene(scene);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

}