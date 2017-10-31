/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.pe.pucp.team_1.dp1.sigapucp.Controllers.RecursosHumanos;

import edu.pe.pucp.team_1.dp1.sigapucp.Controllers.Controller;
import edu.pe.pucp.team_1.dp1.sigapucp.Controllers.Seguridad.ConfirmationAlertController;
import edu.pe.pucp.team_1.dp1.sigapucp.Controllers.Seguridad.InformationAlertController;
import edu.pe.pucp.team_1.dp1.sigapucp.Models.RecursosHumanos.Menu;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.control.TitledPane;
import org.javalite.activejdbc.Base;

/**
 * FXML Controller class
 *
 * @author Jauma
 */
public class CuentaController extends Controller{

    @FXML
    private TitledPane TitlePane;
    @FXML
    private TextField VerNombre;
    @FXML
    private TextField VerApellido;
    @FXML
    private TextField VerTelefono;
    @FXML
    private TextField VerCorreo;
    @FXML
    private TextField VerRol;
    @FXML
    private Label LabelUsuario;
    @FXML
    private TitledPane TitlePane1;
    @FXML
    private PasswordField VerContrasenaActual;
    @FXML
    private PasswordField VerContrasenaNuevaRepetir;
    @FXML
    private PasswordField VerContrasenaNueva;
    
    private Boolean revelarActual;
    private Boolean revelarNueva;

    /**
     * Initializes the controller class.
     */  
    
    private InformationAlertController infoController;
    private ConfirmationAlertController confirmationController;
    @FXML
    private TextField VerTextContrasenaActual;
    @FXML
    private TextField VerTextContrasenaNueva;
    
    public CuentaController()
    {
        if(!Base.hasConnection()) Base.open("org.postgresql.Driver", "jdbc:postgresql://200.16.7.146/sigapucp_db_admin", "sigapucp", "sigapucp");       
        infoController = new InformationAlertController();
        confirmationController = new ConfirmationAlertController();
    }

    @FXML
    private void revelarActual(ActionEvent event) {
        if(!revelarActual)
        {            
            VerContrasenaActual.setVisible(false);
            VerTextContrasenaActual.setText(VerContrasenaActual.getText());
            VerTextContrasenaActual.setVisible(true);                      
        }else
        {
            VerContrasenaActual.setVisible(true);
            VerContrasenaActual.setText(VerTextContrasenaActual.getText());            
            VerTextContrasenaActual.setVisible(false);            
        }
        revelarActual = !revelarActual;
    }

    @FXML
    private void revelarNueva(ActionEvent event) {
        if(!revelarNueva)
        {            
            VerContrasenaNueva.setVisible(false);
            VerTextContrasenaNueva.setText(VerContrasenaNueva.getText());
            VerTextContrasenaNueva.setVisible(true);                        
        }else
        {
            VerContrasenaNueva.setVisible(true);
            VerContrasenaNueva.setText(VerTextContrasenaNueva.getText());            
            VerTextContrasenaNueva.setVisible(false);            
        }   
        revelarNueva = !revelarNueva;
    }

    @FXML
    private void guardarCuenta(ActionEvent event) {
        try {
            
            Base.openTransaction();
            String nombre = VerNombre.getText();
            String apellido = VerApellido.getText();
            String telef = VerTelefono.getText();
            String correo = VerCorreo.getText();

            String contrasenaNueva = VerContrasenaNueva.getText();
            String contrasenaNuevaRepetir = VerContrasenaNuevaRepetir.getText();
            
            if(!contrasenaNueva.isEmpty())
            {
                if(contrasenaNueva.equals(contrasenaNuevaRepetir))
                {
                    if(confirmationController.show("Esta accion cambiara su contraseña", "¿Esta seguro que desea continuar?"))
                    {
                        usuarioActual.set("contrasena_encriptada",contrasenaNueva);
                    }
                }
            }                        
            
            usuarioActual.set("nombre",nombre);
            usuarioActual.set("apellido",apellido);
            usuarioActual.set("email",correo);
            usuarioActual.set("telefono",telef);
            
            usuarioActual.saveIt();            
            infoController.show("Sus datos han sido actualizados satisfactoriamente");     
            Base.commitTransaction();
        } catch (Exception e) {
            Base.rollbackTransaction();
            infoController.show("No se pudo guardar los datos: " + e.getMessage());            
        }
    }
    
    @Override
    public Menu.MENU getMenu()
    {
        return Menu.MENU.Cuenta;
    }
    
    @Override
    public void postInitialize(String gfxmlPath)
    {
        fxmlPath = gfxmlPath;
         try {            
            LabelUsuario.setText("Usuario: " + usuarioActual.getString("usuario_cod"));
            VerNombre.setText(usuarioActual.getString("nombre"));
            VerApellido.setText(usuarioActual.getString("apellido"));
            VerCorreo.setText(usuarioActual.getString("email"));            
            VerTelefono.setText(usuarioActual.getString("telefono"));
            VerRol.setText(usuarioActual.getRol().getString("nombre"));
            VerContrasenaActual.setText(usuarioActual.getString("contrasena_encriptada"));  
            
            revelarActual = false;
            revelarNueva = false;
            
            VerContrasenaActual.setVisible(true);     
            VerContrasenaActual.setEditable(false);
            VerTextContrasenaActual.setVisible(false);     
            VerTextContrasenaActual.setEditable(false);
            
            VerContrasenaNueva.setVisible(true);            
            VerTextContrasenaNueva.setVisible(false);                                 
        } catch (Exception e) {
            infoController.show("Error al cargar informacion de usuario: " + e.getMessage());            
        }        
    }
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
       
    }    
    
}
