/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.pe.pucp.team_1.dp1.sigapucp.Navegacion;

import edu.pe.pucp.team_1.dp1.sigapucp.CustomEvents.EventArgs;
import edu.pe.pucp.team_1.dp1.sigapucp.Models.Materiales.TipoProducto;

/**
 *
 * @author Jauma
 */
public class agregarProductoArgs extends EventArgs{
    
    public TipoProducto producto;
    public agregarProductoArgs(TipoProducto gproducto)
    {
        producto = gproducto;        
    }    
}
