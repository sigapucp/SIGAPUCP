/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.pe.pucp.team_1.dp1.sigapucp.CustomComponents;

import edu.pe.pucp.team_1.dp1.sigapucp.Models.Despachos.Estado;
import edu.pe.pucp.team_1.dp1.sigapucp.Models.Materiales.Producto;
import java.util.Random;
import javafx.scene.paint.Color;

/**
 *
 * @author Jauma
 */
public class TupleProductos {
    private ProductoSimulacion productoUno;
    private ProductoSimulacion productoDos;
    private Estado resultado;
    private Color color;    
    
    public TupleProductos(ProductoSimulacion gProd1,ProductoSimulacion gProd2)
    {
        productoUno = gProd1;
        productoDos = gProd2;
        
        Random rand = new Random();
        float r = rand.nextFloat();
        float g = rand.nextFloat();
        float b = rand.nextFloat();       
       
        color = Color.color(r, g, b);
    }
    
    public void setRuta(Estado ruta)
    {
        resultado = ruta;
    }
    
    public ProductoSimulacion getProductoUno()
    {
        return productoUno;
    }
    
    public ProductoSimulacion getProductoDos()
    {
        return productoDos;
    }
    
    public Estado getEstado()
    {
        return resultado;
    }
    
    public Boolean esPar(ProductoSimulacion producto1,ProductoSimulacion producto2)
    {
        return (producto1 == productoUno&&producto2==productoDos)||(producto1 == productoDos&&producto2 == productoUno);
    }
    
    public Color getColor()
    {
        return color;
    }
    
}
