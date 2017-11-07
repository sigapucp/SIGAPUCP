/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.pe.pucp.team_1.dp1.sigapucp.Controllers.General.Kardex;

import javafx.beans.InvalidationListener;
import javafx.beans.property.StringProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
/**
 *
 * @author Alberto Chang Lopez
 */
public class Kardex {
    
    private final StringProperty fecha;
    private final StringProperty detalle;
    private final StringProperty ent_cant;
    private final StringProperty ent_costo;
    private final StringProperty sal_cant;
    private final StringProperty sal_costo;
    private final StringProperty exi_cant;
    private final StringProperty exi_costo;
    
    public Kardex(String fecha, String detalle, String ent_cant, String ent_costo, String sal_cant, String sal_costo, String exi_cant, String exi_costo){
        this.fecha = new SimpleStringProperty(fecha);
        this.detalle = new SimpleStringProperty(detalle);
        this.ent_cant = new SimpleStringProperty(ent_cant);
        this.ent_costo = new SimpleStringProperty(ent_costo);
        this.sal_cant = new SimpleStringProperty(sal_cant);
        this.sal_costo = new SimpleStringProperty(sal_costo);
        this.exi_cant = new SimpleStringProperty(exi_cant);
        this.exi_costo = new SimpleStringProperty(exi_costo);
    }
    
    /**
     * @return the fecha
     */
    public StringProperty getFecha() {
        return fecha;
    }

    /**
     * @return the detalle
     */
    public StringProperty getDetalle() {
        return detalle;
    }

    /**
     * @return the ent_cant
     */
    public StringProperty getEnt_cant() {
        return ent_cant;
    }

    /**
     * @return the ent_costo
     */
    public StringProperty getEnt_costo() {
        return ent_costo;
    }

    /**
     * @return the sal_cant
     */
    public StringProperty getSal_cant() {
        return sal_cant;
    }

    /**
     * @return the sal_costo
     */
    public StringProperty getSal_costo() {
        return sal_costo;
    }

    /**
     * @return the exi_cant
     */
    public StringProperty getExi_cant() {
        return exi_cant;
    }

    /**
     * @return the exi_costo
     */
    public StringProperty getExi_costo() {
        return exi_costo;
    }
}
