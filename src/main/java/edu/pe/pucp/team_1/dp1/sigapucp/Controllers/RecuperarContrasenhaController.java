/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.pe.pucp.team_1.dp1.sigapucp.Controllers;

import edu.pe.pucp.team_1.dp1.sigapucp.Controllers.Seguridad.ConfirmationAlertController;
import edu.pe.pucp.team_1.dp1.sigapucp.Controllers.Seguridad.ErrorAlertController;
import edu.pe.pucp.team_1.dp1.sigapucp.Models.RecursosHumanos.Usuario;
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
import javafx.scene.control.TextField;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author alulab14
 */
public class RecuperarContrasenhaController implements Initializable {

    private Boolean envio_exitoso;
    @FXML private TextField usuario_email;
    private ErrorAlertController errorController;
    private ConfirmationAlertController confirmationController;
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        envio_exitoso = false;
    }    
    
    @FXML
    public void regresarLogin(ActionEvent event) throws IOException{
        Parent main_content_parent = FXMLLoader.load(getClass().getResource("/fxml/Seguridad/Login.fxml"));
        Scene main_content_scene = new Scene(main_content_parent);
        Stage app_stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        app_stage.setScene(main_content_scene);
        app_stage.show();
    }
    
    @FXML
    public void enviarContrasenha(ActionEvent event){
        try{
            if ( envio_exitoso = Usuario.enviaCorreo(usuario_email.getText())) {
                confirmationController = new ConfirmationAlertController();
                confirmationController.show("En breve le llegará un correo con instrucciones para reestablecer su contraseña", "Error Code");
            }else {
                errorController = new ErrorAlertController();
                errorController.show("La contraseña ingresada no se encuentra registrada", "Error Code");
            }            
        }catch(Exception e)
        {
            System.out.println("Cant load new window");
        }
    }
    
}
