/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.pe.pucp.team_1.dp1.sigapucp.Navegacion;

import edu.pe.pucp.team_1.dp1.sigapucp.CustomEvents.EventArgs;
import edu.pe.pucp.team_1.dp1.sigapucp.Models.RecursosHumanos.Menu;

/**
 *
 * @author Jauma
 */
public class cambiarMenuArgs extends EventArgs{
    public int related_id;
    public Menu.MENU menu;
    public String nombreModulo;    
    public String fxmlPath;
    public String fxmlBotones;
    public cambiarMenuArgs(Menu.MENU gMenu,String gNombreModulo,int grelated_id)
    {
        related_id = grelated_id;
        menu = gMenu;
        nombreModulo = gNombreModulo;       
        fxmlPath = String.format("/fxml/%s/%s/%s.fxml", gNombreModulo,gMenu.name(),gMenu.name());
        fxmlBotones =  String.format("/fxml/%s/%s/%sBotones.fxml", gNombreModulo, gMenu.name(), gMenu.name());
    }
}
