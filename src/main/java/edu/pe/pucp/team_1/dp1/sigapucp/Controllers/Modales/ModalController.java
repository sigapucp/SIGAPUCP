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

/**
 *
 * @author herbert
 */
public class ModalController extends Controller {
    public IEvent<abrirModalPromoArgs> abrirModal;
    
    public ModalController() {
        abrirModal = new Event<>();
    }
}
