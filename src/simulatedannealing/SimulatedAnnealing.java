/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package simulatedannealing;

import java.awt.Point;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 *
 * @author Jauma
 */
public class SimulatedAnnealing {

    /**
     * @param args the command line arguments
     */
    static int nProd;
    static List<Double> pesos;
    static double [][] distancias;
    static double capacidad;
    static int nrArchivos = 3;
    
    static String delimitador_division = ",";
    static BufferedReader buffer = null;
    static String linea = "";    
    static PrintWriter pw;
    static StringBuilder sb = new StringBuilder();
    
    public static void main(String[] args) throws IOException {
        // TODO code application logic here                                       
        leer_en_lote();
    }    
    
     static void  leer_en_lote(){
        for(int i = 1; i <= nrArchivos; i++){
            String archivo = "dataset_tsp_" + i + ".csv"; 
            leer(archivo,i);
            System.out.println("==============================");
        }
    }
     
      static void leer_cabecera(BufferedReader buffer){
       try{
        //cantidad productos  
        String linea = buffer.readLine();
        String[] cabecera = linea.split(delimitador_division);
        nProd = Integer.valueOf(cabecera[1]) - 3;
        //cabecera
        linea = buffer.readLine();
       }
       catch (FileNotFoundException e){
           e.printStackTrace();
       }catch (IOException e){
           e.printStackTrace();
       }
    }
      
      static void leer (String archivo,int nrArchivo){
        String resultados = "";
        try{
            buffer = new BufferedReader(new FileReader(archivo));
            //lectura de cabecera del archivo
            leer_cabecera(buffer);
            //inicializacion del almacen        
            ArrayList<Point> productos = new ArrayList<Point>();            
            while( (linea = buffer.readLine()) != null){
                String[] producto_cadena = linea.split(delimitador_division);
                productos.add(new Point(Integer.valueOf(producto_cadena[1]),Integer.valueOf(producto_cadena[2])));                                                                
            }            
            distancias = new double[nProd+1][nProd+1];
            for(int i = 0;i<nProd+1;i++)
            {
                for(int j = i;j<nProd+1;j++)
                {
                    double distancia = DistanciaEuclidiana(productos.get(i),productos.get(j));
                    distancias[i][j] = distancia;
                    distancias[j][i] = distancia;                    
                }
            }                     
            pesos = new ArrayList<>();            
            for(int i = 0;i<nProd;i++) pesos.add(0.0);
            
            AlgorithmSA simulatedA = new AlgorithmSA(nProd, distancias,pesos, 100);                
            simulatedA.CorrerAlgoritmo();
            
            for (Ruta ruta : simulatedA.rutas)
            {
                ruta.RecalcularEstado();
                ruta.ImprimirRuta();
                ruta.ImprimirCosto();
                System.out.println();
            }
          
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
      
      private static double DistanciaEuclidiana(Point point1,Point point2)
      { 
          int xResult = Math.abs (point1.x - point2.x);
          int yResult = Math.abs (point1.y - point2.y);
          return  Math.sqrt((xResult)*(xResult) +(yResult)*(yResult));
      }      
}
