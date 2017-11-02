/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.pe.pucp.team_1.dp1.sigapucp.Controllers.General.Auditoria;

import javafx.beans.property.StringProperty;
import javafx.beans.property.SimpleStringProperty;


/**
 *
 * @author Hugo
 */
public class Auditoria {
    //private final StringProperty Hora;
    private final StringProperty Fecha;
    private final StringProperty Empleado;
    private final StringProperty Accion;
    private final StringProperty Modulo;
    private final StringProperty Descripcion;
    private final StringProperty Rol;
    
    public Auditoria( String Fecha, String Empleado, String Accion, String Modulo, String Descripcion, String Rol) {
        //this.Hora = new SimpleStringProperty(Hora);
        this.Fecha = new SimpleStringProperty(Fecha);
        this.Empleado = new SimpleStringProperty(Empleado);
        this.Accion = new SimpleStringProperty(Accion);
        this.Modulo = new SimpleStringProperty(Modulo);
        this.Descripcion = new SimpleStringProperty(Descripcion);
        this.Rol = new SimpleStringProperty(Rol);
    }

    /**
     * @return the Hora
     */
    

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

    /**
     * @param Hora the Hora to set
     */

    /**
     * @param Fecha the Fecha to set
     */
    public void setFecha(String Fecha) {
        this.Fecha.set(Fecha);
    }

    /**
     * @param Empleado the Empleado to set
     */
    public void setEmpleado(String Empleado) {
        this.Empleado.set(Empleado);
    }

    /**
     * @param Accion the Accion to set
     */
    public void setAccion(String Accion) {
        this.Accion.set(Accion);
    }

    /**
     * @param Modulo the Modulo to set
     */
    public void setModulo(String Modulo) {
        this.Modulo.set(Modulo);
    }

    /**
     * @param Descripcion the Descripcion to set
     */
    public void setDescripcion(String Descripcion) {
        this.Descripcion.set(Descripcion);
    }
    
    public StringProperty EmpleadoProperty() {
		return Empleado;
	}
    public StringProperty ModuloProperty() {
		return Modulo;
	}

    public StringProperty FechaProperty() {
		return Fecha;
	}
    public StringProperty AccionProperty() {
		return Accion;
	}
    public StringProperty DescripcionProperty() {
		return Descripcion;
	}
    
    public StringProperty RolProperty(){
        return Rol;
    }
}