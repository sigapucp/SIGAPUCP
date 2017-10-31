/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.pe.pucp.team_1.dp1.sigapucp.Controllers.Modales;

import edu.pe.pucp.team_1.dp1.sigapucp.Controllers.Controller;
import edu.pe.pucp.team_1.dp1.sigapucp.CustomEvents.Event;
import edu.pe.pucp.team_1.dp1.sigapucp.CustomEvents.IEvent;
import edu.pe.pucp.team_1.dp1.sigapucp.Navegacion.abrirModalPromoArgs;
import javafx.stage.Stage;

/**
 *
 * @author herbert
 */
public class ModalController extends Controller {
    public IEvent<abrirModalPromoArgs> abrirModal;
    private Stage current_stage;
    
    public ModalController() {
        abrirModal = new Event<>();
    }
    
    public void setCurrentStage(Stage stage) {
        current_stage = stage;
    }
    
    public Stage getCurrentStage() {
        return current_stage;
    }
}
