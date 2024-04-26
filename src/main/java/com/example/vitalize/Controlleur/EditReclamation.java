package controllers;

import javafx.fxml.FXML;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import modals.Reclamation;
import services.ReclamationService;

public class EditReclamation {

    @FXML
    private TextField typeTF;

    @FXML
    private TextArea descriptionTF;

    @FXML
    private TextField medecinTF;

    @FXML
    private TextField sujetTF;

    @FXML
    private TextField fileTF;

    private Reclamation reclamation;
    private ReclamationService rs = new ReclamationService();

    public void initData(Reclamation reclamation) {
        this.reclamation = reclamation;
        typeTF.setText(reclamation.getType());
        descriptionTF.setText(reclamation.getDescription());
        medecinTF.setText(String.valueOf(reclamation.getMedecin()));
        sujetTF.setText(reclamation.getSujet());
        fileTF.setText(reclamation.getFile());
    }

    @FXML
    void EditReclamation() {
        reclamation.setType(typeTF.getText());
        reclamation.setDescription(descriptionTF.getText());
        reclamation.setMedecin(Integer.parseInt(medecinTF.getText()));
        reclamation.setSujet(sujetTF.getText());
        reclamation.setFile(fileTF.getText());

        rs.update(reclamation);
    }


}
