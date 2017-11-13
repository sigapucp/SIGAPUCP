/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.pe.pucp.team_1.dp1.sigapucp.Navegacion;

import edu.pe.pucp.team_1.dp1.sigapucp.CustomEvents.EventArgs;
import edu.pe.pucp.team_1.dp1.sigapucp.Models.Ventas.DocVenta;

/**
 *
 * @author Jauma
 */
public class agregarDocVentasArgs extends EventArgs{
    
    private DocVenta docVenta;
    public agregarDocVentasArgs(DocVenta gDocVenta)
    {
        docVenta = gDocVenta;
    }
    
    public DocVenta getDocVenta()
    {
        return docVenta;
    }
}
