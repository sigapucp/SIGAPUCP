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
public class Genetico {
    
    private static double razon_de_mutacion = 0.015;
    private static int cantidad_participantes_competencia = 5;
    private static boolean elitismo = true;
    
    
    public static Poblacion combinar_poblacion(Poblacion poblacion, int cantidad_participantes){
        Poblacion competencia = new Poblacion(cantidad_participantes_competencia, false);
        for(int i = 0; i < cantidad_participantes_competencia; i++){
            int indice_aleatorio = Operaciones.calcular_posicion_aleatoria(poblacion.cantidad_poblacion());
            competencia.guardar_ruta(i, poblacion.ruta_de_poblacion(indice_aleatorio));
        }
        return competencia;
    }
    public static Ruta seleccion_candidato( Poblacion poblacion){
        //concepto de la seleccion por tournament o competencia
        //http://www.cs.huji.ac.il/~ai/projects/old/tsp2.pdf
        //creacion de una competencia para la seleccion
        Poblacion competencia = new Poblacion(cantidad_participantes_competencia, false);
        //
        competencia = combinar_poblacion(poblacion, cantidad_participantes_competencia);
        //se selecciona el mas optimo
        Ruta ruta_optima = competencia.obtener_la_mejor_ruta_de_poblacion();
        return ruta_optima;
    }
    
    public static Ruta cruce(Ruta ancestro_1, Ruta ancestro_2){
        //creando una ruta descendiente
        Ruta descendiente = new Ruta();
        
        int posicion_inicial = Operaciones.calcular_posicion_aleatoria(ancestro_1.cantidad_productos_en_ruta());
        int posicion_final = Operaciones.calcular_posicion_aleatoria(ancestro_1.cantidad_productos_en_ruta());
        
        if (posicion_inicial > posicion_final){
            int temporal = posicion_inicial;
            posicion_inicial = posicion_final;
            posicion_final = temporal;
        }
        
        //iterando la ruta hija y agregando la sub ruta 
        //desde el padre hasta nuestro hijo creado
        for(int i = posicion_inicial; i <= posicion_final; i++){
            descendiente.colocar_turno_producto_en_ruta(i, ancestro_1.obtener_producto(i));
        }
        
        for(int i = 0; i < ancestro_2.cantidad_productos_en_ruta(); i++){
            
            if (descendiente.no_contiene_producto(ancestro_2.obtener_producto(i))){
                for (int indice_descendiente = 0; indice_descendiente < descendiente.cantidad_productos_en_ruta(); indice_descendiente++){
                    if (descendiente.obtener_producto(indice_descendiente) == null) {
                        descendiente.colocar_turno_producto_en_ruta(indice_descendiente, ancestro_2.obtener_producto(i));
                        break;
                    }
                }
            }
        }
        return descendiente;
    }
    
    //mutacion por intercambio
    public static void mutar ( Ruta ruta){
        // idea de mutacion
        //https://www.researchgate.net/publication/285690217_Comparison_of_parents_selection_methods_of_genetic_algorithm_for_TSP
        for (int indice_ruta = 0; indice_ruta < ruta.cantidad_productos_en_ruta(); indice_ruta++){
            //razon de mutacion
            if ( Math.random() < razon_de_mutacion) {
                //segunda posicion aleatoria
                int nuevo_indice_ruta = Operaciones.calcular_posicion_aleatoria(ruta.cantidad_productos_en_ruta());
                
                //productos provenientes de las nuevas rutas
                Producto producto_1 = ruta.obtener_producto(indice_ruta);
                Producto producto_2 = ruta.obtener_producto(nuevo_indice_ruta);
                
                //intercambio
                ruta.colocar_turno_producto_en_ruta(nuevo_indice_ruta, producto_1);
                ruta.colocar_turno_producto_en_ruta(indice_ruta, producto_2);
            }
        }
    }
    
    public static Poblacion evolucion(Poblacion poblacion){
        Poblacion nueva_poblacion = new Poblacion(poblacion.cantidad_poblacion(), false);
        
        //se guarda nuestro mejor individuo si elitismo esta activado
        //concepto de seleccion por elitismo
        //http://www.obitko.com/tutorials/genetic-algorithms/selection.php
        int bandera_elitismo = 0;
        if (elitismo) {
            nueva_poblacion.guardar_ruta(0, poblacion.obtener_la_mejor_ruta_de_poblacion());
            bandera_elitismo = 1;
        }
        
        //cruce de poblaciones
        for (int indice_poblacion = bandera_elitismo; indice_poblacion < nueva_poblacion.cantidad_poblacion(); indice_poblacion++){
            //seleccion de padres
            Ruta ancestro_1 = seleccion_candidato(poblacion);
            Ruta ancestro_2 = seleccion_candidato(poblacion);
            //cruce de padres
            Ruta descendiente = cruce(ancestro_1, ancestro_2);
            //se agrega el hijo a la nueva poblacion
            nueva_poblacion.guardar_ruta(indice_poblacion, descendiente);
        }
        
        //mutando la nueva poblacion para agregar un nuevo material genetico
        for(int indice_elitismo = 1; indice_elitismo < nueva_poblacion.cantidad_poblacion(); indice_elitismo++){
            mutar(nueva_poblacion.ruta_de_poblacion(indice_elitismo));
        }
        
        return nueva_poblacion;
    }
 
}
