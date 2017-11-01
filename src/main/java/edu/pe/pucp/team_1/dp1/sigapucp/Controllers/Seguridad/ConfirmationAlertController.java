/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.pe.pucp.team_1.dp1.sigapucp.Controllers.Seguridad;

import java.util.Optional;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.image.Image;
import javafx.stage.Stage;

/**
 *
 * @author Jauma
 */
public class ConfirmationAlertController {
    private Stage stage;
    private Alert alert;
    public ConfirmationAlertController()
    {
        System.out.println("AQUI VA LA PANTALLA DE CONFIRMACION");                       
       
    }
    
    public boolean show(String descripcion,String pregunta)
    {     
        alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("SIGAPUCP - Confirmacion");              
        stage = (Stage) alert.getDialogPane().getScene().getWindow();            
        stage.getIcons().add(new Image(this.getClass().getResource("/fxml/Imagenes/fork_lift_icon.png").toString()));     
        alert.setHeaderText(descripcion);
        alert.setContentText(pregunta);
        
        Optional<ButtonType> result = alert.showAndWait();
        return result.get() == ButtonType.OK;       
    }
}
