/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.pe.pucp.team_1.dp1.sigapucp.Navegacion;

import edu.pe.pucp.team_1.dp1.sigapucp.CustomEvents.EventArgs;
import edu.pe.pucp.team_1.dp1.sigapucp.Models.Materiales.Producto;

public class agregarProductoRackArgs extends EventArgs{
    private Producto producto_seleccionado;
    private int cantidad_ingresada;
    private String tipo_ingreso;

    public void setProducto(Producto producto) {
        producto_seleccionado = producto;
    }

    public Producto getProducto() {
        return producto_seleccionado;
    }

    public void setCantidadIngresada(int cantidad) {
        cantidad_ingresada = cantidad;
    }

    public int getCantidadIngresada() {
        return cantidad_ingresada;
    }

    public void setTipoIngreso(String tipo) {
        tipo_ingreso = tipo;
    }

    public String getTipoIngreso() {
        return tipo_ingreso;
    }
}
