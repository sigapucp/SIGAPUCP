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
public class Poblacion {
    Ruta[] rutas;
    
    public int cantidad_poblacion(){
        return rutas.length;
    }
    
    public void guardar_ruta(int indice, Ruta ruta){
        rutas[indice] = ruta;
    }
    
    public Poblacion (int cantidad_poblacion, boolean inicializado){
        rutas = new Ruta[cantidad_poblacion];
        //si se necesita inicializar una poblacion
        if (inicializado){
            for(int indice_poblacion = 0; indice_poblacion < cantidad_poblacion; indice_poblacion++){
                Ruta nueva_ruta = new Ruta();
                nueva_ruta.iniciar_ruta();
                guardar_ruta(indice_poblacion, nueva_ruta);
            }
        }
    }
    
    public Ruta ruta_de_poblacion(int indice){
        return rutas[indice];
    }
    
    public Ruta obtener_la_mejor_ruta_de_poblacion(){
        Ruta posible_ruta_optima = rutas[0];
        for (int indice_rutas = 1; indice_rutas < cantidad_poblacion(); indice_rutas++){
            if (posible_ruta_optima.obtener_objetivo_ruta() <= ruta_de_poblacion(indice_rutas).obtener_objetivo_ruta()){
                posible_ruta_optima = ruta_de_poblacion(indice_rutas);
            }
        }
        return posible_ruta_optima;
    }
    
    
}
