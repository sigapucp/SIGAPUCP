/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.pe.pucp.team_1.dp1.sigapucp.Navegacion;

import edu.pe.pucp.team_1.dp1.sigapucp.CustomEvents.EventArgs;
import edu.pe.pucp.team_1.dp1.sigapucp.Models.Materiales.AlmacenAreaXY;
import edu.pe.pucp.team_1.dp1.sigapucp.Models.Materiales.AlmacenAreaZ;
import edu.pe.pucp.team_1.dp1.sigapucp.Models.Materiales.Producto;

public class agregarProductoRackArgs extends EventArgs{
    private Producto producto_seleccionado;
    private AlmacenAreaZ almacenZ;

    public void setProducto(Producto producto) {
        producto_seleccionado = producto;
    }

    public Producto getProducto() {
        return producto_seleccionado;
    }

    public void setAlmacenZ(AlmacenAreaZ almacen_z) {
        almacenZ = almacen_z;
    }

    public AlmacenAreaZ getAlmacenZ() {
        return almacenZ;
    }
}
