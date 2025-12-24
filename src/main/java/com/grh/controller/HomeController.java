package com.grh.controller;

import com.grh.service.UserService;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;

import java.io.IOException;

public class HomeController {

  @FXML
    private Button logOutButton ;

  @FXML
    private UserService userService ;

  public void SetUserService( UserService userService){
      this.userService = userService ;
  }

  public void handleLogout(){
      try{
          FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/login.fxml"));
          Stage stage = (Stage) logOutButton.getScene().getWindow() ;
          stage.setScene(new Scene(loader.load()));
          stage.show();
      }catch(IOException ex){
          ex.printStackTrace();


      }
  }
}
