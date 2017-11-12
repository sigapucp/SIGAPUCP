/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.pe.pucp.team_1.dp1.sigapucp.Models.Despachos;

import edu.pe.pucp.team_1.dp1.sigapucp.Models.Materiales.Rack;
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
        puntoActual = null;
        ruta = new LinkedList<>();
    }
    
    public Estado(Estado gEstado)
    {
        ruta = new LinkedList<>();
        for(Punto punto:gEstado.ruta)
        {
            ruta.add(new Punto(punto));
        }
        puntoActual = ruta.get(ruta.size()-1);       
    }
    
    public Estado(int gX,int gY)
    {
        puntoActual = new Punto(gX, gY);
        ruta = new LinkedList<>();
        ruta.add(puntoActual);
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
