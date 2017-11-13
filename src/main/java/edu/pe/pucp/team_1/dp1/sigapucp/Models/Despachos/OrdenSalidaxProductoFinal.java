/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.pe.pucp.team_1.dp1.sigapucp.Models.Despachos;

import org.javalite.activejdbc.Model;
import org.javalite.activejdbc.annotations.CompositePK;
import org.javalite.activejdbc.annotations.Table;

/**
 *
 * @author Jauma
 */
@Table("OrdenesSalidaxProductosFinal")
@CompositePK({ "producto_cod", "producto_id", "tipo_cod","tipo_id","orden_entrada_cod","orden_entrada_id","salida_cod","salida_id" })
public class OrdenSalidaxProductoFinal extends Model{
    public enum ESTADO
    {        
        RESERVADO,
        DESPACHADO
    }
}
