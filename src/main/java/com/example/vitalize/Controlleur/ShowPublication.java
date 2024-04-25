package com.example.vitalize.Controlleur;

import io.github.palexdev.materialfx.controls.MFXButton;
import io.github.palexdev.materialfx.utils.SwingFXUtils;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Priority;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;
import com.example.vitalize.Entity.UserSession;
import com.example.vitalize.Entity.Users;
import com.example.vitalize.Service.UserService;
import javafx.stage.Stage;
import com.example.vitalize.Entity.Commentaire;
import com.example.vitalize.Entity.Publication;
import com.example.vitalize.Entity.React;
import com.example.vitalize.Service.Servicecommentaire;
import com.example.vitalize.Service.Servicepublication;
import com.example.vitalize.Service.ServiceReact;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import java.util.stream.Collectors;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;


public class ShowPublication implements Initializable {
    public TextField searchField;
    public MFXButton searchButton;
    public MFXButton addCommentButton;
    public Label label1;
    public MFXButton dislikeButton;
    public MFXButton likeButton;
    @FXML
    private ComboBox<String> typeComboBox;

    private Stage primaryStage;

    @FXML
private MFXButton Ajoutp;

    @FXML
    private MFXButton Delete;

    @FXML
    private MFXButton Editer;

    @FXML
    private ListView<Publication> ListPublication;
        Servicepublication PublicationService=new Servicepublication();
    @FXML
    public void searchByTitle() {
        // Get the search query from the search field
        String query = searchField.getText().trim().toLowerCase();

        // Fetch all publications from the service
        List<Publication> allPublications = PublicationService.getAllPublications();

        // Filter publications by title containing the search query
        List<Publication> filteredPublications = allPublications.stream()
                .filter(publication -> publication.getTitre().toLowerCase().contains(query))
                .collect(Collectors.toList());

        // Clear the list view
        ListPublication.getItems().clear();
        // Populate the list view with filtered publications
        ListPublication.getItems().addAll(filteredPublications);
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        int size = 125;

        ListPublication.setCellFactory(param -> new ListCell<>() {
            @Override
            protected void updateItem(Publication Publication, boolean empty) {
                super.updateItem(Publication, empty);

                if (empty || Publication == null) {
                    setText(null);
                    setGraphic(null);
                } else {
                    GridPane container = new GridPane();
                    TextFlow textFlow = new TextFlow();

                    String labelStyle = "-fx-fill: #9b8385;  -fx-font-size: 27  ;";
                    String nameStyle = "-fx-fill: #8b7080;  -fx-font-size: 40;";
                    String dataStyle = "-fx-fill: #9b8385; -fx-font-size: 20;";

                    Text nameData = new Text(Publication.getTitre() + "\n");
                    nameData.setStyle(nameStyle);

                    Text evaluationText = new Text("Type: ");
                    evaluationText.setStyle(labelStyle);

                    Text evaluationData = new Text(Publication.getType() + "\n");
                    evaluationData.setStyle(dataStyle);

                    Text difficultyText = new Text("Description: ");
                    difficultyText.setStyle(labelStyle);
                    Text difficultyData = new Text(Publication.getDescription() + "\n");
                    difficultyData.setStyle(dataStyle);

                    String demonstrationPath = Publication.getImage();
                    Image demonstrationImage = new Image(new File(demonstrationPath).toURI().toString());
                    ImageView imageView = new ImageView(demonstrationImage);
                    imageView.setFitWidth(200);
                    imageView.setFitHeight(200);
                    nameData.setWrappingWidth(200);
                    evaluationData.setWrappingWidth(200);
                    evaluationText.setWrappingWidth(200);
                    difficultyText.setWrappingWidth(200);
                    difficultyData.setWrappingWidth(200);
                    ColumnConstraints col1 = new ColumnConstraints(400);
                    ColumnConstraints col2 = new ColumnConstraints(450);
                    container.getColumnConstraints().addAll(col1, col2);

                    textFlow.getChildren().addAll(nameData, evaluationText, evaluationData, difficultyText, difficultyData);
                    container.add(textFlow, 1, 0);  // Add textFlow to column 0
                    container.add(imageView, 0, 0); // Add imageView to column 1
                    ColumnConstraints columnConstraints = new ColumnConstraints();
                    columnConstraints.setHgrow(Priority.ALWAYS);
                    container.getColumnConstraints().addAll(columnConstraints, columnConstraints, columnConstraints);

                    container.setHgap(30);

                    // Displaying comments
                    ListView<Commentaire> commentaireListView = new ListView<>();
                    commentaireListView.setPrefSize(600, 100); // Set the size as needed

                    // Retrieve comments for the current publication
                    List<Commentaire> comments = PublicationService.getCommentsForPublication(Publication.getId());

                    // Set custom cell factory for displaying comment content
                    commentaireListView.setCellFactory(listView -> new ListCell<>() {
                        @Override
                        protected void updateItem(Commentaire commentaire, boolean empty) {
                            super.updateItem(commentaire, empty);

                            if (empty || commentaire == null) {
                                setText(null);
                            } else {
                                // Display the content of the commentaire
                                setText(commentaire.getContenu());
                            }
                        }
                    });

                    // Add each comment to the ListView
                    commentaireListView.getItems().addAll(comments);

                    container.add(commentaireListView, 0, 1, 2, 1); // Add the ListView below the publication details

                    // Add the code to calculate total likes here
                    List<React> reacts = PublicationService.getAllReactsForPublication(Publication.getId()); // Assuming you have a method to get all reacts for a publication
                    int totalLikes = 0;
                    int totalDislikes = 0;

                        // Calculate total likes and dislikes
                    for (React react : reacts) {
                        totalLikes += react.getLikeCount();
                        totalDislikes += react.getDislikeCount();
                    }
                    // Create labels for likes and dislikes
                    Label totalLikesLabel = new Label("Likes: " + totalLikes);
                    Label totalDislikesLabel = new Label("Dislikes: " + totalDislikes);

                    // Add likes and dislikes labels to the container
                    container.add(totalLikesLabel, 0, 2);
                    container.add(totalDislikesLabel, 1, 2);  // Add the total likes label below the comments

                    setGraphic(container);
                }
            }
        });


        // Add ComboBox code here
        ObservableList<String> types = FXCollections.observableArrayList();
        List<String> distinctTypes = PublicationService.getAllPublications().stream()
                .map(Publication::getType)
                .distinct()
                .collect(Collectors.toList());
        types.addAll(distinctTypes);
        typeComboBox.setItems(types);

        // Add a listener to handle ComboBox selection changes
        typeComboBox.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            filterPublicationsByType(newValue);
        });

        // Set default selection to the first item
        typeComboBox.getSelectionModel().selectFirst();

        // Initialize the ListView with all publications
        updateListView();
    }

    @FXML
    private void handleCellClick(MouseEvent event){

    }




    @FXML
    private void addCommentButtonClicked(ActionEvent event) {
        Publication selectedPublication = ListPublication.getSelectionModel().getSelectedItem();
        if (selectedPublication != null) {
            int publicationId = selectedPublication.getId();
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/vitalize/AddCommentaire.fxml"));
            Parent root;
            try {
                root = loader.load();
                AddCommentaire addCommentaireController = loader.getController();
                addCommentaireController.populateFields(publicationId);

                Scene currentScene = ((Node) event.getSource()).getScene();
                currentScene.setRoot(root);
            } catch (IOException e) {
                e.printStackTrace();
                // Handle exception
            }
        }
    }


    public void addCommentaire(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/vitalize/AddCommentaire.fxml"));
            Parent root = loader.load();

            Stage stage = new Stage();
            stage.setScene(new Scene(root));

            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    @FXML
    private void likePublication(ActionEvent event) {
        Publication publication = ListPublication.getSelectionModel().getSelectedItem();
        if (publication != null) {
            React react = new React();
            react.setPublication(publication);
            react.setLikeCount(react.getLikeCount() + 1);
            ServiceReact serviceReact = new ServiceReact();
            serviceReact.add(react); // Save the react object in the database
            //closeAndReopenWindow();

        }
    }

    @FXML
    private void dislikePublication(ActionEvent event) {
        Publication publication = ListPublication.getSelectionModel().getSelectedItem();
        if (publication != null) {
            React react = new React();
            react.setPublication(publication);
            react.setDislikeCount(react.getDislikeCount() + 1);
            ServiceReact serviceReact = new ServiceReact();
            serviceReact.add(react); // Save the react object in the database
           // closeAndReopenWindow();
        }
    }

    /*private void closeAndReopenWindow() {
        // Close the current window
        Stage stage = (Stage) ListPublication.getScene().getWindow();
        stage.close();

        // Open a new window
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/ShowPublication.fxml"));
            Parent root = loader.load();
            Scene scene = new Scene(root);
            Stage newStage = new Stage();
            newStage.setScene(scene);
            newStage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }*/
    private void filterPublicationsByType(String type) {
        if (type == null || type.isEmpty()) {
            // If type is empty or null, show all publications
            updateListView();
        } else {
            // Filter publications by type and update the ListView
            List<Publication> filteredPublications = PublicationService.getAllPublications()
                    .stream()
                    .filter(publication -> publication.getType().equals(type))
                    .collect(Collectors.toList());
            ListPublication.getItems().setAll(filteredPublications);
        }
    }

    public void deleteSelectedPublication(ActionEvent event) {

        Publication SP = ListPublication.getSelectionModel().getSelectedItem();

        if (SP != null) {
            int id = SP.getId();
            PublicationService.delete(id);
            ListPublication.getItems().remove(SP);
    }}
    public void Editer(javafx.event.ActionEvent actionEvent) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/vitalize/EditPublication.fxml"));
            loader.setControllerFactory(controllerClass -> {
                if (controllerClass == EditPublication.class) {
                    EditPublication editionController = new EditPublication();
                    Publication selectedExercice = ListPublication.getSelectionModel().getSelectedItem();
                    int id = selectedExercice.getId();
                    editionController.setPassedId(id);
                    return editionController;
                } else {
                    return new EditPublication();
                }
            });

            Parent root = loader.load();

            Stage stage = new Stage();
            stage.setScene(new Scene(root));

            stage.setOnHidden(event -> {
                updateListView();
            });

            stage.show();

            EditPublication editionExerciceController = loader.getController();
            editionExerciceController.setPrimaryStage(primaryStage);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public void updateListView() {
        ListPublication.getItems().setAll(PublicationService.fetch());
    }


    public void ajoutpass(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/vitalize/AddPublication.fxml"));
            Parent root = loader.load();

            Scene currentScene = ((Node) event.getSource()).getScene();

            currentScene.setRoot(root);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
