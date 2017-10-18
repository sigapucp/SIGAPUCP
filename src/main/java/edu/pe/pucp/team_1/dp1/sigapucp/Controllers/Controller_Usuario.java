package edu.pe.pucp.team_1.dp1.sigapucp.Controllers;

import edu.pe.pucp.team_1.dp1.botonimagen.BotonImagen;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class Controller_Usuario implements Initializable {

    private Controller_ContenidoPrincipal ventanaPrincipal;
    
    @Autowired
    private void setVentanaPrincipal(Controller_ContenidoPrincipal ventana) {
        this.ventanaPrincipal = ventana;
    }
    
    @FXML
    private void randomAction(ActionEvent event) {
        BotonImagen boton = (BotonImagen) event.getSource();
        String controller = boton.getTargetController();
        String path_contenido_busqueda = String.format("/fxml/%s/%sFormulario.fxml", controller, controller);

        ventanaPrincipal.renderizarPartial(path_contenido_busqueda, "detalle");
    }
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }
    
}