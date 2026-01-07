package com.grh.controller;

import com.grh.model.Employe ;
import com.grh.config.AppContext;
import com.grh.service.EmployeService;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
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
    @Setter
    private EmployeService employeService ;

    public EmployeController(){
        employeService = AppContext.getEmployeService() ;
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
        employes = employeService.findAllEmployes();
        employeTable.setItems(FXCollections.observableArrayList(employes));
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
