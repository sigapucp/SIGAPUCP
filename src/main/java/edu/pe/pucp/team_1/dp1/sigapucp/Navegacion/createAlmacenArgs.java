/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.pe.pucp.team_1.dp1.sigapucp.Navegacion;

import edu.pe.pucp.team_1.dp1.sigapucp.CustomEvents.EventArgs;

/**
 *
 * @author herbert
 */
public class createAlmacenArgs extends EventArgs {
    private int largo;
    private int ancho;
    private int x_relativo;
    private int y_relativo;
    private String nombre;
    private char es_cental;
    private int longitud_area;

    /**
     * @return the largo
     */
    public int getLargo() {
        return largo;
    }

    /**
     * @param largo the largo to set
     */
    public void setLargo(int largo) {
        this.largo = largo;
    }

    /**
     * @return the ancho
     */
    public int getAncho() {
        return ancho;
    }

    /**
     * @param ancho the ancho to set
     */
    public void setAncho(int ancho) {
        this.ancho = ancho;
    }

    /**
     * @return the x_relativo
     */
    public int getX_relativo() {
        return x_relativo;
    }

    /**
     * @param x_relativo the x_relativo to set
     */
    public void setX_relativo(int x_relativo) {
        this.x_relativo = x_relativo;
    }

    /**
     * @return the y_relativo
     */
    public int getY_relativo() {
        return y_relativo;
    }

    /**
     * @param y_relativo the y_relativo to set
     */
    public void setY_relativo(int y_relativo) {
        this.y_relativo = y_relativo;
    }

    /**
     * @return the nombre
     */
    public String getNombre() {
        return nombre;
    }

    /**
     * @param nombre the nombre to set
     */
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    /**
     * @return the es_cental
     */
    public char getEs_cental() {
        return es_cental;
    }

    /**
     * @param es_cental the es_cental to set
     */
    public void setEs_cental(char es_cental) {
        this.es_cental = es_cental;
    }

    /**
     * @return the longitud_area
     */
    public int getLongitud_area() {
        return longitud_area;
    }

    /**
     * @param longitud_area the longitud_area to set
     */
    public void setLongitud_area(int longitud_area) {
        this.longitud_area = longitud_area;
    }
}
