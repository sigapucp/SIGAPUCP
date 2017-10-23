/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.pe.pucp.team_1.dp1.sigapucp.Controllers.General;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.beans.property.StringProperty;
import javafx.fxml.Initializable;


/**
 *
 * @author Hugo
 */
public class AuditoriaController implements Initializable {
    private StringProperty Hora;
    private StringProperty Fecha;
    private StringProperty Empleado;
    private StringProperty Accion;
    private StringProperty Modulo;
    private StringProperty Descripcion;
    
    /**
     * @return the Hora
     */
    public String getHora() {
        return Hora.get();
    }

    /**
     * @return the Fecha
     */
    public String getFecha() {
        return Fecha.get();
    }

    /**
     * @return the Empleado
     */
    public String getEmpleado() {
        return Empleado.get();
    }

    /**
     * @return the Accion
     */
    public String getAccion() {
        return Accion.get();
    }

    /**
     * @return the Modulo
     */
    public String getModulo() {
        return Modulo.get();
    }

    /**
     * @return the Descripcion
     */
    public String getDescripcion() {
        return Descripcion.get();
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        //TODO
    }
    
}
