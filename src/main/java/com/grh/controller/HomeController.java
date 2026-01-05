package com.grh.controller;

import com.grh.service.EmployeService;
import com.grh.service.DepartementService;
import com.grh.service.CongeService;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.Stage;
import com.grh.config.AppContext ;
import lombok.Setter;

import java.io.IOException;

public class HomeController {

  @FXML
    private Button logOutButton ;

  @FXML
  private Button AccueilButton ;

  @FXML
  private Label TotalEmployes  ;
  @Setter
  private EmployeService employeService ;
  @Setter
  private DepartementService departementService ;
  @Setter
  private CongeService congeService ;

    public HomeController(){
        employeService = AppContext.getEmployeService();
        departementService = AppContext.getDepartementService() ;
        congeService = AppContext.getCongeService() ;
    }
    @FXML
    public void initialize(){
        TotalEmployes.setText(String.valueOf(employeService.countAllEmployes()) ) ;
    }
  public void handleLogout() {
      try {
          FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/login.fxml"));
          Stage stage = (Stage) logOutButton.getScene().getWindow();
          stage.setScene(new Scene(loader.load()));
          stage.show();
      } catch (IOException ex) {
          System.out.println("Je suis dans Home Controller !");
          System.out.println("Exception : " + ex.getMessage());
      }
  }
  public void handleAccueil(){

  }
  public void handleEmployes(){
      try {
          FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/Employe.fxml"));
          Stage stage = (Stage) AccueilButton.getScene().getWindow() ;
          stage.setScene(new Scene(loader.load()));
          stage.show();
      }catch(IOException ex){
          System.out.println("Je suis dans Home Controller !");
          System.out.println("Exception : " + ex.getMessage());
      }
  }
  public void handleDepartement(){
      try {
          FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/Departement.fxml"));
          Stage stage = (Stage) AccueilButton.getScene().getWindow() ;
          stage.setScene(new Scene(loader.load()));
          stage.show();
      }catch(IOException ex){
          System.out.println("Je suis dans Home Controller !");
          System.out.println("Exception : " + ex.getMessage());
      }
  }
  public void handleContrats(){
      try {
          FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/Contrat.fxml"));
          Stage stage = (Stage) AccueilButton.getScene().getWindow() ;
          stage.setScene(new Scene(loader.load()));
          stage.show();
      }catch(IOException ex){
          System.out.println("Je suis dans Home Controller !");
          System.out.println("Exception : " + ex.getMessage());
      }
  }

  public void handleConges(){
      try {
          FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/Conge.fxml"));
          Stage stage = (Stage) AccueilButton.getScene().getWindow() ;
          stage.setScene(new Scene(loader.load()));
          stage.show();
      }catch(IOException ex){
          System.out.println("Je suis dans Home Controller !");
          System.out.println("Exception : " + ex.getMessage());
      }
  }

  public void handleArchives(){
      try {
          FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/Archive.fxml"));
          Stage stage = (Stage) AccueilButton.getScene().getWindow() ;
          stage.setScene(new Scene(loader.load()));
          stage.show();
      }catch(IOException ex){
          System.out.println("Je suis dans Home Controller !");
          System.out.println("Exception : " + ex.getMessage());
      }
  }
}
