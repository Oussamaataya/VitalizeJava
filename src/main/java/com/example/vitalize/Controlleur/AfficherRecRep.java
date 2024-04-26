package controllers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
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
import modals.Reclamation;
import services.ReclamationService;

import java.io.IOException;
import java.util.Date;
import java.util.List;
    public class AfficherRecRep {

        @FXML
        private TableColumn<Reclamation, Date> dateCol;

        @FXML
        private TableColumn<Reclamation, String> sujetCol;

        @FXML
        private TableView<Reclamation> tableId;

        @FXML
        private TableColumn<Reclamation, String> typeCol;


        private final ReclamationService rs = new ReclamationService();

        @FXML
        void initialize() {
            List<Reclamation> reclamations = rs.getAll();
            ObservableList<Reclamation> observableList = FXCollections.observableList(reclamations);
            tableId.setItems(observableList);
            typeCol.setCellValueFactory(new PropertyValueFactory<>("type"));
            sujetCol.setCellValueFactory(new PropertyValueFactory<>("sujet"));
            dateCol.setCellValueFactory(new PropertyValueFactory<>("date"));


            TableColumn<Reclamation, Void> actionsCol = new TableColumn<>("Actions");
            actionsCol.setPrefWidth(100);
            actionsCol.setCellFactory(param -> new TableCell<>() {
                private final Button respondButton = new Button("Repondre");

                {
                    respondButton.setOnAction(event -> {
                        Reclamation reclamation = getTableView().getItems().get(getIndex());
                        navigateToReponseScene(reclamation);
                    });
                }

                private void navigateToReponseScene(Reclamation reclamation) {
                    try {
                        FXMLLoader loader = new FXMLLoader(getClass().getResource("/AjoutReponse.fxml"));
                        Parent root = loader.load();
                        AjoutReponse ajoutReponseController = loader.getController();
                        ajoutReponseController.setReclamationId(reclamation.getId());
                        Scene scene = new Scene(root);
                        Stage stage = new Stage();
                        stage.setScene(scene);
                        stage.show();
                        System.out.println("AjoutReponse.fxml loaded successfully.");
                    } catch (IOException e) {
                        e.printStackTrace();
                        System.err.println("Failed to load AjoutReponse.fxml: " + e.getMessage());
                    }
                }



                @Override
                protected void updateItem(Void item, boolean empty) {
                    super.updateItem(item, empty);
                    if (empty) {
                        setGraphic(null);
                    } else {
                        setGraphic(respondButton);
                    }
                }
            });

            tableId.getColumns().add(actionsCol);
        }

}


