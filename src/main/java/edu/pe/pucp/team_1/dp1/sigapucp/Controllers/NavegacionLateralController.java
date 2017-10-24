package edu.pe.pucp.team_1.dp1.sigapucp.Controllers;

import edu.pe.pucp.team_1.dp1.sigapucp.CustomEvents.Event;
import edu.pe.pucp.team_1.dp1.sigapucp.CustomEvents.IEvent;
import edu.pe.pucp.team_1.dp1.sigapucp.Navegacion.abrirDetallesArgs;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Accordion;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;

public class NavegacionLateralController extends Controller {
    
    public IEvent<abrirDetallesArgs> abrirDetalle;
    
    @FXML
    private Accordion acordion_modulos;
    
    @FXML
    private void abrirBusquedaModulo(ActionEvent event) {
        Button boton = (Button) event.getSource();
        VBox contenedor = (VBox) boton.getChildrenUnmodifiable().get(0);
        Label label = (Label) contenedor.getChildrenUnmodifiable().get(1);
        String controller = label.getText();
        String modulo = acordion_modulos.getExpandedPane().getText();
        
        abrirDetallesArgs args = new abrirDetallesArgs();
        args.setNombreController(controller);
        args.setNombreModulo(modulo);
        
        abrirDetalle.fire(this, args);
    }
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        abrirDetalle = new Event<>();
    }    
    
}
