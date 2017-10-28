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
public class tileArgs extends EventArgs {
    private int x_cord;
    private int y_cord;

    /**
     * @return the x_cord
     */
    public int getX_cord() {
        return x_cord;
    }

    /**
     * @param x_cord the x_cord to set
     */
    public void setX_cord(int x_cord) {
        this.x_cord = x_cord;
    }

    /**
     * @return the y_cord
     */
    public int getY_cord() {
        return y_cord;
    }

    /**
     * @param y_cord the y_cord to set
     */
    public void setY_cord(int y_cord) {
        this.y_cord = y_cord;
    }
    
    
}
