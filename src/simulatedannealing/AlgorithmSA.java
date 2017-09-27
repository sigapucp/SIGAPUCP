/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package simulatedannealing;


import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;


/**
 *
 * @author Jauma
 */
public class AlgorithmSA {
    
    // Representa a todo el mejor estado actual
    LinkedStateList estadoProblema;        
    ArrayList<Ruta> rutas;       
    ArrayList<ProductoNodo> productos;
    
    
    public AlgorithmSA(int nPedidos,int nEmpleados, double [][] distancias,List<Double> pesos, double capacidad)
    {       
        Inicializar(nPedidos,pesos);
        ConfiguracionInicial(distancias,capacidad);                        
    }
    
    public void CorrerAlgoritmo()
    {           
        double reduccionTemperatura = 0.93;
        double multiplicadorDeIteracion = 1.05;
        double iteracionesPorParametro = 5;
        
        double temperatura = 5000;
        ArrayList<Ruta> estadoActual = (ArrayList<Ruta>)ClonarEstado(rutas);        
        
        double costoActual  = GetCosto(estadoActual);
        double costoMejor = GetCosto(rutas);
        
        double time = 0.0;
        
        do {            
            double iteraciones = iteracionesPorParametro;
            do {           
                
                ArrayList<Ruta> nuevoEstado = GenerarVecino(estadoActual);
                double nuevoCosto = GetCosto(nuevoEstado);
                double deltaCosto = nuevoCosto - costoActual;
                if(deltaCosto < 0)
                {
                    estadoActual = nuevoEstado;
                    costoActual = nuevoCosto;
                    
                    if(costoActual < costoMejor)
                    {
                        rutas = estadoActual;
                        estadoProblema = rutas.get(0)._estadoProblema;
                        costoMejor = costoActual;
                    }
                }else
                {
                    if( ThreadLocalRandom.current().nextInt(0, 1) < Math.exp(-deltaCosto/temperatura))
                    {
                        estadoActual = nuevoEstado;
                        costoActual = nuevoCosto;
                    }                    
                }
                iteraciones--;
            } while (iteraciones >= 0);
            temperatura = reduccionTemperatura*temperatura;
            iteracionesPorParametro = multiplicadorDeIteracion*iteracionesPorParametro;
        } while (temperatura > 0.0001);
    }
    
    private ArrayList<Ruta> GenerarVecino(ArrayList<Ruta> estado)
    {
        ArrayList<Ruta> vecino = (ArrayList<Ruta>)ClonarEstado(estado);
        return vecino;        
    }
    
    public double GetCosto(ArrayList<Ruta> rutas)
    {
        double cost = 0.0;
        for(Ruta ruta : rutas)
        {
            cost += ruta.GetDistancia();
        }
        return cost;
    }
    
    private ArrayList<Ruta> ClonarEstado(ArrayList<Ruta> estado)
    {
        LinkedStateList nuevoEstado = estadoProblema.clone();        
        ArrayList<Ruta> nuevaRuta = new ArrayList<>();
        
        for(Ruta ruta : estado)
        {
            nuevaRuta.add((Ruta)ruta.Clonar(nuevoEstado));
        }               
        return nuevaRuta;
    }
    
    private void Inicializar(int nPedidos,List<Double> pesos)            
    {        
        productos = new ArrayList<>();     
        rutas = new ArrayList<>();
        estadoProblema = new LinkedStateList();
        for(int i = 0; i<nPedidos; i++)
        {
            productos.add(new ProductoNodo(i+1, "Prod" + String.valueOf(i+1), pesos.get(i),false));           
        }                       
    }        
    
    private void ConfiguracionInicial(double [][] distancias,double capacidad)
    {
        int nRutas = 0;
        Ruta nuevaRuta = null;
        do {     
            ProductoNodo nuevaPuerta = new ProductoNodo(0,"Depot" , 0.0, true);
            if(nuevaRuta != null) 
            {
                nuevaRuta.AgregarPunto(nuevaPuerta);
            }else
            {
                estadoProblema.addAtEnd(nuevaPuerta);                
            }
                        
            nuevaRuta = new Ruta(estadoProblema,distancias,"Ruta" + String.valueOf(nRutas), nRutas, nuevaPuerta);            
            rutas.add(nuevaRuta);            
                                         
            for (ProductoNodo producto : productos) {
                if(!producto.GetVisitado() && (nuevaRuta.GetPeso() + producto.GetPeso()) < capacidad)
                {
                    producto.SetVisitado(true);                                         
                    nuevaRuta.IncrementarPeso(producto.GetPeso());                    
                    nuevaRuta.AgregarPunto(producto);                    
                }
            }
            nRutas++;            
        } while (!productos.stream().allMatch(i -> i.GetVisitado())); 
        
        ProductoNodo nuevaPuerta = new ProductoNodo(0,"Depot" , 0.0, true);      
        nuevaRuta.AgregarPunto(nuevaPuerta);                    
    }        
}
