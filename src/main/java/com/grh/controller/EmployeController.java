package com.grh.controller;

import com.grh.model.Conge;
import com.grh.model.Contrat;
import com.grh.model.Employe ;
import com.grh.config.AppContext;
import com.grh.service.CongeService;
import com.grh.service.ContratService;
import com.grh.service.EmployeService;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import lombok.Setter;

import java.io.IOException;
import java.util.List;

public class EmployeController {

    @FXML
    private Button logOutButton ;

    @FXML
    private Button EmployesButton ;

    @FXML
    private TextField searchField ;

    @FXML private TableView<Employe> employeTable;
    @FXML private TableColumn<Employe, Integer> colId;
    @FXML private TableColumn<Employe, String> colNom;
    @FXML private TableColumn<Employe, String> colPrenom;
    @FXML private TableColumn<Employe, String> colCin;
    @FXML private TableColumn<Employe, String> colEmail;
    @FXML private TableColumn<Employe, String> colTelephone;
    @FXML private TableColumn<Employe, String> colAdresse;
    @FXML private TableColumn<Employe, String> colDateNaissance;
    @FXML private TableColumn<Employe, String> colDateRecrutement;
    @FXML private TableColumn<Employe, String> colPoste;
    @FXML private TableColumn<Employe, Double> colSalaire;
    @FXML private TableColumn<Employe, String> colDepartement;
    @FXML private TableColumn<Employe, Integer> colJoursCongeRestant;

    @FXML private List<Employe> employes ;
    @FXML private List<Conge> conges ;
    @FXML private List<Contrat> contrats  ;

    @Setter
    private EmployeService employeService ;
    @Setter
    private ContratService contratService ;
    @Setter
    private CongeService congeService ;

    public EmployeController(){

        employeService = AppContext.getEmployeService() ;
        congeService = AppContext.getCongeService() ;
        contratService = AppContext.getContratService() ;
        employes = employeService.findAllEmployes() ;
        conges = congeService.getAllCongesEnCours() ;
        contrats = contratService.findAllContrats() ;
    }
    @FXML
    public void initialize(){
        colId.setCellValueFactory(new PropertyValueFactory<>("id_employe"));
        colNom.setCellValueFactory(new PropertyValueFactory<>("nom"));
        colPrenom.setCellValueFactory(new PropertyValueFactory<>("prenom"));
        colCin.setCellValueFactory(new PropertyValueFactory<>("cin"));
        colEmail.setCellValueFactory(new PropertyValueFactory<>("email"));
        colTelephone.setCellValueFactory(new PropertyValueFactory<>("telephone"));
        colAdresse.setCellValueFactory(new PropertyValueFactory<>("adresse"));
        colDateNaissance.setCellValueFactory(new PropertyValueFactory<>("date_naissance"));
        colDateRecrutement.setCellValueFactory(new PropertyValueFactory<>("date_recrutement"));
        colPoste.setCellValueFactory(new PropertyValueFactory<>("poste"));
        colSalaire.setCellValueFactory(new PropertyValueFactory<>("salaire"));
        colJoursCongeRestant.setCellValueFactory(new PropertyValueFactory<>("jours_conge_annuel"));
        colDepartement.setCellValueFactory(new PropertyValueFactory<>("nom_departement"));
        employeTable.setItems(FXCollections.observableArrayList(employes));
        searchField.textProperty().addListener(
                (observableValue, s, t1) ->
                {
                    employeTable.setItems(
                            FXCollections.observableArrayList(
                                    employeService.ListerEmployesParNom(employes , t1)
                            )
                    );
                }
                ) ;

    }

public void ajouterEmploye(){
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/AjouterEmploye.fxml"));
                Parent root = loader.load();
                AjouterEmployeController controller = loader.getController() ;
                controller.setEmployes(employes);
                Stage stage = (Stage) EmployesButton.getScene().getWindow() ;
                stage.setScene(new Scene(root));
                stage.show();
            }catch(IOException ex){
                ex.printStackTrace();
            }
    }
    public void modifierEmploye(){
        Employe e = employeTable.getSelectionModel().getSelectedItem() ;

        if(e != null){
            List<Employe> employes = employeService.findAllEmployes() ;
            try{
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/ModifierEmploye.fxml"));
                Parent root = loader.load();
                ModifierEmployeController controller = loader.getController() ;
                controller.setEmploye(e);
                controller.setEmployes(employes);
                Stage stage = (Stage) EmployesButton.getScene().getWindow() ;
                stage.setScene(new Scene(root));
                stage.show();
            }catch(IOException ex){
                ex.printStackTrace();
            }
        }
    }


    private void showWarning(String message) {
        Alert alert = new Alert(Alert.AlertType.WARNING); // Type warning
        alert.setTitle("Attention");                     // Titre de la fenêtre
        alert.setHeaderText(null);                       // Pas de sous-titre
        alert.setContentText(message);                   // Message à afficher
        alert.showAndWait();                             // Affiche et attend que l'utilisateur ferme
    }


    public void archiverEmploye(){

        Employe employe = employeTable.getSelectionModel().getSelectedItem();
        if (employe != null) {
        }

    }
    private boolean archiverEmployeComplet(Employe employe) {
        // Supprime d'abord les congés en attente
        try {
            boolean suppr = congeService.supprimerCongesEnAttente(employe.getId_employe());
            if (!suppr) {
                System.out.println("Aucun congé en attente à supprimer pour cet employé.");
            }
        } catch (Exception ex) {
            showWarning("Exception lors de la suppression des congés en attente : " + ex.getMessage());
            return false;
        }

        // Récupère les congés et contrats de l'employé
        List<Conge> congesEmploye = conges.stream()
                .filter(c -> c.getId_employe() == employe.getId_employe())
                .toList();

        List<Contrat> contratsEmploye = contrats.stream()
                .filter(c -> c.getId_employe() == employe.getId_employe())
                .toList();

        // Archivage de l'employé
        try {
            boolean ok = employeService.archiverEmploye(employe);
            if (!ok) {
                showWarning("Erreur : Impossible d'archiver l'employé " + employe.getNom());
                return false;
            }
        } catch (Exception ex) {
            showWarning("Exception lors de l'archivage de l'employé : " + ex.getMessage());
            return false;
        }

        // Archivage des contrats
        for (Contrat contrat : contratsEmploye) {
            try {
                boolean ok = contratService.archiverContrat(contrat);
                if (!ok) {
                    showWarning("Erreur : Impossible d'archiver le contrat ID " + contrat.getId_contrat());
                    return false;
                }
            } catch (Exception ex) {
                showWarning("Exception lors de l'archivage du contrat ID " + contrat.getId_contrat() + " : " + ex.getMessage());
                return false;
            }
        }
        contrats = contrats.stream()
                .filter(c -> c.getId_employe() != employe.getId_employe())
                .toList();

        // Archivage des congés
        for (Conge conge : congesEmploye) {
            try {
                boolean ok = congeService.archiverConge(conge);
                if (!ok) {
                    showWarning("Erreur : Impossible d'archiver le congé ID " + conge.getId_conge());
                    return false;
                }
            } catch (Exception ex) {
                showWarning("Exception lors de l'archivage du congé ID " + conge.getId_conge() + " : " + ex.getMessage());
                return false;
            }
        }
        conges = conges.stream()
                .filter(c -> c.getId_employe() != employe.getId_employe())
                .toList();

        return true;
    }


    public void supprimerEmploye() {
        Employe employe = employeTable.getSelectionModel().getSelectedItem();
        if (employe != null) {
            if (congeService.verifierEmployeInCongesEnCours(conges, employe.getId_employe())) {
                showWarning("Impossible de supprimer cet employé : il a des congés en cours.");
                return;
            }

            // Appel de la fonction d'archivage
            boolean archiveOk = archiverEmployeComplet(employe);
            if (!archiveOk) {
                return; // Si échec, on quitte
            }

            // Suppression de l'employé dans la liste
            employes = employes.stream()
                    .filter(e -> e.getId_employe() != employe.getId_employe())
                    .toList();
            employeTable.setItems(FXCollections.observableArrayList(employes));

            System.out.println("Employé supprimé avec succès.");
        }
    }

    public void handleLogout() {
        try {

            FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/login.fxml"));
            Stage stage = (Stage) logOutButton.getScene().getWindow();
            stage.setScene(new Scene(loader.load()));
            stage.show();
        } catch (IOException ex) {
            ex.printStackTrace();


        }
    }
    public void handleAccueil(){
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/Home.fxml"));
            Stage stage = (Stage) EmployesButton.getScene().getWindow() ;
            stage.setScene(new Scene(loader.load()));
            stage.show();
        }catch(IOException ex){
            System.out.println("Je suis dans Employe Controller !");
            System.out.println("Exception : " + ex.getMessage());
        }
    }
    public void handleEmployes(){
    }
    public void handleDepartement(){
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/Departement.fxml"));
            Stage stage = (Stage) EmployesButton.getScene().getWindow() ;
            stage.setScene(new Scene(loader.load()));
            stage.show();
        }catch(IOException ex){
            System.out.println("Je suis dans Employe Controller !");
            System.out.println("Exception : " + ex.getMessage());
        }
    }
    public void handleContrats(){
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/Contrat.fxml"));
            Stage stage = (Stage) EmployesButton.getScene().getWindow() ;
            stage.setScene(new Scene(loader.load()));
            stage.show();
        }catch(IOException ex){
            System.out.println("Je suis dans Employe Controller !");
            System.out.println("Exception : " + ex.getMessage());
        }
    }

    public void handleConges(){
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/Conge.fxml"));
            Stage stage = (Stage) EmployesButton.getScene().getWindow() ;
            stage.setScene(new Scene(loader.load()));
            stage.show();
        }catch(IOException ex){
            System.out.println("Je suis dans Employe Controller !");
            System.out.println("Exception : " + ex.getMessage());
        }
    }

    public void handleArchives(){
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/Archive.fxml"));
            Stage stage = (Stage) EmployesButton.getScene().getWindow() ;
            stage.setScene(new Scene(loader.load()));
            stage.show();
        }catch(IOException ex){
            System.out.println("Je suis dans Employe Controller !");
            System.out.println("Exception : " + ex.getMessage());
        }
    }
}
