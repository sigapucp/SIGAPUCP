package edu.pe.pucp.team_1.dp1.sigapucp.Navegacion;


import edu.pe.pucp.team_1.dp1.sigapucp.CustomEvents.EventArgs;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author herbert
 */
public class createRackArgs extends EventArgs {
    private int x_ancla1;
    private int x_ancla2;
    private int y_ancla1;
    private int y_ancla2;
    private double longitud;
    private Boolean is_uniforme;

    /**
     * @return the x_ancla1
     */
    public int getX_ancla1() {
        return x_ancla1;
    }

    /**
     * @param x_ancla1 the x_ancla1 to set
     */
    public void setX_ancla1(int x_ancla1) {
        this.x_ancla1 = x_ancla1;
    }

    /**
     * @return the x_ancla2
     */
    public int getX_ancla2() {
        return x_ancla2;
    }

    /**
     * @param x_ancla2 the x_ancla2 to set
     */
    public void setX_ancla2(int x_ancla2) {
        this.x_ancla2 = x_ancla2;
    }

    /**
     * @return the y_ancla1
     */
    public int getY_ancla1() {
        return y_ancla1;
    }

    /**
     * @param y_ancla1 the y_ancla1 to set
     */
    public void setY_ancla1(int y_ancla1) {
        this.y_ancla1 = y_ancla1;
    }

    /**
     * @return the y_ancla2
     */
    public int getY_ancla2() {
        return y_ancla2;
    }

    /**
     * @param y_ancla2 the y_ancla2 to set
     */
    public void setY_ancla2(int y_ancla2) {
        this.y_ancla2 = y_ancla2;
    }

    /**
     * @return the longitud
     */
    public double getLongitud() {
        return longitud;
    }

    /**
     * @param longitud the longitud to set
     */
    public void setLongitud(double longitud) {
        this.longitud = longitud;
    }

    /**
     * @return the is_uniforme
     */
    public Boolean getIs_uniforme() {
        return is_uniforme;
    }

    /**
     * @param is_uniforme the is_uniforme to set
     */
    public void setIs_uniforme(Boolean is_uniforme) {
        this.is_uniforme = is_uniforme;
    }
    
}
