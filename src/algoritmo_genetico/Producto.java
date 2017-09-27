/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package algoritmo_genetico;

/**
 *
 * @author Gustavo
 */
public class Producto {
    int coordenada_x;
    int coordenada_y;
    
    //constructor
    public Producto(int coordenada_x, int coordenada_y){
        this.coordenada_x = coordenada_x;
        this.coordenada_y = coordenada_y;
    }
    
    public int obtener_coordenada_x(){
        return this.coordenada_x;
    }
    
    public int obtener_coordenada_y(){
        return this.coordenada_y;
    }
    
    public double calcular_distancia_entre_productos(Producto producto){
        int distancia_entre_coordenadas_x = Math.abs(obtener_coordenada_x() - producto.obtener_coordenada_x() );
        int distancia_entre_coordenadas_y = Math.abs(obtener_coordenada_y() - producto.obtener_coordenada_y() );
        
        //calculo de la distancia
        double distancia_entre_puntos = Operaciones.distancia_euclidiana(distancia_entre_coordenadas_x, distancia_entre_coordenadas_y);
        
        return distancia_entre_puntos;
    }
    
    public String mostrar_posicion(){
        return obtener_coordenada_x() + "," + obtener_coordenada_y();
    }
    
}
