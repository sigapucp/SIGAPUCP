/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package algoritmo_genetico;

/**
 *
 * @author alulab14
 */
public class Operaciones {
    
    public static double distancia_euclidiana(int valor_1, int valor_2){
        return Math.sqrt( (valor_1*valor_1) + (valor_2*valor_2) );
    }
    
    public static int calcular_posicion_aleatoria(int cantidad){
        return (int) (Math.random() * cantidad);
    }

    public static double funcion_objetivo(int sumatoria){
        return ( 1/(double)sumatoria );
    }
}
