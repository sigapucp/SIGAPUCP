/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package simulatedannealing;


import java.util.AbstractMap;
import java.util.AbstractMap.SimpleEntry;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;


/**
 *
 * @author Jauma
 */
public class AlgorithmSA {
    
    // Representa a todo el mejor estado actual
    EstadoProblema estadoProblema;        
    ArrayList<Ruta> rutas;       
    ArrayList<ProductoNodo> productos;
    HashMap<ArrayList<Ruta>, EstadoProblema> rutaAEstado;
    double[][] _distancias;
        
    public AlgorithmSA(int nPedidos, double [][] distancias,List<Double> pesos, double capacidad)
    {       
        Inicializar(nPedidos,pesos,distancias);
        ConfiguracionInicial(distancias,capacidad);                        
    }
    
    public void CorrerAlgoritmo()
    {           
        double reduccionTemperatura = 0.93;
        double multiplicadorDeIteracion = 1.03;
        double iteracionesPorParametro = 7;
        
        double temperatura = 5000;
        ArrayList<Ruta> estadoActual = new ArrayList<>();
        ClonarEstado(rutas,estadoActual);      
        rutaAEstado.put(estadoActual, estadoActual.get(0)._estadoProblema);
        
        double costoActual  = GetCosto(estadoActual);
        double costoMejor = GetCosto(rutas);
        
        double time = 0.0;
        
        do {            
            double iteraciones = iteracionesPorParametro;
            do {           
                
                ArrayList<Ruta> nuevoEstado = new ArrayList<>();
                GenerarVecino(nuevoEstado,estadoActual);                
                
                double nuevoCosto = GetCosto(nuevoEstado);
                double deltaCosto = nuevoCosto - costoActual;
                if(deltaCosto < 0)
                {
                    rutaAEstado.remove(estadoActual);
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
                        rutaAEstado.remove(estadoActual);
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
    
    private void GenerarVecino(ArrayList<Ruta> vecino,ArrayList<Ruta> rutas)
    {        
        ClonarEstado(rutas,vecino);       
        rutaAEstado.put(vecino,vecino.get(0)._estadoProblema);  
        
        EstadoProblema estado = rutaAEstado.get(vecino);
      
        int nrRutas = vecino.size();   
        int nintentos = 7;
        Boolean optMejora = false;
        
        while(nintentos>0 && !optMejora)
        {          
            if(nrRutas > 1)
            {
                // Modificaciones solo en una ruta
                optMejora = IntraRouteOpt(vecino,estado);            
            }else
            {
                // Modificaciones en varias rutas
                optMejora = InterRouteOpt(vecino, estado);            
            }                 
            nintentos--;
        }
                
        HighestAverage(vecino,estado);                       
        
        for(Ruta ruta : vecino)
        {
            if(ruta.EsEstadoSucio()) ruta.RecalcularEstado();
        }                
    }
    
    public Boolean InterRouteOpt(ArrayList<Ruta> vecino,EstadoProblema estado)
    {
        int nrRutas = vecino.size();        
        int nrRuta = ThreadLocalRandom.current().nextInt(0, nrRutas);
        int ruta1Size = vecino.get(nrRuta).GetNrPuntos();
        
        if(ruta1Size <= 3) return false;
        Ruta ruta = vecino.get(nrRuta);        
        int ruta2Size = ruta.GetNrPuntos();
        
        int nrPunto1 = ThreadLocalRandom.current().nextInt(1, ruta1Size);
        int nrPunto2 = ThreadLocalRandom.current().nextInt(nrPunto1, ruta2Size);
        
        ProductoNodo nodo1 = ruta.GetPunto(nrPunto1);              
        ProductoNodo nodo2 = ruta.GetPunto(nrPunto2);      
        
        ProductoNodo nodo1Ant = nodo1.ant;       
        ProductoNodo nodo2Sig = nodo2.sig;
        
        double distanciaActual = 0.0;
        double distanciaNueva = 0.0;                                
        
        if(nodo1.ant!=null) distanciaActual += _distancias[nodo1.GetLlave()][nodo1.ant.GetLlave()];        
        if(nodo2.sig!=null) distanciaActual += _distancias[nodo1.GetLlave()][nodo2.sig.GetLlave()];        
                        
        if(nodo2Sig!=null) distanciaNueva += _distancias[nodo1.GetLlave()][nodo2Sig.GetLlave()];        
        if(nodo1Ant!=null) distanciaNueva += _distancias[nodo2.GetLlave()][nodo1Ant.GetLlave()];  
        
        if(distanciaActual < distanciaNueva) return false;
                        
        if(estado.GetNodoFinal() != nodo2) estado.TwoOpt(nodo1, nodo2, vecino, nrRuta, nrRuta);      
        return true;
    }
    
    public Boolean IntraRouteOpt(ArrayList<Ruta> vecino,EstadoProblema estado)
    {
        int nrRutas = vecino.size();  
        
        int nrRuta1 = ThreadLocalRandom.current().nextInt(0, nrRutas);
        int nextRange = ThreadLocalRandom.current().nextInt(1,3);
        int nextRutaLimite = ((nrRuta1 + nextRange)> nrRutas) ? (nrRutas)  : (nrRuta1 + nextRange);
        int nrRuta2 = ThreadLocalRandom.current().nextInt(nrRuta1, nextRutaLimite);
        if(nrRuta2 == nrRuta1)
        {           
            return InterRouteOpt(vecino, estado);            
        }                        
        
        int ruta1Size = vecino.get(nrRuta1).GetNrPuntos();
        int ruta2Size = vecino.get(nrRuta2).GetNrPuntos();                
        
        int nrPunto1 = ThreadLocalRandom.current().nextInt(1, ruta1Size);        
        int nrPunto2 = ThreadLocalRandom.current().nextInt(1, ruta2Size);
        
        ProductoNodo nodo1 = vecino.get(nrRuta1).GetPunto(nrPunto1);              
        ProductoNodo nodo2 = vecino.get(nrRuta2).GetPunto(nrPunto2);          
        
        ProductoNodo nodo1Ant = nodo1.ant;       
        ProductoNodo nodo2Sig = nodo2.sig;
        
        double distanciaActual = 0.0;
        double distanciaNueva = 0.0;                                
        
        if(nodo1.ant!=null) distanciaActual += _distancias[nodo1.GetLlave()][nodo1.ant.GetLlave()];        
        if(nodo2.sig!=null) distanciaActual += _distancias[nodo1.GetLlave()][nodo2.sig.GetLlave()];        
                        
        if(nodo2Sig!=null) distanciaNueva += _distancias[nodo1.GetLlave()][nodo2Sig.GetLlave()];        
        if(nodo1Ant!=null) distanciaNueva += _distancias[nodo2.GetLlave()][nodo1Ant.GetLlave()];        
        
        if(distanciaActual < distanciaNueva) return false;
                
        if(estado.GetNodoFinal() != nodo2) estado.TwoOpt(nodo1, nodo2, vecino, nrRuta1, nrRuta2);                    
        return true;
    }
    
    public void HighestAverage(ArrayList<Ruta> vecino,EstadoProblema estado)
    {      
        ArrayList<AbstractMap.SimpleEntry<ProductoNodo,Ruta>> productoNodos = new ArrayList<>();
        int nrRutas = vecino.size();
                        
        for(Ruta ruta : vecino)
        {
            ProductoNodo temp = ruta.GetNodoInicial();
            ProductoNodo nodoFinal = ruta.GetNodoActual();
            while(temp!=nodoFinal)
            {
                if(!temp.EsDeposito()) productoNodos.add(new SimpleEntry<>(temp,ruta));
                temp = temp.sig;
            }            
        }
                
        Collections.sort(productoNodos, (SimpleEntry<ProductoNodo,Ruta> o1, SimpleEntry<ProductoNodo,Ruta> o2) -> {
            double dist1 = o1.getKey().GetDistanciaMedia(_distancias[o1.getKey().GetLlave()]);
            double dist2 = o2.getKey().GetDistanciaMedia(_distancias[o2.getKey().GetLlave()]);
            if(dist1 > dist2) return -1;
            if(dist1 < dist2) return 1;
            return 0;
        });

          int nrProductos = productos.size();
          int nrInsetar = (nrProductos*0.2>5) ? 5 : (int)((int)(nrProductos*0.2));
          
          //HEY
          
          for(int i = 0;i<nrInsetar;i++)
          {
              int nrRuta = ThreadLocalRandom.current().nextInt(0,nrRutas);
              Ruta ruta = vecino.get(nrRuta);
              int nrInsertar = ThreadLocalRandom.current().nextInt(0,ruta.GetNrPuntos());
              SimpleEntry<ProductoNodo,Ruta> entrada = productoNodos.get(i);
              ProductoNodo nodo = entrada.getKey();              
              entrada.getValue().RemoverNodo(nodo);              
              estado.deleteNode(nodo);
              ruta.AgregarEn(nodo, nrInsertar);
          }          
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
    
    private void ClonarEstado(ArrayList<Ruta> rutas,ArrayList<Ruta> nuevaRutas)
    {
        EstadoProblema nuevoEstado = new EstadoProblema();
        EstadoProblema a = rutaAEstado.get(rutas);
        HashMap<ProductoNodo,Integer> depotAIndex = new HashMap<>();
        rutaAEstado.get(rutas).clone(nuevoEstado,depotAIndex);                           
        for(Ruta ruta : rutas)
        {
            nuevaRutas.add((Ruta)ruta.Clonar(nuevoEstado,depotAIndex));
        }                      
    }
    
    private void Inicializar(int nPedidos,List<Double> pesos,double [][] distancias)            
    {        
        productos = new ArrayList<>();     
        rutas = new ArrayList<>();
        estadoProblema = new EstadoProblema();
        rutaAEstado = new HashMap<>();
        _distancias = distancias;
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
                if(!producto.GetVisitado() && (nuevaRuta.GetPeso() + producto.GetPeso()) <= capacidad)
                {
                    producto.SetVisitado(true);                                                                             
                    nuevaRuta.AgregarPunto(producto);                    
                }
            }
            nRutas++;            
        } while (!productos.stream().allMatch(i -> i.GetVisitado())); 
        
        ProductoNodo nuevaPuerta = new ProductoNodo(0,"Depot" , 0.0, true);      
        nuevaRuta.AgregarPunto(nuevaPuerta);      
        rutaAEstado.put(rutas, estadoProblema);  
    }        
}

