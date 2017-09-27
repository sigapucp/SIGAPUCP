/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package algoritmo_genetico;

import java.util.ArrayList;
import java.util.Collections;

/**
 *
 * @author Gustavo
 */
public class Ruta {
    
    private ArrayList ruta = new ArrayList<Producto>();
    
    //funcion fitness
    private double objetivo = 0;
    private int distancia = 0;
    
    //ruta vacia inicial
    public Ruta(){
        for(int i = 0; i < Almacen.cantidad_de_productos(); i++){
            ruta.add(null);
        }
    }
    
    //ruta inicializada con ruta por defecto
    public Ruta(ArrayList ruta){
        this.ruta = ruta;
    }
    
    //en el arreglo de ruta
    // 0  1  2  3
    //[A, B, C, D]
    //se coloca en una posicion un producto
    public void colocar_turno_producto_en_ruta(int turno_en_ruta, Producto producto){
        ruta.set(turno_en_ruta, producto);
        //*
        //objetivo = 0;
        //distancia = 0;
    }
    
  
    public void iniciar_ruta(){
        //se agrega uno a uno los objetos del almacen a la ruta
        for(int indice_producto = 0; indice_producto < Almacen.cantidad_de_productos(); indice_producto++){
            colocar_turno_producto_en_ruta(indice_producto, Almacen.obtener_producto(indice_producto));
        }
        
        //se reordenan aleatoriamente
        Collections.shuffle(ruta);
    }
    
    public int cantidad_productos_en_ruta(){
        return ruta.size();
    }
    
    public Producto obtener_producto(int turno_producto){
        return (Producto)ruta.get(turno_producto);
    }
        
    public int obtener_distancia_total_a_recorrer(){
        if (distancia == 0){
            int distancia_total_a_recorrer = 0;
            //se recorre toda la ruta
            for(int indice_producto = 0; indice_producto < cantidad_productos_en_ruta(); indice_producto++){
                //se obtiene el producto del que se partio
                Producto producto_partida = obtener_producto(indice_producto);
                //producto a donde nos dirigiremos
                Producto producto_a_visitar;
                //verificamos que el producto a visitar no sea nuestra ultima ciudad
                //por lo que deberiamos marcar el viaje de regreso al punto de partida
                if (indice_producto + 1 < cantidad_productos_en_ruta()){
                    producto_a_visitar = obtener_producto(indice_producto + 1);
                }
                else{
                    //inicio
                    producto_a_visitar = obtener_producto(0);
                }
                //se calcula la distancia entre ambos puntos
                distancia_total_a_recorrer += producto_partida.calcular_distancia_entre_productos(producto_a_visitar);
            }
            distancia = distancia_total_a_recorrer;
        }
        return distancia;
    }
    
    public double obtener_objetivo_ruta(){
        //funcion objetivo
        //http://www.cs.huji.ac.il/~ai/projects/old/tsp2.pdf
        if (objetivo == 0){
            objetivo = Operaciones.funcion_objetivo(obtener_distancia_total_a_recorrer());
        }
        return objetivo;
    }
    
    public boolean no_contiene_producto(Producto producto){
        return !(ruta.contains(producto));
    }
    
    public String mostrar_ruta(){
        String ruta = "|";
        for(int indice_ruta = 0; indice_ruta < cantidad_productos_en_ruta(); indice_ruta++){
            ruta += obtener_producto(indice_ruta).mostrar_posicion() + "|";
        }
        return ruta;
    }
}
