/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.pe.pucp.team_1.dp1.sigapucp.Controllers.Seguridad;

import javafx.scene.control.Alert;
import javafx.scene.image.Image;
import javafx.stage.Stage;

/**
 *
 * @author Jauma
 */
public class InformationAlertController {
    private Stage stage;
    private Alert alert;
    public InformationAlertController()
    {
        System.out.println("AQUI VA LA PANTALLA DE INFORMACION");                       
        alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("SIGAPUCP - Informacion");              
        stage = (Stage) alert.getDialogPane().getScene().getWindow();            
        stage.getIcons().add(new Image(this.getClass().getResource("/fxml/Imagenes/fork_fit_icon.png").toString()));     
        alert.setHeaderText(null);
    }
    
    public void show(String mensaje)
    {                   
        alert.setContentText(mensaje);
        stage.showAndWait();
    }    
}
