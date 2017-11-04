package edu.pe.pucp.team_1.dp1.sigapucp.Controllers;

import edu.pe.pucp.team_1.dp1.sigapucp.Controllers.Seguridad.WarningAlertController;
import edu.pe.pucp.team_1.dp1.sigapucp.CustomEvents.IEvent;
import edu.pe.pucp.team_1.dp1.sigapucp.CustomEvents.IEventHandler;
import edu.pe.pucp.team_1.dp1.sigapucp.Models.RecursosHumanos.Accion;
import edu.pe.pucp.team_1.dp1.sigapucp.Models.RecursosHumanos.Menu;
import edu.pe.pucp.team_1.dp1.sigapucp.Models.RecursosHumanos.Usuario;
import edu.pe.pucp.team_1.dp1.sigapucp.Navegacion.cambiarMenuArgs;
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
import org.javalite.activejdbc.Base;

public class ContenidoPrincipalController extends Controller {

    @FXML private AnchorPane contenedor_modulos_contenido;
    @FXML private AnchorPane contenedor_botones_acciones;
    @FXML private NavegacionLateralController NavegacionLateralController;
    private Controller activeController;
    private WarningAlertController warningController;
    
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
            case "Cargar":
                controller.cargar();
            case "Completar":
                controller.cambiarEstado();
            default:
                break;
        }
    }
    
    public ContenidoPrincipalController()
    {        
        warningController = new WarningAlertController();
    }
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        activeController = new Controller();

        NavegacionLateralController.abrirDetalle.addHandler((sender, args) -> {         
              
            setNuevaVista(args.getPathContenido(),args.getPathBotonesAcciones());
            activeController.cambiarMenuEvent.addHandler((Object sender1, cambiarMenuArgs args1) -> {                
                setNuevaVista(args1.fxmlPath,args1.fxmlBotones);                                  
                activeController.menuCall(args1);
            });                                                                     
        });
    }
    
    public void setNuevaVista(String fxmlPath,String fxmlBotones)
    {
        try {
            System.out.println(fxmlPath);
            FXMLLoader loaderContenido = new FXMLLoader(getClass().getResource(fxmlPath));
            AnchorPane contenido = (AnchorPane) loaderContenido.load();
            activeController = loaderContenido.<Controller>getController();            
            
            if(!Usuario.tienePermiso(permisosActual, activeController.getMenu(), Accion.ACCION.VIW))
            {
                warningController.show("Permisos insuficientes", "El rol con codigo: " + usuarioActual.getString("rol_cod") + " no tiene permisos suficientes para realizar esta accion");
            }

            System.out.println(contenido);
            System.out.println(loaderContenido);                   
            activeController.setUsuarioActual(usuarioActual,permisosActual);
            activeController.postInitialize(fxmlPath);
            contenedor_modulos_contenido.getChildren().setAll(contenido);
            
             // Cargar el contenido de los botones de accion
            FXMLLoader loaderAcciones = new FXMLLoader(getClass().getResource(fxmlBotones));
            AnchorPane contenidoAcciones = (AnchorPane) loaderAcciones.load();
            Controller controllerAcciones = loaderAcciones.<Controller>getController();
            IEvent<ejecutarAccionArgs> evento =  controllerAcciones.getActionEvent();
            evento.addHandler((accionSender, accionArgs) -> {
                seleccionarAccionRealizar(accionArgs.getAccion(), activeController);
            });
            contenedor_botones_acciones.getChildren().setAll(contenidoAcciones);
        } catch (Exception e) {
            Logger.getLogger(ContenidoPrincipalController.class.getName()).log(Level.SEVERE, null, e);
        }                                
    }
}
