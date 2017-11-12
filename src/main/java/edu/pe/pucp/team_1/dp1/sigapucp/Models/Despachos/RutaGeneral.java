/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.pe.pucp.team_1.dp1.sigapucp.Models.Despachos;


import java.util.AbstractMap;
import java.util.AbstractMap.SimpleEntry;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;


/**
 *
 * @author Jauma
 */
public class RutaGeneral {
    
    // Representa a todo el mejor estado actual
    EstadoProblema estadoProblema;        
    ArrayList<Ruta> rutas;       
    ArrayList<ProductoNodo> productos;
    HashMap<ArrayList<Ruta>, EstadoProblema> rutaAEstado;
    double _capacidad = 0.0;
    double[][] _distancias;
        
    public RutaGeneral(int nPedidos, double [][] distancias,List<Double> pesos, double capacidad) throws Exception
    {       
        Inicializar(nPedidos,pesos,distancias);
        ConfiguracionInicial(distancias,capacidad);                        
    }
    
    public ArrayList<Ruta> getRutas()
    {
        return rutas;
    }
    
    public void CorrerAlgoritmo() throws Exception
    {           
        double reduccionTemperatura = 0.93;
        double multiplicadorDeIteracion = 1.02;
        double iteracionesPorParametro = 5;        
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
                    if( ThreadLocalRandom.current().nextDouble(0, 1) < Math.exp(-deltaCosto/temperatura))
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
    
    private void GenerarVecino(ArrayList<Ruta> vecino,ArrayList<Ruta> rutas) throws Exception
    {  
        ClonarEstado(rutas,vecino);       
        rutaAEstado.put(vecino,vecino.get(0)._estadoProblema);
                
        EstadoProblema estado = rutaAEstado.get(vecino);
      
        int nrRutas = vecino.size();   
        int nintentosRutas = 5;
        Boolean optMejora = false;
        
        while(nintentosRutas>0 && !optMejora)
        {          
            if(nrRutas > 1)
            {                
                // Modificaciones en varias rutas
                optMejora = IntraRouteOpt(vecino,estado,-1);    
                //HighestAverage(vecino,estado);                                              
            }else
            {
               // Modificaciones solo en una ruta
                optMejora = InterRouteOpt(vecino, estado);                           
            }                 
            nintentosRutas--;
        }                
        //HighestAverage(vecino,estado);                               
        for(Ruta ruta : vecino)
        {            
            ruta.RecalcularEstado();            
        }                          
    }
    
    public Boolean InterRouteOpt(ArrayList<Ruta> vecino,EstadoProblema estado) throws Exception
    {
        try {            
            int nrRutas = vecino.size();        
            int nrRuta = ThreadLocalRandom.current().nextInt(0, nrRutas);
            Ruta ruta = vecino.get(nrRuta);        
            int rutaSize = ruta.GetNrPuntos();

            if(rutaSize <= 3) return false;

            int nrPunto1 = ThreadLocalRandom.current().nextInt(1, rutaSize-1);
            int nrPunto2 = ThreadLocalRandom.current().nextInt(nrPunto1, rutaSize-1);

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
        } catch (Exception e) {
            throw new Exception("InterRouteOpt: " + e.getMessage());
        }        
    }
    
    public Boolean IntraRouteOpt(ArrayList<Ruta> vecino,EstadoProblema estado,int nrIntentos) throws Exception
    {
        int nrRutas = vecino.size();          
        try {
            int nrRuta1 = ThreadLocalRandom.current().nextInt(0, nrRutas);
            int nextRange = ThreadLocalRandom.current().nextInt(1,3);
            int nextRutaLimite = ((nrRuta1 + nextRange)> nrRutas) ? (nrRutas)  : (nrRuta1 + nextRange);
            int nrRuta2 = ThreadLocalRandom.current().nextInt(nrRuta1, nextRutaLimite);

            if(nrRuta2 == nrRuta1)
            {           
                return InterRouteOpt(vecino, estado);                                     
            }                        

            ArrayList<ProductoNodo> ruta1NodosPorProbar = new ArrayList<>();
            ArrayList<ProductoNodo> ruta2NodosPorProbar = new ArrayList<>();

            vecino.get(nrRuta1).ClonarListaTwoOpt(ruta1NodosPorProbar);
            vecino.get(nrRuta2).ClonarListaTwoOpt(ruta2NodosPorProbar);

            while(!ruta1NodosPorProbar.isEmpty()&&nrIntentos!=0)
            {
                int nrPunto1 = ThreadLocalRandom.current().nextInt(0, ruta1NodosPorProbar.size());        
                int nrPunto2 = ThreadLocalRandom.current().nextInt(0, ruta2NodosPorProbar.size());

                ProductoNodo nodo1 = ruta1NodosPorProbar.get(nrPunto1);              
                ProductoNodo nodo2 = ruta2NodosPorProbar.get(nrPunto2);          

                if(ProbarConsistenciaPeso(nodo1,nodo2)&&ProbarMejoraDistancia(nodo1,nodo2)&&estado.GetNodoFinal() != nodo2)
                {
                    estado.TwoOpt(nodo1, nodo2, vecino, nrRuta1, nrRuta2);                       
                    return true;
                } 
                ruta1NodosPorProbar.remove(nodo1);
                ruta2NodosPorProbar.remove(nodo2);

                if(ruta2NodosPorProbar.isEmpty()) 
                { 
                    ruta2NodosPorProbar = new ArrayList<>();
                    vecino.get(nrRuta2).ClonarListaTwoOpt(ruta2NodosPorProbar);
                }            
                nrIntentos--;
            }                                               
            return false;            
        } catch (Exception e) {
            throw new Exception("IntraRouteOpt: " + e.getMessage());
        }
      
    }
    
    private Boolean ProbarMejoraDistancia(ProductoNodo nodo1,ProductoNodo nodo2) throws Exception
    {
        try {
            ProductoNodo nodo1Ant = nodo1.ant;                   
            ProductoNodo nodo2Sig = nodo2.sig;

            double distanciaActual = 0.0;
            double distanciaNueva = 0.0;                                

            if(nodo1.ant!=null) distanciaActual += _distancias[nodo1.GetLlave()][nodo1.ant.GetLlave()];        
            if(nodo2.sig!=null) distanciaActual += _distancias[nodo1.GetLlave()][nodo2.sig.GetLlave()];        

            if(nodo2Sig!=null) distanciaNueva += _distancias[nodo1.GetLlave()][nodo2Sig.GetLlave()];        
            if(nodo1Ant!=null) distanciaNueva += _distancias[nodo2.GetLlave()][nodo1Ant.GetLlave()];        

            if(distanciaActual < distanciaNueva) return false;
            return true;        
        } catch (Exception e) {
            throw new Exception("ProbarMejoraDistancia: " + e.getMessage());
        }        
    }
    
    private Boolean ProbarConsistenciaPeso(ProductoNodo nodo1,ProductoNodo nodo2) throws Exception
    {
        double pesoRuta1 = 0;
        double pesoRuta2 = 0;
        try {
            ProductoNodo ruta1TempIzquierda = nodo1.ant;        
            ProductoNodo ruta1TempDerecha = nodo2;

            ProductoNodo ruta2TempIzquierda = nodo1;        
            ProductoNodo ruta2TempDerecha = nodo2.sig;

            while(ruta1TempIzquierda!=null&&!ruta1TempIzquierda.EsDeposito())
            {
                pesoRuta1 += ruta1TempIzquierda.GetPeso();
                ruta1TempIzquierda = ruta1TempIzquierda.ant;
            }

            while(ruta1TempDerecha!=null&&!ruta1TempDerecha.EsDeposito())
            {
                pesoRuta1 += ruta1TempDerecha.GetPeso();
                ruta1TempDerecha = ruta1TempDerecha.ant;
            }

            if(pesoRuta1 > _capacidad) return false;

            while(ruta2TempIzquierda!=null&&!ruta2TempIzquierda.EsDeposito())
            {
                pesoRuta2 += ruta2TempIzquierda.GetPeso();
                ruta2TempIzquierda = ruta2TempIzquierda.sig;
            }

            while(ruta2TempDerecha!=null&&!ruta2TempDerecha.EsDeposito())
            {
                pesoRuta2 += ruta2TempDerecha.GetPeso();
                ruta2TempDerecha = ruta2TempDerecha.sig;
            }
            if(pesoRuta2 > _capacidad) return false;
            return true;            
        } catch (Exception e) {
            throw new Exception("ProbarConsistenciaPeso: " + e.getMessage());
        }        
    }
    
    public void HighestAverage(ArrayList<Ruta> vecino,EstadoProblema estado) throws Exception
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
    
    private void ClonarEstado(ArrayList<Ruta> rutas,ArrayList<Ruta> nuevaRutas) throws Exception
    {
        EstadoProblema nuevoEstado = new EstadoProblema();       
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
        if(pesos.isEmpty())
        {
            for(int i = 0;i<nPedidos;i++)
            {
                pesos.add(0.0);
            }
        }
        for(int i = 0; i<nPedidos; i++)
        {
            productos.add(new ProductoNodo(i+1, "Prod" + String.valueOf(i+1), pesos.get(i),false));           
        }                       
    }        
    
    private void ConfiguracionInicial(double [][] distancias,double capacidad) throws Exception
    {
        int nRutas = 0;
        _capacidad = capacidad;
        Ruta nuevaRuta = null;
        try {
            do {     
                ProductoNodo nuevaPuerta = new ProductoNodo(0,"Depot" , 0.0, true);
                if(nuevaRuta != null) 
                {
                    nuevaRuta.AgregarPunto(nuevaPuerta);
                }else
                {
                    estadoProblema.addAtEnd(nuevaPuerta);                
                }

                nuevaRuta = new Ruta(estadoProblema,distancias,"Ruta" + String.valueOf(nRutas), nRutas, nuevaPuerta,capacidad);            
                rutas.add(nuevaRuta);            

                if(estadoProblema.GetNodoFinal().EsDeposito())
                {
                    ProductoNodo deposito = estadoProblema.GetNodoFinal();
                    Collections.sort(productos,new Comparator<ProductoNodo>() {
                        @Override
                        public int compare(ProductoNodo o1, ProductoNodo o2) {
                            double dist1 = _distancias[deposito.GetLlave()][o1.GetLlave()];
                            double dist2 = _distancias[deposito.GetLlave()][o2.GetLlave()];
                            if(dist1 > dist2) return -1;
                            if(dist1 < dist1) return 1;
                            return 0;
                        }
                    });
                }else
                {
                    ProductoNodo antNodo = estadoProblema.GetNodoFinal();
                     Collections.sort(productos,new Comparator<ProductoNodo>() {

                        @Override
                        public int compare(ProductoNodo o1, ProductoNodo o2) {
                            double dist1 = _distancias[antNodo.GetLlave()][o1.GetLlave()];
                            double dist2 = _distancias[antNodo.GetLlave()][o2.GetLlave()];
                            if(dist1 > dist2) return 1;
                            if(dist1 < dist1) return -1;
                            return 0;
                        }
                    });
                }
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
        } catch (Exception e) {
            throw new Exception("ConfiguracionInicial: " + e.getMessage());
        }        
    }                
}






