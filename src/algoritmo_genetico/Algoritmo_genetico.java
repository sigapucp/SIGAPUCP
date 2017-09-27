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
public class Algoritmo_genetico {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here
        Producto producto1 = new Producto(5, 5);
        Producto producto2 = new Producto(5, 10);
        Producto producto3 = new Producto(10, 10);
        Producto producto4 = new Producto(10, 5);        
        
        //System.out.println(producto1.mostrar_posicion());
        //System.out.println(producto2.mostrar_posicion());
        
        Almacen almacen = new Almacen();
        
        almacen.agregar_producto(producto1);
        almacen.agregar_producto(producto2);
        almacen.agregar_producto(producto3);
        almacen.agregar_producto(producto4);        
        
        System.out.println(almacen.cantidad_de_productos());
        
        Ruta ruta = new Ruta();
        
        ruta.iniciar_ruta();
        System.out.println(ruta.cantidad_productos_en_ruta());
        System.out.println(ruta.mostrar_ruta());
        System.out.println(ruta.obtener_distancia_total_a_recorrer());
        System.out.println(ruta.obtener_objetivo_ruta());
        
        //inicio de poblacion
        Poblacion poblacion = new Poblacion(10, true);
        
        //evolucion para 100 generaciones
        poblacion = Genetico.evolucion(poblacion);
        for (int indice_generacion = 0; indice_generacion < 100; indice_generacion++){
            poblacion = Genetico.evolucion(poblacion);
        }
        System.out.println("Distancia final" + poblacion.obtener_la_mejor_ruta_de_poblacion().obtener_distancia_total_a_recorrer());
        System.out.println("Solucion");
        System.out.println(poblacion.obtener_la_mejor_ruta_de_poblacion().mostrar_ruta());
        
    }
    
}
