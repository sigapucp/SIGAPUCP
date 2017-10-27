/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.pe.pucp.team_1.dp1.sigapucp.Models.RecursosHumanos;

import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleStringProperty;

/**
 *
 * @author Jauma
 */
public class PrivilegioEntrada {
    private SimpleStringProperty nombrePrivilegio;
    private SimpleBooleanProperty estadoPrivilegio;
    private TIPOPRIVILEGIO tipo;
    private int id;
    private String cod;    
    private int idMenu;
    private String codMenu;
    
    public PrivilegioEntrada(int gid,String gcod,String nombre,Boolean estado,TIPOPRIVILEGIO gtipo)
    {
        id = gid;
        tipo = gtipo;
        nombrePrivilegio = new SimpleStringProperty();
        estadoPrivilegio = new SimpleBooleanProperty();
        setNombrePrivilegio(nombre);
        setEstadoPrivilegio(estado);
        idMenu = 0;
        cod = gcod;
    }
    
    public void setIdMenu(int gidMenu)
    {
        idMenu = gidMenu;       
    }        
    
    public void setCodMenu(String gcodMenu)
    {
        codMenu = gcodMenu;
    }
    
    public String getCodMenu()
    {
        return codMenu;
    }
    
    public int getIdMenu()
    {
        return idMenu;
    }
    
    public int getId()
    {
        return id;
    }
    
    public String getCod()
    {
        return cod;
    }
    
    public TIPOPRIVILEGIO getTipo()
    {
        return tipo;
    }

    /**
     * @return the nombrePrivilegio
     */
    public SimpleStringProperty getNombrePrivilegio() {
        return nombrePrivilegio;
    }

    /**
     * @param nombrePrivilegio the nombrePrivilegio to set
     */
    public void setNombrePrivilegio(String nombrePrivilegio) {
        this.nombrePrivilegio.set(nombrePrivilegio);
    }

    /**
     * @return the estadoPrivilegio
     */
    public SimpleBooleanProperty getEstadoPrivilegio() {
        return estadoPrivilegio;
    }

    /**
     * @param estadoPrivilegio the estadoPrivilegio to set
     */
    public void setEstadoPrivilegio(Boolean estadoPrivilegio) {
        this.estadoPrivilegio.set(estadoPrivilegio);
    }        
    
    public enum TIPOPRIVILEGIO
    {
        MENU,
        ACCION
    }
    
}
