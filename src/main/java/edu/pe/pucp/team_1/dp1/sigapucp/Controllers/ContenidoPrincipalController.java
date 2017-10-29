package edu.pe.pucp.team_1.dp1.sigapucp.Controllers;

import edu.pe.pucp.team_1.dp1.sigapucp.CustomEvents.IEvent;
import edu.pe.pucp.team_1.dp1.sigapucp.Navegacion.ejecutarAccionArgs;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.layout.AnchorPane;

public class ContenidoPrincipalController extends Controller {

    @FXML private AnchorPane contenedor_modulos_contenido;
    @FXML private AnchorPane contenedor_botones_acciones;
    @FXML private NavegacionLateralController NavegacionLateralController;
    private Controller activeController;
    
    private void seleccionarAccionRealizar(String nombre, Controller controller) {
        switch(nombre){
            case "Guardar":
                controller.guardar();
                break;
            case "Nuevo":
                controller.nuevo();
                break;
            case "Desactivar":
                controller.desactivar();
            default:
                break;
        }
    }
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        activeController = new Controller();

        NavegacionLateralController.abrirDetalle.addHandler((sender, args) -> {
            try {
                // Cargar el contenido de las pantallas
                System.out.println(args.getPathContenido());
                FXMLLoader loaderContenido = new FXMLLoader(getClass().getResource(args.getPathContenido()));
                AnchorPane contenido = (AnchorPane) loaderContenido.load();
                Controller controller = loaderContenido.getController();
                System.out.println(contenido);
                System.out.println(loaderContenido);    
                activeController = loaderContenido.<Controller>getController();
                activeController.setUsuarioActual(usuarioActual);
                contenedor_modulos_contenido.getChildren().setAll(contenido);
                
                // Cargar el contenido de los botones de accion
                FXMLLoader loaderAcciones = new FXMLLoader(getClass().getResource(args.getPathBotonesAcciones()));
                AnchorPane contenidoAcciones = (AnchorPane) loaderAcciones.load();
                Controller controllerAcciones = loaderAcciones.<Controller>getController();
                IEvent<ejecutarAccionArgs> evento =  controllerAcciones.getActionEvent();
                evento.addHandler((accionSender, accionArgs) -> {
                    seleccionarAccionRealizar(accionArgs.getAccion(), activeController);
                });
                contenedor_botones_acciones.getChildren().setAll(contenidoAcciones);
            } catch (IOException ex) {
                Logger.getLogger(ContenidoPrincipalController.class.getName()).log(Level.SEVERE, null, ex);
            }
        });
    }
}
