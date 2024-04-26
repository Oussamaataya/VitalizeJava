package controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;
import services.RendezVousService;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.sql.SQLIntegrityConstraintViolationException;
import java.time.LocalTime;

public class RendezVous {
    @FXML
    private RadioButton inPersonR;

    @FXML
    private RadioButton onlineR;

    @FXML
    private DatePicker datePicker;

    private ToggleGroup typeGroup;

    @FXML
    private Spinner<Integer> HoursSpinner;

    @FXML
    public void initialize() {
        typeGroup = new ToggleGroup();
        inPersonR.setToggleGroup(typeGroup);
        onlineR.setToggleGroup(typeGroup);
        inPersonR.setSelected(true);

        SpinnerValueFactory.IntegerSpinnerValueFactory valueFactory =
                new SpinnerValueFactory.IntegerSpinnerValueFactory(8, 18, 8);
        HoursSpinner.setValueFactory(valueFactory);
    }

    @FXML
    void AddRdv(ActionEvent event) {
        LocalDateTime now = LocalDateTime.now();

        LocalDate selectedDate = datePicker.getValue();
        if (selectedDate == null) {
            showAlert(Alert.AlertType.ERROR, "Error", "Missing Date", "Please select a date for the appointment.");
            return;
        }

        if (selectedDate.isBefore(now.toLocalDate())) {
            showAlert(Alert.AlertType.ERROR, "Error", "Invalid Date", "Please select a date starting from today.");
            return;
        }

        int selectedHour = HoursSpinner.getValue();
        if (selectedHour == 0) {
            showAlert(Alert.AlertType.ERROR, "Error", "Missing Time", "Please select a time for the appointment.");
            return;
        }

        if (selectedDate.isEqual(now.toLocalDate()) && selectedHour < now.getHour()) {
            showAlert(Alert.AlertType.ERROR, "Error", "Invalid Time", "Please select a time starting from the current hour.");
            return;
        }

        String selectedType = "";
        if (inPersonR.isSelected()) {
            selectedType = "In person";
        } else if (onlineR.isSelected()) {
            selectedType = "Online";
        }
        if (selectedType.isEmpty()) {
            showAlert(Alert.AlertType.ERROR, "Error", "Missing Appointment Type", "Please select a type for the appointment (In person or Online).");
            return;
        }

        LocalDateTime selectedDateTime = LocalDateTime.of(selectedDate, LocalTime.of(selectedHour, 0));
        models.RendezVous rdv = new models.RendezVous();
        rdv.setType(selectedType);
        rdv.setDate(selectedDateTime);

        RendezVousService service = new RendezVousService();

        try {
            boolean conflictingAppointment = service.existsForDifferentType(rdv);
            if (conflictingAppointment) {
                showAlert(Alert.AlertType.ERROR, "Error", "Conflicting Appointment", "An appointment for the selected date and time already exists with a different type. Please choose another date or time.");
            } else {
                if (service.exists(rdv)) {
                    showAlert(Alert.AlertType.ERROR, "Error", "This Appointment Is Already Booked", "An appointment for the selected date and time already exists. Please choose another date or time.");
                } else {
                    service.add(rdv);
                    showAlert(Alert.AlertType.INFORMATION, "Success", "RendezVous Added", "New RendezVous added successfully.");
                }
            }
        } catch (Exception e) {
            showAlert(Alert.AlertType.ERROR, "Error", "Unexpected Error", e.getMessage());
        }
    }

    private void showAlert(Alert.AlertType alertType, String title, String header, String content) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(header);
        alert.setContentText(content);
        alert.showAndWait();
    }
    public void NaviguerRdv(ActionEvent event) {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("/AfficherRdv.fxml"));
            Scene scene = new Scene(root);
            Stage window = (Stage) ((javafx.scene.Node) event.getSource()).getScene().getWindow();
            window.setScene(scene);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
