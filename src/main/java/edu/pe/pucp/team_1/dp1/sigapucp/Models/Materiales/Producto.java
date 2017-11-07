/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.pe.pucp.team_1.dp1.sigapucp.Models.Materiales;

import org.javalite.activejdbc.Model;
import org.javalite.activejdbc.annotations.CompositePK;
import org.javalite.activejdbc.annotations.IdName;
import org.javalite.activejdbc.annotations.Table;

/**
 *
 * @author Jauma
 */
@Table("Productos")
@CompositePK({ "producto_id", "orden_entrada_id", "tipo_id","producto_cod","orden_entrada_cod","tipo_cod" })
public class Producto extends Model{
    
    public enum ESTADO
    {
        INGRESADO,        
        RESERVADO,
        DESPACHADO
    }
    
}
