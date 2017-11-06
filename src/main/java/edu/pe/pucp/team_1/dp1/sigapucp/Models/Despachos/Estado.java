/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.pe.pucp.team_1.dp1.sigapucp.Models.Despachos;

import java.util.LinkedList;
import java.util.List;

/**
 *
 * @author Jauma
 */
public class Estado {
    public Punto puntoActual;
    
    public List<Punto> ruta;
    
    public Estado()
    {
        puntoActual = new Punto(0,0);
        ruta = new LinkedList<>();
    }
    
    public Estado(Estado gEstado)
    {
        puntoActual = new Punto(gEstado.puntoActual);
        ruta = new LinkedList<>(gEstado.ruta);
    }
    
    public Estado(int gX,int gY)
    {
        puntoActual = new Punto(gX, gY);
        ruta = new LinkedList<>();
    }
    
     public Estado(Punto gPunto,List<Punto> gRuta)
    {
        puntoActual = new Punto(gPunto);
        ruta = gRuta;
    }
    
    public int getCost()
    {
        return ruta.size();
    }
    
    public void add(Punto punto)
    {
        ruta.add(punto);
        puntoActual = punto;
    }
    
    public Punto get(int index)
    {
        return ruta.get(index);
    }
}
