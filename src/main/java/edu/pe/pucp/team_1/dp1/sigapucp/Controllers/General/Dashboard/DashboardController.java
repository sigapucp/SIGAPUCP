/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.pe.pucp.team_1.dp1.sigapucp.Controllers.General.Dashboard;

import edu.pe.pucp.team_1.dp1.sigapucp.Controllers.Controller;
import edu.pe.pucp.team_1.dp1.sigapucp.Models.RecursosHumanos.Menu;
import java.net.URL;
import java.util.ResourceBundle;


/**
 *
 * @author Hugo
 */
public class DashboardController extends Controller{

    @Override
    public void initialize(URL location, ResourceBundle resources) {
            //TODO
    }
    
    @Override
    public Menu.MENU getMenu(){
        return Menu.MENU.Dashboard;
    }
}
