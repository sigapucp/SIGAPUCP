/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.pe.pucp.team_1.dp1.sigapucp.Controllers.Despachos;

import edu.pe.pucp.team_1.dp1.sigapucp.Controllers.Controller;
import edu.pe.pucp.team_1.dp1.sigapucp.Models.Despachos.TestRouting;
import edu.pe.pucp.team_1.dp1.sigapucp.Models.RecursosHumanos.Menu;
import java.io.FileNotFoundException;
import java.io.UnsupportedEncodingException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.fxml.Initializable;

/**
 * FXML Controller class
 *
 * @author Jauma
 */
public class SimulacionesController extends Controller{

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        TestRouting test = new TestRouting();
        try {
            test.runTest();
        } catch (FileNotFoundException ex) {
            Logger.getLogger(SimulacionesController.class.getName()).log(Level.SEVERE, null, ex);
        } catch (UnsupportedEncodingException ex) {
            Logger.getLogger(SimulacionesController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }    
    @Override
    public Menu.MENU getMenu()
    {
        return Menu.MENU.Simulaciones;
    }
}
