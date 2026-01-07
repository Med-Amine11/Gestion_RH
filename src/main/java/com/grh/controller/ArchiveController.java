package com.grh.controller;

import com.grh.config.AppContext;
import com.grh.service.CongeService;
import com.grh.service.ContratService;
import com.grh.service.EmployeService;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;
import lombok.Setter;

import java.io.IOException;

import javafx.scene.control.TableView;
import javafx.scene.control.TableColumn;
import javafx.scene.control.Alert;

public class ArchiveController {
    @FXML
    private Button logOutButton;

    @FXML
    private Button ArchivesButton;

    @FXML
    private TableView<Object> archiveTable; // Using Object for now, should be specific model
    @FXML
    private TableColumn<Object, String> colId;
    @FXML
    private TableColumn<Object, String> colNom;
    @FXML
    private TableColumn<Object, String> colType;
    @FXML
    private TableColumn<Object, String> colDate;
    @FXML
    private TableColumn<Object, String> colDetails;

    @FXML
    private javafx.scene.control.ComboBox<String> filterType;

    @Setter
    private EmployeService employeService;
    @Setter
    private ContratService contratService;
    @Setter
    private CongeService congeService;

    public ArchiveController() {
        employeService = AppContext.getEmployeService();
        contratService = AppContext.getContratService();
        congeService = AppContext.getCongeService();
    }

    @FXML
    public void initialize() {
        if (filterType != null) {
            filterType.getItems().addAll("Employé", "Département", "Contrat");
        }
    }

    public void handleLogout() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/login.fxml"));
            Stage stage = (Stage) logOutButton.getScene().getWindow();
            stage.setScene(new Scene(loader.load()));
            stage.show();
        } catch (IOException ex) {
            System.out.println("Je suis dans Archive Controller !");
            System.out.println("Exception : " + ex.getMessage());

        }
    }

    public void handleAccueil() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/Home.fxml"));
            Stage stage = (Stage) ArchivesButton.getScene().getWindow();
            stage.setScene(new Scene(loader.load()));
            stage.show();
        } catch (IOException ex) {
            System.out.println("Je suis dans Archive Controller !");
            System.out.println("Exception : " + ex.getMessage());
        }
    }

    public void handleEmployes() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/Employe.fxml"));
            Stage stage = (Stage) ArchivesButton.getScene().getWindow();
            stage.setScene(new Scene(loader.load()));
            stage.show();
        } catch (IOException ex) {
            System.out.println("Je suis dans Archive Controller !");
            System.out.println("Exception : " + ex.getMessage());
        }
    }

    public void handleDepartement() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/Departement.fxml"));
            Stage stage = (Stage) ArchivesButton.getScene().getWindow();
            stage.setScene(new Scene(loader.load()));
            stage.show();
        } catch (IOException ex) {
            System.out.println("Je suis dans Archive Controller !");
            System.out.println("Exception : " + ex.getMessage());
        }
    }

    public void handleContrats() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/Contrat.fxml"));
            Stage stage = (Stage) ArchivesButton.getScene().getWindow();
            stage.setScene(new Scene(loader.load()));
            stage.show();
        } catch (IOException ex) {
            System.out.println("Je suis dans Archive Controller !");
            System.out.println("Exception : " + ex.getMessage());
        }
    }

    public void handleConges() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/Conge.fxml"));
            Stage stage = (Stage) ArchivesButton.getScene().getWindow();
            stage.setScene(new Scene(loader.load()));
            stage.show();
        } catch (IOException ex) {
            System.out.println("Je suis dans Archive Controller !");
            System.out.println("Exception : " + ex.getMessage());
        }
    }

    public void handleArchives() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/Archive.fxml"));
            Stage stage = (Stage) ArchivesButton.getScene().getWindow();
            stage.setScene(new Scene(loader.load()));
            stage.show();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    @FXML
    public void handleDelete() {
        Object selected = archiveTable.getSelectionModel().getSelectedItem();
        if (selected == null) {
            showAlert(Alert.AlertType.WARNING, "Attention",
                    "Veuillez sélectionner un élément à supprimer définitivement.");
            return;
        }
        // TODO: Implement permanent delete logic
        showAlert(Alert.AlertType.CONFIRMATION, "Confirmation",
                "Voulez-vous vraiment supprimer cet élément définitivement ?");
    }

    private void showAlert(Alert.AlertType alertType, String title, String message) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}