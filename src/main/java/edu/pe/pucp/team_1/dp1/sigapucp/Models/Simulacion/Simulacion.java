/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.pe.pucp.team_1.dp1.sigapucp.Models.Simulacion;

import edu.pe.pucp.team_1.dp1.sigapucp.Models.Despachos.ProductoNodo;
import edu.pe.pucp.team_1.dp1.sigapucp.Models.Despachos.Punto;
import edu.pe.pucp.team_1.dp1.sigapucp.Models.Despachos.Ruta;
import edu.pe.pucp.team_1.dp1.sigapucp.Models.Despachos.RutaGeneral;
import java.util.List;

/**
 *
 * @author Jauma
 */
public class Simulacion {
       
    private List<Ruta> rutas;
    private RutaGeneral rutaGeneral;
    private Punto acopio;
    private Double capacidad;  
    private int nodoActualIndex;
    private ProductoNodo nodoActual;
    private List<ProductoNodo> rutaActual;
    private int rutaActualIndex;
    
    public Simulacion(RutaGeneral gRuta,Punto gAcopio,Double gCapacidad)
    {
        rutas = gRuta.getRutas();    
        acopio = gAcopio;
        capacidad = gCapacidad;
        
        rutaActual = rutas.get(0).getRutaList();
        nodoActualIndex = 0;      
        rutaActualIndex = 0;
    }
        
    public Double getCosto()
    {
        return rutaGeneral.GetCosto(rutaGeneral.getRutas());
    }
    
    public Double getCostoRutaActual()
    {
        return rutas.get(rutaActualIndex).GetDistancia();
    }       
    
    public Boolean moverEnRuta()
    {
        Boolean loop = false;  
        nodoActualIndex++;         
        if(nodoActualIndex == rutaActual.size()) 
        {          
            nodoActualIndex = 0;           
            loop = true;
        }               
        return loop;        
    }
    
    public ProductoNodo getNodoActual()
    {
        if(rutaActual == null) return null;
        return rutaActual.get(nodoActualIndex);
    }
    
    public List<ProductoNodo> getRutaActual()
    {
        return rutaActual;
    }  

    public void setRutaActual(int nrRuta)
    {
        if(nrRuta < 0 || nrRuta >= rutas.size()) return;
        rutaActual = rutas.get(nrRuta).getRutaList();
        rutaActualIndex = nrRuta;
    }
    
    public void setNodoActual(int nrNodo)
    {
        if(nrNodo < 0 || nrNodo >= rutaActual.size()) return;
        nodoActual = rutaActual.get(nrNodo);
        nodoActualIndex = nrNodo;
    }
    
}
