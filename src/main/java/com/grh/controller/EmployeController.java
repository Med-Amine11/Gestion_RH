package com.grh.controller;

import com.grh.service.UserService;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;

import java.io.IOException;

public class EmployeController {

    @FXML
    private Button logOutButton ;

    @FXML
    private Button EmployesButton ;

    @FXML
    private UserService userService ;

    public void SetUserService( UserService userService){
        this.userService = userService ;
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
            ex.printStackTrace();
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
            ex.printStackTrace();
        }
    }
    public void handleContrats(){
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/Contrat.fxml"));
            Stage stage = (Stage) EmployesButton.getScene().getWindow() ;
            stage.setScene(new Scene(loader.load()));
            stage.show();
        }catch(IOException ex){
            ex.printStackTrace();
        }
    }

    public void handleConges(){
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/Conge.fxml"));
            Stage stage = (Stage) EmployesButton.getScene().getWindow() ;
            stage.setScene(new Scene(loader.load()));
            stage.show();
        }catch(IOException ex){
            ex.printStackTrace();
        }
    }

    public void handleArchives(){
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/Archive.fxml"));
            Stage stage = (Stage) EmployesButton.getScene().getWindow() ;
            stage.setScene(new Scene(loader.load()));
            stage.show();
        }catch(IOException ex){
            ex.printStackTrace();
        }
    }
}
