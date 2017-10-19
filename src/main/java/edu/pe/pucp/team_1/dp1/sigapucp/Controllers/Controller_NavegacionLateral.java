package edu.pe.pucp.team_1.dp1.sigapucp.Controllers;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class Controller_NavegacionLateral implements Initializable {

    private EventosPersonalizados evento;
    
    @FXML
    private void abrirBusquedaModulo(ActionEvent event) {
        Button boton = (Button) event.getSource();
        String controller = boton.getText();
        String path_contenido_busqueda = String.format("/fxml/%s/%sVista.fxml", controller, controller);
        
        
//        ventanaPrincipal.renderizarPartial(path_contenido_busqueda, "busqueda");
    }
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    
    
}
