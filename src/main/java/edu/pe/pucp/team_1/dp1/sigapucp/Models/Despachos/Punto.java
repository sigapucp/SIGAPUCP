/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.pe.pucp.team_1.dp1.sigapucp.Models.Despachos;

import edu.pe.pucp.team_1.dp1.sigapucp.Models.Materiales.Rack;

/**
 *
 * @author Jauma
 */
public class Punto {
    
    public int x;
    public int y;  
    public DIRECCION dir;
    public Rack.ANCLA anchorPoint;
    
    public static DIRECCION invertirDireccion(DIRECCION gDir)
    {
        if(gDir == DIRECCION.E) return DIRECCION.O;
        if(gDir == DIRECCION.O) return DIRECCION.E;
        if(gDir == DIRECCION.N) return DIRECCION.S;
        if(gDir == DIRECCION.S) return DIRECCION.N;
        return gDir;
    }
    
    public Punto(int gX,int gY)
    {
        x = gX;
        y = gY;
    }
    
    public Punto(int gX,int gY,Rack.ANCLA gAnchor)
    {
        // Para Horizontal:
        
        //  NE                NO
        //  ********************
        //  SE                SO
        
        // Para Vertical:
        
        // NE * NO
        //    *
        //    *
        //    *
        //    *
        //    *
        //    *
        // SE * SO                
        
        x = gX;
        y = gY;
        anchorPoint = gAnchor;
    }
      
    public Boolean esValido(int height,int width)
    {
        return (x>=0&&x<width)&&(y>=0&&y<height);
    }
    
    public Punto(int gX,int gY,Punto gPunto)
    {
        x = gX;
        y = gY;      
    }
    
    public Punto(Punto gPunto)
    {
        x = gPunto.x;
        y = gPunto.y;       
        dir = gPunto.dir;
    }
    
    public Boolean isEqual(Punto p)
    {
        return (p.x == x && p.y == y);
    }
    
    public Punto mover(DIRECCION gDir)
    {             
        int moveFactorX = 0;
        int moveFactorY = 0;
        
        if(gDir == DIRECCION.N)
        {
            moveFactorY--;
        }
        
        if(gDir == DIRECCION.S)
        {
            moveFactorY++;
        }
        
        if(gDir == DIRECCION.E)
        {
            moveFactorX--;
        }
        
        if(gDir == DIRECCION.O)
        {
            moveFactorX++;
        }
        
        dir = gDir;             
        return new Punto(x+moveFactorX,y+moveFactorY);   
    }
        
    public void print()
    {
        System.out.print(x + " " + y);
    }
    
    public void println()
    {
        System.out.print(x + " " + y);
    }
    
    public enum DIRECCION
    {
        N,
        S,
        E,
        O               
    }
    
    @Override
    public String toString()
    {
        return (x + "-" + y);
    }
}
