package tn.esprit.controllers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;
import tn.esprit.entities.Meal;
import tn.esprit.services.MealService;

import java.io.File;
import java.io.IOException;
import java.util.List;

public class ListMeal {
    @FXML
    private TableColumn<Meal, String> durée;

    @FXML
    private TableColumn<Meal, String> igredientsCol;

    @FXML
    private TableColumn<Meal, String> image;

    @FXML
    private TableColumn<Meal, String> nomRcol;

    @FXML
    private TableColumn<Meal, Integer> nombrecol;

    @FXML
    private TableColumn<Meal, Double> prixcol;

    @FXML
    private TableColumn<Meal, Integer> quantitécol;

    @FXML
    private TableColumn<Meal, String> recettecol;

    @FXML
    private TableColumn<Meal, Void> actionsCol;

    @FXML
    private TableColumn<Meal, String> typecol;

    @FXML
    private TableView<Meal> tableview;

    private final MealService ms = new MealService();

    @FXML
    void initialize() {
        List<Meal> meals = ms.getAll();
        ObservableList<Meal> observableList = FXCollections.observableList(meals);
        tableview.setItems(observableList);
        nomRcol.setCellValueFactory(new PropertyValueFactory<>("nom_repas"));
        igredientsCol.setCellValueFactory(new PropertyValueFactory<>("ingredients"));
        recettecol.setCellValueFactory(new PropertyValueFactory<>("recette"));
        durée.setCellValueFactory(new PropertyValueFactory<>("duree_preparation"));
        typecol.setCellValueFactory(new PropertyValueFactory<>("type_repas"));
        image.setCellValueFactory(new PropertyValueFactory<>("image"));
        nombrecol.setCellValueFactory(new PropertyValueFactory<>("nombre_personnes"));
        quantitécol.setCellValueFactory(new PropertyValueFactory<>("quantity"));
        prixcol.setCellValueFactory(new PropertyValueFactory<>("prix"));
        actionsCol.setCellFactory(column -> {
            return new TableCell<>() {
                final Button editButton = new Button("Edit");
                final Button deleteButton = new Button("Delete");

                {
                    // Définir le style pour le bouton Edit
                    editButton.setStyle("-fx-background-color:#2aa9a3 red; -fx-text-fill: white;");

                    // Définir le style pour le bouton Delete
                    deleteButton.setStyle("-fx-background-color: red; -fx-text-fill: white;");

                    editButton.setOnAction(event -> {
                        Meal meal = getTableView().getItems().get(getIndex());
                        editMeal(meal);
                    });

                    deleteButton.setOnAction(event -> {
                        Meal meal = getTableView().getItems().get(getIndex());
                        deleteMeal(meal);
                    });
                }

                @Override
                protected void updateItem(Void item, boolean empty) {
                    super.updateItem(item, empty);
                    if (empty) {
                        setGraphic(null);
                    } else {
                        HBox container = new HBox(editButton, deleteButton);
                        setGraphic(container);
                    }
                }
            };
        });
    }

    private void editMeal(Meal meal) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/EditRepas.fxml"));
            Parent root = loader.load();
            EditRepas controller = loader.getController();
            controller.setMealToEdit(meal);
            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void deleteMeal(Meal meal) {
        Meal MealToDelete = ms.getOne(meal.getId());
        ms.delete(MealToDelete);
        tableview.getItems().remove(meal);
    }

    @FXML
    void naviguertoaddMeeal(ActionEvent event) {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("/AjouterRepas.fxml"));
            Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
            window.setScene(new Scene(root));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
