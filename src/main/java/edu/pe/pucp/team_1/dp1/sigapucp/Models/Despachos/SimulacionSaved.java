/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.pe.pucp.team_1.dp1.sigapucp.Models.Despachos;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Jauma
 */
public class SimulacionSaved {
    
   private Double costo;
   private List<RutaSaved> rutas;
   private RutaSaved rutaActual;
   private Punto acopio;
   private Double capacidad_carro;
   private int nrProductos;
   
   public SimulacionSaved(Double gCosto,Double gCapacidad_carro,int gNrProductos,int punto_x,int punto_y)
   {       
       costo = gCosto;
       capacidad_carro = gCapacidad_carro;
       nrProductos = gNrProductos;
       acopio = new Punto(punto_x,punto_y);
       rutas = new ArrayList<>();
   }
   
   public void addRuta(RutaSaved ruta)
   {
       rutas.add(ruta);
       rutaActual = ruta;
   }
   
   public int getNrRutas()
   {
       return rutas.size();       
   }
   
   public Double getCosto()
   {
       return costo;       
   }
   
   public void setRutaActual(int nrRuta)
   {
       rutaActual = rutas.get(nrRuta);
   }
   
   public RutaSaved getRutaActual()
   {
       return rutaActual;
   }
   
   public Double getCostoRutaActual()
   {
       return rutaActual.getCosto();
   }
}
