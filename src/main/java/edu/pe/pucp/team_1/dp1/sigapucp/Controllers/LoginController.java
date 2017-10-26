/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.pe.pucp.team_1.dp1.sigapucp.Controllers;

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
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import org.javalite.activejdbc.Base;

public class LoginController implements Initializable{
    private Boolean login_exitoso;
    private ErrorAlertController errorController;
    
    @FXML private TextField usuario_login;
    @FXML private TextField usuario_contrasenha;
    
    public LoginController()
    {
        if(!Base.hasConnection()) Base.open("org.postgresql.Driver", "jdbc:postgresql://200.16.7.146/sigapucp_db_admin", "sigapucp", "sigapucp");
    }

    @FXML
    private void handleButtonAction(ActionEvent event) throws IOException {
        System.out.println("inicio");
        if (true){
        //if ( login_exitoso = Usuario.autenticacion(usuario_login.getText(), usuario_contrasenha.getText()) ) {       
            Usuario usuarioActual = Usuario.findFirst("email = ? AND contrasena_encriptada = ?", usuario_login.getText(),usuario_contrasenha.getText());
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/ContenidoPrincipal.fxml"));
            ContenidoPrincipalController mainController = new ContenidoPrincipalController();
            mainController.setUsuarioActual(usuarioActual);
            loader.setController(mainController);           
            Scene main_content_scene = new Scene((Parent)loader.load());
            Stage app_stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            app_stage.setScene(main_content_scene);
            if(Base.hasConnection()) Base.close();
            app_stage.show();
        }else {
            errorController = new ErrorAlertController();
            errorController.show("El usuario o contraseña es incorrecto", "Error Code");
        }
    }
    
    @FXML
    private void abrirOlvidarContrasenha(ActionEvent event) throws IOException{
        //Cambio de toda la escena del login hacia el olvidar contraseña
        Parent main_content_parent = FXMLLoader.load(getClass().getResource("/fxml/Seguridad/RecuperarContrasenha.fxml"));
        Scene main_content_scene = new Scene(main_content_parent);
        Stage app_stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        app_stage.setScene(main_content_scene);
        app_stage.show();
    }
    
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        //TODO
        login_exitoso = false;
    }
}
