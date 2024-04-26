package controllers;

import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import modals.Reclamation;
import services.ReclamationService;

import java.io.IOException;
import java.sql.SQLException;
import java.util.Date;
import java.util.List;

public class AfficherReclamation {

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
    void initialize() throws SQLException {
        List<Reclamation> reclamations = rs.getAll();
        ObservableList<Reclamation> observableList = FXCollections.observableList(reclamations);
        tableId.setItems(observableList);
        typeCol.setCellValueFactory(new PropertyValueFactory<>("type"));
        sujetCol.setCellValueFactory(new PropertyValueFactory<>("sujet"));
        dateCol.setCellValueFactory(new PropertyValueFactory<>("date"));

        // Add action buttons to the actions column
        TableColumn<Reclamation, Void> actionsCol = new TableColumn<>("Actions");
        actionsCol.setPrefWidth(150.0);

        actionsCol.setCellFactory(param -> new TableCell<>() {
            private final Button deleteButton = new Button("Delete");
            private final Button updateButton = new Button("Update");
            {
                deleteButton.setOnAction(event -> {
                    Reclamation reclamation = getTableView().getItems().get(getIndex());
                    rs.delete(reclamation);
                });

                updateButton.setOnAction(event -> {
                    Reclamation reclamation = getTableView().getItems().get(getIndex());
                    navigateToEditScene(reclamation);
                });
            }
            private void navigateToEditScene(Reclamation reclamation) {
                try {
                    FXMLLoader loader = new FXMLLoader(getClass().getResource("/EditReclamation.fxml"));
                    Parent root = loader.load();

                    EditReclamation controller = loader.getController();
                    controller.initData(reclamation);

                    Scene scene = new Scene(root);
                    Stage stage = new Stage();
                    stage.setScene(scene);
                    stage.show();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }


            @Override
            protected void updateItem(Void item, boolean empty) {
                super.updateItem(item, empty);
                if (empty) {
                    setGraphic(null);
                } else {
                    setGraphic(new javafx.scene.layout.HBox(deleteButton, updateButton));
                }
            }
        });

        tableId.getColumns().add(actionsCol);
    }
}
