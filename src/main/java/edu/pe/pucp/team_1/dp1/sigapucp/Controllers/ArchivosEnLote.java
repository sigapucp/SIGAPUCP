/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.pe.pucp.team_1.dp1.sigapucp.Controllers;

import edu.pe.pucp.team_1.dp1.sigapucp.Models.Ventas.Cliente;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

/**
 *
 * @author Gustavo
 */
public class ArchivosEnLote {
    
    private String direccion_archivo;
    
    private static void leer_cabecera(BufferedReader buffer){
       String linea;
       try{
            linea = buffer.readLine();
       }
       catch (FileNotFoundException e){
           e.printStackTrace();
       }catch (IOException e){
           e.printStackTrace();
       }
    }

    private static void crear_objeto(String[] valores_cadena, String modelo){
        try{
            if (modelo == "cliente"){
                Cliente nuevo_cliente = new Cliente();
                //TODO
                //nuevo_cliente.asignar_atributos(valores_cadena[0], ...);
                if ( nuevo_cliente.saveIt()){
                    System.out.println("todo Ok");
                }
            }
        }
        catch(Exception e){
            System.out.println(e);
        }        
    }
    
    private static void leer_archivo(BufferedReader buffer, String modelo){
       String linea;
       String delimitador_division = ",";
       try{
           while( (linea = buffer.readLine()) != null){
                String[] valores_cadena = linea.split(delimitador_division);
                crear_objeto(valores_cadena, modelo);
           }
       }
       catch (FileNotFoundException e){
           e.printStackTrace();
       }catch (IOException e){
           e.printStackTrace();
       }        
    }
    

    public static void crear_en_lote(String modelo){
        //String archivo = direccion_archivo + "prueba" + ".csv"; 
        //TODO
        String archivo = "";
        String valores = "";
        BufferedReader buffer = null;
        try{
           buffer = new BufferedReader( new FileReader(archivo));
           leer_cabecera(buffer);
           leer_archivo(buffer, modelo) ;
            
        }catch (Exception e){
                
        }
    }
    
}
