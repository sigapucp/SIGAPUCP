/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.pe.pucp.team_1.dp1.sigapucp.Navegacion;

import com.google.common.base.CaseFormat;
import edu.pe.pucp.team_1.dp1.sigapucp.CustomEvents.EventArgs;

/**
 *
 * @author herbert
 */
public class abrirDetallesArgs extends EventArgs {
    private String nombre_modulo;
    private String nombre_controller;

    /**
     * @param nombre_modulo the nombre_modulo to set
     */
    public void setNombreModulo(String nombre_modulo) {
        this.nombre_modulo = nombre_modulo;
    }

    /**
     * @param nombre_controller the nombre_controller to set
     */
    public void setNombreController(String nombre_controller) {
        this.nombre_controller = nombre_controller;
    }

    /**
     * @return the path_contenido
     */
    public String getPathContenido() {
        String modulo = nombre_modulo.replaceAll("\\s+", "");
        String controller = CaseFormat.LOWER_UNDERSCORE.to(CaseFormat.UPPER_CAMEL, nombre_controller.replaceAll("\\s+", "_"));

        return String.format("/fxml/%s/%s.fxml", modulo, controller);
    }
    
    /**
     * @return the path_contenido
     */
    public String getPathBotonesAcciones() {
        String modulo = nombre_modulo.replaceAll("\\s+", "");
        String controller = CaseFormat.LOWER_UNDERSCORE.to(CaseFormat.UPPER_CAMEL, nombre_controller.replaceAll("\\s+", "_"));

        return String.format("/fxml/%s/%sBotones.fxml", modulo, controller);
    }
    
    
}
