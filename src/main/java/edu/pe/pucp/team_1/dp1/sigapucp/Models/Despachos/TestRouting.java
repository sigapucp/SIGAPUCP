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
import java.util.List;
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
        
        int height = almacenCentral.getInteger("largo");
        int width = almacenCentral.getInteger("ancho");
        
        Celda.TIPO[][] mapa = new Celda.TIPO[height][width];
        
        for(int i = 0;i<width;i++)
        {
            for(int j = 0;j<height;j++)
            {
                mapa[j][i] = Celda.TIPO.LIBRE;
            }
        }
        
        List<Almacen> almacenes = Almacen.where("es_central = ?", "F");
        
        for(Almacen almacen:almacenes)            
        {
            int relative_x = almacen.getInteger("x_relativo_central");
            int relative_y = almacen.getInteger("y_relativo_central");
            
            List<Rack> racks = Rack.where("almacen_id = ?",almacen.getId());
            for(Rack rack:racks)
            {
                int anchorX1 = rack.getInteger("x_ancla1");
                int anchorY1 = rack.getInteger("y_ancla1");
                
                int anchorX2 = rack.getInteger("x_ancla2");                
                int anchorY2 = rack.getInteger("y_ancla2");
                
                if(rack.getString("tipo").equals(Rack.TIPO.HORIZONTAL.name()))
                {
                    int start = Integer.min(anchorX1, anchorX2) + relative_x;
                    int finish = Integer.max(anchorX1, anchorX2) + relative_x;
                    
                    for(;start<finish;start++)
                    {
                        mapa[relative_y+anchorY1][start] = TIPO.RACK;                                                
                    }
                    
                }else
                {
                    int start = Integer.min(anchorY1, anchorY2) + relative_y;
                    int finish = Integer.max(anchorY1, anchorY2) + relative_y;
                    
                    for(;start<finish;start++)
                    {
                        mapa[start][anchorX1 + relative_x] = TIPO.RACK;                        
                    }
                }
            }            
        }
        
        PrintWriter writer = new PrintWriter("the-file-name.txt", "UTF-8");
        for(int j = 0;j<height;j++)
        {
            for(int i = 0;i<width;i++)
            {
                if(mapa[j][i] == TIPO.LIBRE)
                {
                    writer.print(" ");
                }
                
                if(mapa[j][i] == TIPO.RACK)
                {
                    writer.print("#");
                }
            }            
            writer.println();
        }           
        
        writer.close();
    }    
}
