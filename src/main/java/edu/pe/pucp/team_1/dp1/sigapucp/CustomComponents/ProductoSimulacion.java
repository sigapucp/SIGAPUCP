/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.pe.pucp.team_1.dp1.sigapucp.CustomComponents;

import edu.pe.pucp.team_1.dp1.sigapucp.Models.Despachos.Punto;
import edu.pe.pucp.team_1.dp1.sigapucp.Models.Materiales.Almacen;
import edu.pe.pucp.team_1.dp1.sigapucp.Models.Materiales.AlmacenAreaXY;
import edu.pe.pucp.team_1.dp1.sigapucp.Models.Materiales.AlmacenAreaZ;
import edu.pe.pucp.team_1.dp1.sigapucp.Models.Materiales.Producto;
import java.util.Random;
import javafx.scene.paint.Color;

/**
 *
 * @author Jauma
 */
public class ProductoSimulacion {
    
    private Producto producto;
    private Punto punto;
    private AlmacenAreaXY areaXY;
    private AlmacenAreaZ areaZ;
    private Boolean isDepot;
    private Color color;
    
    public ProductoSimulacion(Producto gProducto)
    {
        AlmacenAreaXY xy = AlmacenAreaXY.findById(gProducto.get("almacen_xy_id"));
        AlmacenAreaZ z = AlmacenAreaZ.findFirst("almacen_z_id = ?",gProducto.get("almacen_z_id"));
        Almacen almacen = Almacen.findById(gProducto.get("almacen_id"));
        
        int x_relativo = almacen.getInteger("x_relativo_central");
        int y_relativo = almacen.getInteger("y_relativo_central");
        
        int x = xy.getInteger("x") + x_relativo; 
        int y = xy.getInteger("y") + y_relativo;
        
        int xfactor = 0;
        int yfactor = 0;
        
        xfactor = (gProducto.getString("tipo_posicion").equals(AlmacenAreaXY.POSICION.IZQUIERDA.name())) ? -1 : 0;
        if(xfactor == 0) xfactor = (gProducto.getString("tipo_posicion").equals(AlmacenAreaXY.POSICION.DERECHA.name())) ? 1 : 0;
                
        yfactor = (gProducto.getString("tipo_posicion").equals(AlmacenAreaXY.POSICION.ATRAS.name())) ? -1 : 0;
        if(yfactor == 0) yfactor = (gProducto.getString("tipo_posicion").equals(AlmacenAreaXY.POSICION.ADELANTE.name())) ? 1 : 0;
        
        punto =  new Punto(x+xfactor,y+yfactor );           
        areaXY = xy;
        areaZ = z;
        producto = gProducto;        
        isDepot = false;
        
        Random rand = new Random();
        float r = rand.nextFloat();
        float g = rand.nextFloat();
        float b = rand.nextFloat();       
       
        color = Color.color(r, g, b);
    }
    
    public ProductoSimulacion()
    {
        isDepot = true;
        punto = new Punto(0,0);
        color = Color.BLACK;
    }
    
    public ProductoSimulacion(Punto acopio)
    {
        isDepot = true;
        punto = acopio;
        color = Color.BLACK;
    }
    
    public void setPunto(Punto p)
    {
        punto = p;
    }
    public Producto getProducto()
    {
        return producto;
    }
    
    public Punto getPunto()
    {
        return punto;
    }
    
    public AlmacenAreaXY getAreaXY()
    {
        return areaXY;
    }
    
    public AlmacenAreaZ getAreaZ()
    {
        return areaZ;
    }
    
    public Boolean isDepot()
    {
        return isDepot;
    }
    
    public Color getColor()
    {
        return color;
    }
}
