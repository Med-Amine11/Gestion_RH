package com.grh.controller;

import com.grh.config.AppContext;
import com.grh.service.DepartementService;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;
import javafx.scene.control.TableView;


import java.io.IOException;
import com.grh.config.AppContext ;
public class DepartementController {
    @FXML
    private Button logOutButton ;

    @FXML
    private Button DepartementsButton ;


    private DepartementService departementService ;

    public DepartementController(){
        departementService = AppContext.getDepartementService();
    }

    public void handleLogout() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/login.fxml"));
            Stage stage = (Stage) logOutButton.getScene().getWindow();
            stage.setScene(new Scene(loader.load()));
            stage.show();
        } catch (IOException ex) {
            System.out.println("Je suis dans Département Controller !");
            System.out.println("Exception : " + ex.getMessage());


        }
    }
    public void handleAccueil(){

        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/Home.fxml"));
            Stage stage = (Stage) DepartementsButton.getScene().getWindow() ;
            stage.setScene(new Scene(loader.load()));
            stage.show();
        }catch(IOException ex){
            System.out.println("Je suis dans Département Controller !");
            System.out.println("Exception : " + ex.getMessage());
        }
    }
    public void handleEmployes(){
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/Employe.fxml"));
            Stage stage = (Stage) DepartementsButton.getScene().getWindow() ;
            stage.setScene(new Scene(loader.load()));
            stage.show();
        }catch(IOException ex){
            System.out.println("Je suis dans Département Controller !");
            System.out.println("Exception : " + ex.getMessage());
        }
    }
    @FXML
    private TableView<com.grh.model.Departement> departementTable;
    @FXML
    private javafx.scene.control.TableColumn<com.grh.model.Departement, Integer> colId;
    @FXML
    private javafx.scene.control.TableColumn<com.grh.model.Departement, String> colNom;
    @FXML
    private javafx.scene.control.TableColumn<com.grh.model.Departement, String> colDescription;
    @FXML
    private javafx.scene.control.TextField addDepartementNom;
    @FXML
    private javafx.scene.control.TextArea addDepartementDescription;

    @FXML
    private javafx.scene.control.TableColumn<com.grh.model.Departement, Void> colActions;

    private com.grh.model.Departement selectedDepartement;

    public void initialize() {
        if (colId != null) {
            colId.setCellValueFactory(new javafx.scene.control.cell.PropertyValueFactory<>("id_departement"));
            colNom.setCellValueFactory(new javafx.scene.control.cell.PropertyValueFactory<>("nom"));
            colDescription.setCellValueFactory(new javafx.scene.control.cell.PropertyValueFactory<>("description"));

            // Add Actions Cell Factory
            if (colActions != null) {
                javafx.util.Callback<javafx.scene.control.TableColumn<com.grh.model.Departement, Void>, javafx.scene.control.TableCell<com.grh.model.Departement, Void>> cellFactory = new javafx.util.Callback<>() {
                    @Override
                    public javafx.scene.control.TableCell<com.grh.model.Departement, Void> call(final javafx.scene.control.TableColumn<com.grh.model.Departement, Void> param) {
                        return new javafx.scene.control.TableCell<>() {
                            private final Button btnEdit = new Button("Modifier");
                            private final Button btnDelete = new Button("Supprimer");

                            {
                                btnEdit.setOnAction(event -> {
                                    com.grh.model.Departement data = getTableView().getItems().get(getIndex());
                                    handleEditAction(data);
                                });
                                btnDelete.setOnAction(event -> {
                                    com.grh.model.Departement data = getTableView().getItems().get(getIndex());
                                    handleDeleteAction(data);
                                });
                                btnEdit.setStyle("-fx-background-color: #4caf50; -fx-text-fill: white; -fx-cursor: hand;");
                                btnDelete.setStyle("-fx-background-color: #f44336; -fx-text-fill: white; -fx-cursor: hand;");
                            }

                            @Override
                            public void updateItem(Void item, boolean empty) {
                                super.updateItem(item, empty);
                                if (empty) {
                                    setGraphic(null);
                                } else {
                                    javafx.scene.layout.HBox pane = new javafx.scene.layout.HBox(btnEdit, btnDelete);
                                    pane.setSpacing(10);
                                    setGraphic(pane);
                                }
                            }
                        };
                    }
                };
                colActions.setCellFactory(cellFactory);
            }

            try {
                loadDepartements();
            } catch (Exception e) {
                System.out.println("Error loading departments: " + e.getMessage());
                e.printStackTrace();
            }
        }
    }

    private void handleEditAction(com.grh.model.Departement departement) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/departement_add.fxml"));
            Scene scene = new Scene(loader.load());
            DepartementController controller = loader.getController();
            controller.initData(departement);
            Stage stage = (Stage) DepartementsButton.getScene().getWindow();
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void handleDeleteAction(com.grh.model.Departement departement) {
        javafx.scene.control.Alert alert = new javafx.scene.control.Alert(javafx.scene.control.Alert.AlertType.CONFIRMATION);
        alert.setTitle("Confirmation de suppression");
        alert.setHeaderText(null);
        alert.setContentText("Voulez-vous vraiment supprimer le département " + departement.getNom() + " ?");

        java.util.Optional<javafx.scene.control.ButtonType> result = alert.showAndWait();
        if (result.isPresent() && result.get() == javafx.scene.control.ButtonType.OK) {
            if (departementService.deleteDepartementById(departement.getId_departement())) {
                showAlert(javafx.scene.control.Alert.AlertType.INFORMATION, "Succès", "Département supprimé.");
                loadDepartements();
            } else {
                showAlert(javafx.scene.control.Alert.AlertType.ERROR, "Erreur", "Impossible de supprimer le département.");
            }
        }
    }

    // Method to initialize data for editing
    public void initData(com.grh.model.Departement departement) {
        this.selectedDepartement = departement;
        if (addDepartementNom != null) {
            addDepartementNom.setText(departement.getNom());
        }
        if (addDepartementDescription != null) {
            addDepartementDescription.setText(departement.getDescription());
        }
    }

    private void loadDepartements() {
        if (departementTable != null) {
            java.util.List<com.grh.model.Departement> list = departementService.getAllDepartements();
            javafx.collections.ObservableList<com.grh.model.Departement> observableList = javafx.collections.FXCollections.observableArrayList(list);
            departementTable.setItems(observableList);
        }
    }

    public void handleDismiss() {
        handleDepartement();
    }

    public void handleDefaultAndShowAddView() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/departement_add.fxml"));
            Stage stage = (Stage) DepartementsButton.getScene().getWindow();
            stage.setScene(new Scene(loader.load()));
            stage.show();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    public void saveDepartement() {
        String nom = addDepartementNom.getText();
        String description = addDepartementDescription.getText();

        if (nom == null || nom.trim().isEmpty()) {
            showAlert(javafx.scene.control.Alert.AlertType.ERROR, "Erreur", "Le nom du département ne peut pas être vide.");
            return;
        }

        com.grh.model.Departement existing = departementService.findDepartementByName(nom);

        // Special validation check for Update: if name exists and it's NOT the same department we are editing
        if (existing != null) {
            if (selectedDepartement == null) {
                // Adding new, so any existence is an error
                showAlert(javafx.scene.control.Alert.AlertType.ERROR, "Erreur", "Ce département existe déjà.");
                return;
            } else if (existing.getId_departement() != selectedDepartement.getId_departement()) {
                // Updating, but found ANOTHER department with this name
                showAlert(javafx.scene.control.Alert.AlertType.ERROR, "Erreur", "Ce département existe déjà.");
                return;
            }
        }

        if (selectedDepartement == null) {
            // Create New
            com.grh.model.Departement dep = new com.grh.model.Departement();
            dep.setNom(nom);
            dep.setDescription(description);

            if (departementService.addDepartement(dep)) {
                showAlert(javafx.scene.control.Alert.AlertType.INFORMATION, "Succès", "Département ajouté avec succès.");
                handleDepartement();
            } else {
                showAlert(javafx.scene.control.Alert.AlertType.ERROR, "Erreur", "Erreur lors de l'ajout du département.");
            }
        } else {
            // Update Existing
            selectedDepartement.setNom(nom);
            selectedDepartement.setDescription(description);

            if (departementService.updateDepartement(selectedDepartement)) {
                showAlert(javafx.scene.control.Alert.AlertType.INFORMATION, "Succès", "Département modifié avec succès.");
                handleDepartement();
            } else {
                showAlert(javafx.scene.control.Alert.AlertType.ERROR, "Erreur", "Erreur lors de la modification du département.");
            }
        }
    }

    private void showAlert(javafx.scene.control.Alert.AlertType alertType, String title, String message) {
        javafx.scene.control.Alert alert = new javafx.scene.control.Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    public void handleDepartement(){
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/Departement.fxml"));
            Stage stage = null;
            // Robust way to find the stage
            if (logOutButton != null && logOutButton.getScene() != null) {
                stage = (Stage) logOutButton.getScene().getWindow();
            } else if (DepartementsButton != null && DepartementsButton.getScene() != null) {
                stage = (Stage) DepartementsButton.getScene().getWindow();
            } else if (addDepartementNom != null && addDepartementNom.getScene() != null) {
                // For the Add View which doesn't have sidebar buttons sometimes
                stage = (Stage) addDepartementNom.getScene().getWindow();
            }

            if (stage != null) {
                stage.setScene(new Scene(loader.load()));
                stage.show();
            }
        } catch(IOException ex){
            System.out.println("Je suis dans Département Controller !");
            System.out.println("Exception : " + ex.getMessage());
            ex.printStackTrace();
        }
    }
    public void handleContrats(){
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/Contrat.fxml"));
            Stage stage = (Stage) DepartementsButton.getScene().getWindow() ;
            stage.setScene(new Scene(loader.load()));
            stage.show();
        }catch(IOException ex){
            System.out.println("Je suis dans Département Controller !");
            System.out.println("Exception : " + ex.getMessage());
        }
    }

    public void handleConges(){
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/Conge.fxml"));
            Stage stage = (Stage) DepartementsButton.getScene().getWindow() ;
            stage.setScene(new Scene(loader.load()));
            stage.show();
        }catch(IOException ex){
            ex.printStackTrace();
        }
    }

    public void handleArchives(){
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/Archive.fxml"));
            Stage stage = (Stage) DepartementsButton.getScene().getWindow() ;
            stage.setScene(new Scene(loader.load()));
            stage.show();
        }catch(IOException ex){
            ex.printStackTrace();
        }
    }
}
