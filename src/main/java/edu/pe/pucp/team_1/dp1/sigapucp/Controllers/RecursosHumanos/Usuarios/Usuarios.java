/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.pe.pucp.team_1.dp1.sigapucp.Controllers.RecursosHumanos.Usuarios;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

/**
 *
 * @author Hugo
 */
public class Usuarios {
    private final StringProperty Nombre;
    private final StringProperty Apellido;
    private final StringProperty Correo;
    
    public Usuarios(String Nombre, String Apellido, String Correo) {
        this.Nombre = new SimpleStringProperty(Nombre);
        this.Apellido = new SimpleStringProperty(Apellido);
        this.Correo = new SimpleStringProperty(Correo);
    }

    /**
     * @return the Nombre
     */
    public String getNombre() {
        return Nombre.get();
    }

    /**
     * @param Nombre the Nombre to set
     */
    public void setNombre(String Nombre) {
        this.Nombre.set(Nombre);
    }
    
    public StringProperty NombreProperty() {
	return Nombre;
    }
    /**
     * @return the Apellido
     */
    public String getApellido() {
        return Apellido.get();
    }
    
    /**
     * @param Apellido the Apellido to set
     */
    public void setApellido(String Apellido) {
        this.Apellido.set(Apellido);
    }

    public StringProperty ApellidoProperty() {
        return Apellido;
    }    
    /**
     * @return the Correo
     */
    public String getCorreo() {
        return Correo.get();
    }

    /**
     * @param Correo the Correo to set
     */
    public void setCorreo(String Correo) {
        this.Correo.set(Correo);
    }
    public StringProperty CorreoProperty(){
        return Correo;
    }
}
