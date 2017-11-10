/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.pe.pucp.team_1.dp1.sigapucp.Models.Despachos;

import com.google.common.collect.TreeMultiset;
import com.sun.javafx.collections.SortableList;
import edu.pe.pucp.team_1.dp1.sigapucp.Models.Despachos.Celda.TIPO;
import edu.pe.pucp.team_1.dp1.sigapucp.Models.Despachos.Punto.DIRECCION;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Comparator;
import java.util.Dictionary;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.ListIterator;
import java.util.Random;
import java.util.TreeSet;
import javafx.beans.property.SimpleListProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableArray;
import javafx.collections.ObservableList;
import javafx.collections.transformation.SortedList;


/**
 *
 * @author Jauma
 */
public class RutaDetallada {
    
    private int NR_ITERACIONES = 10;
    private Double alfa_corte_ruta = 0.4;
    private List<Estado> poblacion;
    private double alfa = 0.33;
    private int DIVFACTOR = 2;
    Integer a;  
    Integer c;         
    public Estado obtenerRutaOptima(Celda.TIPO[][] mapa,int altura,int ancho,Punto puntoInicial,Punto puntoFinal)
    {   
        try {
            Boolean solucionLista = false;
        poblacion = new ArrayList<>();
        
        Estado mejorSolucion = null;
                
        int nrIteracion = 0;
        
        while(NR_ITERACIONES>=nrIteracion&&!solucionLista)
        {            
            Celda.TIPO[][] nuevoMapa = copiarMapa(mapa, altura, ancho);
            Estado estadoInicial = getEstadoInicial(nrIteracion);
            Estado estadoFinal = getEstadoFinal(nrIteracion);
            
            Punto pIni = null;
            Punto pFin = null;
                    
           
            if(estadoInicial!=null)
            {
                a = (new Random()).nextInt((estadoInicial.getCost()+1)/DIVFACTOR);
                pIni = estadoInicial.get(a);
            }else
            {
                pIni = puntoInicial;               
            }
                 
            if(estadoFinal!=null)
            {
                c = (new Random()).nextInt((estadoFinal.getCost()+1)/DIVFACTOR);
                pFin = estadoFinal.get(estadoFinal.getCost() - c - 1);
            }else
            {
                pFin = puntoFinal;
            }                            
                       
            Estado nuevaSolucion = generarRutaGreedy(nuevoMapa,altura,ancho,pIni,pFin);                   
            nuevaSolucion = juntarEstado(estadoInicial,nuevaSolucion,estadoFinal,pIni,pFin);          
            if(nuevaSolucion == null) 
            {
                continue;       
            }
            
            //if(nuevaSolucion.get)
            
            if(mejorSolucion == null || mejorSolucion.getCost() > nuevaSolucion.getCost())
            {
                mejorSolucion = nuevaSolucion;
               
            }            
            poblacion.add(nuevaSolucion);                            
            nrIteracion++;                                   
        }        
        return mejorSolucion;       
            
        } catch (Exception e) {
            
        }
        return null;
    }   
    
    private Estado juntarEstado(Estado inicial,Estado central,Estado fin,Punto pIni,Punto pFin)
    {
        Estado nuevoEstado = new Estado();        
        if(inicial!=null)
        {
            for(Punto punto:inicial.ruta)
            {
                nuevoEstado.add(punto);
                if(punto.isEqual(pIni)) 
                {
                    break;
                }
            }            
        }        
        if(central!=null)
        {
            for(ListIterator iter = central.ruta.listIterator(1);iter.hasNext();) {
                Punto punto = (Punto)iter.next();
                nuevoEstado.add(punto);
            }          
        }
                
        if(fin!=null)
        {
            for(ListIterator iter = fin.ruta.listIterator(fin.ruta.indexOf(pFin));iter.hasNext();) {
                Punto punto = (Punto)iter.next();
                nuevoEstado.add(punto);
            }      
        }
        return nuevoEstado;        
    }
    
    private Boolean esSolucion(Estado estado,Punto pInicial,Punto pFinal)
    {
        return estado.get(0).isEqual(pInicial) && estado.puntoActual.isEqual(pFinal);        
    }
       
    
    private List<Punto.DIRECCION> movimientosPosibles(TIPO[][] mapa,Punto p,int altura,int ancho)
    {
        List<Punto.DIRECCION> movs = new ArrayList<>();
        
        if(puntoValido(p.x + 1, p.y, altura, ancho) && mapa[p.y][p.x + 1] == Celda.TIPO.LIBRE)
        {
            movs.add(Punto.DIRECCION.O);
        }
        
        if(puntoValido(p.x - 1, p.y, altura, ancho) && mapa[p.y][p.x - 1] == Celda.TIPO.LIBRE)
        {
            movs.add(Punto.DIRECCION.E);
        }
        
        if(puntoValido(p.x, p.y + 1, altura, ancho) && mapa[p.y + 1][p.x] == Celda.TIPO.LIBRE)
        {
            movs.add(Punto.DIRECCION.S);
        }
        
        if(puntoValido(p.x, p.y - 1, altura, ancho) && mapa[p.y - 1][p.x] == Celda.TIPO.LIBRE)
        {
            movs.add(Punto.DIRECCION.N);
        }        
        return movs;
    }    
      
    public Punto getRandomItem(List<Punto> estados)
    {
        return estados.get((new Random()).nextInt(estados.size()));
    }    
    private Boolean puntoValido(int x,int y,int altura,int ancho)
    {
        return (x>=0&&x<ancho) && (y>=0&&y<altura);
    }              
    private Estado getEstadoInicial(int nrIteracion)
    {
        if(poblacion.isEmpty()) return null;
        if(poblacion.size() == 1) return poblacion.get(0);
        
        //if(((double)nrIteracion/(double)NR_ITERACIONES) > Math.random()) return null;
        poblacion.get((new Random()).nextInt((poblacion.size()+1)/DIVFACTOR));        
        return null;
    }    
    private Estado getEstadoFinal(int nrIteracion)
    {
        if(poblacion.isEmpty()) return null;
        if(poblacion.size() == 1) return poblacion.get(0);
        
        //if(((double)nrIteracion/(double)NR_ITERACIONES) > Math.random()) return null;
        
        poblacion.get((new Random()).nextInt((poblacion.size()+1)/DIVFACTOR));        
        return null;
    }    
    private Celda.TIPO[][] copiarMapa(Celda.TIPO[][] mapa,int altura,int ancho)
    {
        Celda.TIPO[][] nuevoMapa = new Celda.TIPO[altura][ancho];
        
        for(int i = 0;i<ancho;i++)
        {
            for(int j = 0;j<altura;j++)
            {
                nuevoMapa[j][i] = mapa[j][i];
            }
        }
        return nuevoMapa;
    }    
    private int distanciaManhattam(Punto p1,Punto p2)
    {
        return Math.abs(p1.x-p2.x) + Math.abs(p1.y-p1.y);
    }    
    private void dibujarRuta(Celda.TIPO[][] mapa,Estado solucion,int altura,int ancho)                        
    {               
        for(int j = 0;j<altura;j++)
        { 
            for(int i = 0;i<ancho;i++)
            {                
                if(mapa[j][i] == Celda.TIPO.LIBRE)
                {
                    System.out.print(" ");                    
                }
                
                if(mapa[j][i] == Celda.TIPO.OCUPADA)
                {
                    System.out.print("@");                    
                }
                
                if(mapa[j][i] == Celda.TIPO.VISITADA)
                {
                    System.out.print("·");                    
                }                
            }
            System.out.println();                    
        }
    }
    private Celda.TIPO[][] llenarMapa(Celda.TIPO[][] mapa,Estado solucion)
    {
        for(Punto punto:solucion.ruta)
        {
            mapa[punto.y][punto.x] = Celda.TIPO.VISITADA;
        }
        return mapa;
    }
    public Estado generarRutaGreedy(TIPO[][] mapa,int altura,int ancho,Punto puntoInicial, Punto puntoFinal)
    {
        TIPO[][] mapaProblema = copiarMapa(mapa, altura, ancho);
        Estado estadoActual = new Estado();        
        estadoActual.add(puntoInicial);    
        
//        HashMap<Estado,Integer> costos = new HashMap<>();                           
//        costos.put(estadoActual,(new Random()).nextInt(distanciaManhattam(estadoActual.get(0), puntoFinal)));        
       Comparator<Estado> comparar = new Comparator<Estado>() {
            @Override
            public int compare(Estado o1, Estado o2) {
                return (getCostoGreedy(o1, puntoFinal, null) - getCostoGreedy(o2, puntoFinal, null));
            }
        };              

        List<Estado> frontera = new ArrayList<>();
              
        frontera.add(estadoActual);
                
        while(!frontera.isEmpty())
        {
            estadoActual = getEstadoNuevo(frontera);    
            frontera.remove(estadoActual);
            if(esSolucion(estadoActual, puntoInicial, puntoFinal)) return estadoActual;            
            mapaProblema[estadoActual.puntoActual.y][estadoActual.puntoActual.x] = TIPO.VISITADA;
                        
            //dibujarRuta(llenarMapa(copiarMapa(mapa, altura, ancho),estadoActual), estadoActual, altura, ancho);
            //System.out.println("");
           List<DIRECCION> movs = movimientosPosibles(mapaProblema,estadoActual.puntoActual, altura, ancho);            
            for(DIRECCION dir:movs)
            {        
                Estado nuevoEstado = new Estado(estadoActual);
                Punto puntoNuevo = nuevoEstado.puntoActual.mover(dir);
                nuevoEstado.add(puntoNuevo);
                //costos.put(nuevoEstado,(new Random()).nextInt(distanciaManhattam(nuevoEstado.get(0), puntoFinal)));
                Boolean estaEnExplorado = mapaProblema[puntoNuevo.y][puntoNuevo.x] != TIPO.VISITADA;
                Estado estaEnFrontera = estadoEnFrontera(frontera, puntoNuevo);
                        
                if(estaEnExplorado && estaEnFrontera == null)
                {
                    frontera.add(nuevoEstado);   
                    frontera.sort(comparar);                                                  
                }else{
                    if(estaEnFrontera != null)
                    {
                        if(getCostoGreedy(nuevoEstado, puntoFinal,null)<getCostoGreedy(estaEnFrontera, puntoFinal,null))
                        {
                            frontera.remove(estaEnFrontera);
                            frontera.add(nuevoEstado);     
                            frontera.sort(comparar);
                        }                        
                    }                    
                }
            }            
        }        
        return null;
    }
    
    private Integer getCostoGreedy(Estado estado,Punto pFinal,HashMap<Estado,Integer> costos)
    {
        return  estado.ruta.size() + distanciaManhattam(estado.puntoActual,pFinal);
    }
    
    private Estado estadoEnFrontera(Collection<Estado> frontera,Punto punto)
    {
        for(Estado ruta:frontera)
        {
            if(ruta.puntoActual.isEqual(punto))
            {
                return ruta;
            }
        }
        return null;
    }
    
    private Estado getEstadoNuevo(List<Estado> fronteraObs)
    {            
        Integer fronteraSize = fronteraObs.size();
        Estado estadoObtener = fronteraObs.get(0);     
        if(fronteraObs.size() < 3) return estadoObtener;
        
        Integer index = (new Random()).nextInt(fronteraSize/DIVFACTOR);
        
        int i = 0;
        for(Estado ruta:fronteraObs)
        {
            if(i>= index)
            {
                 return ruta;
            }
            i++;
        }        
        return estadoObtener;        
    }                   
}
