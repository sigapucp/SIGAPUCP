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
public class WarningAlertController {
    private Stage stage;
    private Alert alert;
    public WarningAlertController()
    {
        System.out.println("AQUI VA LA PANTALLA DE WARNING");                       
        alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle("SIGAPUCP - Alerta");              
        stage = (Stage) alert.getDialogPane().getScene().getWindow();            
        stage.getIcons().add(new Image(this.getClass().getResource("/fxml/Imagenes/fork_lift_icon.png").toString()));     
    }
    
    public void show(String tituloAlerta,String explicacionAlerta)
    {           
        alert.setHeaderText(tituloAlerta);
        alert.setContentText(explicacionAlerta);
        stage.showAndWait();
    }    
}
