/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package algoritmo_genetico;

import java.util.ArrayList;

/**
 *
 * @author Gustavo
 */
public class Almacen {
    //colleccion de muchos productos en un almacen de un pedido
    private static ArrayList almacen = new ArrayList<Producto>();
    
    public static void agregar_producto(Producto producto){
        almacen.add(producto);
    }
    
    //entiendase como la cantidad de productos que son parte del pedido 
    //que se tomaran para el calculo del algoritmo
    public static int cantidad_de_productos(){
        return almacen.size();
    }
    
    public static Producto obtener_producto(int posicion){
        return (Producto)almacen.get(posicion);
    }
}
