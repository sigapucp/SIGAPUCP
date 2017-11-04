/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.pe.pucp.team_1.dp1.sigapucp.Models.Materiales;

import org.javalite.activejdbc.Model;
import org.javalite.activejdbc.annotations.IdName;
import org.javalite.activejdbc.annotations.Table;

/**
 *
 * @author Jauma
 */
@Table("AlmacenAreaXYs")
@IdName("almacen_xy_id")
public class AlmacenAreaXY extends Model{
    
    public enum POSICION
    {
        ADELANTE,
        ATRAS,
        IZQUIERDA,
        DERECHA        
    }
    
    public enum TIPO
    {
        RACK,
        VACIO,
        OCUPADO
    }
    
}
