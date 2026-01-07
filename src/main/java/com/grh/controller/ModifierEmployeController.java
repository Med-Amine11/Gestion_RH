package com.grh.controller;

import com.grh.model.Departement;
import com.grh.model.Employe;
import com.grh.service.DepartementService;
import com.grh.service.EmployeService;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableArray;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;
import lombok.Setter;
import com.grh.config.AppContext ;
import java.io.IOException;
import java.time.LocalDate;
import java.time.Period;
import java.util.List;

public class ModifierEmployeController {
    @FXML
    private TextField txtNomEmploye;
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

    @FXML
    Label lblErreurEmploye ;
    @FXML private Button btnModifierEmploye ;
    @FXML private Button btnAnnulerEmploye ;

    @Setter
    private EmployeService employeService ;

    Employe employe ;
    @Setter
    private DepartementService departementService ;
    @Setter
    private List<Employe> employes ;
    @FXML
    private Button logOutButton ;

    public  ModifierEmployeController(){

        employeService = AppContext.getEmployeService() ;
        departementService = AppContext.getDepartementService() ;
    }
    public void setEmploye(Employe employe){
        this.employe = employe ;

        if(employe == null) return ;

        txtNomEmploye.setText(employe.getNom()) ;
        txtPrenomEmploye.setText(employe.getPrenom()) ;
        txtCinEmploye.setText(employe.getCin());
        txtAdresseEmploye.setText(employe.getAdresse());
        txtEmailEmploye.setText(employe.getEmail());
        txtTelephoneEmploye.setText(employe.getTelephone());
        txtSalaireEmploye.setText(String.valueOf(employe.getSalaire()));
        dpDateNaissanceEmploye.setValue(LocalDate.parse(employe.getDate_naissance()));
        dpDateEmbaucheEmploye.setValue(LocalDate.parse(employe.getDate_recrutement()));
        cmbPosteEmploye.setValue(employe.getPoste());
        cmbDepartementEmploye.setValue(departementService.findDepartementByName(employe.getNom_departement()));
    }
    @FXML
    public void initialize(){
        List<Departement> departements = departementService.getAllDepartements() ;
        cmbDepartementEmploye.setItems(FXCollections.observableArrayList(departements));
        ObservableList postes = FXCollections.observableArrayList("Développeur",
                "Responsable RH",
                "Comptable");
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
    public void modifierEmploye(){
        System.out.println(employe);
        int id = employe.getId_employe() ;
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
        if (employeService.employeExisteParNomDifferentId(employes , nom , prenom , id)) {
            setErreurEmploye("Un employé avec le même nom et prénom existe déjà.");
            return;
        }

        // CIN
        if (cin.isEmpty()) {
            setErreurEmploye("Le CIN est obligatoire.");
            return;
        }
        // Existence CIN (APRÈS validation)
        if (employeService.employeExisteParCinDifferentId(employes , cin , id)) {
            setErreurEmploye("Un employé avec le même cin existe déjà.");
            return;
        }
        // Email
        if (email.isEmpty()) {
            setErreurEmploye("L'email est obligatoire.");
            return;
        }
        // Existence EMAIL (APRÈS validation)
        if (employeService.employeExisteParEmailDifferentId(employes,email,id)) {
            setErreurEmploye("Un employé avec le même email existe déjà.");
            return;
        }
        // Téléphone
        if (telephone.isEmpty()) {
            setErreurEmploye("Le téléphone est obligatoire.");
            return;
        }
        // Existence TELEPHONE (APRÈS validation)
        if (employeService.employeExisteParTelephoneDifferentId(employes,telephone,id)) {
            setErreurEmploye("Un employé avec le même telephone existe déjà.");
            return;
        }
        // Adresse
        if (adresse.isEmpty()) {
            setErreurEmploye("L'adresse est obligatoire.");
            return;
        }
        // Existence ADRESSE (APRÈS validation)
        if (employeService.employeExisteParAdresseDifferentId(employes , adresse , id)) {
            setErreurEmploye("Un employé avec la même adresse existe déjà.");
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


        employe.setNom(nom);
        employe.setPrenom(prenom);
        employe.setCin(cin);
        employe.setAdresse(adresse);
        employe.setEmail(email);
        employe.setPoste(poste);
        employe.setTelephone(telephone);
        employe.setId_departement(departement.getId_departement());
        employe.setSalaire(salaire);
        employe.setDate_naissance(String.valueOf(dateNaissance));
        employe.setDate_recrutement(String.valueOf(dateEmbauche));

        System.out.println(employe);
        System.out.println(employeService.employeExiste(employes ,employe));
        if(!employeService.employeExiste(employes ,employe)){


        if(!employeService.updateEmploye(employe)) {
            setErreurEmploye("Une erreur est survenue lors de la modification de l'employé");
            return;
        }}
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
            System.out.println("Je suis dans Modifier Employé Controller !");
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
            System.out.println("Je suis dans Modifier Employé Controller !");
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
            System.out.println("Je suis dans Modifier Employé Controller !");
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
            System.out.println("Je suis dans Modifier Employé Controller !");
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
            System.out.println("Je suis dans Modifier Employé Controller !");
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
            System.out.println("Je suis dans Modifier Employé Controller !");
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
            System.out.println("Je suis dans Modifier Employé Controller !");
            System.out.println("Exception : " + ex.getMessage());
        }
    }
}
