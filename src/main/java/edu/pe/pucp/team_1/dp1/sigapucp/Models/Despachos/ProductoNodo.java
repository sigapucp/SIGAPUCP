/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.pe.pucp.team_1.dp1.sigapucp.Models.Despachos;

import java.awt.Point;
import java.text.DecimalFormat;

/**
 *
 * @author Jauma
 */
public class ProductoNodo {
    
    private final int _llave;
    private final String _nombre;
    private final double _peso;
    private final Boolean _esDeposito;
    private Boolean _visitado;         
    public ProductoNodo ant;
    public ProductoNodo sig;
    public Point coordenada; 
        
    public ProductoNodo(int llave,String nombre,double peso, Boolean esDeposito)
    {
        _llave = llave;
        if (nombre == null)
        {
            _nombre = "Prod" + llave;
        }else
        {
            _nombre = nombre;
        }                
        _visitado = false;
        _peso = peso;
        _esDeposito = esDeposito;        
        ant = null;
        sig = null;
    }
    
     public ProductoNodo(ProductoNodo nodo)
    {                
        _llave = nodo._llave;
        if (nodo._nombre == null)
        {
            _nombre = "Prod" + nodo._llave;
        }else
        {
            _nombre = nodo._nombre;
        }                
        _visitado = nodo._visitado;
        _peso = nodo._peso;
        _esDeposito = nodo._esDeposito;        
        ant = null;
        sig = null;
    }
    
    public Boolean GetVisitado() { return _visitado; }
    public void SetVisitado(Boolean valor) { _visitado = valor; }    
    public int GetLlave() { return _llave; }
    public Boolean EsDeposito() { return _esDeposito; }
    public double GetPeso() { return _peso ; }
    
    
    public double GetDistanciaMedia(double[] distancias) {
        
        double distMedia = 0;
        if(sig!=null)
        {
            distMedia += distancias[sig.GetLlave()];
        }
        
        if(ant!=null)
        {
            distMedia += distancias[ant.GetLlave()];
        }        
        return distMedia/2;
    }
        
    public void Imprimir()
    {
        System.out.print(_nombre + " ");
    }
    
    public void ImprimirInfo(double[] distancias)
    {
        System.out.print(_nombre + " " + new DecimalFormat("#.##").format(GetDistanciaMedia(distancias)) + "  ");
    }
    public void ImprimirLn()
    {
        System.out.println(_nombre);
    }
}
