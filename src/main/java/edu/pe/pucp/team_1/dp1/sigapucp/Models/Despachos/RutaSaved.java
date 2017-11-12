/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.pe.pucp.team_1.dp1.sigapucp.Models.Despachos;

/**
 *
 * @author Jauma
 */
public class RutaSaved {
    String ruta;
    Double costo;
    
    public RutaSaved(String gRuta,Double gCosto)
    {
        ruta = gRuta;
        costo = gCosto;
    }
    public String getRuta()
    {
        return ruta;
    }
    
    public Double getCosto()
    {
        return costo;
    }
}
