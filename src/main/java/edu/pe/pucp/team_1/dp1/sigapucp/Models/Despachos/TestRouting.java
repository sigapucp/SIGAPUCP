/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.pe.pucp.team_1.dp1.sigapucp.Models.Despachos;

import edu.pe.pucp.team_1.dp1.sigapucp.Models.Despachos.Celda.TIPO;
import edu.pe.pucp.team_1.dp1.sigapucp.Models.Materiales.Almacen;
import edu.pe.pucp.team_1.dp1.sigapucp.Models.Materiales.Rack;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;
import org.javalite.activejdbc.Base;

/**
 *
 * @author Jauma
 */
public class TestRouting {
    
    
    public TestRouting()
    {
        if(!Base.hasConnection()) Base.open("org.postgresql.Driver", "jdbc:postgresql://200.16.7.146/sigapucp_db_admin", "sigapucp", "sigapucp");
    }
    
    public void runTest() throws FileNotFoundException, UnsupportedEncodingException
    {
        
        Almacen almacenCentral = Almacen.findFirst("es_central = ?", "T");
        int areaSize = almacenCentral.getInteger("longitud_area");
        int height = almacenCentral.getInteger("largo")/areaSize;
        int width = almacenCentral.getInteger("ancho")/areaSize;
        HashMap<Rack,Punto> relativoAlmacen = new HashMap<>();
        Celda.TIPO[][] mapa = new Celda.TIPO[height][width];
        
        for(int i = 0;i<width;i++)
        {
            for(int j = 0;j<height;j++)
            {
                mapa[j][i] = Celda.TIPO.LIBRE;
            }
        }
        
        List<Almacen> almacenes = Almacen.where("es_central = ?", "F");
        List<Rack> racksAll = Rack.findAll();
        for(Almacen almacen:almacenes)            
        {
            int relative_x = almacen.getInteger("x_relativo_central");
            int relative_y = almacen.getInteger("y_relativo_central");
            
            List<Rack> racks = racksAll.stream().filter(x -> Objects.equals(x.getInteger("almacen_id"), almacen.getInteger("almacen_id"))).collect(Collectors.toList());
            for(Rack rack:racks)
            {
                int anchorX1 = rack.getInteger("x_ancla1") + relative_x;
                int anchorY1 = rack.getInteger("y_ancla1") + relative_y;
                
                int anchorX2 = rack.getInteger("x_ancla2") + relative_x;     
                int anchorY2 = rack.getInteger("y_ancla2") + relative_y;
                
                relativoAlmacen.put(rack, new Punto(relative_x, relative_y));
                
                if(rack.getString("tipo").equals(Rack.TIPO.HORIZONTAL.name()))
                {
                    int start = Integer.min(anchorX1, anchorX2);
                    int finish = Integer.max(anchorX1, anchorX2);
                    
                    for(;start<finish;start++)
                    {
                        mapa[anchorY1][start] = TIPO.RACK;                                                
                    }                    
                }else
                {
                    int start = Integer.min(anchorY1, anchorY2);
                    int finish = Integer.max(anchorY1, anchorY2);
                    
                    for(;start<finish;start++)
                    {
                        mapa[start][anchorX1] = TIPO.RACK;                        
                    }
                }
            }                        
        }                      
        RutaDetallada rutaGenerator = new RutaDetallada();
        PrintWriter writer = new PrintWriter("rutaTest.txt", "UTF-8");
        int nrRacks = racksAll.size();        
        for(int i = 0;i<nrRacks-1;i++)
        {            
            Rack rack1 = racksAll.get(i);
            Punto rack1Relativo = relativoAlmacen.get(rack1);           
                       
            List<Punto> puntosRack1 = rack1.getAnchorPoints(height, width,rack1Relativo.x,rack1Relativo.y);                                                            
            for(int j = i+1;j<nrRacks;j++)
            {
                Rack rack2 = racksAll.get(j);
                Punto rack2Relativo = relativoAlmacen.get(rack2);
                
                List<Punto> puntosRack2 = rack2.getAnchorPoints(height, width,rack2Relativo.x,rack2Relativo.y);    
              
                
                for(Punto puntoRack1:puntosRack1)
                {
                    for(Punto puntoRack2:puntosRack2)
                    {                                               
                        
                        if(mapa[puntoRack1.y][puntoRack1.x] != TIPO.LIBRE)
                        {
                            int a =  4;
                        }
                        
                        if(mapa[puntoRack2.y][puntoRack2.x] != TIPO.LIBRE)
                        {
                            int a =  4;
                        }
                        Estado solucion = rutaGenerator.generarRutaGreedy(copiarMapa(mapa,width,height), width, height, puntoRack1, puntoRack2); 
                        Celda.TIPO[][] mapaDibujo = copiarMapa(mapa, width, height);
                        for(Punto punto:solucion.ruta)
                        {
                            mapaDibujo[punto.y][punto.x] = Celda.TIPO.VISITADA;
                        }
                        try {
                            dibujarRuta(mapaDibujo, width, height,writer);
                        } catch (Exception ex) {
                            Logger.getLogger(TestRouting.class.getName()).log(Level.SEVERE, null, ex);
                        }                                              
                    }
                }                               
            }            
        } 
        writer.close();
    }       
    
     private Celda.TIPO[][] copiarMapa(Celda.TIPO[][] mapa,int altura,int ancho)
    {
        Celda.TIPO[][] nuevoMapa = new Celda.TIPO[altura][ancho];
        
        for(int i = 0;i<ancho;i++)
        {
            for(int j = 0;j<altura;j++)
            {
                nuevoMapa[j][i] = mapa[j][i];
            }
        }
        return nuevoMapa;
    }
    
    private void dibujarRuta(Celda.TIPO[][] mapa,int altura,int ancho,PrintWriter writer) throws Exception
    {              
        for(int j = 0;j<altura;j++)
        { 
            for(int i = 0;i<ancho;i++)
            {                
                if(mapa[j][i] == Celda.TIPO.LIBRE)
                {
                    writer.print(" ");                    
                }
                
                if(mapa[j][i] == Celda.TIPO.OCUPADA||mapa[j][i] == Celda.TIPO.RACK)
                {
                    writer.print("@");                    
                }
                
                if(mapa[j][i] == Celda.TIPO.VISITADA)
                {
                    writer.print("·");                    
                }                
            }
            writer.println();                    
        }        
    }
}
