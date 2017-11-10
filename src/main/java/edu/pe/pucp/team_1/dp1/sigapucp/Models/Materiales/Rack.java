/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.pe.pucp.team_1.dp1.sigapucp.Models.Materiales;

import edu.pe.pucp.team_1.dp1.sigapucp.Models.Despachos.Punto;
import java.util.ArrayList;
import java.util.List;
import org.javalite.activejdbc.Model;
import org.javalite.activejdbc.annotations.BelongsTo;
import org.javalite.activejdbc.annotations.IdName;
import org.javalite.activejdbc.annotations.Table;

/**
 *
 * @author Jauma
 */
@Table("Racks")
@IdName("rack_id")
@BelongsTo(parent = Almacen.class, foreignKeyName="almacen_id")
public class Rack extends Model{
    static {
        dateFormat("dd/MM/yyyy", "last_date_change");
    }
    
    public void asignarAtributosRack(String rackCod,
            int rackLongitud,
            char rackEsUniforme,
            int rackXAncla1,
            int rackYAncla1,
            int rackXAncla2,
            int rackYAncla2,
            String rackEstado)
    {
        set("rack_cod", rackCod);
        set("longitud", rackLongitud);
        set("es_uniforme", rackEsUniforme);
        set("x_ancla1", rackXAncla1);
        set("y_ancla1", rackYAncla1);
        set("x_ancla2", rackXAncla2);
        set("y_ancla2", rackYAncla2);
        set("estado", rackEstado);
        
        if(rackXAncla1==rackXAncla2) {
            set("tipo",TIPO.VERTICAL.name());
        } else {
            set("tipo",TIPO.HORIZONTAL.name());
        }
    }
    
    public String generarCodigoRack(String almacenCod) {
        String cod = almacenCod;
        int rackX1 = Integer.valueOf(String.valueOf(get("x_ancla1")));
        int rackX2 = Integer.valueOf(String.valueOf(get("x_ancla2")));
        int rackY1 = Integer.valueOf(String.valueOf(get("y_ancla1")));
        int rackY2 = Integer.valueOf(String.valueOf(get("y_ancla2")));
        
        cod = cod.concat(String.format("-X%dY%d-X%dY%d", rackX1, rackY1, rackX2, rackY2));
        return cod;
    }
    
    public List<Punto> getAnchorPoints(int height,int width,int relative_x,int relative_y)
    {
        int x_ancla1 = getInteger("x_ancla1") + relative_x;
        int x_ancla2 = getInteger("x_ancla2") + relative_x;

        int y_ancla1 = getInteger("y_ancla1") + relative_y;
        int y_ancla2 = getInteger("y_ancla2") + relative_y;

       
        List<Punto> puntos = new ArrayList<>();

        if(getString("tipo").equals(Rack.TIPO.HORIZONTAL.name()))
        {
            puntos.add(new Punto(x_ancla1, y_ancla1-1,ANCLA.NE));
            puntos.add(new Punto(x_ancla1, y_ancla1+1,ANCLA.SE));

            puntos.add(new Punto(x_ancla2, y_ancla2-1,ANCLA.NO));
            puntos.add(new Punto(x_ancla2, y_ancla2+1,ANCLA.SO));

        }else
        {
            puntos.add(new Punto(x_ancla1+1, y_ancla1,ANCLA.NE));
            puntos.add(new Punto(x_ancla1-1, y_ancla1,ANCLA.NO));

            puntos.add(new Punto(x_ancla2+1, y_ancla2,ANCLA.SE));
            puntos.add(new Punto(x_ancla2-1, y_ancla2,ANCLA.SO));                
        }      
        
        puntos.removeIf(x->!x.esValido(height, width));           
         
        return puntos;
    }
    
    public Punto getAncla(int height,int width,ANCLA tipo,int relative_x,int relative_y)
    {
        int x_ancla1 = getInteger("x_ancla1") + relative_x;
        int x_ancla2 = getInteger("x_ancla2") + relative_x;

        int y_ancla1 = getInteger("y_ancla1") + relative_y;
        int y_ancla2 = getInteger("y_ancla2") + relative_y;
        
        Punto punto = null;
        if(getString("tipo").equals(Rack.TIPO.HORIZONTAL.name()))
        {
            if(tipo == ANCLA.NE) punto = new Punto(x_ancla1, y_ancla1-1,tipo);            
            if(tipo == ANCLA.SE) punto = new Punto(x_ancla1, y_ancla1+1,tipo);
            
            if(tipo == ANCLA.NO) punto = new Punto(x_ancla2, y_ancla2-1,tipo);
            if(tipo == ANCLA.SO) punto = new Punto(x_ancla2, y_ancla2+1,tipo);

        }else
        {
            if(tipo == ANCLA.NE) punto = new Punto(x_ancla1-1, y_ancla1,tipo);
            if(tipo == ANCLA.NO) punto = new Punto(x_ancla1+1, y_ancla1,tipo);
            
            if(tipo == ANCLA.SE) punto = new Punto(x_ancla2-1, y_ancla2,tipo);
            if(tipo == ANCLA.SO) punto = new Punto(x_ancla2+1, y_ancla2,tipo);                
        }      
        if(punto != null && !punto.esValido(height, width)) punto = null;
        return punto;               
    }
    
    public enum TIPO
    {
        VERTICAL,
        HORIZONTAL
    }
    
    public enum ANCLA
    {
        NE,
        NO,
        SE,
        SO
    }
}
