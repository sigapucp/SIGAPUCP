/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.pe.pucp.team_1.dp1.sigapucp.Models.Ventas;

/**
 *
 * @author Jauma
 */
public class DocVentaxProducto {
    
    private int cantidad;
    private String codigo;
    private String descripcion;
    private Double precioUnitario;
    private Double descuentos;
    private Double fletes;
    private Double subtotal;
    
    public DocVentaxProducto(int gCantidad,String gCodigo,String gDescripcion,Double gPrecioUnitario,Double gDescuentos,Double gFletes,int cantidadTotal)
    {
        if(cantidadTotal == 0) cantidadTotal = 1;
        cantidad = gCantidad;
        codigo = gCodigo;
        descripcion = gDescripcion;
        precioUnitario = gPrecioUnitario;
        descuentos = gDescuentos*(cantidad/cantidadTotal);
        fletes = gFletes*(cantidad/cantidadTotal);
        subtotal = cantidad*precioUnitario - descuentos + fletes;
    }
    
    public String getCantidad()
    {
        return String.valueOf(cantidad);
    }
    
    public String getCodigo()
    {
        return codigo;
    }
    
    public String getDescripcion()
    {
        return descripcion;                        
    }
    
    public String getPrecioUnitario()
    {
        return String.valueOf(precioUnitario);
    }
    
    public String getDescuento()
    {
        return String.valueOf(descuentos);
    }
    
    public String getFlete()
    {
        return String.valueOf(fletes);
    }
    
    public String getSubtotal()
    {
        return String.valueOf(subtotal);
    }
    
    
    public Double getSubTotalDouble()
    {
        return subtotal;
    }
    
}
