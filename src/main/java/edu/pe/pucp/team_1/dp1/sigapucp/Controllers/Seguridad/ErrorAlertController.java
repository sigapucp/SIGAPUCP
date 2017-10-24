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
public class ErrorAlertController {
    private Stage stage;
    private Alert alert;
    public ErrorAlertController()
    {
        System.out.println("AQUI VA LA PANTALLA DE ERROR");                       
        alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("SIGAPUCP - Error");              
        stage = (Stage) alert.getDialogPane().getScene().getWindow();            
        stage.getIcons().add(new Image(this.getClass().getResource("/fxml/Imagenes/fork_fit_icon.png").toString()));     
    }
    
    public void show(String descripcionError,String codigoError)
    {           
        alert.setHeaderText(descripcionError);
        alert.setContentText(codigoError);
        stage.showAndWait();
    }
}
