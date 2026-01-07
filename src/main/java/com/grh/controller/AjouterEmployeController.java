package com.grh.controller;
import com.grh.config.AppContext ;
import com.grh.model.Departement;
import com.grh.model.Employe ;
import com.grh.service.DepartementService;
import com.grh.service.EmployeService;
import javafx.collections.FXCollections;
import javafx.collections.ObservableArray;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;
import lombok.Setter;

import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Period;
import java.util.List;
import java.util.stream.Collectors;

public class AjouterEmployeController {

    @FXML private TextField txtNomEmploye;
    @FXML private TextField txtPrenomEmploye;
    @FXML private TextField txtCinEmploye;
    @FXML private TextField txtEmailEmploye;
    @FXML private TextField txtTelephoneEmploye;
    @FXML private TextField txtAdresseEmploye;
    @FXML private TextField txtSalaireEmploye;

    @FXML private ComboBox<String> cmbPosteEmploye;
    @FXML private ComboBox<Departement> cmbDepartementEmploye;

    @FXML private DatePicker dpDateNaissanceEmploye;
    @FXML private DatePicker dpDateEmbaucheEmploye;

    @FXML Label lblErreurEmploye ;
    @FXML private Button btnEnregistrerEmploye;
    @FXML private Button btnAnnulerEmploye ;

    @Setter
    private EmployeService employeService ;

    @Setter
    private List<Employe> employes ;
    @Setter
    private DepartementService departementService ;
    @FXML
    private Button logOutButton ;

    public AjouterEmployeController(){
        employeService = AppContext.getEmployeService() ;
        departementService = AppContext.getDepartementService() ;
    }

    public void initialize(){
        List<Departement> departements = departementService.getAllDepartements() ;
        cmbDepartementEmploye.setItems(FXCollections.observableArrayList(departements));
        ObservableList<String> postes = FXCollections.observableArrayList(
                "Développeur" ,
                "Responsable RH" ,
                "Comptable"
        ) ;
        cmbPosteEmploye.setItems(postes);

    }
    public void setErreurEmploye(String Message){
        lblErreurEmploye.setText(Message);
        lblErreurEmploye.setVisible(true);
    }
    public void clearErreurEmploye(){
        lblErreurEmploye.setText("");
        lblErreurEmploye.setVisible(false);
    }
    public void enregistrerEmploye(){

        // ===== Récupération des valeurs =====
        String nom = txtNomEmploye.getText().trim();
        String prenom = txtPrenomEmploye.getText().trim();
        String cin = txtCinEmploye.getText().trim();
        String email = txtEmailEmploye.getText().trim();
        String telephone = txtTelephoneEmploye.getText().trim();
        String adresse = txtAdresseEmploye.getText().trim();
        String poste = cmbPosteEmploye.getValue(); // ComboBox<String>
        Departement departement = cmbDepartementEmploye.getValue(); // ComboBox<Departement>
        LocalDate dateNaissance = dpDateNaissanceEmploye.getValue();
        LocalDate dateEmbauche = dpDateEmbaucheEmploye.getValue();

        String salaireText = txtSalaireEmploye.getText().trim();

        // ===== Validations =====

        // Nom
        if (nom.isEmpty()) {
            setErreurEmploye("Le nom est obligatoire.");
            return;
        }

        // Prénom
        if (prenom.isEmpty()) {
            setErreurEmploye("Le prénom est obligatoire.");
            return;
        }

        // Existence nom + prénom (APRÈS validation)
        if (employeService.employeExisteParNomPrenom(employes,nom, prenom)) {
            setErreurEmploye("Un employé avec le même nom et prénom existe déjà.");
            return;
        }

        // CIN
        if (cin.isEmpty()) {
            setErreurEmploye("Le CIN est obligatoire.");
            return;
        }
        // Existence CIN (APRÈS validation)
        if (employeService.employeExisteParCin(employes,cin)) {
            setErreurEmploye("Un employé avec le même cin existe déjà.");
            return;
        }
        // Email
        if (email.isEmpty()) {
            setErreurEmploye("L'email est obligatoire.");
            return;
        }
        // Existence EMAIL (APRÈS validation)
        if (employeService.employeExisteParEmail(employes, email)) {
            setErreurEmploye("Un employé avec le même email existe déjà.");
            return;
        }
        // Téléphone
        if (telephone.isEmpty()) {
            setErreurEmploye("Le téléphone est obligatoire.");
            return;
        }
        // Existence TELEPHONE (APRÈS validation)
        if (employeService.employeExisteParTelephone(employes , telephone)) {
            setErreurEmploye("Un employé avec le même telephone existe déjà.");
            return;
        }
        // Adresse
        if (adresse.isEmpty()) {
            setErreurEmploye("L'adresse est obligatoire.");
            return;
        }
        // Existence ADRESSE (APRÈS validation)
        if (employeService.employeExisteParAdresse(employes, adresse)) {
            setErreurEmploye("Un employé avec la même adresse existe déjà.");
            return;
        }
        // Poste
        if (poste == null) {
            setErreurEmploye("Veuillez sélectionner un poste.");
            return;
        }

        // Département
        if (departement == null) {
            setErreurEmploye("Veuillez sélectionner un département.");
            return;
        }

        // Date de naissance
        if (dateNaissance == null) {
            setErreurEmploye("La date de naissance est obligatoire.");
            return;
        }

        // Date d'embauche
        if (dateEmbauche == null) {
            setErreurEmploye("La date d'embauche est obligatoire.");
            return;
        }
        // ===== Date de naissance =====
        LocalDate dateNaissanceMin = LocalDate.now().minusYears(65);
        LocalDate dateNaissanceMax = LocalDate.now().minusYears(20);

        if (dateNaissance.isBefore(dateNaissanceMin) || dateNaissance.isAfter(dateNaissanceMax)) {
            setErreurEmploye("L'employé doit avoir un âge compris entre 20 et 65 ans.");
            return;
        }

        // ===== Date d'embauche =====
        LocalDate dateEmbaucheMin = LocalDate.now().minusYears(30);
        LocalDate dateEmbaucheMax = LocalDate.now();

        if (dateEmbauche.isBefore(dateEmbaucheMin) || dateEmbauche.isAfter(dateEmbaucheMax)) {
            setErreurEmploye("La date d'embauche doit être comprise dans les 30 dernières années et ne pas être future.");
            return;
        }

        int ageEmbauche = Period.between(dateNaissance, dateEmbauche).getYears();

        if (ageEmbauche <= 0) {
            setErreurEmploye("La date d'embauche doit être après la date de naissance.");
            return;
        }

        // Salaire vide
        if (salaireText.isEmpty()) {
            setErreurEmploye("Le salaire est obligatoire.");
            return;
        }

        // ===== Casting salaire =====
        double salaire;
        try {
            salaire = Double.parseDouble(salaireText);
            if (salaire <= 3000) {
                setErreurEmploye("Le salaire doit être supérieur strictemenr à 3000.");
                return;
            }
        } catch (NumberFormatException e) {
            setErreurEmploye("Le salaire doit être un nombre valide.");
            return;
        }

        Employe e = new Employe() ;
        e.setNom(nom);
        e.setPrenom(prenom);
        e.setCin(cin);
        e.setAdresse(adresse);
        e.setJours_conge_annuel(21);
        e.setEmail(email);
        e.setPoste(poste);
        e.setTelephone(telephone);
        e.setId_departement(departement.getId_departement());
        e.setSalaire(salaire);
        e.setDate_naissance(String.valueOf(dateNaissance));
        e.setDate_recrutement(String.valueOf(dateEmbauche));

        if(!employeService.addEmploye(e)){
            setErreurEmploye("Une erreur est survneu lors de l'insertion de l'employé");
            return;
        }
        clearErreurEmploye();
        handleEmployes();
    }
public void handleLogout() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/login.fxml"));
            Stage stage = (Stage) logOutButton.getScene().getWindow();
            stage.setScene(new Scene(loader.load()));
            stage.show();
        } catch (IOException ex) {
            System.out.println("Je suis dans Ajouter Employé Controller !");
            System.out.println("Exception : " + ex.getMessage());
        }
    }
    public void handleAccueil(){
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/Home.fxml"));
            Stage stage = (Stage) logOutButton.getScene().getWindow() ;
            stage.setScene(new Scene(loader.load()));
            stage.show();
        }catch(IOException ex){
            System.out.println("Je suis dans Ajouter Employé Controller !");
            System.out.println("Exception : " + ex.getMessage());
        }
    }
    public void handleEmployes(){
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/Employe.fxml"));
            Stage stage = (Stage) logOutButton.getScene().getWindow() ;
            stage.setScene(new Scene(loader.load()));
            stage.show();
        }catch(IOException ex){
            System.out.println("Je suis dans Ajouter Employé Controller !");
            System.out.println("Exception : " + ex.getMessage());
        }
    }
    public void handleDepartement(){
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/Departement.fxml"));
            Stage stage = (Stage) logOutButton.getScene().getWindow() ;
            stage.setScene(new Scene(loader.load()));
            stage.show();
        }catch(IOException ex){
            System.out.println("Je suis dans Ajouter Employé Controller !");
            System.out.println("Exception : " + ex.getMessage());
        }
    }
    public void handleContrats(){
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/Contrat.fxml"));
            Stage stage = (Stage) logOutButton.getScene().getWindow() ;
            stage.setScene(new Scene(loader.load()));
            stage.show();
        }catch(IOException ex){
            System.out.println("Je suis dans Ajouter Employé Controller !");
            System.out.println("Exception : " + ex.getMessage());
        }
    }

    public void handleConges(){
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/Conge.fxml"));
            Stage stage = (Stage) logOutButton.getScene().getWindow() ;
            stage.setScene(new Scene(loader.load()));
            stage.show();
        }catch(IOException ex){
            System.out.println("Je suis dans Ajouter Employé Controller !");
            System.out.println("Exception : " + ex.getMessage());
        }
    }

    public void handleArchives(){
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/Archive.fxml"));
            Stage stage = (Stage) logOutButton.getScene().getWindow() ;
            stage.setScene(new Scene(loader.load()));
            stage.show();
        }catch(IOException ex){
            System.out.println("Je suis dans Ajouter Employé Controller !");
            System.out.println("Exception : " + ex.getMessage());
        }
    }
}
