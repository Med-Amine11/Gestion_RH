package com.grh.controller;

import com.grh.config.AppContext;
import com.grh.model.Contrat;
import com.grh.model.Employe;
import com.grh.service.ContratService;
import com.grh.service.EmployeService;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;
import javafx.util.StringConverter;
import lombok.Setter;

import java.io.IOException;
import java.time.LocalDate;
import java.util.List;

public class ModifierContratController {

    @FXML
    private ComboBox<Employe> cmbEmploye;
    @FXML
    private ComboBox<String> cmbTypeContrat;
    @FXML
    private TextField txtSalaireBase;
    @FXML
    private DatePicker dpDateDebut;
    @FXML
    private DatePicker dpDateFin;
    @FXML
    private Label lblErreurContrat;

    @FXML
    private Button btnEnregistrerContrat;
    @FXML
    private Button btnAnnulerContrat;

    // Navigation buttons
    @FXML
    private Button logOutButton;
    @FXML
    private Button AccueilButton;
    @FXML
    private Button EmployesButton;
    @FXML
    private Button DepartementsButton;
    @FXML
    private Button ContratsButton;
    @FXML
    private Button CongesButton;
    @FXML
    private Button ArchivesButton;

    @Setter
    private ContratService contratService;
    @Setter
    private EmployeService employeService;

    private Contrat contratAModifier;

    public ModifierContratController() {
        contratService = AppContext.getContratService();
        employeService = AppContext.getEmployeService();
    }

    public void initialize() {
        // Load Employes
        List<Employe> employes = employeService.findAllEmployes();
        cmbEmploye.setItems(FXCollections.observableArrayList(employes));

        // Display generic connection of Employe (Nom + Prenom)
        cmbEmploye.setConverter(new StringConverter<Employe>() {
            @Override
            public String toString(Employe e) {
                return e == null ? "" : e.getNom() + " " + e.getPrenom() + " (ID: " + e.getId_employe() + ")";
            }

            @Override
            public Employe fromString(String string) {
                return null;
            }
        });

        // Load Contract Types
        cmbTypeContrat.setItems(FXCollections.observableArrayList("CDI", "CDD", "Stage", "Freelance"));
    }

    public void initData(Contrat contrat) {
        this.contratAModifier = contrat;

        // Select Employe by ID
        for (Employe e : cmbEmploye.getItems()) {
            if (e.getId_employe() == contrat.getId_employe()) {
                cmbEmploye.setValue(e);
                break;
            }
        }

        cmbTypeContrat.setValue(contrat.getType_contrat());
        txtSalaireBase.setText(String.valueOf(contrat.getSalaire_base()));
        dpDateDebut.setValue(contrat.getDate_debut());
        dpDateFin.setValue(contrat.getDate_fin());

        // Lock Employe selection if needed? Usually contract is tied to employee, but
        // editable is fine.
    }

    private void setErreur(String message) {
        lblErreurContrat.setText(message);
        lblErreurContrat.setVisible(true);
    }

    public void modifierContrat() {
        Employe employe = cmbEmploye.getValue();
        String type = cmbTypeContrat.getValue();
        String salaireText = txtSalaireBase.getText().trim();
        LocalDate dateDebut = dpDateDebut.getValue();
        LocalDate dateFin = dpDateFin.getValue();

        // Validation
        if (employe == null) {
            setErreur("Veuillez sélectionner un employé.");
            return;
        }
        if (type == null) {
            setErreur("Veuillez sélectionner un type de contrat.");
            return;
        }
        if (salaireText.isEmpty()) {
            setErreur("Le salaire est obligatoire.");
            return;
        }
        if (dateDebut == null) {
            setErreur("La date de début est obligatoire.");
            return;
        }

        if (dateFin != null && dateFin.isBefore(dateDebut)) {
            setErreur("La date de fin ne peut pas être antérieure à la date de début.");
            return;
        }

        double salaire;
        try {
            salaire = Double.parseDouble(salaireText);
            if (salaire <= 0) {
                setErreur("Le salaire doit être positif.");
                return;
            }
        } catch (NumberFormatException e) {
            setErreur("Salaire invalide.");
            return;
        }

        // Update object
        contratAModifier.setId_employe(employe.getId_employe());
        contratAModifier.setType_contrat(type);
        contratAModifier.setSalaire_base(salaire);
        contratAModifier.setDate_debut(dateDebut);
        contratAModifier.setDate_fin(dateFin);

        if (contratService.updateContrat(contratAModifier)) {
            handleContrats();
        } else {
            setErreur("Erreur lors de la modification du contrat.");
        }
    }

    // Navigation methods
    private void navigateTo(String fxmlPath) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource(fxmlPath));
            Stage stage = (Stage) ContratsButton.getScene().getWindow();
            stage.setScene(new Scene(loader.load()));
            stage.show();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    public void handleLogout() {
        navigateTo("/view/login.fxml");
    }

    public void handleAccueil() {
        navigateTo("/view/Home.fxml");
    }

    public void handleEmployes() {
        navigateTo("/view/Employe.fxml");
    }

    public void handleDepartement() {
        navigateTo("/view/Departement.fxml");
    }

    public void handleContrats() {
        navigateTo("/view/Contrat.fxml");
    }

    public void handleConges() {
        navigateTo("/view/Conge.fxml");
    }

    public void handleArchives() {
        navigateTo("/view/Archive.fxml");
    }
}
