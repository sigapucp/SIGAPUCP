/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.pe.pucp.team_1.dp1.sigapucp.Navegacion;

import edu.pe.pucp.team_1.dp1.sigapucp.CustomEvents.EventArgs;
import edu.pe.pucp.team_1.dp1.sigapucp.Models.Ventas.OrdenCompraxProducto;

/**
 *
 * @author Gustavo
 */
public class agregarOrdenCompraProductoArgs extends EventArgs {
    public OrdenCompraxProducto orden_compra_producto;
    
    public  agregarOrdenCompraProductoArgs(OrdenCompraxProducto orden_compra_producto){
        orden_compra_producto = orden_compra_producto;
    }
}
