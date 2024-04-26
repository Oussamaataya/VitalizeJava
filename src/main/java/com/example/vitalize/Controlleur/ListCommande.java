package tn.esprit.controllers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import tn.esprit.entities.Commande;
import tn.esprit.services.CommandeService;

import java.io.IOException;
import java.util.List;

public class ListCommande {

    @FXML
    private TableColumn<Commande, Void> actionsCol;

    @FXML
    private TableColumn<Commande, String> clientAdresseCol;

    @FXML
    private TableColumn<Commande, String> clientFamilyNameCol;

    @FXML
    private TableColumn<Commande, String> clientNameCol;

    @FXML
    private TableColumn<Commande, String> clientPhoneCol;

    @FXML
    private TableColumn<Commande, String> dateCol;

    @FXML
    private TableColumn<Commande, String> etatCommandeCol;

    @FXML
    private TableColumn<Commande, String> instructionSpecialeCol;

    @FXML
    private TableColumn<Commande, String> methodePaiementCol;

    @FXML
    private TableColumn<Commande, Float> prixtotalCol;

    @FXML
    private TableView<Commande> tableview;

    private final CommandeService commandeService = new CommandeService();

    @FXML
    void initialize() {
        List<Commande> commandes = commandeService.getAll();
        ObservableList<Commande> observableList = FXCollections.observableList(commandes);
        tableview.setItems(observableList);

        clientNameCol.setCellValueFactory(new PropertyValueFactory<>("client_name"));
        clientFamilyNameCol.setCellValueFactory(new PropertyValueFactory<>("client_family_name"));
        clientAdresseCol.setCellValueFactory(new PropertyValueFactory<>("client_adresse"));
        clientPhoneCol.setCellValueFactory(new PropertyValueFactory<>("client_phone"));
        methodePaiementCol.setCellValueFactory(new PropertyValueFactory<>("methodePaiement"));
        etatCommandeCol.setCellValueFactory(new PropertyValueFactory<>("etatCommande"));
        dateCol.setCellValueFactory(new PropertyValueFactory<>("date"));
        instructionSpecialeCol.setCellValueFactory(new PropertyValueFactory<>("instructionSpeciale"));
        prixtotalCol.setCellValueFactory(new PropertyValueFactory<>("prixtotal"));

        actionsCol.setCellFactory(column -> {
            return new TableCell<>() {
                final Button editButton = new Button("Edit");
                final Button deleteButton = new Button("Delete");

                {
                    // Définir le style pour le bouton Edit
                    editButton.setStyle("-fx-background-color: #2aa9a3; -fx-text-fill: white;");

                    // Définir le style pour le bouton Delete
                    deleteButton.setStyle("-fx-background-color: red; -fx-text-fill: white;");
                    editButton.setOnAction(event -> {
                        Commande commande = getTableView().getItems().get(getIndex());
                        editCommande(commande);
                    });

                    deleteButton.setOnAction(event -> {
                        Commande commande = getTableView().getItems().get(getIndex());
                        deleteCommande(commande);
                    });
                }

                @Override
                protected void updateItem(Void item, boolean empty) {
                    super.updateItem(item, empty);
                    if (empty) {
                        setGraphic(null);
                    } else {
                        setGraphic(new javafx.scene.layout.HBox(editButton, deleteButton));
                    }
                }
            };
        });
    }
    private void editCommande(Commande commande) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/EditCommande.fxml"));
            Parent root = loader.load();
            EditCommande controller = loader.getController();
            controller.setCommandeToEdit(commande);
            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }



    private void deleteCommande(Commande commande) {
        commandeService.delete(commande);
        tableview.getItems().remove(commande);
    }
    @FXML
    void navigateToAddCommande() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/AjouterCommande.fxml"));
            Parent root = loader.load();
            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
