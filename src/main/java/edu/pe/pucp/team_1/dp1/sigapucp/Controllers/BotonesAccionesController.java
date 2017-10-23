/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.pe.pucp.team_1.dp1.sigapucp.Controllers;

import edu.pe.pucp.team_1.dp1.sigapucp.CustomEvents.Event;
import edu.pe.pucp.team_1.dp1.sigapucp.CustomEvents.IEvent;
import edu.pe.pucp.team_1.dp1.sigapucp.Navegacion.ejecutarAccionArgs;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;

/**
 *
 * @author herbert
 */
public class BotonesAccionesController extends Controller {

    private IEvent<ejecutarAccionArgs> ejecutarAccionController;
    
    @FXML
    public void actionHumo(ActionEvent event) {
        ejecutarAccionArgs args = new ejecutarAccionArgs();
        Button accionButton = (Button) event.getSource();
        String accion = accionButton.getText();
        
        args.setAccion(accion);
        ejecutarAccionController.fire(this, args);
    }
    

    @Override
    public IEvent<ejecutarAccionArgs> getActionEvent() {
        return ejecutarAccionController;
    }
    
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        ejecutarAccionController = new Event<>();
    }
    
}
