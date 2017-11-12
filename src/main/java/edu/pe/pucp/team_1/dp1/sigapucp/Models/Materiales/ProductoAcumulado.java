/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.pe.pucp.team_1.dp1.sigapucp.Models.Materiales;

/**
 *
 * @author Jauma
 */
public class ProductoAcumulado {
    
    private String cantidad;
    private String almacen;
    private String rack;
    private String posicion;
    private String nivel;
    private String capacidad_restante;
    
    public ProductoAcumulado(int gCantidad)
    {
        cantidad = String.valueOf(gCantidad);
        almacen = "No Ubicado";
        rack = "--";
        posicion = "--";
        nivel = "--";
        capacidad_restante = "--";        
    }
    
    public ProductoAcumulado(int gCantidad,Producto producto,AlmacenAreaZ areaZ)
    {
        cantidad = String.valueOf(gCantidad);
        almacen = producto.getString("almacen_cod");
        rack = producto.getString("rack_cod");
        posicion = producto.getString("posicion_rack");
        nivel = String.valueOf(areaZ.getInteger("level")+1);
        capacidad_restante = areaZ.getString("capacidad_restante");
    }
    
    public String getCantidad()
    {
        return cantidad;
    }
    
    public String getAlmacen()
    {
        return almacen;
    }
    
    public String getRack()
    {
        return rack;
    }
    
    public String getPosicion()
    {
        return posicion;
    }
    
    public String getNivel()
    {
        return nivel;
    }
    
    public String getRestante()
    {
        return capacidad_restante;
    }
}
