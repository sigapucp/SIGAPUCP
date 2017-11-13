/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.pe.pucp.team_1.dp1.sigapucp.Models.Simulacion;

import edu.pe.pucp.team_1.dp1.sigapucp.CustomComponents.ProductoSimulacion;
import edu.pe.pucp.team_1.dp1.sigapucp.CustomComponents.TupleProductos;
import edu.pe.pucp.team_1.dp1.sigapucp.Models.Despachos.ProductoNodo;
import edu.pe.pucp.team_1.dp1.sigapucp.Models.Despachos.Punto;
import edu.pe.pucp.team_1.dp1.sigapucp.Models.Despachos.Ruta;
import edu.pe.pucp.team_1.dp1.sigapucp.Models.Despachos.RutaGeneral;
import java.util.List;
import javafx.scene.paint.Color;

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
    private List<TupleProductos> rutasProductos;
    private Integer nrProductos;
    
    public Simulacion(RutaGeneral gRuta,List<TupleProductos> gRutasProductos,Punto gAcopio,Double gCapacidad,Integer gNrProductos)
    {
        rutaGeneral = gRuta;        
        rutas = gRuta.getRutas();    
        rutasProductos = gRutasProductos;
        acopio = gAcopio;
        capacidad = gCapacidad;
        nrProductos = gNrProductos;
        
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
    
    public Punto getPuntoAcopio()
    {
        return acopio;
    }
    
    public Integer getNrProductos()
    {
        return nrProductos;
    }
    
    public List<ProductoNodo> getRutaActual()
    {
        return rutaActual;
    }  
    
    public int getNrRutas()
    {
        return rutas.size();
    }

    public void setRutaActual(int nrRuta)
    {
        if(nrRuta < 0 || nrRuta >= rutas.size()) return;
        rutaActual = rutas.get(nrRuta).getRutaList();
        rutaActualIndex = nrRuta;
    }
    
    public Double getCapcacidadCarro()
    {
        return capacidad;
    }
    
    public void setNodoActual(int nrNodo)
    {
        if(nrNodo < 0 || nrNodo >= rutaActual.size()) return;
        nodoActual = rutaActual.get(nrNodo);
        nodoActualIndex = nrNodo;
    }
    
    public List<TupleProductos> getRutasProductos()
    {
        return rutasProductos;
    }
    
    public String getRutaString(int index,List<ProductoSimulacion> productosSimulacion)
    {
        List<ProductoNodo> rutaList = rutas.get(index).getRutaList();
        String ruta =  acopio.toString() + ",";
        for(ProductoNodo producto:rutaList)
        {
            TupleProductos tupla = rutasProductos.stream().filter(x -> x.esPar(productosSimulacion.get(producto.GetLlave()), productosSimulacion.get(producto.sig.GetLlave()))).findFirst().get();                       
            
            Color color = tupla.getProductoUno().getColor();
            
            ruta += getColorPointString(color) + ",";
            ruta += getColorRouteString(tupla.getColor()) + ",";
                        
            if(productosSimulacion.get(producto.GetLlave()) == tupla.getProductoUno())
            {
                for(Punto punto:tupla.getEstado().ruta)
                {        
                    if(punto.dir == null) continue;
                    ruta += punto.dir.name();                
                }                                
            }else
            {        
                for(int i = tupla.getEstado().ruta.size() -1;i>=0;i--)
                {
                    if(tupla.getEstado().ruta.get(i).dir == null) continue;
                    ruta += Punto.invertirDireccion(tupla.getEstado().ruta.get(i).dir).name();                                    
                }                               
            }                             
            if(producto.sig.EsDeposito()) 
            {               
                break;
            }
            ruta += ",";
        }     
        return ruta;
    }    
    
    public List<Ruta> getRutas()
    {
        return rutas;
    }
    
    public String getColorPointString(Color color)
    {
        return  (int)(color.getRed()*255) + "-" + (int)(color.getGreen()*255) + "-" + (int)(color.getBlue()*255);
    }
    
    public String getColorRouteString(Color color)
    {
        return (int)(color.getRed()*255) + "-" + (int)(color.getGreen()*255) + "-" + (int)(color.getBlue()*255);
    }
}
