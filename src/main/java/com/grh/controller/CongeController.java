package com.grh.controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.util.Callback;

import com.grh.model.Employe;
import com.grh.model.Conge;
import com.grh.service.CongeService;
import com.grh.config.AppContext;

import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

import java.io.IOException;
import java.time.LocalDate;
import java.util.List;

public class CongeController {

    private final CongeService congeService = AppContext.getCongeService();

    @FXML
    private TextField typeCongeField;
    @FXML
    private DatePicker dateDebutPicker;
    @FXML
    private DatePicker dateFinPicker;
    @FXML
    private TextField motifField;
    @FXML
    private TextField txtNomEmploye;
    @FXML
    private TableView<Conge> tableConges;
    @FXML
    private TableColumn<Conge, Integer> colId;
    @FXML
    private TableColumn<Conge, String> colType;
    @FXML
    private TableColumn<Conge, LocalDate> colDateDebut;
    @FXML
    private TableColumn<Conge, LocalDate> colDateFin;
    @FXML
    private TableColumn<Conge, String> colStatut;
    @FXML
    private TableColumn<Conge, String> colMotif;
    @FXML
    private TableColumn<Conge, Integer> colIdEmploye;
    @FXML
    private ComboBox<Employe> employeCombo;

    @FXML
    private Button AccueilButton;
    @FXML
    private Button DepartementsButton;
    @FXML
    private Button EmployesButton;
    @FXML
    private Button ContratsButton;
    @FXML
    private Button CongesButton;
    @FXML
    private Button ArchivesButton;
    @FXML
    private Button LogOutButton;
    @FXML
    private Button ajouterButton;
    @FXML
    private Button modifierButton;
    @FXML
    private Button rechercherButton;

    private ObservableList<Conge> congeList = FXCollections.observableArrayList();

    private void chargerConges() {
        congeList.clear();
        List<Conge> liste = congeService.listerConges();
        congeList.addAll(liste);
        tableConges.setItems(congeList);
    }

    // ------------------ Gestion des congés ------------------

    @FXML
    private void ajouterConge() {
        try {
            String type = typeCongeField.getText();
            LocalDate debut = dateDebutPicker.getValue();
            LocalDate fin = dateFinPicker.getValue();
            String motif = motifField.getText();
            Employe emp = employeCombo.getSelectionModel().getSelectedItem();
            if (emp == null)
                throw new Exception("Sélectionnez un employé !");
            int idEmp = emp.getId_employe();

            Conge conge = new Conge(0, type, debut, fin, "En attente", motif, idEmp);

            if (congeService.ajouterConge(conge)) {
                showAlert(Alert.AlertType.INFORMATION, "Succès", "Le congé a été ajouté !");
                chargerConges();
            } else {
                showAlert(Alert.AlertType.ERROR, "Erreur", "Impossible d'ajouter le congé !");
            }
        } catch (Exception e) {
            showAlert(Alert.AlertType.ERROR, "Erreur", e.getMessage());
        }
    }

    @FXML
    public void initialize() {
        // 🔹 Initialisation des colonnes TableView
        colId.setCellValueFactory(new PropertyValueFactory<>("id_conge"));
        colType.setCellValueFactory(new PropertyValueFactory<>("type_conge"));
        colDateDebut.setCellValueFactory(new PropertyValueFactory<>("date_debut"));
        colDateFin.setCellValueFactory(new PropertyValueFactory<>("date_fin"));
        colStatut.setCellValueFactory(new PropertyValueFactory<>("statut"));
        colMotif.setCellValueFactory(new PropertyValueFactory<>("motif"));
        colIdEmploye.setCellValueFactory(new PropertyValueFactory<>("id_employe"));

        // 🔹 Charger les congés
        chargerConges();

        // 🔹 Charger les employés dans le ComboBox
        chargerEmployesCombo();

        // 🔹 Configurer le bouton de recherche
        rechercherButton.setOnAction(e -> rechercherConge());
    }

    /**
     * Charge les employés depuis la DB et configure le ComboBox.
     */
    private void chargerEmployesCombo() {
        try {
            List<Employe> employes = AppContext.getEmployeService().findAllEmployes();

            if (employes == null || employes.isEmpty()) {
                System.out.println("⚠️ Aucun employé trouvé dans la DB !");
                employeCombo.setItems(FXCollections.observableArrayList());

                // Alert the user
                showAlert(Alert.AlertType.WARNING, "Aucun employé",
                        "Aucun employé trouvé. Veuillez d'abord ajouter des employés dans la section 'Employés'.");
                return;
            }

            ObservableList<Employe> obsEmployes = FXCollections.observableArrayList(employes);
            employeCombo.setItems(obsEmployes);

            // Afficher nom + prénom dans la liste et dans le bouton du ComboBox
            Callback<ListView<Employe>, ListCell<Employe>> cellFactory = lv -> new ListCell<Employe>() {
                @Override
                protected void updateItem(Employe item, boolean empty) {
                    super.updateItem(item, empty);
                    setText((item == null || empty) ? "" : item.getNom() + " " + item.getPrenom());
                }
            };

            employeCombo.setCellFactory(cellFactory);
            employeCombo.setButtonCell(cellFactory.call(null));

        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("❌ Erreur lors du chargement des employés : " + e.getMessage());
        }
    }

    @FXML
    private void modifierConge() {
        Conge selected = tableConges.getSelectionModel().getSelectedItem();
        if (selected != null) {
            try {
                String type = typeCongeField.getText();
                LocalDate debut = dateDebutPicker.getValue();
                LocalDate fin = dateFinPicker.getValue();
                String motif = motifField.getText();
                Employe emp = employeCombo.getSelectionModel().getSelectedItem();
                int idEmp = (emp != null) ? emp.getId_employe() : selected.getId_employe();

                Conge conge = new Conge(selected.getId_conge(), type, debut, fin, selected.getStatut(), motif, idEmp);

                if (congeService.modifierConge(conge)) {
                    showAlert(Alert.AlertType.INFORMATION, "Succès", "Le congé a été modifié !");
                    chargerConges();
                } else {
                    showAlert(Alert.AlertType.ERROR, "Erreur", "Impossible de modifier le congé !");
                }
            } catch (Exception e) {
                showAlert(Alert.AlertType.ERROR, "Erreur", e.getMessage());
            }
        }
    }

    @FXML
    private void validerConge() {
        Conge selected = tableConges.getSelectionModel().getSelectedItem();
        if (selected != null && congeService.validerConge(selected.getId_conge())) {
            showAlert(Alert.AlertType.INFORMATION, "Succès", "Le congé a été validé !");
            chargerConges();
        }
    }

    @FXML
    private void refuserConge() {
        Conge selected = tableConges.getSelectionModel().getSelectedItem();
        if (selected != null && congeService.refuserConge(selected.getId_conge())) {
            showAlert(Alert.AlertType.INFORMATION, "Succès", "Le congé a été refusé !");
            chargerConges();
        }
    }

    @FXML
    private void rechercherConge() {
        String nom = txtNomEmploye.getText();
        if (!nom.isEmpty()) {
            List<Conge> result = congeService.rechercherCongeParNomEmploye(nom);
            congeList.setAll(result);
        } else {
            chargerConges();
        }
    }

    private void showAlert(Alert.AlertType type, String title, String message) {
        Alert alert = new Alert(type);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    // ------------------ Navigation ------------------
    @FXML
    private void handleAccueil() {
        navigateTo("/view/Home.fxml", AccueilButton);
    }

    @FXML
    private void handleEmployes() {
        navigateTo("/view/Employe.fxml", EmployesButton);
    }

    @FXML
    private void handleDepartement() {
        navigateTo("/view/Departement.fxml", DepartementsButton);
    }

    @FXML
    private void handleContrats() {
        navigateTo("/view/Contrat.fxml", ContratsButton);
    }

    @FXML
    private void handleConges() {
        // Rien, on reste sur la même vue
    }

    @FXML
    private void handleArchives() {
        navigateTo("/view/Archive.fxml", ArchivesButton);
    }

    @FXML
    private void handleLogout() {
        navigateTo("/view/login.fxml", LogOutButton);
    }

    private void navigateTo(String fxmlPath, Button btn) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource(fxmlPath));
            Stage stage = (Stage) btn.getScene().getWindow();
            stage.setScene(new Scene(loader.load()));
            stage.show();
        } catch (IOException ex) {
            System.out.println("Erreur navigation vers " + fxmlPath + " : " + ex.getMessage());
        }
    }
}
