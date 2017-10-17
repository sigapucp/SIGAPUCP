package edu.pe.pucp.team_1.dp1.sigapucp.Controllers;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.layout.AnchorPane;
import org.springframework.stereotype.Component;

@Component
public class Controller_ContenidoPrincipal implements Initializable {

    @FXML private AnchorPane contenedor_busqueda;
    @FXML private AnchorPane contenedor_detalle;
    @FXML private AnchorPane contenedor_botones_acciones;
    
    public AnchorPane seleccionarContenedor(String tipo) {
        switch(tipo){
            case "busqueda":
                return contenedor_busqueda;
            case "detalle":
                return contenedor_detalle;
            case "acciones":
                return contenedor_botones_acciones;
            default:
                break;
        }
        return null;
    }
    
    public void renderizarPartial(String path, String tipo) {
        AnchorPane contenedor = seleccionarContenedor(tipo);
        if (contenedor != null) {
            try {
                AnchorPane contenido = FXMLLoader.load(getClass().getResource(path));
                contenedor.getChildren().setAll(contenido);
            } catch (IOException ex) {
                Logger.getLogger(Controller_ContenidoPrincipal.class.getName()).log(Level.SEVERE, null, ex);
            }   
        }
    }
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    
    
}
