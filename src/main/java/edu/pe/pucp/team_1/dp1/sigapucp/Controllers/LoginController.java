/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.pe.pucp.team_1.dp1.sigapucp.Controllers;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.stage.Stage;
import org.springframework.stereotype.Component;

/**
 *
 * @author Alberto Chang Lopez
 */
@Component
public class LoginController implements Initializable{
    
    @FXML
    private Label label;
    
    @FXML
    private void handleButtonAction(ActionEvent event) throws IOException {
        System.out.println("Isiiiiiiiiiiiiiiiiiiiiiiiiii");
        Parent main_content_parent = FXMLLoader.load(getClass().getResource("/fxml/MainContent.fxml"));
        Scene main_content_scene = new Scene(main_content_parent);
        Stage app_stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        app_stage.setScene(main_content_scene);
        app_stage.show();
    }
    
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        //TODO
    }
    
    
}