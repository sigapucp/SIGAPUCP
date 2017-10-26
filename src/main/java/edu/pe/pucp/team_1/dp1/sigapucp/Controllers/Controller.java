/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.pe.pucp.team_1.dp1.sigapucp.Controllers;

import edu.pe.pucp.team_1.dp1.sigapucp.CustomEvents.IEvent;
import edu.pe.pucp.team_1.dp1.sigapucp.Models.RecursosHumanos.Usuario;
import edu.pe.pucp.team_1.dp1.sigapucp.Navegacion.ejecutarAccionArgs;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.Initializable;

/**
 *
 * @author herbert
 */
public class Controller implements Initializable {

    public Usuario usuarioActual = null;
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        //TODO
    }
    
    public IEvent<ejecutarAccionArgs> getActionEvent() {
        return null;
    }
    
    public void guardar() {
        System.out.println("Estoy creando");
    }
    
    public void nuevo() {
        System.out.println("Nuevo");
    }
    
    public void desactivar() {
        System.out.println("Desactivar");
    }
    
    public void setUsuarioActual(Usuario usuario)
    {
        usuarioActual = usuario;
    }
}
