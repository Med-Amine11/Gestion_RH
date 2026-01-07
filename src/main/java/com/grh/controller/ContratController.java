
package com.grh.controller;

import com.grh.config.AppContext;
import com.grh.model.Contrat;
import com.grh.model.Employe;
import com.grh.service.ContratService;
import com.grh.service.EmployeService;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import javafx.util.Callback;
import lombok.Setter;

import java.io.IOException;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class ContratController {

    @FXML
    private Button logOutButton;
    @FXML
    private Button ContratsButton; // Used for scene reference
    @FXML
    private Button btnNouveauContrat;

    @FXML
    private TableView<Contrat> tableContrats;
    @FXML
    private TableColumn<Contrat, Integer> colId;
    @FXML
    private TableColumn<Contrat, String> colEmploye;
    @FXML
    private TableColumn<Contrat, String> colType;
    @FXML
    private TableColumn<Contrat, LocalDate> colDebut;
    @FXML
    private TableColumn<Contrat, LocalDate> colFin;
    @FXML
    private TableColumn<Contrat, Double> colSalaire;
    @FXML
    private TableColumn<Contrat, Void> colModifier;
    @FXML
    private TableColumn<Contrat, Void> colResilier;

    @Setter
    private ContratService contratService;
    @Setter
    private EmployeService employeService;

    // Cache for employe names
    private Map<Integer, String> employeNames;

    public ContratController() {
        contratService = AppContext.getContratService();
        employeService = AppContext.getEmployeService();
    }

    public void initialize() {
        setupTable();
        loadData();
    }

    private void setupTable() {
        colId.setCellValueFactory(new PropertyValueFactory<>("id_contrat"));
        colType.setCellValueFactory(new PropertyValueFactory<>("type_contrat"));
        colDebut.setCellValueFactory(new PropertyValueFactory<>("date_debut"));
        colFin.setCellValueFactory(new PropertyValueFactory<>("date_fin"));
        colSalaire.setCellValueFactory(new PropertyValueFactory<>("salaire_base"));

        // Custom cell factory for Employe Name
        colEmploye.setCellValueFactory(cellData -> {
            int employeId = cellData.getValue().getId_employe();
            String name = employeNames.getOrDefault(employeId, "ID: " + employeId);
            return new javafx.beans.property.SimpleStringProperty(name);
        });

        // Modifier Button
        colModifier.setCellFactory(new Callback<>() {
            @Override
            public TableCell<Contrat, Void> call(TableColumn<Contrat, Void> param) {
                return new TableCell<>() {
                    private final Button btn = new Button("Modifier");
                    {
                        btn.getStyleClass().add("update-btn");
                        btn.setOnAction(event -> {
                            Contrat contrat = getTableView().getItems().get(getIndex());
                            handleModifierContrat(contrat);
                        });
                    }

                    @Override
                    protected void updateItem(Void item, boolean empty) {
                        super.updateItem(item, empty);
                        if (empty)
                            setGraphic(null);
                        else
                            setGraphic(btn);
                    }
                };
            }
        });

        // Résilier Button
        colResilier.setCellFactory(new Callback<>() {
            @Override
            public TableCell<Contrat, Void> call(TableColumn<Contrat, Void> param) {
                return new TableCell<>() {
                    private final Button btn = new Button("Résilier/Archiver");
                    {
                        btn.getStyleClass().add("delete-btn"); // or similar
                        btn.setOnAction(event -> {
                            Contrat contrat = getTableView().getItems().get(getIndex());
                            handleResilierContrat(contrat);
                        });
                    }

                    @Override
                    protected void updateItem(Void item, boolean empty) {
                        super.updateItem(item, empty);
                        if (empty)
                            setGraphic(null);
                            // Check if already archived/ended?
                        else {
                            Contrat c = getTableView().getItems().get(getIndex());
                            if (c.getDate_fin() != null && !c.getDate_fin().isAfter(LocalDate.now())) {
                                btn.setDisable(true);
                                btn.setText("Terminé");
                            } else {
                                btn.setDisable(false);
                                btn.setText("Résilier/Archiver");
                            }
                            setGraphic(btn);
                        }
                    }
                };
            }
        });
    }

    private void loadData() {
        // Cache employees first
        List<Employe> employes = employeService.findAllEmployes();
        employeNames = employes.stream().collect(Collectors.toMap(
                Employe::getId_employe,
                e -> e.getNom() + " " + e.getPrenom()));

        List<Contrat> contrats = contratService.findAllContrats();
        ObservableList<Contrat> list = FXCollections.observableArrayList(contrats);
        tableContrats.setItems(list);
    }

    @FXML
    public void handleAjouterContrat() {
        navigateTo("/view/AjouterContrat.fxml");
    }

    @FXML
    public void handleModifierContrat(Contrat contrat) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/ModifierContrat.fxml"));
            Scene scene = new Scene(loader.load());
            ModifierContratController controller = loader.getController();
            controller.initData(contrat);
            Stage stage = (Stage) ContratsButton.getScene().getWindow();
            stage.setScene(scene);
            stage.show();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    @FXML
    public void handleResilierContrat(Contrat contrat) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Confirmation de résiliation");
        alert.setHeaderText("Résilier le contrat ?");
        alert.setContentText("Cette action mettra fin au contrat aujourd'hui. Voulez-vous continuer ?");

        if (alert.showAndWait().orElse(ButtonType.CANCEL) == ButtonType.OK) {
            contrat.setDate_fin(LocalDate.now());
            if (contratService.updateContrat(contrat)) {
                loadData(); // Refresh
            } else {
                Alert error = new Alert(Alert.AlertType.ERROR);
                error.setTitle("Erreur");
                error.setHeaderText("Impossible de résilier");
                error.show();
            }
        }
    }

    // Standard Navigation
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
}
