/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.pe.pucp.team_1.dp1.sigapucp.Navegacion;

import edu.pe.pucp.team_1.dp1.sigapucp.CustomEvents.EventArgs;
import edu.pe.pucp.team_1.dp1.sigapucp.Models.Despachos.OrdenSalida;

/**
 *
 * @author Jauma
 */
public class agregarOrdenSalidaArgs extends EventArgs{
    
    private OrdenSalida ordenSalida;
    public agregarOrdenSalidaArgs(OrdenSalida orden)
    {
        ordenSalida = orden;        
    }
    
    public OrdenSalida getOrdenSalida()
    {
        return ordenSalida;
    }
    
}
