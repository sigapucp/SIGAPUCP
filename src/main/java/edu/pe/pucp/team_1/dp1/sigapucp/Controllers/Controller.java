/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.pe.pucp.team_1.dp1.sigapucp.Controllers;

import edu.pe.pucp.team_1.dp1.sigapucp.CustomEvents.Event;
import edu.pe.pucp.team_1.dp1.sigapucp.CustomEvents.IEvent;
import edu.pe.pucp.team_1.dp1.sigapucp.Models.RecursosHumanos.Accion;
import edu.pe.pucp.team_1.dp1.sigapucp.Models.RecursosHumanos.AccionxRol;
import edu.pe.pucp.team_1.dp1.sigapucp.Models.RecursosHumanos.Menu;
import edu.pe.pucp.team_1.dp1.sigapucp.Models.RecursosHumanos.Usuario;
import edu.pe.pucp.team_1.dp1.sigapucp.Models.Seguridad.AccionLoggerSingleton;
import edu.pe.pucp.team_1.dp1.sigapucp.Navegacion.cambiarMenuArgs;
import edu.pe.pucp.team_1.dp1.sigapucp.Navegacion.ejecutarAccionArgs;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import javafx.fxml.Initializable;

/**
 *
 * @author herbert
 */
public class Controller implements Initializable {

    public Usuario usuarioActual = null;
    public List<AccionxRol> permisosActual = null;
    public String fxmlPath;
    public Validator validator = new Validator();
    
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        //TODO
    }
    
    public IEvent<ejecutarAccionArgs> getActionEvent() {
        return null;
    }
    
    public void guardar() {
        System.out.println("Estoy creando");
    }
    
    public void nuevo() {
        System.out.println("Nuevo");
    }
    
    public void desactivar() {
        System.out.println("Desactivar");
    }
    
    public void cambiarEstado()
    {
        System.out.println("Cambiar estado");
    }
    
    public void cargar(){
        System.out.println("Se hará la carga masiva de datos");
    }
    
    public void menuCall(cambiarMenuArgs eventArgs)
    {
        System.out.println("Se responde a la llamada desde otro menu");
    }
    
    public void postInitialize(String gFxmlPath)
    {        
        fxmlPath = gFxmlPath;
    }
    
    public void setUsuarioActual(Usuario usuario,List<AccionxRol> permisos)
    {
        usuarioActual = usuario;
        permisosActual = permisos;
    }
    
    public Menu.MENU getMenu()
    {
        return Menu.MENU.NullMenu;
    }

    public String generarQueryBusqueda(String... params) {
        String query = "";
        for(String param : params) {
            query = query + (param.equals("") ? "" : "" );
        }
        return query;
    }

   public Event<cambiarMenuArgs> cambiarMenuEvent = new Event<>();
}
