/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.pe.pucp.team_1.dp1.sigapucp.Controllers.Ventas;

import edu.pe.pucp.team_1.dp1.sigapucp.Controllers.Controller;
import edu.pe.pucp.team_1.dp1.sigapucp.Models.RecursosHumanos.Menu;
import org.javalite.activejdbc.Base;

/**
 *
 * @author a20103656
 */
public class GuiasDeRemisionController extends Controller{
    public GuiasDeRemisionController(){
        if(!Base.hasConnection()) Base.open("org.postgresql.Driver", "jdbc:postgresql://200.16.7.146/sigapucp_db_admin", "sigapucp", "sigapucp");
  
    }
    
    @Override
    public Menu.MENU getMenu(){
        return Menu.MENU.GuiaDeRemision;
    }
}
