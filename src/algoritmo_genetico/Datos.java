/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package algoritmo_genetico;
import java.io.FileNotFoundException;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.PrintWriter;
import java.io.File;
import java.io.IOException;

/**
 *
 * @author Gustavo
 */
public class Datos {
    int cantidad_conjunto_datos = 40;
    int cantidad_generaciones = 100;
    String cantidad_productos;
    String direccion_archivos = "I:\\genetico\\SIGAPUCP\\dataset\\dataSet_medium";
    String archivo_resultado = "I:\\genetico\\SIGAPUCP\\dataset\\dataSet_medium\\resultados.csv";
    BufferedReader buffer = null;
    String linea = "";
    String delimitador_division = ",";
    PrintWriter pw;
    StringBuilder sb = new StringBuilder();
    
    
    void leer_cabecera(BufferedReader buffer){
       try{
        //cantidad productos  
        linea = buffer.readLine();
        String[] cabecera = linea.split(delimitador_division);
        cantidad_productos = cabecera[1];
        //cabecera
        linea = buffer.readLine();
       }
       catch (FileNotFoundException e){
           e.printStackTrace();
       }catch (IOException e){
           e.printStackTrace();
       }
    }
    void leer_en_lote(){
        for(int i = 1; i <= cantidad_conjunto_datos; i++){
            String archivo = direccion_archivos + "\\dataSet_medium_" + i + ".csv"; 
            leer(archivo);
            System.out.println(i + "==============================");
        }
    }
    void leer (String archivo){
        String resultados = "";
        try{
            buffer = new BufferedReader(new FileReader(archivo));
            //lectura de cabecera del archivo
            leer_cabecera(buffer);
            //inicializacion del almacen
            
            Almacen almacen = new Almacen();
            while( (linea = buffer.readLine()) != null){
                String[] producto_cadena = linea.split(delimitador_division);
                Producto producto_nuevo = new Producto(Integer.parseInt(producto_cadena[1]), Integer.parseInt(producto_cadena[2]));
                almacen.agregar_producto(producto_nuevo);
                //System.out.println("coordenada x " +  productos[1] + " " + "coordenada y "+ productos[2] );
            }
            Ruta ruta = new Ruta();
            Poblacion poblacion = new Poblacion(Integer.parseInt(cantidad_productos), true);
            resultados = poblacion.obtener_la_mejor_ruta_de_poblacion().obtener_distancia_total_a_recorrer() + ",";
            poblacion = Genetico.evolucion(poblacion);
            for (int indice_generacion = 0; indice_generacion < cantidad_generaciones; indice_generacion++){
                poblacion = Genetico.evolucion(poblacion);
            }
            resultados += poblacion.obtener_la_mejor_ruta_de_poblacion().obtener_distancia_total_a_recorrer() + "";
            //agregar_resultado("" + poblacion.obtener_la_mejor_ruta_de_poblacion().obtener_distancia_total_a_recorrer());
            agregar_resultado(resultados);
            //System.out.println("Distancia final" + );
            //System.out.println("Solucion");
            System.out.println(poblacion.obtener_la_mejor_ruta_de_poblacion().mostrar_ruta());
        }
        catch (FileNotFoundException e){
            e.printStackTrace();
        } catch (IOException e){
            e.printStackTrace();
        }finally{
            if (buffer != null){
                try{
                    buffer.close();
                }catch (IOException e){
                    e.printStackTrace();
                }
            }
        }
    }
    void agregar_resultado(String datos) {
        String [] resultados = datos.split(",");
        sb.append(resultados[0]);
        sb.append(",");
        sb.append(resultados[1]);
        sb.append("\n");
    }
    void preparar_reporte(boolean ultimo){
        try{
            PrintWriter pw = new PrintWriter(archivo_resultado);        
            if (ultimo){
                pw.write(sb.toString());        
                pw.close();
            }
            else{
                StringBuilder sb = new StringBuilder();                  
            }
        }catch (FileNotFoundException e){
            e.printStackTrace();
        }
    }
}
