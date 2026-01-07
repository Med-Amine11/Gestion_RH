package com.grh.controller;

import  com.grh.config.AppContext ;
import com.grh.config.AppContext;
import com.grh.model.User;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import com.grh.service.UserService ;
import javafx.stage.Stage;
import lombok.Setter;

import java.io.IOException;


public class UserController {

    @FXML
    private TextField emailField;
    @FXML
    private PasswordField passwordField;
    @FXML
    private Label errorLabel;
    @FXML
    private Button loginButton;
    @Setter
    private UserService userService ;

    public UserController(){
        userService = AppContext.getUserService() ;
    }
    @FXML
    public void handleLogin(){
       String email = emailField.getText();
       String password = passwordField.getText();


        User user = userService.login(email,password) ;

        if(user != null){
            try{
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/Home.fxml"));
            Parent root = loader.load() ;
            Stage stage = (Stage) loginButton.getScene().getWindow() ;
            stage.setScene(new Scene(root));
            stage.show();
            }
            catch(IOException ex){
                System.out.println("Je suis dans User Controller !");
                System.out.println("Exception : " + ex.getMessage());
            }
        }
        else{
            errorLabel.setText("Email ou mot de passe incorrect");
            errorLabel.setVisible(true);
        }

    }

}